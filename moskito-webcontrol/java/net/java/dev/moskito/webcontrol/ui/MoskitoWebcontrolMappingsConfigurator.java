package net.java.dev.moskito.webcontrol.ui;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMappings;
import net.anotheria.maf.ActionMappingsConfigurator;

public class MoskitoWebcontrolMappingsConfigurator implements ActionMappingsConfigurator {
		
		public void configureActionMappings(){
			ActionMappings.addMapping("mwcShowAll", "net.java.dev.moskito.webcontrol.ui.action.ShowAllViewsAction", 
					new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/allViews.jsp"));
			ActionMappings.addMapping("mwcShowView", "net.java.dev.moskito.webcontrol.ui.action.ShowViewAction", 
					new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/showView.jsp"));
			ActionMappings.addMapping("mwcCSS", "net.java.dev.moskito.webcontrol.ui.action.CssAction", 
					new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/CSS.jsp"));
		}

	}

