package net.anotheria.moskito.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * This filter dump out to sysout all headers, parameters and attributes of request, session id and session's attributes if url
 * have a special parameter mskDiagnosticsDebugRequest.
 * @author asamoilich.
 */
public class DebugRequestFilter implements Filter {

    private static final String PARAM_DIAGNOSTIC_DEBUG = "mskDiagnosticsDebugRequest";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || request.getParameter(PARAM_DIAGNOSTIC_DEBUG) == null){
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("headers:");
        Enumeration headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            System.out.println(headerName+" = "+req.getHeader(headerName));
        }
        System.out.println("attributes:");
        Enumeration attributeNames = req.getAttributeNames();
        while(attributeNames.hasMoreElements()) {
            String attributeName = (String)attributeNames.nextElement();
            System.out.println(attributeName+" = "+req.getAttribute(attributeName));
        }
        System.out.println("parameters:");
        Enumeration parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String parameterName = (String)parameterNames.nextElement();
            System.out.println(parameterName+" = "+req.getParameter(parameterName));
        }
        HttpSession session = req.getSession(false);
        if(session != null){
            System.out.println("Session = "+session.getId());
            System.out.println("sessionAttributes:");
            Enumeration sessionAttributeNames = session.getAttributeNames();
            while(sessionAttributeNames.hasMoreElements()) {
                String sessionAttributeName = (String)sessionAttributeNames.nextElement();
                System.out.println(sessionAttributeName+" = "+session.getAttribute(sessionAttributeName));
            }
        }
        System.out.println("Remote ip = "+req.getRemoteAddr());
        System.out.println("Server name = "+req.getServerName());
        System.out.println("Server port = "+req.getServerPort());
    }

    @Override
    public void destroy() {
    }
}
