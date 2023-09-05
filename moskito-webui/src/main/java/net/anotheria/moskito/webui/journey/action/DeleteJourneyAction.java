package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a journey.
 *
 * @author lrosenberg
 * @since 22.05.14 09:14
 */
public class DeleteJourneyAction extends BaseJourneyAction {

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws APIException {

		String journeyName = req.getParameter("pJourneyName");
		getJourneyAPI().deleteJourney(journeyName);
		return mapping.redirect();
	}


}