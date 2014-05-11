package net.anotheria.moskito.webui.journey.action;

import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

/**
 * Base action for journeys.
 * @author lrosenberg
 */
abstract class BaseJourneyAction extends BaseMoskitoUIAction {
	/**
	 * Creates a new action instance.
	 */
	protected BaseJourneyAction(){
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.JOURNEYS;
	}

	@Override
	protected String getSubTitle() {
		return "Journeys";
	}

}
