package net.anotheria.moskito.webui.journey.action;

import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

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

	protected JourneyManager getJourneyManager(){
		return journeyManager;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.JOURNEYS;
	}
	
}
