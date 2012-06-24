package net.java.dev.moskito.webui.action;

import net.java.dev.moskito.core.journey.JourneyManager;
import net.java.dev.moskito.core.journey.JourneyManagerFactory;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
 * Base action for journeys.
 * @author lrosenberg
 */
abstract class BaseJourneyAction extends BaseMoskitoUIAction{
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
