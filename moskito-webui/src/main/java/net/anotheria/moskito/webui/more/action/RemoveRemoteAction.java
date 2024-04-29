package net.anotheria.moskito.webui.more.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.util.WebUIConfig;

public class RemoveRemoteAction extends ShowRemotesAction{
    @Override
    public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String remoteName = httpServletRequest.getParameter("pName");
        WebUIConfig.getInstance().removeRemote(remoteName);
        return actionMapping.redirect();
    }
}
