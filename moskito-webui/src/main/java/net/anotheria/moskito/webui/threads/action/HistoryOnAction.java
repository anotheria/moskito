package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Switches the history on.
 */
public class HistoryOnAction extends ThreadsHistoryAction{

	@Override
	public ActionCommand execute(ActionMapping mapping,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		getThreadAPI().activateHistory();
		return super.execute(mapping, req, res);
	}

}
