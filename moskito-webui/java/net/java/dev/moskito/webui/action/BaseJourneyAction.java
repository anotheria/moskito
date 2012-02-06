package net.java.dev.moskito.webui.action;

import net.java.dev.moskito.core.journey.JourneyManager;
import net.java.dev.moskito.core.journey.JourneyManagerFactory;
import net.java.dev.moskito.webui.bean.NaviItem;

abstract class BaseJourneyAction extends BaseMoskitoUIAction{
	private JourneyManager journeyManager;

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
