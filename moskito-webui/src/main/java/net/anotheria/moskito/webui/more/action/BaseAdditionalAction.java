package net.anotheria.moskito.webui.more.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.10.12 23:41
 */
public abstract class BaseAdditionalAction extends BaseMoskitoUIAction{
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

	protected AdditionalFunctionalityAPI getAdditionalFunctionalityAPI(){
		return APILookupUtility.getAdditionalFunctionalityAPI();
	}
}
