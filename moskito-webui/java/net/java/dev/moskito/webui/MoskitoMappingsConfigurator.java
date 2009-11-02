package net.java.dev.moskito.webui;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMappings;
import net.anotheria.maf.ActionMappingsConfigurator;


public class MoskitoMappingsConfigurator implements ActionMappingsConfigurator{
	
	public void configureActionMappings(){
		ActionMappings.addMapping("mskCSS", "net.java.dev.moskito.webui.action.CssAction", new ActionForward("css", "/net/java/dev/moskito/webui/jsp/CSS.jsp"));

		ActionMappings.addMapping("mskShowAllProducers", "net.java.dev.moskito.webui.action.ShowAllProducersAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		ActionMappings.addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
 
		ActionMappings.addMapping("mskShowProducersByCategory", "net.java.dev.moskito.webui.action.ShowProducersForCategoryAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		ActionMappings.addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");

		ActionMappings.addMapping("mskShowProducersBySubsystem", "net.java.dev.moskito.webui.action.ShowProducersForSubsystemAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		ActionMappings.addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");

		ActionMappings.addMapping("mskShowProducer", "net.java.dev.moskito.webui.action.ShowProducerAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp")
		);
		
		ActionMappings.addAlias("mskShowProducer.csv", "mskShowProducer");
		ActionMappings.addAlias("mskShowProducer.xml", "mskShowProducer");

		ActionMappings.addMapping("mskInspectProducer", "net.java.dev.moskito.webui.action.InspectProducerAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/InspectProducerXML.jsp")
		);
		
		ActionMappings.addMapping("mskShowExplanations", "net.java.dev.moskito.webui.action.ShowExplanationsAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Explanations.jsp")
		);

		ActionMappings.addMapping("mskShowUseCases", "net.java.dev.moskito.webui.action.ShowUseCasesAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/UseCases.jsp")
		);
		ActionMappings.addMapping("mskShowRecordedUseCase", "net.java.dev.moskito.webui.action.ShowRecordedUseCaseAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/RecordedUseCase.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSessions", "net.java.dev.moskito.webui.action.ShowMonitoringSessionsAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessions.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSession", "net.java.dev.moskito.webui.action.ShowMonitoringSessionAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSession.jsp")
		);
		ActionMappings.addMapping("mskShowMonitoringSessionCall", "net.java.dev.moskito.webui.action.ShowMonitoringSessionCallAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessionCall.jsp")
		);
	}

}
