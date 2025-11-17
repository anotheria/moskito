# Files with TODO in Class Comments

**Generated:** 2025-11-16
**Total Files:** 100+

This document lists all Java files in the MoSKito project that have TODO comments in their class-level JavaDoc documentation, indicating missing or incomplete class documentation.

---

## Summary Statistics

| Category | Count |
|----------|-------|
| **Production Code** | 42 |
| **Test Code** | 58 |
| **Total** | 100 |

---

## 1. Production Code Files (42)

### moskito-core (16 files)

#### Core Tracing
- `moskito-core/src/main/java/net/anotheria/moskito/core/tracer/Tracer.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/tracer/Tracers.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/tracer/TraceSortType.java`

#### Snapshots
- `moskito-core/src/main/java/net/anotheria/moskito/core/snapshot/StatSnapshot.java`

#### Context & Measurements
- `moskito-core/src/main/java/net/anotheria/moskito/core/context/CurrentMeasurement.java`

#### Entry Points
- `moskito-core/src/main/java/net/anotheria/moskito/core/entrypoint/ActiveMeasurement.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/entrypoint/EntryPointRepository.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/entrypoint/EntryPoint.java`

#### Error Handling
- `moskito-core/src/main/java/net/anotheria/moskito/core/errorhandling/CaughtError.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/errorhandling/ErrorCatcherFactory.java`

#### Thresholds
- `moskito-core/src/main/java/net/anotheria/moskito/core/threshold/CustomThresholdStatus.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/threshold/alerts/notificationprovider/SyserrNotificationProvider.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/threshold/alerts/notificationprovider/LogFileNotificationProvider.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/threshold/alerts/notificationprovider/SysoutNotificationProvider.java`

#### Statistics & Logging
- `moskito-core/src/main/java/net/anotheria/moskito/core/util/statistics/StatisticStats.java`
- `moskito-core/src/main/java/net/anotheria/moskito/core/logging/CallLogger.java` *(note: "TODO document me !ine")*
- `moskito-core/src/main/java/net/anotheria/moskito/core/logging/SLF4JCallLogger.java`

---

### moskito-web (3 files)

- `moskito-web/src/main/java/net/anotheria/moskito/web/filters/caseextractor/AbstractFilterCaseExtractor.java`
- `moskito-web/src/main/java/net/anotheria/moskito/web/filters/caseextractor/FilterCaseExtractor.java`
- `moskito-web/src/main/java/net/anotheria/moskito/web/session/SessionByTldListener.java`

**Special Note:**
- `moskito-web/src/main/java/net/anotheria/moskito/web/filters/JourneyStarterFilter.java` has TODO: *"we have to check if the both filter coexist or fight each other."*

---

### moskito-webui (18 files)

#### Load Factors
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/loadfactors/action/BaseLoadFactorsAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/loadfactors/action/ShowLoadFactorsAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/loadfactors/api/LoadFactorsAPI.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/loadfactors/api/LoadFactorsAPIFactory.java`

#### Producers
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/action/DisableSourceMonitoringAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/action/EnableSourceMonitoringAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/action/DisableProducerLoggingAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/action/EnableProducerLoggingAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/filters/ProducerIdFilter.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/filters/ProducerWithMatcherFilter.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/filters/ProducerSubsystemFilter.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/ProducerAPIFactory.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/StatLineAO.java`

#### Shared/Common
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/action/GetExplanationsByDecoratorNameAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/action/QuickConnectAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/AdditionalFunctionalityAPIImpl.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/AbstractMoskitoAPIImpl.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/CaughtErrorAO.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/AdditionalFunctionalityAPIFactory.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/filter/Matchers.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/resource/VersionResource.java`

#### Thresholds
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threshold/util/ThresholdStatusBeanUtility.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threshold/bean/ThresholdStatusBean.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threshold/api/ThresholdAPIFactory.java`

#### Threads
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threads/api/ThreadAPIFactory.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threads/api/ThreadAPIImpl.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/threads/api/ActiveThreadHistoryAO.java`

#### Plugins
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/plugins/action/ShowPluginsAction.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/plugins/action/BasePluginAction.java`

#### Utilities
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/util/DecoratorConfig.java`
- `moskito-webui/src/main/java/net/anotheria/moskito/webui/util/ProducerFilterConfig.java`

---

### moskito-aop (1 file)

- `moskito-aop/src/main/java/net/anotheria/moskito/aop/aspect/UserActivityAspect.java`

---

### moskito-extensions (4 files)

#### Sampling API
- `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/endpoints/servlet/SamplingServlet.java`
- `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/Sample.java`
- `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/SamplingEngine.java`
- `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/mappers/ServiceRequestStatsMapper.java`
- `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/StatsMapper.java`

#### SaaS Plugin
- `moskito-extensions/moskito-saas/src/main/java/net/anotheria/moskito/moskitosaas/errorhandling/MoskitoSaasErrorHandlingPlugin.java`
- `moskito-extensions/moskito-saas/src/main/java/net/anotheria/moskito/moskitosaas/errorhandling/MoskitoSaasErrorCatcher.java`
- `moskito-extensions/moskito-saas/src/main/java/net/anotheria/moskito/moskitosaas/errorhandling/ErrorWrapper.java`

---

### moskito-inspect-standalone (4 files)

- `moskito-inspect-standalone/src/main/java/net/anotheria/inspect/testing/FibonacciCalculator.java`
- `moskito-inspect-standalone/src/main/java/net/anotheria/inspect/testing/InspectMappingsConfigurator.java`
- `moskito-inspect-standalone/src/main/java/net/anotheria/inspect/testing/InspectTestingFilter.java`
- `moskito-inspect-standalone/src/main/java/net/anotheria/inspect/testing/FibonacciAction.java`

---

## 2. Test Code Files (58)

### moskito-core Tests (25 files)

#### Tracer Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/tracer/ShrinkingStrategyTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/tracer/TracerTest.java`

#### Snapshot Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/snapshot/SnapshotCreatorTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/snapshot/BaseSnapshotTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/snapshot/SnapshotRepositoryTest.java`

#### Context Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/context/MoSKitoContextTest.java`

#### Error Handling Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/errorhandling/ErrorTestSecondServiceImpl.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/errorhandling/ErrorCatcherTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/errorhandling/ErrorTestServiceImpl.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/errorhandling/ErrorTestService.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/errorhandling/ErrorTestSecondService.java`

#### Journey Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/journey/JourneyManagerFactoryTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/journey/JourneyManagerImplTest.java`

#### Predefined Stats Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/predefined/CacheStatsTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/predefined/ThreadStateStatsTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/predefined/ThreadCountStatsTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/predefined/QueueStatsTest.java`

#### Accumulation Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/accumulation/MSK156Test.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/accumulation/AutoAccumulationDefinitionTest.java`

#### Threshold/Alert Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/threshold/alerts/AlertDispatcherTest.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/threshold/alerts/TestNotificationProvider.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/threshold/alerts/DummyNotificationProvider.java`

#### Registry Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/registry/filters/DummyProducer.java`
- `moskito-core/src/test/java/net/anotheria/moskito/core/registry/filters/FiltersTest.java`

#### Counter Tests
- `moskito-core/src/test/java/net/anotheria/moskito/core/counter/CounterStatsTest.java`

---

### moskito-web Tests (1 file)

- `moskito-web/src/main/java/net/anotheria/moskito/web/session/TestListener.java` *(appears to be in main but is a test class)*

---

### moskito-webui Tests (7 files)

- `moskito-webui/src/test/java/net/anotheria/moskito/webui/producers/api/ProducerAPITest.java`
- `moskito-webui/src/test/java/net/anotheria/moskito/webui/util/Ping.java`
- `moskito-webui/src/test/java/net/anotheria/moskito/webui/shared/api/filter/MatchersTest.java`
- `moskito-webui/src/test/java/net/anotheria/moskito/webui/threshold/api/ThresholdAPITest.java`
- `moskito-webui/src/test/java/net/anotheria/moskito/webui/dashboards/DashboardsAPITest.java`
- `moskito-webui/src/test/java/net/anotheria/moskito/webui/accumulators/api/AccumulatorAPITest.java`

---

### moskito-integration Tests (1 file)

- `moskito-integration/moskito-sql/src/test/java/net/anotheria/moskito/sql/callingAspect/ConnectionCallAspectTest.java`

---

### moskito-extensions Tests (2 files)

- `moskito-extensions/moskito-notification-providers/src/test/java/net/anotheria/moskito/extensions/notificationproviders/DummyNotificationProvider.java`
- `moskito-extensions/moskito-additional-producers/src/test/java/net/anotheria/moskito/extensions/producers/RollingOnDemandStatsProducerTest.java`

---

## 3. Recommendations

### Priority Classification

#### High Priority (Public API & Core Functionality) - 15 files
These are core public API classes that should be documented first:

1. `moskito-core/src/main/java/net/anotheria/moskito/core/tracer/Tracer.java`
2. `moskito-core/src/main/java/net/anotheria/moskito/core/tracer/Tracers.java`
3. `moskito-core/src/main/java/net/anotheria/moskito/core/entrypoint/EntryPoint.java`
4. `moskito-core/src/main/java/net/anotheria/moskito/core/errorhandling/ErrorCatcherFactory.java`
5. `moskito-webui/src/main/java/net/anotheria/moskito/webui/shared/api/AbstractMoskitoAPIImpl.java`
6. `moskito-webui/src/main/java/net/anotheria/moskito/webui/producers/api/ProducerAPIFactory.java`
7. `moskito-webui/src/main/java/net/anotheria/moskito/webui/threshold/api/ThresholdAPIFactory.java`
8. `moskito-webui/src/main/java/net/anotheria/moskito/webui/threads/api/ThreadAPIImpl.java`
9. `moskito-extensions/moskito-sampling-api/src/main/java/net/anotheria/moskito/extensions/sampling/SamplingEngine.java`
10. `moskito-aop/src/main/java/net/anotheria/moskito/aop/aspect/UserActivityAspect.java`

#### Medium Priority (Internal APIs & Utilities) - 27 files
Supporting classes and internal utilities.

#### Low Priority (Test Code) - 58 files
Test classes - can document later or skip if test names are self-explanatory.

---

## 4. Documentation Guidelines

When documenting these classes, include:

1. **Purpose**: What does this class do?
2. **Usage**: How and when should it be used?
3. **Thread Safety**: Is it thread-safe? Any concurrency considerations?
4. **Example**: Simple code example (if appropriate)
5. **Related Classes**: Links to related classes using `@see`
6. **Author/Since**: Keep existing `@author` and `@since` tags

### Example Template

```java
/**
 * Manages the lifecycle and execution of monitoring tracers.
 *
 * <p>Tracers capture detailed execution paths and timing information
 * for monitored operations. This class provides the central registry
 * and management interface for all active tracers in the system.
 *
 * <p><b>Thread Safety:</b> This class is thread-safe and can be safely
 * accessed from multiple threads concurrently.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * Tracer tracer = Tracers.getTracer("myOperation");
 * tracer.start();
 * try {
 *     // monitored code
 * } finally {
 *     tracer.stop();
 * }
 * </pre>
 *
 * @author [author]
 * @since [version]
 * @see Tracers
 * @see TraceSortType
 */
public class Tracer {
    // ...
}
```

---

## 5. Tracking Progress

Consider creating GitHub issues for documentation work:

- [ ] **Issue #1:** Document core API classes (15 high priority files)
- [ ] **Issue #2:** Document WebUI API factories and implementations
- [ ] **Issue #3:** Document notification providers
- [ ] **Issue #4:** Document sampling API
- [ ] **Issue #5:** Document filter and action classes
- [ ] **Issue #6:** Document test classes (if needed)

---

**Note:** This list was generated by searching for `TODO comment this class` patterns in JavaDoc comments. There may be additional undocumented classes without explicit TODO markers.
