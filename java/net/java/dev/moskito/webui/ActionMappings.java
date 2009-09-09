package net.java.dev.moskito.webui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.java.dev.moskito.webui.action.ActionForward;
import net.java.dev.moskito.webui.action.ActionMapping;


public final class ActionMappings {
	
	private static final Map<String, String> aliases = new ConcurrentHashMap<String, String>();
	private static final Map<String, ActionMapping> mappings = new ConcurrentHashMap<String, ActionMapping>(); 

	
	static{
		addMapping("mskCSS", "net.java.dev.moskito.webui.action.CssAction", new ActionForward("css", "/net/java/dev/moskito/webui/jsp/CSS.jsp"));

		addMapping("mskShowAllProducers", "net.java.dev.moskito.webui.action.ShowAllProducersAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		addAlias("mskShowAllProducers.csv", "mskShowAllProducers");
		addAlias("mskShowAllProducers.xml", "mskShowAllProducers");
 
		addMapping("mskShowProducersByCategory", "net.java.dev.moskito.webui.action.ShowProducersForCategoryAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		addAlias("mskShowProducersByCategory.csv", "mskShowProducersByCategory");
		addAlias("mskShowProducersByCategory.xml", "mskShowProducersByCategory");

		addMapping("mskShowProducersBySubsystem", "net.java.dev.moskito.webui.action.ShowProducersForSubsystemAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producers.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp")
		);
		
		addAlias("mskShowProducersBySubsystem.csv", "mskShowProducersBySubsystem");
		addAlias("mskShowProducersBySubsystem.xml", "mskShowProducersBySubsystem");

		addMapping("mskShowProducer", "net.java.dev.moskito.webui.action.ShowProducerAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/Producer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"),
				new ActionForward("csv", "/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp")
		);
		
		addAlias("mskShowProducer.csv", "mskShowProducer");
		addAlias("mskShowProducer.xml", "mskShowProducer");

		addMapping("mskInspectProducer", "net.java.dev.moskito.webui.action.InspectProducerAction", 
				new ActionForward("html", "/net/java/dev/moskito/webui/jsp/InspectProducer.jsp"),
				new ActionForward("xml", "/net/java/dev/moskito/webui/jsp/InspectProducerXML.jsp")
		);
		
		addMapping("mskShowExplanations", "net.java.dev.moskito.webui.action.ShowExplanationsAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/Explanations.jsp")
		);

		addMapping("mskShowUseCases", "net.java.dev.moskito.webui.action.ShowUseCasesAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/UseCases.jsp")
		);
		addMapping("mskShowRecordedUseCase", "net.java.dev.moskito.webui.action.ShowRecordedUseCaseAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/RecordedUseCase.jsp")
		);
		addMapping("mskShowMonitoringSessions", "net.java.dev.moskito.webui.action.ShowMonitoringSessionsAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessions.jsp")
		);
		addMapping("mskShowMonitoringSession", "net.java.dev.moskito.webui.action.ShowMonitoringSessionAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSession.jsp")
		);
		addMapping("mskShowMonitoringSessionCall", "net.java.dev.moskito.webui.action.ShowMonitoringSessionCallAction", 
				new ActionForward("success", "/net/java/dev/moskito/webui/jsp/MonitoringSessionCall.jsp")
		);
	}

	
		private static final void addMapping(String path, String type, ActionForward... forwards){
			mappings.put(path, new ActionMapping(path, type, forwards));
		}
		
		private static final void addAlias(String sourcePath, String targetPath){
			aliases.put(sourcePath, targetPath);
		}
		
		protected static final ActionMapping findMapping(String actionPath){
			String alias = aliases.get(actionPath);
			if (alias!=null)
				return findMapping(alias);
			return mappings.get(actionPath);
		}


	   		/*
	   		
		     <action path="/msk" type="net.java.dev.moskito.webui.action.ShowProducersAction">
	             <forward name="html" path="/net/java/dev/moskito/webui/jsp/Moskito.jsp"/>
	        </action>
	   		
	   		<action path="/mskShowAllProducersYUI" type="net.java.dev.moskito.webui.action.ShowProducersAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducersYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducersYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"/>
	   		</action>
	   		<action path="/mskShowAllProducersYUI.json" type="net.java.dev.moskito.webui.action.ShowProducersAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducersYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducersYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"/>
	   		</action>
	   		<action path="/mskShowAllProducersYUI.text" type="net.java.dev.moskito.webui.action.ShowProducersAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducersYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducersYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"/>
		   		<forward name="text" path="/net/java/dev/moskito/webui/jsp/ProducersYUIText.jsp"/>
	   		</action>
	   		<action path="/mskShowProducersByCategoryYUI" type="net.java.dev.moskito.webui.action.ShowProducersForCategoryAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducersYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducersYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducersXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducersCSV.jsp"/>
	   		</action>
	   		<action path="/mskShowProducerYUI" type="net.java.dev.moskito.webui.action.ShowProducerAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducerYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducerYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducerYUIXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp"/>
	   		</action>
			<action path="/mskShowProducerYUI.json" type="net.java.dev.moskito.webui.action.ShowProducerAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/ProducerYUI.jsp"/>
		   		<forward name="json" path="/net/java/dev/moskito/webui/jsp/ProducerYUIJSON.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/ProducerXML.jsp"/>
		   		<forward name="csv" path="/net/java/dev/moskito/webui/jsp/ProducerCSV.jsp"/>
	   		</action>
	   		
		    <action path="/mskInspectProducerYUI" type="net.java.dev.moskito.webui.action.InspectProducerAction">
		   		<forward name="html" path="/net/java/dev/moskito/webui/jsp/InspectProducerYUI.jsp"/>
		   		<forward name="xml" path="/net/java/dev/moskito/webui/jsp/InspectProducerXML.jsp"/>
	   		</action>   		

	    </action-mappings>
		*/

	
	
	private ActionMappings(){}
}
