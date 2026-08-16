# TopProducers — improvement backlog

Review of the top-producers ranking after its integration into `moskito-core`.
Created 2026-08-16 on branch `develop`. Nothing here is implemented yet — this is a
decision document, to be worked through item by item.

Code under review:

- `moskito-core/src/main/java/net/anotheria/moskito/core/topproducers/` —
  `TopProducersRepository`, `ProducerTemporaryEntry`, `ProducerEntry`, `ProducerEntryValue`, `Category`
- `moskito-core/src/main/java/net/anotheria/moskito/core/config/producers/TopProducersConfig.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/topproducers/api/TopProducersAPIImpl.java`

---

## How scoring works today (baseline for the discussion)

Every tick of the configured interval (`TopProducersConfig.intervalName`, default `1m`),
`TopProducersRepository.intervalUpdated()` ranks all producers per `Category` and gives each
producer a score equal to **the number of producers it beat in that interval**. Those per-interval
scores are summed for the lifetime of the JVM; `getTopProducers()` sorts by that sum.

The score is purely **ordinal** — raw values (requests, ns, errors) decide the order and are then
discarded. A producer with 1.000.000 requests scores exactly +1 over one with 10.000 requests.

Per-interval score formula, in practice:

- `score = (number of producers with a strictly smaller value) + 1` for any producer with value > 0
  (the `+1` comes from the sentinel node, see item 4)
- `score = 0` for a producer whose value is 0 — it ties into the sentinel's `same` list

---

## Decisions so far

| # | Item | Decision |
|---|------|----------|
| 6 | Exponential decay of the score | **Agreed** — Leon: "auto-decay is a very good idea" |
| 8 | Show real values, not only ranks | **Agreed, and stronger than originally framed** — see below |
| 7 | Minimum-volume floor for `ERROR_RATE` | **Rejected** — see below |
| 3.3 | Magnitude-aware score in addition to rank | **Proposed** (Leon) — recommendation: normalize by sum, not max; keep both scores |

**Item 7 rejected, with a concrete counter-example.** The original proposal was to make a producer
ineligible for the `ERROR_RATE` ranking below some minimum request count per interval, on the theory
that "1 request, 1 error = 100%" is noise. It is not. The `SQLQueries` producer surfaced two queries
running *once per minute* at a 100% error rate — `SELECT count(id) FROM …` against a column that did
not exist. Low volume plus high error rate is exactly the signal that finds broken-but-rarely-executed
code, and a floor would have hidden it. **Do not add a volume floor.** The real answer to
"is this worth my attention?" is item 8: show the absolute numbers and let the human judge.

**Item 8 is a first-class requirement, not polish.** Ordinal scores hide magnitude, and magnitude is
often the whole point: 1.000.000 requests vs 10.000 requests is worth investigating even though the
two producers sit one score point apart. At minimum the raw per-interval values must be carried
through to the UI and MCP output for `REQUESTS` and `TOTAL_TIME`. Open question for later: whether the
*score itself* should become magnitude-aware (e.g. log-scaled buckets instead of pure rank) — not
decided, and not required for the display fix.

---

## 1. Defects

### 1.1 An exception in `intervalUpdated()` silently kills the rest of the interval

`IntervalImpl.notifyListeners()` (`IntervalImpl.java:114-118`) has no try/catch, and
`TopProducersRepository` is registered as a **secondary** interval listener alongside
`SnapshotRepository`, `TieableRepository` (accumulators + thresholds) and `IntervalStatsLogger`.
If `intervalUpdated()` throws, every listener registered after it loses that tick.

Two live throw sites:

- `addScore()` → `producerRegistryAPI.getProducer(producerId)` (`TopProducersRepository.java:145`)
  throws `NoSuchProducerException` — unchecked, `ProducerRegistryAPIException extends RuntimeException`
  — if the producer was unregistered between `getAllProducers()` (line 96) and the scoring walk
  (line 121). `Storage` and anything else calling `unregisterProducer` can do exactly that.
- `Category.extractValue()` (`Category.java:34`, `Category.java:61`) → `NumberFormatException` from a
  custom `RequestOrientedStats` implementation returning something unparseable.

**Fix:** try/catch around the per-producer body and around each entry in the scoring walk; log and
continue. **Still open.**

**Done 2026-08-16 — `IntervalImpl.notifyListeners()` hardened.** Each listener call is now wrapped in
try/catch, failures are logged and the remaining listeners are notified regardless. Catches `Throwable`,
not `Exception`, deliberately: `UpdateTriggerServiceImpl` drives updates from a `java.util.Timer`
(`UpdateTriggerServiceImpl.java:56`) and `UpdateableWrapper.run()` only catches `Exception`
(`UpdateableWrapper.java:70`) — so an `Error` from any listener (e.g. the `StackOverflowError` that
§2.1 describes) would kill the `MoskitoIntervalUpdater` thread and stop *all* interval updates for the
lifetime of the jvm. Since `update()` does nothing but set a timestamp and call `notifyListeners()`
twice, nothing can escape `update()` any more. Covered by two new tests in `IntervalImplTest`.

This is defense in depth, not a substitute for the per-producer try/catch above: it stops a throwing
`TopProducersRepository` from taking down thresholds, accumulators and snapshots, but the ranking
itself would still lose the whole tick.

### 1.2 `getTopProducers()` can throw `IllegalArgumentException: Comparison method violates its general contract!`

The comparator (`TopProducersRepository.java:170-171`) reads `cumulatedScore` **live** while the
interval thread mutates it in `ProducerEntryValue.addScore()` — plain `long`, no synchronization,
no happens-before. TimSort detects the inconsistent ordering and throws, once the merge path kicks in
at >32 elements, i.e. precisely the installations this feature is for.

**Fix:** snapshot `(entry, score)` pairs into a local list first, sort that. Also makes the returned
data consistent with the ordering it was sorted by.

**Done 2026-08-16.** Three changes:

- `getTopProducers()` reads each score exactly once into a local `ScoredEntry` record and sorts the
  copy, so the sort keys cannot move mid-sort.
- Equal scores now break ties by producer id, so repeated calls return a stable order instead of
  `ConcurrentHashMap` iteration order. Relevant today because every idle producer sits at score 0
  (§1.3), which makes the tail of the list jump around between ui refreshes.
- The fields of `ProducerEntryValue` are volatile now, so readers see fresh values rather than
  arbitrarily stale ones. The read-modify-writes in `addScore` stay unsynchronized on purpose: there
  is exactly one writer, since all intervals are updated from the single `java.util.Timer` thread of
  `UpdateTriggerServiceImpl`, and `IntervalRegistry.forceUpdateIntervalForTestingPurposes()` is
  test-only. Documented on the class - a second writer would require synchronization.

**Also done, a slice of §4.1** (needed, the fix was untestable otherwise): `TopProducersRepository`
got a package-private constructor taking `(TopProducersConfig, IProducerRegistryAPI)` that does *not*
attach itself to an interval. The singleton constructor delegates to it and registers afterwards, so
production behaviour is unchanged. New `TopProducersRepositoryTest` uses it with a fake registry api
and covers descending order, the stable tie-break, the exact per-interval scores (4/2/2/1 for
300/200/200/100, which pins the current semantics before §2.1 rewrites them) and the limit.

An end-to-end variant against the global registry was tried first and rejected: `FindThresholdTest`,
`SnapshotRepositoryTest` and `OnDemandStatsProducerListenerTest` call `ProducerRegistryFactory.reset()`,
which leaves a cached `IProducerRegistryAPI` pointing at a detached registry, so the ranking came back
empty depending on test order. Worth knowing for any further test in this area.

Not covered by a test: the TimSort crash itself, which is a race and cannot be reproduced
deterministically without driving concurrent updates through global state.

### 1.3 `producerEntries` only ever grows

`TopProducersRepository.producerEntries` is never pruned:

- producers that have been unregistered keep their entry for the JVM's lifetime
- perpetually idle producers still get an entry, because a zero value ties into the sentinel and
  still calls `addScore(c, 0)`, which creates the `ProducerEntry` and makes `getValue(category)`
  non-null — so they also show up in the result list padded at the bottom

With high-cardinality producers (URL-based filters, `Storage`) this grows without bound.

**Fix:** skip zero-value producers entirely, and drop entries whose id is no longer in the registry
during the interval walk.

**Done 2026-08-16.** Two changes in `intervalUpdated()`:

- A value of `0` is no longer inserted into the interval ranking, so an idle producer is not ranked
  and never gets an entry. A producer is now only ranked in a category it actually had a value in,
  which is what the javadoc of `getTopProducers()` claimed all along.
- `producerEntries.keySet().retainAll(registeredProducerIds)` drops the entries of producers that left
  the registry. The retain set is collected for **all** producers, not only the rankable ones, so that
  a producer which is registered but momentarily not rankable (empty stats, for example) keeps the
  history it accumulated. Only a producer that is really gone loses its entry.

The `producers.isEmpty()` early return is load-bearing now, it prevents a momentarily empty registry
from wiping every entry — see §4.4.

**Two behaviour changes follow from this**, both improvements, but worth knowing:

- `averageScore` is now the average over the intervals a producer was *active* in, not over every
  interval since it was first seen. Previously a bursty producer was dragged towards zero by each idle
  interval.
- `bottomScore` is no longer permanently `0` for every producer that was ever idle for a single
  interval, which had made the value useless.

Covered by `testIdleProducersAreNotRanked` and `testProducersLeavingTheRegistryAreDropped`, both
verified to fail against the previous behaviour.

---

## 2. Simplification

### 2.1 Delete `ProducerTemporaryEntry`, sort instead

130 lines of hand-rolled descending linked list — root-swapping, a `same` list for ties, a sentinel
node, and a `producerIds.contains(...)` filter to undo the sentinel — all to compute "how many
producers did I beat".

Beyond the complexity, two properties are actively bad:

- insertion is **O(n²)** overall, ×5 categories, every interval
- `insert()`/`insertNotRoot()` recurse **one stack frame per list position**
  (`ProducerTemporaryEntry.java:48`), so inserting a low value into a several-thousand-producer chain
  recurses that deep — a `StackOverflowError` waiting to happen in exactly the large deployments
  where the ranking matters

**Fix:** sort a list of `(producerId, value)` descending and walk it once, assigning ranks with tie
handling. ~15 lines, O(n log n), no recursion, no sentinel, no filter. `ProducerTemporaryEntry` and
`ProducerTemporaryEntryTest` both get deleted.

### 2.2 Drop the sentinel and its silent `+1`

The sentinel is a fake node carrying the category name as a producer id (`TopProducersRepository.java:92-93`).
It has to be filtered back out by id, it would collide with a real producer literally named `REQUESTS`,
and it adds an undocumented `+1` to every non-zero score. It disappears along with item 2.1.

### 2.3 `stats.get(0)` is an unwritten contract

`TopProducersRepository.java:113` assumes the first stats object is the producer-level aggregate.
That holds for `OnDemandStatsProducer` only because its constructor creates `"cumulated"` first
(`OnDemandStatsProducer.java:159`). Any hand-written `IStatsProducer` exposing per-method
`RequestOrientedStats` gets ranked by whichever stat happens to sit at index 0 — silently measuring
one method instead of the producer.

**Fix:** look up `OnDemandStatsProducer.CUMULATED_STATS_NAME` by name, fall back to summing across all
stats objects.

---

## 3. Scoring model

### 3.1 Exponential decay instead of an ever-growing sum — **agreed**

`cumulatedScore` is age-biased and is the default sort key. A producer registered at boot collects
score every minute forever; one that appears an hour later can never catch up, even if it is currently
the heaviest thing running. A producer that was terrible last week and got fixed on Monday keeps its
crown.

**Proposal:** per interval, `cumulated = cumulated * α + score`, with α ≈ 0.95 at a 1m interval
(≈ 20 minute half-life), α configurable via `TopProducersConfig`. O(1), no extra state, self-bounding,
recency-weighted. Keep the raw lifetime sum alongside it for anyone who wants it.

Zero-effort interim: sort by `averageScore`, which `ProducerEntryValue` already computes and which is
at least age-neutral.

### 3.2 Carry the raw values through to the output — **agreed, high priority**

`TopProducerAO` currently carries six score numbers and zero context: you learn that a producer ranks
first, not that it burned 40 seconds or served 1.000.000 requests. Required at least for `REQUESTS`
and `TOTAL_TIME`; the `SQLQueries` case argues for `ERRORS`/`ERROR_RATE` too (an error rate is only
interpretable next to its request count).

Needs: last raw value per category kept in `ProducerEntryValue`, exposed on `ProducerEntry`, mapped in
`TopProducersAPIImpl.map()`, rendered in `TopProducers.jsp` and in the MCP tool output.

See §3.3 for the related question of making the score itself magnitude-aware.

### 3.3 Magnitude-aware score: share of total, kept *alongside* the ordinal score

Raised by Leon 2026-08-16: consider the value as a percentage of the category's max, not only the
list position. Right idea; the recommendation is to normalize by the **sum** rather than the max.

Worked example, `REQUESTS` in one interval:

| Producer | Raw value | Ordinal (today) | % of max | % of total | log-scaled |
|---|---:|---:|---:|---:|---:|
| A | 1.000.000 | 5 | 100 | 82,6 | 100 |
| B | 200.000 | 4 | 20 | 16,5 | 88 |
| C | 10.000 | 3 | 1,0 | 0,83 | 67 |
| D | 500 | 2 | 0,05 | 0,04 | 45 |
| E | 1 | 1 | 0,0001 | 0,00008 | 5 |

**Why sum, not max:**

- The max is the least robust statistic in the sample. Normalizing by it lets a single spike — batch
  job, cache warmup, crawler — drive every other producer's score to ~0 for that interval. The one
  real virtue of the ordinal score is that it uses only order statistics and is therefore immune to
  outliers; max-normalization replaces that with maximum outlier sensitivity.
- Share-of-total has a **constant-sum invariant**: every interval distributes exactly 100 points,
  busy or idle. Combined with decay (§3.1) the accumulated score converges to `share × 1/(1-α)`, so
  the steady-state score is directly proportional to the long-run share of load — bounded and
  interpretable. Under share-of-max the top producer scores 100 every interval whether it is 99% or
  20% of the system, i.e. the number carries no information about actual dominance.
- It answers the real question: "this producer is 82% of all time spent" bounds what fixing it buys
  you (Amdahl). "40% as heavy as the heaviest" is not actionable.

**The tail collapse is intended.** Both linear schemes flatten the small producers to ~0; only
log-scaling keeps the tail rankable. Take linear anyway: the tail is covered by the other two views —
the ordinal score ranks producers that are all small, and `ERROR_RATE` surfaces the once-a-minute
broken query (see §3.4). If A is 82% of traffic, C at 0,8% genuinely does not matter *for volume*.
Log-scaling remains the fallback if a single blended number is ever wanted, but its unit is
meaningless and it is hard to explain in the UI.

**Add, do not replace.** Ordinal and share answer different questions — "how consistently is this in
the mix" vs. "how much of the system is this". Both are cheap to keep.

**Not every category is additive.** Share-of-total is only meaningful for extensive quantities:

- additive — `REQUESTS`, `TOTAL_TIME`, `ERRORS` → share-of-total
- intensive — `ERROR_RATE` is a ratio (summing rates across producers is meaningless) and is already
  a 0–100 percentage, so use the raw value directly, no normalization; `MAX_CONCURRENT_REQUEST` is
  itself a max → share-of-max, or ordinal only

This fits the existing shape: `Category` is already an enum whose constants override behaviour per
value (`ERROR_RATE` overrides `extractValue`). Add `normalize(value, sum, max)` next to it, so the
normalization policy lives with the metric definition rather than in the repository.

Keep `long` arithmetic and scale to basis points (`share × 10.000`) rather than switching to double —
consistent with how `ERROR_RATE` already multiplies by 100, and it avoids float drift once decay
multiplies the accumulator every interval.

**Open API question, decide early:** `getTopProducers(category, limit)` has one implicit sort key.
Two scores mean it needs a `sortBy` parameter or a configured default, propagating to
`TopProducersAPI`, the MCP tool schema and `TopProducers.jsp`. Awkward to retrofit later.

### 3.4 ~~Minimum-volume floor for `ERROR_RATE`~~ — **rejected**

See "Decisions so far". Low-volume, high-error-rate producers are a wanted signal, not noise.

---

## 4. API and testability

### 4.1 The repository is untestable

Private constructor, global `MoskitoConfigurationHolder`, global `IntervalRegistry`, singleton.
`ProducerTemporaryEntryTest` covers the data structure; **nothing covers the scoring rules** — ties,
zero values, `builtin` filtering, sentinel exclusion, accumulation across intervals.

**Fix:** package-private constructor taking `(TopProducersConfig, IProducerRegistryAPI)` plus a
package-private `rank(List<IStatsProducer>)`, so scoring can be tested without a running interval.
This is the item to do first among the non-defects — it makes 2.1 and 3.1 safe to attempt.

**Partially done 2026-08-16 while fixing §1.2** — the injecting constructor exists and
`TopProducersRepositoryTest` drives the ranking through it. Still open: extracting `rank(...)` so the
scoring can be exercised without going through `intervalUpdated`, and covering the remaining rules
(zero values, `builtin` filtering, accumulation across several intervals, the other categories).

### 4.2 Return a snapshot, not live objects

`getTopProducers()` hands webui live `ProducerEntry` instances that the interval thread keeps writing
to, plus `getValues()`/`setValues()` expose the internal map. A record fits the Java 17 baseline for
5.0 and removes both problems.

### 4.3 `enabled` does not disable anything

`StartBuiltInProducers.java:58` checks `isEnabled()` and skips `getInstance()` — but
`TopProducersAPIImpl.java:24` calls `getInstance()` unconditionally, which constructs the repository
and registers the interval listener. So `enabled=false` actually means "start lazily on first UI
access". The javadoc on `TopProducersConfig.enabled` says as much; the field name promises otherwise.

**Fix:** either honor it in the API layer (return empty), or rename it to `autostart`.

### 4.4 Minor

- ~~`stats.size() == 0` → `isEmpty()`~~ — done 2026-08-16 alongside §1.3
- ~~the `producers.isEmpty()` early return is redundant, the loop handles it~~ — **withdrawn**, since
  §1.3 it guards the entry cleanup: an empty registry would otherwise drop every accumulated entry
- `Category` (ranking dimension) vs. producer category (string) is a confusing name collision —
  `RankingCategory` would be clearer, but it is public API now

---

## Suggested order

1. **1.1 + 1.2 + 1.3** — the three defects, small and independent
2. **4.1** — make scoring testable, write the missing scoring tests against current behaviour
3. **2.1 + 2.2** — replace the linked list with a sort, tests from step 2 keep it honest
4. **3.2** — raw values through to UI/MCP
5. **3.1** — decay, plus config
6. **2.3, 4.2, 4.3, 4.4** — cleanup
