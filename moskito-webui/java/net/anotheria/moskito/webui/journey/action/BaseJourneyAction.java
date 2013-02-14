package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

/**
 * Base action for journeys.
 * @author lrosenberg
 */
abstract class BaseJourneyAction extends BaseMoskitoUIAction {

	private JourneyAPI journeyAPI = APIFinder.findAPI(JourneyAPI.class);

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
		return journeyAPI;
	}
}
