package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Switches the history on.
 */
public class HistoryOnAction extends ThreadsHistoryAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ThreadHistoryUtility.INSTANCE.activate();
		return super.execute(mapping, formBean, req, res);
	}

}
