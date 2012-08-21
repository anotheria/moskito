package net.java.dev.moskito.webui.action.accumulators;

import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;
import net.java.dev.moskito.webui.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base action for accumulators.
 * @author lrosenberg
 */
public abstract class BaseAccumulatorsAction extends BaseMoskitoUIAction{
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.ACCUMULATORS;
    }
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "";
    }

}
