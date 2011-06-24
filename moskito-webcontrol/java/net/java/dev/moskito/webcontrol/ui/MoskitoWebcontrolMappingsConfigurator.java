package net.java.dev.moskito.webcontrol.ui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webcontrol.ui.action.CssAction;
import net.java.dev.moskito.webcontrol.ui.action.ShowAllViewsAction;
import net.java.dev.moskito.webcontrol.ui.action.ShowViewAction;
import net.java.dev.moskito.webcontrol.ui.action.ViewAdd;
import net.java.dev.moskito.webcontrol.ui.action.ViewConfigAction;
import net.java.dev.moskito.webcontrol.ui.action.ViewConfigActionV2;
import net.java.dev.moskito.webcontrol.ui.action.ViewDelete;
import net.java.dev.moskito.webcontrol.ui.action.ViewRename;

public class MoskitoWebcontrolMappingsConfigurator implements ActionMappingsConfigurator {

	public void configureActionMappings() {
		ActionMappings.addMapping("mwcShowAll", ShowAllViewsAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/allViews.jsp"));
		ActionMappings.addMapping("mwcShowView", ShowViewAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/showView.jsp"));
		ActionMappings.addMapping("mwcCSS", CssAction.class, new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/CSS.jsp"));

		ActionMappings.addMapping("viewConfig", ViewConfigAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/viewConfig.jsp"));

		ActionMappings.addMapping("viewConfig2", ViewConfigActionV2.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/viewConfig2.jsp"));
		ActionMappings.addMapping("mwcShowView2", ShowViewAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/showView2.jsp"));
		ActionMappings.addMapping("viewAdd", ViewAdd.class);
		ActionMappings.addMapping("viewRename", ViewRename.class);
		ActionMappings.addMapping("viewDelete", ViewDelete.class);
	}

}
