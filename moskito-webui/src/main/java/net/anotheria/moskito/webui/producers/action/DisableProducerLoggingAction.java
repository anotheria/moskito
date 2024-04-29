package net.anotheria.moskito.webui.producers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 2019-08-20 12:02
 */
public class DisableProducerLoggingAction extends BaseMoskitoUIAction {
	@Override
	public ActionCommand execute(ActionMapping actionMapping,  HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String producerId = getProducerIdParameter(httpServletRequest);
		getProducerAPI().disableLogging(producerId);
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
