package net.java.dev.moskito.webui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webui.action.*;

/**
 * Mappings configurator for MoSKito project for the AnoMaf framework.
 * @author lrosenberg.
 *
 */
public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	@Override public void configureActionMappings(){
		ActionMappings.addMapping("mskCSS", CssAction.class, new ActionForward("css", "/net/java/dev/moskito/webui/jsp/CSS.jsp"));

		ActionMappings.addMapping("mskDashBoard", ShowDashboardAction.class,
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Dashboard.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);

		ActionMappings.addAlias("mskDashBoard.csv", "mskDashBoard");
		ActionMappings.addAlias("mskDashBoard.xml", "mskDashBoard");
		ActionMappings.addAlias("mskDashBoard.json", "mskDashBoard");

		ActionMappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class,
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);

		ActionMappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		ActionMappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
		ActionMappings.addAlias("mskShowAllProducers.json", "mskShowAllProducers");
 
		ActionMappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		ActionMappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");
		ActionMappings.addAlias("mskShowProducersByCategory.json", "mskShowProducersByCategory");

		ActionMappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
		 		new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducersJSON.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		ActionMappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");
		ActionMappings.addAlias("mskShowProducersBySubsystem.json", "mskShowProducersBySubsystem");

		ActionMappings.addMapping("mskShowProducer", ShowProducerAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ProducerJSON.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		ActionMappings.addAlias("mskShowProducer.xml", "mskShowProducer");
		ActionMappings.addAlias("mskShowProducer.json", "mskShowProducer");

		ActionMappings.addMapping("mskInspectProducer", InspectProducerAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/InspectProducerXML.jsp")
		);
		
		ActionMappings.addMapping("mskShowExplanations", ShowExplanationsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Explanations.jsp")
		);

		ActionMappings.addMapping("mskShowUseCases", ShowUseCasesAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/UseCases.jsp")
		);
		ActionMappings.addMapping("mskShowRecordedUseCase", ShowRecordedUseCaseAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/RecordedUseCase.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSessions", ShowMonitoringSessionsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessions.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSession", ShowMonitoringSessionAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSession.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSessionCall", ShowMonitoringSessionCallAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessionCall.jsp")
		);

		ActionMappings.addMapping("getChartData", GetChartDataAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/ChartData.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ChartDataXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ChartDataCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ChartDataJSON.jsp")

		);
		
		ActionMappings.addMapping("getChartMetaData", GetChartMetaDataAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/ChartMetaData.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ChartMetaDataXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ChartMetaDataCSV.jsp"),
				new ActionForward("json", "/net/java/dev/moskito/webui/jsp/ChartMetaDataJSON.jsp")

		);
		
		ActionMappings.addMapping("mskShowCharts", ShowChartsAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Charts.jsp")
		);
		
		ActionMappings.addMapping("mskForceIntervalUpdate", ForceIntervalUpdateAction.class, (ActionForward[])null);

		ActionMappings.addMapping("mskThresholds", ShowThresholdsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Thresholds.jsp")
		);

		ActionMappings.addMapping("mskAccumulators", ShowAccumulatorsAction.class, 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Accumulators.jsp")
		);
		
	}

}
