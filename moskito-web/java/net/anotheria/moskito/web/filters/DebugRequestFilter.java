package net.anotheria.moskito.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * This filter dump out to sysout all headers, parameters and attributes of request, session id and session's attributes if url
 * have a special parameter mskDiagnosticsDebugRequest.
 * @author asamoilich.
 */
public class DebugRequestFilter implements Filter {

    private static final String PARAM_DIAGNOSTIC_DEBUG = "mskDiagnosticsDebugRequest";

    private HttpServletRequest request;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private enum RequestParamType{
        HEADERS("headers:"),
        ATTRIBUTES("attributes:"),
        PARAMETERS("parameters:"),
        SESSION_ATTRIBUTES("sessionAttributes:");

        private String value;

        private RequestParamType(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest) || req.getParameter(PARAM_DIAGNOSTIC_DEBUG) == null){
            chain.doFilter(req, response);
            return;
        }
        request = (HttpServletRequest) req;
        sysOut(RequestParamType.HEADERS);
        sysOut(RequestParamType.ATTRIBUTES);
        sysOut(RequestParamType.PARAMETERS);
        HttpSession session = getSession();
        if(session != null){
            System.out.println("Session = "+session.getId());
            sysOut(RequestParamType.SESSION_ATTRIBUTES);
        }
        System.out.println("Remote ip = "+req.getRemoteAddr());
        System.out.println("Server name = "+req.getServerName());
        System.out.println("Server port = "+req.getServerPort());
    }

    private void sysOut(RequestParamType type){
        System.out.println(type.getValue());
        Enumeration enumeration = getEnumerationByType(type);
        while(enumeration.hasMoreElements()) {
            String elementName = (String)enumeration.nextElement();
            System.out.println(elementName+" = "+ getElementValueFromRequestByType(elementName, type));
        }
    }

    private Enumeration getEnumerationByType(RequestParamType type){
        switch (type){
            case HEADERS:
                return request.getHeaderNames();
            case ATTRIBUTES:
                return request.getAttributeNames();
            case PARAMETERS:
                return request.getParameterNames();
            case SESSION_ATTRIBUTES:
                return getSession() == null ? Collections.emptyEnumeration() : getSession().getAttributeNames();
        }
        return Collections.emptyEnumeration();
    }

    private Object getElementValueFromRequestByType(String elementName, RequestParamType type){
        switch (type){
            case HEADERS:
                return request.getHeader(elementName);
            case ATTRIBUTES:
                return request.getAttribute(elementName);
            case PARAMETERS:
                return request.getParameter(elementName);
            case SESSION_ATTRIBUTES:
                return getSession() == null ? "" : getSession().getAttribute(elementName);
        }
        return "";
    }

    private HttpSession getSession(){
        return request.getSession(false);
    }

    @Override
    public void destroy() {
    }
}
