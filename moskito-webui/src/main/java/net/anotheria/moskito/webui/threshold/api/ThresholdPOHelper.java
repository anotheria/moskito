package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.moskito.webui.shared.api.TieablePOHelper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class.
 */
public class ThresholdPOHelper {
    public static ThresholdPO fromHttpServletRequest(HttpServletRequest request){
        ThresholdPO ret = new ThresholdPO();
        parseHttpRequest(ret, request);
        return ret;
    }

    private static void parseHttpRequest(ThresholdPO po, HttpServletRequest request){
        TieablePOHelper.parseHttpRequest(po, request);

        po.setYellowDir(request.getParameter("yellowDir"));
        po.setYellowValue(request.getParameter("yellowValue"));
        po.setOrangeDir(request.getParameter("orangeDir"));
        po.setOrangeValue(request.getParameter("orangeValue"));
        po.setRedDir(request.getParameter("redDir"));
        po.setRedValue(request.getParameter("redValue"));
        po.setPurpleDir(request.getParameter("purpleDir"));
        po.setPurpleValue(request.getParameter("purpleValue"));
        po.setGreenDir(request.getParameter("greenDir"));
        po.setGreenValue(request.getParameter("greenValue"));


    }


}
