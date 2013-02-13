package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base action for accumulators.
 * @author lrosenberg
 */
public abstract class BaseAccumulatorsAction extends BaseMoskitoUIAction {
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.ACCUMULATORS;
    }
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "";
    }

}
