package net.anotheria.moskito.webui.more.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.webui.plugins.action.BasePluginAction;
import net.anotheria.moskito.webui.shared.api.PluginAO;
import net.anotheria.moskito.webui.shared.bean.LabelValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.RemoteInstance;
import net.anotheria.moskito.webui.util.WebUIConfig;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ShowRemotesAction extends BaseAdditionalAction {
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskRemotes";
    }

    @Override
    public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        RemoteInstance[] remotes = WebUIConfig.getInstance().getRemotes();
        List<LabelValueBean> beans = new LinkedList<>();
        if (remotes.length>0){
            for (RemoteInstance remote : remotes){
                beans.add(new LabelValueBean(remote.toString(), remote.getSelectKey()));
            }
        }
        httpServletRequest.setAttribute("remotes", beans);

        return actionMapping.success();
    }

    protected NaviItem getCurrentSubNaviItem() {
        return NaviItem.MORE_REMOTES;
    }

}
