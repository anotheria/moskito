package net.anotheria.moskito.webui.errors.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

/**
 * @author strel
 */
public abstract class BaseErrorAction extends BaseMoskitoUIAction {

    protected BaseErrorAction(){
    }

    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.ERRORS;
    }

}
