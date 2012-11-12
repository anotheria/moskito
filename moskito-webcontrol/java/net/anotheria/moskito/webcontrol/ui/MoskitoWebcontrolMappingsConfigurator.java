package net.anotheria.moskito.webcontrol.ui;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskito.webcontrol.ui.action.CssAction;
import net.anotheria.moskito.webcontrol.ui.action.ShowAllViewsAction;
import net.anotheria.moskito.webcontrol.ui.action.ShowViewAction;
import net.anotheria.moskito.webcontrol.ui.action.ViewAdd;
import net.anotheria.moskito.webcontrol.ui.action.ViewConfigAction;
import net.anotheria.moskito.webcontrol.ui.action.ViewConfigActionV2;
import net.anotheria.moskito.webcontrol.ui.action.ViewDelete;
import net.anotheria.moskito.webcontrol.ui.action.ViewRename;

public class MoskitoWebcontrolMappingsConfigurator implements ActionMappingsConfigurator {

	public void configureActionMappings(ActionMappings mappings) {
		mappings.addMapping("mwcShowAll", ShowAllViewsAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/allViews.jsp"));
		mappings.addMapping("mwcShowView", ShowViewAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/showView.jsp"));
		mappings.addMapping("mwcCSS", CssAction.class, new ActionForward("success", "/net/java/dev/moskito/webcontrol/ui/jsp/CSS.jsp"));

		mappings.addMapping("viewConfig", ViewConfigAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/viewConfig.jsp"));

		mappings.addMapping("viewConfig2", ViewConfigActionV2.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/viewConfig2.jsp"));
		mappings.addMapping("mwcShowView2", ShowViewAction.class, new ActionForward("success",
				"/net/java/dev/moskito/webcontrol/ui/jsp/showView2.jsp"));
		mappings.addMapping("viewAdd", ViewAdd.class);
		mappings.addMapping("viewRename", ViewRename.class);
		mappings.addMapping("viewDelete", ViewDelete.class);
	}

}
