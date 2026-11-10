# MoSKito Change Log / Release notes

_Please note that there is a [previous version of the ChangeLog here](https://confluence.opensource.anotheria.net/display/MSK/Change+Log)_

## 5.0.2 - 2026-10-25

**Bug fixes:**
* Per-interval values of levels no longer reset to 0 on every interval rollover. `currentRequests` could turn negative in an interval when a request started in the previous interval and finished in the current one, and `maxCurrentRequests` under-reported requests still running from the previous interval. Introduces the new value type `StatValueTypes.LEVEL`; `StatValueTypes.COUNTER` is deprecated and identical to it. Thanks to @dajiaohuang. See https://github.com/anotheria/moskito/issues/328 and https://github.com/anotheria/moskito/pull/326
* Session min/max now start each interval at the current session count instead of showing constant defaults in intervals without session activity. The session count is now included in the session stats values. See https://github.com/anotheria/moskito/issues/330 and https://github.com/anotheria/moskito/pull/331
* Memory and memory pool values no longer drop to 0 briefly after an interval rollover (often seen under load), `MemoryStats` min is no longer always 0, a memory spike is no longer carried over into the next interval's max, and memory pool min values are no longer capped at 2 GB. See https://github.com/anotheria/moskito/issues/329 and https://github.com/anotheria/moskito/pull/331
* `moskito-webui` declares the JSTL api next to the impl, so wars depending on it no longer fail JSP compilation on containers that don't supply JSTL (e.g. Tomcat 10).

**Dependency updates:**
* Parent 4.6, which manages logback-classic and logback-core together (1.6.5) and slf4j-api 2.0.19; MoSKito's own logback-classic override is removed. See https://github.com/anotheria/moskito/pull/333
* ano-util 4.0.10: jackson-databind is no longer pulled in transitively. MoSKito itself doesn't need it; 4.0.10 fixes the `NoClassDefFoundError` of 4.0.9 in the mail notification templates.
* Minor/patch upgrades: AspectJ 1.9.26, ano-plass 4.1.1, commons-codec 1.23.0, commons-lang3 3.21.0, mockito 5.24.0, jstl-api 3.0.2, mongodb-driver-sync 5.13.0, rabbitmq amqp-client 5.37.0, maven-compiler-plugin 3.16.0. See https://github.com/anotheria/moskito/pull/334 and https://github.com/anotheria/moskito/pull/327

## 5.0.1 - 2026-08-26
* TopProducers ranking, previously a standalone project, integrated into the core product (engine in `moskito-core`, API and HTTP MCP in `moskito-webui`), with share score, score explanations, producer links and a taglib.
* Interval listeners are isolated from each other: a failing listener no longer stops the update of the others.
* Removed the deprecated `RequestURIFilter`.
* Removed the ano-net dependency, DistributeMe and other dependency upgrades.
* Renamed `getTresholdConfig` to `getThresholdConfig`. See https://github.com/anotheria/moskito/pull/318

## 5.0.0 - 2026-07-13 (Java 17 baseline)

**Breaking changes:**
* Minimum Java version raised from 11 to 17. See https://github.com/anotheria/moskito/pull/307
* Removed the legacy `moskito-springboot` (Spring Boot 1) integration module; use `moskito-springboot2` instead (supports Spring Boot 2/3/4). See https://github.com/anotheria/moskito/pull/305
* Removed the `moskito-ehcache` integration module (it targeted end-of-life Ehcache 2.x). See https://github.com/anotheria/moskito/pull/314
* Moved `RollingOnDemandStatsProducer` from the `moskito-additional-producers` artifact (package `net.anotheria.moskito.extensions.producers`) into `moskito-core` (package `net.anotheria.moskito.core.dynamic`); the `moskito-additional-producers` module has been removed. See https://github.com/anotheria/moskito/pull/315
* `moskito-webui`: replaced `org.json` with gson. The public methods `MultilineChartAO.getAccumulatorsColorsDataJSON()`, `AccumulatorUtility.accumulatorsColorsToJSON()` and `AccumulatedSingleGraphAO.mapColorDataToJSON()` now return gson `JsonArray`/`JsonObject` instead of `org.json.JSONArray`/`JSONObject`. See https://github.com/anotheria/moskito/pull/306

**Security / dependency updates:**
* Spring Boot 3.3.11 → 3.5.16 and Spring Framework / spring-web 6.0.23 → 6.2.19, fixing multiple CVEs (including the June 2026 16-CVE Spring release). See https://github.com/anotheria/moskito/pull/296 and https://github.com/anotheria/moskito/pull/310
* Jersey 3.1.3 → 3.1.12, fixing CVE-2025-12383. See https://github.com/anotheria/moskito/pull/309
* MongoDB: migrated from the legacy `mongodb-driver` 3.x to `mongodb-driver-sync` 5.9.0. See https://github.com/anotheria/moskito/pull/312
* Numerous minor/patch dependency upgrades (logback, commons-lang3, mockito, log4j, gson, rabbitmq, jakarta APIs, …). See https://github.com/anotheria/moskito/pull/297 and https://github.com/anotheria/moskito/pull/313

**Build / tooling:**
* Enabled Dependabot weekly Maven version-update PRs, with ignore rules for the majors MoSKito deliberately holds (Spring 4 / Framework 7, and Jakarta EE 11 / Jersey 4 — security fixes are taken within the current major line). See https://github.com/anotheria/moskito/pull/296 and https://github.com/anotheria/moskito/pull/308

## 4.0.9 - 2026-06-27
* Async support added to all non-deprecated servlet filters, so downstream async endpoints (e.g. SSE) can call request.startAsync() without an IllegalStateException. See https://github.com/anotheria/moskito/pull/294
* Migrated the test suite from JUnit 4 to JUnit 5; parent upgraded to 4.4. See https://github.com/anotheria/moskito/pull/295
* **Important:** This is the release required for MCP support on MoSKito covered paths. 

## 4.0.8 - 2026-05-30
* Update dependency to ano-tags to 4.0.1 in order to update transitive dependency to bean-utils to 1.11.0


## 4.0.7 - 2026-05-30
* Multiple dependency upgrades to fix various github dependency alerts.

## 4.0.6 - 2025-12-26
* support for AutoConfiguration in SpringBoot3 (via springboot2 package, added org.springframework.boot.autoconfigure.AutoConfiguration.imports).
* Small improvements/changes in logging.
* SECURITY_AND_CODE_QUALITY_ANALYSIS - Resource Leak - Process Not Destroyed

## 4.0.5 - FAILED
* Release failed due to errors in pom, ignore.

## 4.0.4 - 2025-04-22
* https://github.com/anotheria/moskito/issues/292

## 4.0.3 - 2024-07-19
* Reactivated spring-boot support and updated to latest springboot version (3.3.2)
* See https://github.com/anotheria/moskito-demo/tree/master/recipes/spring-boot for example on integration

## 4.0.2 - 2024-04-29
* https://github.com/anotheria/moskito/issues/284
* https://github.com/anotheria/moskito/issues/283
* https://github.com/anotheria/moskito/issues/285
* https://github.com/anotheria/moskito/issues/286
* https://github.com/anotheria/moskito/issues/287


## 4.0.1 - 2024-03-04
* Update DistributeMe dependency to remove any dependencies to Java8 libraries.

## 4.0.0 - 2023-12-24 Migration to Java 21

## 3.2.8 - 2023-12-20

* [#272](https://github.com/anotheria/moskito/issues/272) JourneyStarterFilter always overrides
* [#273](https://github.com/anotheria/moskito/issues/273) Single tracing binoculars appear even if the producer is not traceable
* [#275](https://github.com/anotheria/moskito/issues/275) Entity Collection Size Monitoring.



## 3.2.7 - 2023-10-22
* [#271](https://github.com/anotheria/moskito/issues/271) Impossible to update Thresholds/Accumulators in a remote instance with http libraries due to compile time dependency to HttpServletRequest