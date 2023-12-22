package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.moskito.webui.shared.api.TieablePOHelper;

import javax.servlet.http.HttpServletRequest;

public class AccumulatorPOHelper {
    public static AccumulatorPO fromHttpServletRequest(HttpServletRequest request){
        AccumulatorPO ret = new AccumulatorPO();
        TieablePOHelper.parseHttpRequest(ret, request);
        return ret;
    }

}
