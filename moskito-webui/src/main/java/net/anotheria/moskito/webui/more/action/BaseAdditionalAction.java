package net.anotheria.moskito.webui.more.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

/**
 * Base action for the 'additional' section which includes everything, that has no own section.
 *
 * @author lrosenberg
 * @since 28.10.12 23:41
 */
public abstract class BaseAdditionalAction extends BaseMoskitoUIAction{
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

}
