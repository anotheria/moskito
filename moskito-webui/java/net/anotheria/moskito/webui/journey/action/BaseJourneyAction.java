package net.anotheria.moskito.webui.journey.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

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
