package net.java.dev.moskito.control.monitor.shared;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.control.monitor.ui.actions.DetailsScreenAction;
import net.java.dev.moskito.control.monitor.ui.actions.MainScreenAction;
import net.java.dev.moskito.control.monitor.ui.actions.ViewsScreenAction;


public class MoskitoControlMappingsConfigurator implements ActionMappingsConfigurator {

	public void configureActionMappings() {
		ActionMappings.addMapping("index.html", MainScreenAction.class, new ActionForward("success", "net/java/dev/moskito/control/monitor/ui/jsp/index.jsp"));
		ActionMappings.addMapping("allcomponents.html", MainScreenAction.class, new ActionForward("success", "net/java/dev/moskito/control/monitor/ui/jsp/index.jsp"));
		ActionMappings.addMapping("details.html", DetailsScreenAction.class, new ActionForward("success", "net/java/dev/moskito/control/monitor/ui/jsp/details_screen.jsp"));
		ActionMappings.addMapping("views.html", ViewsScreenAction.class, new ActionForward("success", "net/java/dev/moskito/control/monitor/ui/jsp/views_screen.jsp"));
	}
}
