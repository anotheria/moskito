package net.anotheria.moskito.webui;

import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.maf.action.CommandForward;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.moskito.webui.accumulators.action.CreateAccumulatorAction;
import net.anotheria.moskito.webui.accumulators.action.DeleteAccumulatorAction;
import net.anotheria.moskito.webui.accumulators.action.ShowAccumulatorsAction;
import net.anotheria.moskito.webui.auth.actions.SignInAction;
import net.anotheria.moskito.webui.auth.actions.SignOutAction;
import net.anotheria.moskito.webui.dashboards.action.CreateDashboardAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardAddChartAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardAddGaugeAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardAddProducerAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardAddThresholdAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardRemoveChartAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardRemoveGaugeAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardRemoveProducerAction;
import net.anotheria.moskito.webui.dashboards.action.DashboardRemoveThresholdAction;
import net.anotheria.moskito.webui.dashboards.action.DeleteDashboardAction;
import net.anotheria.moskito.webui.dashboards.action.ShowDashboardAction;
import net.anotheria.moskito.webui.errors.action.ShowErrorAction;
import net.anotheria.moskito.webui.errors.action.ShowErrorsAction;
import net.anotheria.moskito.webui.gauges.action.ShowGaugesAction;
import net.anotheria.moskito.webui.journey.action.AnalyzeJourneyAction;
import net.anotheria.moskito.webui.journey.action.AnalyzeJourneyByMethodAction;
import net.anotheria.moskito.webui.journey.action.DeleteJourneyAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneyAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneyCallAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneysAction;
import net.anotheria.moskito.webui.loadfactors.action.ShowLoadFactorsAction;
import net.anotheria.moskito.webui.more.action.AdditionalSectionAction;
import net.anotheria.moskito.webui.more.action.ShowConfigAction;
import net.anotheria.moskito.webui.more.action.ShowKillSwitchAction;
import net.anotheria.moskito.webui.more.action.ShowLibsAction;
import net.anotheria.moskito.webui.more.action.ShowMBeansAction;
import net.anotheria.moskito.webui.more.action.SwitchKillSettingAction;
import net.anotheria.moskito.webui.more.action.UpdateAction;
import net.anotheria.moskito.webui.nowrunning.action.DeleteNowRunningAction;
import net.anotheria.moskito.webui.nowrunning.action.ShowEntryPointsAction;
import net.anotheria.moskito.webui.plugins.action.RemovePluginAction;
import net.anotheria.moskito.webui.plugins.action.ShowPluginsAction;
import net.anotheria.moskito.webui.producers.action.DisableProducerLoggingAction;
import net.anotheria.moskito.webui.producers.action.DisableSourceMonitoringAction;
import net.anotheria.moskito.webui.producers.action.EnableProducerLoggingAction;
import net.anotheria.moskito.webui.producers.action.EnableSourceMonitoringAction;
import net.anotheria.moskito.webui.producers.action.ShowAllProducersAction;
import net.anotheria.moskito.webui.producers.action.ShowCumulatedProducersAction;
import net.anotheria.moskito.webui.producers.action.ShowProducerAction;
import net.anotheria.moskito.webui.producers.action.ShowProducersForCategoryAction;
import net.anotheria.moskito.webui.producers.action.ShowProducersForSubsystemAction;
import net.anotheria.moskito.webui.shared.action.ForceIntervalUpdateAction;
import net.anotheria.moskito.webui.shared.action.GetExplanationsByDecoratorNameAction;
import net.anotheria.moskito.webui.shared.action.QuickConnectAction;
import net.anotheria.moskito.webui.shared.action.SaveNavMenuStateAction;
import net.anotheria.moskito.webui.shared.action.SelectServerAction;
import net.anotheria.moskito.webui.shared.action.ShowExplanationsAction;
import net.anotheria.moskito.webui.shared.commands.CommandDeepLinkRedirect;
import net.anotheria.moskito.webui.tags.action.AddTagAction;
import net.anotheria.moskito.webui.tags.action.ShowTagsAction;
import net.anotheria.moskito.webui.threads.action.HistoryOffAction;
import net.anotheria.moskito.webui.threads.action.HistoryOnAction;
import net.anotheria.moskito.webui.threads.action.SetHistoryListSizeAction;
import net.anotheria.moskito.webui.threads.action.StartThreadAction;
import net.anotheria.moskito.webui.threads.action.ThreadsDumpAction;
import net.anotheria.moskito.webui.threads.action.ThreadsHistoryAction;
import net.anotheria.moskito.webui.threads.action.ThreadsListAction;
import net.anotheria.moskito.webui.threads.action.ThreadsOverviewAction;
import net.anotheria.moskito.webui.threshold.action.CreateThresholdAction;
import net.anotheria.moskito.webui.threshold.action.DeleteThresholdAction;
import net.anotheria.moskito.webui.threshold.action.GetThresholdDefinitionAction;
import net.anotheria.moskito.webui.threshold.action.ShowThresholdsAction;
import net.anotheria.moskito.webui.threshold.action.UpdateThresholdAction;
import net.anotheria.moskito.webui.tracers.action.CreateTracerAction;
import net.anotheria.moskito.webui.tracers.action.DisableTracerAction;
import net.anotheria.moskito.webui.tracers.action.EnableTracerAction;
import net.anotheria.moskito.webui.tracers.action.RemoveTracerAction;
import net.anotheria.moskito.webui.tracers.action.ShowTracerAction;
import net.anotheria.moskito.webui.tracers.action.ShowTracersAction;

/**
 * Mappings configurator for MoSKito project for the AnoMaf framework.
 *
 * @author lrosenberg.
 */
public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator {

    @Override
    public void configureActionMappings(ActionMappings mappings) {

        mappings.addMapping("mskSignIn", SignInAction.class,
                // Default redirect in case user directly pass to login page
                new CommandForward("loginPage", "/net/anotheria/moskito/webui/auth/jsp/Login.jsp"),
                new CommandRedirect("defaultRedirect", "mskDashboard")
        );
        mappings.addMapping("mskSignOut", SignOutAction.class,
                new CommandRedirect("redirect", "mskDashboard")
        );

        mappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/producers/jsp/Producers.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/producers/jsp/ProducersXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/producers/jsp/ProducersCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/producers/jsp/ProducersJSON.jsp")
        );

        mappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
        mappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
        mappings.addAlias("mskShowAllProducers.json", "mskShowAllProducers");

        mappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/producers/jsp/Producers.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/producers/jsp/ProducersXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/producers/jsp/ProducersCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/producers/jsp/ProducersJSON.jsp")
        );

        mappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
        mappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");
        mappings.addAlias("mskShowProducersByCategory.json", "mskShowProducersByCategory");

        mappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/producers/jsp/Producers.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/producers/jsp/ProducersXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/producers/jsp/ProducersCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/producers/jsp/ProducersJSON.jsp")
        );

        mappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
        mappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");
        mappings.addAlias("mskShowProducersBySubsystem.json", "mskShowProducersBySubsystem");

        mappings.addMapping("mskShowProducer", ShowProducerAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/producers/jsp/Producer.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/producers/jsp/ProducerXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/producers/jsp/ProducerCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/producers/jsp/ProducerJSON.jsp")
                //new ActionForward("selection", "/net/anotheria/moskito/webui/producers/jsp/ProducerForSelection.jsp")
        );

        mappings.addAlias("mskShowProducer.csv", "mskShowProducer");
        mappings.addAlias("mskShowProducer.xml", "mskShowProducer");
        mappings.addAlias("mskShowProducer.json", "mskShowProducer");
        mappings.addAlias("mskShowProducerForSelection", "mskShowProducer");


        mappings.addMapping("mskShowCumulatedProducers", ShowCumulatedProducersAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/producers/jsp/CumulatedProducers.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/producers/jsp/CumulatedProducersXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/producers/jsp/CumulatedProducersCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/producers/jsp/CumulatedProducersJSON.jsp")
        );

        mappings.addAlias("mskShowCumulatedProducers.csv", "mskShowCumulatedProducers");
        mappings.addAlias("mskShowCumulatedProducers.xml", "mskShowCumulatedProducers");
        mappings.addAlias("mskShowCumulatedProducers.json", "mskShowCumulatedProducers");


        mappings.addMapping("mskEnableLogging", EnableProducerLoggingAction.class);
        mappings.addMapping("mskDisableLogging", DisableProducerLoggingAction.class);

        mappings.addMapping("mskEnableSourceMonitoring", EnableSourceMonitoringAction.class);
        mappings.addMapping("mskDisableSourceMonitoring", DisableSourceMonitoringAction.class);

        mappings.addMapping("mskShowExplanations", ShowExplanationsAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/shared/jsp/Explanations.jsp")
        );

        mappings.addMapping("mskShowJourneys", ShowJourneysAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/journey/jsp/Journeys.jsp")
        );
        mappings.addMapping("mskShowJourney", ShowJourneyAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/journey/jsp/Journey.jsp")
        );
        mappings.addMapping("mskShowJourneyCall", ShowJourneyCallAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/journey/jsp/JourneyCall.jsp")
        );

        mappings.addMapping("mskDeleteJourney", DeleteJourneyAction.class,
                new CommandDeepLinkRedirect("redirect", "mskShowJourneys")
        );


        mappings.addMapping("mskForceIntervalUpdate", ForceIntervalUpdateAction.class);

        mappings.addMapping("mskThresholds", ShowThresholdsAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/threshold/jsp/Thresholds.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/threshold/jsp/ThresholdsXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/threshold/jsp/ThresholdsCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/threshold/jsp/ThresholdsJSON.jsp")
        );
        mappings.addAlias("mskThresholds.csv", "mskThresholds");
        mappings.addAlias("mskThresholds.xml", "mskThresholds");
        mappings.addAlias("mskThresholds.json", "mskThresholds");

        //mappings.addMapping("mskThresholdEdit", EditThresholdAction.class,
        //		new ActionForward("success", "/net/anotheria/moskito/webui/threshold/jsp/EditThreshold.jsp")
        //);

        mappings.addMapping("mskThresholdDelete", DeleteThresholdAction.class,
                new CommandDeepLinkRedirect("redirect", "mskThresholds"));
        mappings.addMapping("mskThresholdCreate", CreateThresholdAction.class,
                new CommandDeepLinkRedirect("redirect", "mskShowProducer"));
        mappings.addMapping("mskThresholdUpdate", UpdateThresholdAction.class,
                new CommandDeepLinkRedirect("redirect", "mskThresholds"));


        mappings.addMapping("mskAccumulators", ShowAccumulatorsAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/accumulators/jsp/Accumulators.jsp"),
                new CommandForward("xml", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorsXML.jsp"),
                new CommandForward("csv", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorsCSV.jsp"),
                new CommandForward("json", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorsJSON.jsp")
        );
        mappings.addAlias("mskAccumulators.csv", "mskAccumulators");
        mappings.addAlias("mskAccumulators.xml", "mskAccumulators");
        mappings.addAlias("mskAccumulators.json", "mskAccumulators");

        mappings.addMapping("mskAccumulatorDelete", DeleteAccumulatorAction.class,
                new CommandRedirect("redirect", "mskAccumulators"));
        mappings.addMapping("mskAccumulatorCreate", CreateAccumulatorAction.class,
                new CommandRedirect("redirect", "mskShowProducer"));


        //gauges, now just for testing purposes
        mappings.addMapping("mskGauges", ShowGaugesAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/gauges/jsp/Gauges.jsp"));
        //mappings.addMapping("mskGenerateChart", GenerateChartAction.class);

        //tracers
        mappings.addMapping("mskTracers", ShowTracersAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/tracers/jsp/Tracers.jsp"));
        mappings.addMapping("mskTracer", ShowTracerAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/tracers/jsp/Tracer.jsp"));

        mappings.addMapping("mskCreateTracer", CreateTracerAction.class);
        mappings.addMapping("mskRemoveTracer", RemoveTracerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskTracers"));
        mappings.addMapping("mskDisableTracer", DisableTracerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskTracers"));
        mappings.addMapping("mskEnableTracer", EnableTracerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskTracers"));


        //analyze journey
        mappings.addMapping("mskAnalyzeJourney", AnalyzeJourneyAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/journey/jsp/AnalyzeJourney.jsp")
        );
        mappings.addMapping("mskAnalyzeJourneyByMethod", AnalyzeJourneyByMethodAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/journey/jsp/AnalyzeJourneyByMethod.jsp")
        );


        //threads
        mappings.addMapping("mskThreads", ThreadsOverviewAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/Threads.jsp"));
        mappings.addMapping("mskThreadsList", ThreadsListAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsList.jsp"));
        mappings.addMapping("mskThreadsDump", ThreadsDumpAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsDump.jsp"));
        mappings.addMapping("mskThreadsHistory", ThreadsHistoryAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
        //hidden features.
        mappings.addMapping("mskThreadsSetHistoryListSize", SetHistoryListSizeAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
        mappings.addMapping("mskThreadsStartTestThread", StartThreadAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
        mappings.addMapping("mskThreadsHistoryOff", HistoryOffAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
        mappings.addMapping("mskThreadsHistoryOn", HistoryOnAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));

        //additional information section
        mappings.addMapping("mskMore", AdditionalSectionAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/AdditionalItems.jsp")
        );
        mappings.addMapping("mskConfig", ShowConfigAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/Config.jsp")
        );
        mappings.addMapping("mskErrors", ShowErrorsAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/errors/jsp/Errors.jsp")
        );
        mappings.addMapping("mskError", ShowErrorAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/errors/jsp/Error.jsp")
        );
        mappings.addMapping("mskLibs", ShowLibsAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/Libs.jsp")
        );
        mappings.addMapping("mskMBeans", ShowMBeansAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/MBeans.jsp")
        );
        mappings.addMapping("mskPlugins", ShowPluginsAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/Plugins.jsp")
        );
        mappings.addMapping("mskKillSwitch", ShowKillSwitchAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/KillSwitch.jsp")
        );
        mappings.addMapping("mskSwitchKillSetting", SwitchKillSettingAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/KillSwitch.jsp")
        );
        mappings.addMapping("mskRemovePlugin", RemovePluginAction.class,
                new CommandDeepLinkRedirect("redirect", "mskPlugins")
        );

        mappings.addMapping("mskSelectServer", SelectServerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard")
        );

        mappings.addMapping("mskQuickConnect", QuickConnectAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard")
        );


        // Tags section
        mappings.addMapping("mskTags", ShowTagsAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/tags/jsp/Tags.jsp")
        );

        mappings.addMapping("mskAddTag", AddTagAction.class,
                new CommandDeepLinkRedirect("redirect", "mskTags")
        );


        mappings.addMapping("mskUpdate", UpdateAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/more/jsp/Update.jsp")
        );

        // ajax
        mappings.addMapping("mskSaveNavMenuState", SaveNavMenuStateAction.class);
        mappings.addMapping("mskGetExplanationsByName", GetExplanationsByDecoratorNameAction.class);
        mappings.addMapping("mskGetThresholdDefinition", GetThresholdDefinitionAction.class);

        //errors
        mappings.setOnError(new CommandForward("error", "/net/anotheria/moskito/webui/shared/jsp/Error.jsp"));

        //dashboards
        mappings.addMapping("mskDashboard", ShowDashboardAction.class,
                new CommandForward("success", "/net/anotheria/moskito/webui/dashboards/jsp/Dashboard.jsp")
        );
        mappings.addMapping("mskCreateDashboard", CreateDashboardAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));
        mappings.addMapping("mskDeleteDashboard", DeleteDashboardAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));

        mappings.addMapping("mskAddGaugeToDashboard", DashboardAddGaugeAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));
        mappings.addMapping("mskDashboardRemoveGauge", DashboardRemoveGaugeAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));

        mappings.addMapping("mskAddThresholdToDashboard", DashboardAddThresholdAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));
        mappings.addMapping("mskDashboardRemoveThreshold", DashboardRemoveThresholdAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));

        mappings.addMapping("mskAddChartToDashboard", DashboardAddChartAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));
        mappings.addMapping("mskDashboardRemoveChart", DashboardRemoveChartAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));

        mappings.addMapping("mskAddProducerToDashboard", DashboardAddProducerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));
        mappings.addMapping("mskDashboardRemoveProducer", DashboardRemoveProducerAction.class,
                new CommandDeepLinkRedirect("redirect", "mskDashboard"));


        //Loadfactors feature.
        mappings.addMapping("mskLoadFactors", ShowLoadFactorsAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/loadfactors/jsp/LoadFactors.jsp")
        );

        //Entry points and now running feature.
        mappings.addMapping("mskNowRunning", ShowEntryPointsAction.class,
                new CommandForward("html", "/net/anotheria/moskito/webui/nowrunning/jsp/NowRunning.jsp")
        );
        mappings.addMapping("mskNowRunningDelete", DeleteNowRunningAction.class,
                new CommandRedirect("redirect", "mskNowRunning"));


    }

}
