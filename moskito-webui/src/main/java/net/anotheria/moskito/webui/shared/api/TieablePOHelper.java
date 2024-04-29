package net.anotheria.moskito.webui.shared.api;

import jakarta.servlet.http.HttpServletRequest;

public class TieablePOHelper {
    public static void parseHttpRequest(TieablePO po, HttpServletRequest request) {
        po.setProducerId(request.getParameter("producerId"));
        po.setValueName(request.getParameter("valueName"));
        po.setUnit(request.getParameter("unit"));
        po.setName(request.getParameter("name"));
        po.setInterval(request.getParameter("interval"));
        po.setStatName(request.getParameter("statName"));
    }
}
