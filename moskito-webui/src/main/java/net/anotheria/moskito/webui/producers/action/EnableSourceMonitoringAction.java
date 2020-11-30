package net.anotheria.moskito.webui.producers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 2019-08-20 11:55
 */
public class EnableSourceMonitoringAction extends BaseMoskitoUIAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String producerId = getProducerIdParameter(httpServletRequest);
		getProducerAPI().enableSourceMonitoring(producerId);
		setInfoMessage("Source monitoring for producer "+producerId+" enabled");
		httpServletResponse.sendRedirect("mskShowProducer?"+PARAM_PRODUCER_ID+ '=' + URLEncoder.encode(producerId, "UTF-8"));
		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}
}
