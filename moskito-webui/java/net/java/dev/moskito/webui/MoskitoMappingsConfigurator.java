package net.java.dev.moskito.webui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webui.action.CssAction;
import net.java.dev.moskito.webui.action.GetChartDataAction;
import net.java.dev.moskito.webui.action.InspectProducerAction;
import net.java.dev.moskito.webui.action.ShowAllProducersAction;
import net.java.dev.moskito.webui.action.ShowExplanationsAction;
import net.java.dev.moskito.webui.action.ShowMonitoringSessionAction;
import net.java.dev.moskito.webui.action.ShowMonitoringSessionCallAction;
import net.java.dev.moskito.webui.action.ShowMonitoringSessionsAction;
import net.java.dev.moskito.webui.action.ShowProducerAction;
import net.java.dev.moskito.webui.action.ShowProducersForCategoryAction;
import net.java.dev.moskito.webui.action.ShowProducersForSubsystemAction;
import net.java.dev.moskito.webui.action.ShowRecordedUseCaseAction;
import net.java.dev.moskito.webui.action.ShowUseCasesAction;


public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	public void configureActionMappings(){
		ActionMappings.addMapping("mskCSS", CssAction.class, new ActionForward("css", "/net/java/dev/moskito/webui/jsp/CSS.jsp"));

		ActionMappings.addMapping("mskShowAllProducers", ShowAllProducersAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		ActionMappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
 
		ActionMappings.addMapping("mskShowProducersByCategory", ShowProducersForCategoryAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		ActionMappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");

		ActionMappings.addMapping("mskShowProducersBySubsystem", ShowProducersForSubsystemAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		ActionMappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");

		ActionMappings.addMapping("mskShowProducer", ShowProducerAction.class, 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		ActionMappings.addAlias("mskShowProducer.xml", "mskShowProducer");

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
		
	}

}
