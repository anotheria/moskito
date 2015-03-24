package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.ConnectivityMode;
import net.anotheria.moskito.webui.util.RemoteInstance;
import net.anotheria.moskito.webui.util.WebUIConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action sets the target server.
 *
 * @author lrosenberg
 * @since 24.03.14 00:33
 */
public class SelectServerAction extends BaseMoskitoUIAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String targetServer = req.getParameter("pTargetServer");
		if (targetServer.equalsIgnoreCase("local")){
			APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.LOCAL);
		}else{
			APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.REMOTE);
			for (RemoteInstance ri : WebUIConfig.getInstance().getRemotes()){
				if (ri.equalsByKey(targetServer)){
					APILookupUtility.setCurrentRemoteInstance(ri);
					break;
				}
			}
		}
		return mapping.redirect();
	}

    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return null;
    }

    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.NONE;
    }
}
