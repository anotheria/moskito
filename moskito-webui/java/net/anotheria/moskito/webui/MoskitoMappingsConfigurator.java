package net.anotheria.moskito.webui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.moskito.webui.journey.action.AnalyzeJourneyAction;
import net.anotheria.moskito.webui.shared.action.ForceIntervalUpdateAction;
import net.anotheria.moskito.webui.producers.action.InspectProducerAction;
import net.anotheria.moskito.webui.producers.action.ShowAllProducersAction;
import net.anotheria.moskito.webui.shared.action.ShowDashboardAction;
import net.anotheria.moskito.webui.shared.action.ShowExplanationsAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneyAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneyCallAction;
import net.anotheria.moskito.webui.journey.action.ShowJourneysAction;
import net.anotheria.moskito.webui.producers.action.ShowProducerAction;
import net.anotheria.moskito.webui.producers.action.ShowProducersForCategoryAction;
import net.anotheria.moskito.webui.producers.action.ShowProducersForSubsystemAction;
import net.anotheria.moskito.webui.accumulators.action.CreateAccumulatorAction;
import net.anotheria.moskito.webui.accumulators.action.DeleteAccumulatorAction;
import net.anotheria.moskito.webui.accumulators.action.ShowAccumulatorAction;
import net.anotheria.moskito.webui.accumulators.action.ShowAccumulatorsAction;
import net.anotheria.moskito.webui.shared.action.additional.AdditionalSectionAction;
import net.anotheria.moskito.webui.shared.action.additional.ShowConfigAction;
import net.anotheria.moskito.webui.shared.action.additional.ShowLibsAction;
import net.anotheria.moskito.webui.shared.action.charts.GetChartDataAction;
import net.anotheria.moskito.webui.shared.action.charts.GetChartMetaDataAction;
import net.anotheria.moskito.webui.shared.action.charts.ShowChartsAction;
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
import net.anotheria.moskito.webui.threshold.action.ShowThresholdsAction;
import net.anotheria.moskito.webui.threshold.action.UpdateThresholdAction;
import net.anotheria.moskito.webui.threshold.action.EditThresholdAction;

/**
 * Mappings configurator for MoSKito project for the AnoMaf framework.
 * @author lrosenberg.
 *
 */
public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	@Override public void configureActionMappings(ActionMappings mappings){
		mappings.addForward("mskCSS", "/net/anotheria/moskito/webui/shared/jsp/CSS.jsp");

		mappings.addMapping("mskDashBoard", ShowDashboardAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Dashboard.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskDashBoard.csv", "mskDashBoard");
		mappings.addAlias("mskDashBoard.xml", "mskDashBoard");
		mappings.addAlias("mskDashBoard.json", "mskDashBoard");

		mappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.json", "mskShowAllProducers");
 
		mappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.json", "mskShowProducersByCategory");

		mappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Producers.jsp"),
		 		new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.json", "mskShowProducersBySubsystem");

		mappings.addMapping("mskShowProducer", ShowProducerAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ProducerCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ProducerJSON.jsp"),
				new ActionForward("selection", "/net/anotheria/moskito/webui/shared/jsp/ProducerForSelection.jsp")
		);
		
		mappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		mappings.addAlias("mskShowProducer.xml", "mskShowProducer");
		mappings.addAlias("mskShowProducer.json", "mskShowProducer");
		mappings.addAlias("mskShowProducerForSelection", "mskShowProducer");

		mappings.addMapping("mskInspectProducer", InspectProducerAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/InspectProducerXML.jsp")
		);
		
		mappings.addMapping("mskShowExplanations", ShowExplanationsAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/shared/jsp/Explanations.jsp")
		);

//		mappings.addMapping("mskShowUseCases", ShowUseCasesAction.class,
//				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/UseCases.jsp")
//		);
//		mappings.addMapping("mskShowRecordedUseCase", ShowRecordedUseCaseAction.class,
//				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/RecordedUseCase.jsp")
//		);
		mappings.addMapping("mskShowJourneys", ShowJourneysAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/journey/jsp/Journeys.jsp")
		);
		mappings.addMapping("mskShowJourney", ShowJourneyAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/journey/jsp/Journey.jsp")
		);
		mappings.addMapping("mskShowJourneyCall", ShowJourneyCallAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/journey/jsp/JourneyCall.jsp")
		);

		mappings.addMapping("getChartData", GetChartDataAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/ChartData.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ChartDataXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ChartDataCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ChartDataJSON.jsp")

		);
		
		mappings.addMapping("getChartMetaData", GetChartMetaDataAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/ChartMetaData.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/ChartMetaDataXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ChartMetaDataCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/shared/jsp/ChartMetaDataJSON.jsp")

		);
		
		mappings.addMapping("mskShowCharts", ShowChartsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/shared/jsp/Charts.jsp")
		);
		
		mappings.addMapping("mskForceIntervalUpdate", ForceIntervalUpdateAction.class, (ActionForward[])null);

		mappings.addMapping("mskThresholds", ShowThresholdsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/threshold/jsp/Thresholds.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/threshold/jsp/ThresholdsXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/shared/jsp/ThresholdsCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/threshold/jsp/ThresholdsJSON.jsp")
		);
		mappings.addAlias("mskThresholds.csv", "mskThresholds");
		mappings.addAlias("mskThresholds.xml", "mskThresholds");
		mappings.addAlias("mskThresholds.json", "mskThresholds");

		mappings.addMapping("mskThresholdEdit", EditThresholdAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/shared/jsp/EditThreshold.jsp")
		);

		mappings.addMapping("mskThresholdDelete", DeleteThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));
		mappings.addMapping("mskThresholdCreate", CreateThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));
		mappings.addMapping("mskThresholdUpdate", UpdateThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));


		mappings.addMapping("mskAccumulators", ShowAccumulatorsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/accumulators/jsp/Accumulators.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/AccumulatorsXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorsCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorsJSON.jsp")
		);
		mappings.addAlias("mskAccumulators.csv", "mskAccumulators");
		mappings.addAlias("mskAccumulators.xml", "mskAccumulators");
		mappings.addAlias("mskAccumulators.json", "mskAccumulators");
		
		mappings.addMapping("mskAccumulator", ShowAccumulatorAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/accumulators/jsp/Accumulator.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/shared/jsp/AccumulatorXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/accumulators/jsp/AccumulatorJSON.jsp")
		);
		mappings.addAlias("mskAccumulator.csv", "mskAccumulator");
		mappings.addAlias("mskAccumulator.xml", "mskAccumulator");
		mappings.addAlias("mskAccumulator.json", "mskAccumulator");
		
		mappings.addMapping("mskAccumulatorDelete", DeleteAccumulatorAction.class,
                new CommandRedirect("redirect", "mskAccumulators"));
		mappings.addMapping("mskAccumulatorCreate", CreateAccumulatorAction.class,
				new CommandRedirect("redirect", "mskAccumulators"));


		
		//analyze journey
		mappings.addMapping("mskAnalyzeJourney", AnalyzeJourneyAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/journey/jsp/AnalyzeJourney.jsp")
		);
		
		//threads
		mappings.addMapping("mskThreads", ThreadsOverviewAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/Threads.jsp"));
		mappings.addMapping("mskThreadsList", ThreadsListAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsList.jsp"));
		mappings.addMapping("mskThreadsDump", ThreadsDumpAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsDump.jsp"));
		mappings.addMapping("mskThreadsHistory", ThreadsHistoryAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
		//hidden features.
		mappings.addMapping("mskThreadsSetHistoryListSize", SetHistoryListSizeAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsStartTestThread", StartThreadAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsHistoryOff", HistoryOffAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsHistoryOn", HistoryOnAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/threads/jsp/ThreadsHistory.jsp"));

		//additional information section
		mappings.addMapping("mskMore", AdditionalSectionAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/shared/jsp/AdditionalItems.jsp")
		);
		mappings.addMapping("mskConfig", ShowConfigAction.class,
			new ActionForward("success", "/net/anotheria/moskito/webui/shared/jsp/ConfigView.jsp")
		);
		mappings.addMapping("mskLibs", ShowLibsAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/shared/jsp/ConfigView.jsp")
		);

		//errors
		mappings.setOnError(new ActionForward("error", "/net/anotheria/moskito/webui/shared/jsp/Error.jsp"));

	}

}
