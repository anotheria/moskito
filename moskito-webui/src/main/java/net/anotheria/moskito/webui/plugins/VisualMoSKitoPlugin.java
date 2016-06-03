package net.anotheria.moskito.webui.plugins;

import net.anotheria.maf.action.ActionMappings;
import net.anotheria.moskito.core.plugins.MoskitoPlugin;

/**
 * This interface should be implemented by every MoSKito Plugin that wants to be presented in MoSKito Inspect UI.
 *
 * @author lrosenberg
 * @since 18.04.16 14:25
 */
public interface VisualMoSKitoPlugin extends MoskitoPlugin {
	/**
	 * Returns the name in the submenu of the Plugins navigation.
	 * @return
	 */
	String getSubMenuName();

	/**
	 * Get the sub navigation (menu) icon. The icon must be font-awesome icon.
	 * @return
	 */
	String getSubMenuIcon();


	/**
	 * Return the action (incl parameter) to execute from the navigation bar.
	 * @return
	 */
	String getNavigationEntryAction();

	/**
	 * Called by the framework. Allows to add its own bindings to the MoSKito Inspect, to call own pages.
	 */
	void addBindings(ActionMappings actionMappings);
}
