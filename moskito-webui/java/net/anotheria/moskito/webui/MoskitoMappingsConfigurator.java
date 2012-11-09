package net.anotheria.moskito.webui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.moskito.webui.action.AnalyzeJourneyAction;
import net.anotheria.moskito.webui.action.ForceIntervalUpdateAction;
import net.anotheria.moskito.webui.action.InspectProducerAction;
import net.anotheria.moskito.webui.action.ShowAllProducersAction;
import net.anotheria.moskito.webui.action.ShowDashboardAction;
import net.anotheria.moskito.webui.action.ShowExplanationsAction;
import net.anotheria.moskito.webui.action.ShowJourneyAction;
import net.anotheria.moskito.webui.action.ShowJourneyCallAction;
import net.anotheria.moskito.webui.action.ShowJourneysAction;
import net.anotheria.moskito.webui.action.ShowProducerAction;
import net.anotheria.moskito.webui.action.ShowProducersForCategoryAction;
import net.anotheria.moskito.webui.action.ShowProducersForSubsystemAction;
import net.anotheria.moskito.webui.action.accumulators.CreateAccumulatorAction;
import net.anotheria.moskito.webui.action.accumulators.DeleteAccumulatorAction;
import net.anotheria.moskito.webui.action.accumulators.ShowAccumulatorAction;
import net.anotheria.moskito.webui.action.accumulators.ShowAccumulatorsAction;
import net.anotheria.moskito.webui.action.additional.AdditionalSectionAction;
import net.anotheria.moskito.webui.action.additional.ShowConfigAction;
import net.anotheria.moskito.webui.action.additional.ShowLibsAction;
import net.anotheria.moskito.webui.action.charts.GetChartDataAction;
import net.anotheria.moskito.webui.action.charts.GetChartMetaDataAction;
import net.anotheria.moskito.webui.action.charts.ShowChartsAction;
import net.anotheria.moskito.webui.action.threads.HistoryOffAction;
import net.anotheria.moskito.webui.action.threads.HistoryOnAction;
import net.anotheria.moskito.webui.action.threads.SetHistoryListSizeAction;
import net.anotheria.moskito.webui.action.threads.StartThreadAction;
import net.anotheria.moskito.webui.action.threads.ThreadsDumpAction;
import net.anotheria.moskito.webui.action.threads.ThreadsHistoryAction;
import net.anotheria.moskito.webui.action.threads.ThreadsListAction;
import net.anotheria.moskito.webui.action.threads.ThreadsOverviewAction;
import net.anotheria.moskito.webui.action.thresholds.CreateThresholdAction;
import net.anotheria.moskito.webui.action.thresholds.DeleteThresholdAction;
import net.anotheria.moskito.webui.action.thresholds.ShowThresholdsAction;
import net.anotheria.moskito.webui.action.thresholds.UpdateThresholdAction;

/**
 * Mappings configurator for MoSKito project for the AnoMaf framework.
 * @author lrosenberg.
 *
 */
public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	@Override public void configureActionMappings(ActionMappings mappings){
		mappings.addForward("mskCSS", "/net/anotheria/moskito/webui/jsp/CSS.jsp");

		mappings.addMapping("mskDashBoard", ShowDashboardAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Dashboard.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskDashBoard.csv", "mskDashBoard");
		mappings.addAlias("mskDashBoard.xml", "mskDashBoard");
		mappings.addAlias("mskDashBoard.json", "mskDashBoard");

		mappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ProducersJSON.jsp")
		);

		mappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
		mappings.addAlias("mskShowAllProducers.json", "mskShowAllProducers");
 
		mappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");
		mappings.addAlias("mskShowProducersByCategory.json", "mskShowProducersByCategory");

		mappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Producers.jsp"),
		 		new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		mappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");
		mappings.addAlias("mskShowProducersBySubsystem.json", "mskShowProducersBySubsystem");

		mappings.addMapping("mskShowProducer", ShowProducerAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ProducerCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ProducerJSON.jsp"),
				new ActionForward("selection", "/net/anotheria/moskito/webui/jsp/ProducerForSelection.jsp")
		);
		
		mappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		mappings.addAlias("mskShowProducer.xml", "mskShowProducer");
		mappings.addAlias("mskShowProducer.json", "mskShowProducer");
		mappings.addAlias("mskShowProducerForSelection", "mskShowProducer");

		mappings.addMapping("mskInspectProducer", InspectProducerAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/InspectProducerXML.jsp")
		);
		
		mappings.addMapping("mskShowExplanations", ShowExplanationsAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/Explanations.jsp")
		);

//		mappings.addMapping("mskShowUseCases", ShowUseCasesAction.class,
//				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/UseCases.jsp")
//		);
//		mappings.addMapping("mskShowRecordedUseCase", ShowRecordedUseCaseAction.class,
//				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/RecordedUseCase.jsp")
//		);
		mappings.addMapping("mskShowJourneys", ShowJourneysAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/Journeys.jsp")
		);
		mappings.addMapping("mskShowJourney", ShowJourneyAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/Journey.jsp")
		);
		mappings.addMapping("mskShowJourneyCall", ShowJourneyCallAction.class, 
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/JourneyCall.jsp")
		);

		mappings.addMapping("getChartData", GetChartDataAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/ChartData.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ChartDataXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ChartDataCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ChartDataJSON.jsp")

		);
		
		mappings.addMapping("getChartMetaData", GetChartMetaDataAction.class, 
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/ChartMetaData.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ChartMetaDataXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ChartMetaDataCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ChartMetaDataJSON.jsp")

		);
		
		mappings.addMapping("mskShowCharts", ShowChartsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Charts.jsp")
		);
		
		mappings.addMapping("mskForceIntervalUpdate", ForceIntervalUpdateAction.class, (ActionForward[])null);

		mappings.addMapping("mskThresholds", ShowThresholdsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Thresholds.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/ThresholdsXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/ThresholdsCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/ThresholdsJSON.jsp")
		);
		mappings.addAlias("mskThresholds.csv", "mskThresholds");
		mappings.addAlias("mskThresholds.xml", "mskThresholds");
		mappings.addAlias("mskThresholds.json", "mskThresholds");

		mappings.addMapping("mskThresholdEdit", net.anotheria.moskito.webui.action.thresholds.EditThresholdAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/EditThreshold.jsp")
		);

		mappings.addMapping("mskThresholdDelete", DeleteThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));
		mappings.addMapping("mskThresholdCreate", CreateThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));
		mappings.addMapping("mskThresholdUpdate", UpdateThresholdAction.class,
				new CommandRedirect("redirect", "mskThresholds"));


		mappings.addMapping("mskAccumulators", ShowAccumulatorsAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Accumulators.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/AccumulatorsXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/AccumulatorsCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/AccumulatorsJSON.jsp")
		);
		mappings.addAlias("mskAccumulators.csv", "mskAccumulators");
		mappings.addAlias("mskAccumulators.xml", "mskAccumulators");
		mappings.addAlias("mskAccumulators.json", "mskAccumulators");
		
		mappings.addMapping("mskAccumulator", ShowAccumulatorAction.class,
				new ActionForward("html", "/net/anotheria/moskito/webui/jsp/Accumulator.jsp"),
				new ActionForward("xml", "/net/anotheria/moskito/webui/jsp/AccumulatorXML.jsp"),
				new ActionForward("csv", "/net/anotheria/moskito/webui/jsp/AccumulatorCSV.jsp"),
				new ActionForward("json", "/net/anotheria/moskito/webui/jsp/AccumulatorJSON.jsp")
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
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/AnalyzeJourney.jsp")
		);
		
		//threads
		mappings.addMapping("mskThreads", ThreadsOverviewAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/Threads.jsp"));
		mappings.addMapping("mskThreadsList", ThreadsListAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsList.jsp"));
		mappings.addMapping("mskThreadsDump", ThreadsDumpAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsDump.jsp"));
		mappings.addMapping("mskThreadsHistory", ThreadsHistoryAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsHistory.jsp"));
		//hidden features.
		mappings.addMapping("mskThreadsSetHistoryListSize", SetHistoryListSizeAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsStartTestThread", StartThreadAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsHistoryOff", HistoryOffAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsHistory.jsp"));
		mappings.addMapping("mskThreadsHistoryOn", HistoryOnAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ThreadsHistory.jsp"));

		//additional information section
		mappings.addMapping("mskMore", AdditionalSectionAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/AdditionalItems.jsp")
		);
		mappings.addMapping("mskConfig", ShowConfigAction.class,
			new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ConfigView.jsp")
		);
		mappings.addMapping("mskLibs", ShowLibsAction.class,
				new ActionForward("success", "/net/anotheria/moskito/webui/jsp/ConfigView.jsp")
		);

		//errors
		mappings.setOnError(new ActionForward("error", "/net/anotheria/moskito/webui/jsp/Error.jsp"));

	}

}
