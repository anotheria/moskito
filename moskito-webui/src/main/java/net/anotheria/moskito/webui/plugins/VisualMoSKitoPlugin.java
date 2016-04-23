package net.anotheria.moskito.webui.plugins;

import net.anotheria.moskito.core.plugins.MoskitoPlugin;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 18.04.16 14:25
 */
public interface VisualMoSKitoPlugin extends MoskitoPlugin {
	String getSubMenuName();
	String getSubMenuIcon();
	void addBindings();
}
