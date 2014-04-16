package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.ConnectivityMode;
import net.anotheria.moskito.webui.util.RemoteInstance;
import net.anotheria.moskito.webui.util.WebUIConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 16.04.14 15:35
 */
public class QuickConnectAction extends AbstractAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String server = req.getParameter("pServerName");
		if (server==null || server.length()==0)
			throw new IllegalArgumentException("Server name can't be null or empty.");
		String sPort = req.getParameter("pServerPort");
		if (sPort==null || sPort.length()==0)
			throw new IllegalArgumentException("Server port can't be null or empty.");
		int port = -1;
		try{
			port = Integer.parseInt(sPort);
		}catch(NumberFormatException e){
			throw new IllegalArgumentException("Port "+sPort+" is not a number.");
		}
		RemoteInstance newRemoteInstance = new RemoteInstance();
		newRemoteInstance.setHost(server);
		newRemoteInstance.setPort(port);
		WebUIConfig.getInstance().addRemote(newRemoteInstance);
		APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.REMOTE);
		APILookupUtility.setCurrentRemoteInstance(newRemoteInstance);

		return mapping.redirect();
	}
}
