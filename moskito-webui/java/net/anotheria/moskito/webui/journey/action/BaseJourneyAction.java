package net.anotheria.moskito.webui.journey.action;

import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
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
	 * Link to the journey manager.
	 */
	private JourneyManager journeyManager;

	/**
	 * Creates a new action instance.
	 */
	protected BaseJourneyAction(){
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	//use getJourneyAPI instead
	@Deprecated
	protected JourneyManager getJourneyManager(){
		return journeyManager;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.JOURNEYS;
	}

	protected JourneyAPI getJourneyAPI(){
		return APILookupUtility.getJourneyAPI();
	}
}
