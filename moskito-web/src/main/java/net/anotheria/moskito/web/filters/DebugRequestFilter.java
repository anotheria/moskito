package net.anotheria.moskito.web.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * This filter dump out to sysOut all headers, parameters and attributes of request, session id and session's attributes
 * if url have a special parameter mskDiagnosticsDebugRequest.
 *
 * @author asamoilich.
 */
public class DebugRequestFilter implements Filter {

    /**
     * Logger util.
     */
    private static Logger logger = LoggerFactory.getLogger(DebugRequestFilter.class);
    /**
     * If parameter with this name is presented in request than doFilter method will be executed.
     */
    private static final String PARAM_DIAGNOSTIC_DEBUG = "mskDiagnosticsDebugRequest";
    /**
     * Constant for request headers.
     */
    private static final String HEADERS = "headers:";
    /**
     * Constant for request attributes.
     */
    private static final String ATTRIBUTES = "attributes:";
    /**
     * Constant for request parameters.
     */
    private static final String PARAMETERS = "parameters:";
    /**
     * Constant for session attributes.
     */
    private static final String SESSION_ATTRIBUTES = "sessionAttributes:";

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest) || req.getParameter(PARAM_DIAGNOSTIC_DEBUG) == null) {
            chain.doFilter(req, response);
            return;
        }
        debug((HttpServletRequest) req);
        chain.doFilter(req, response);
    }

    /**
     * Dump out to sysOut request parameters.
     *
     * @param request provided {@link HttpServletRequest}
     */
    private void debug(final HttpServletRequest request) {
        if (!logger.isInfoEnabled()) {
            logger.warn("Logger INTO not enabled!");
            return;
        }

        logger.info(HEADERS);
        Enumeration headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String elementName = (String) headerEnumeration.nextElement();
            logger.info(elementName + " = " + request.getHeader(elementName));
        }

        logger.info(ATTRIBUTES);
        Enumeration attributeEnumeration = request.getAttributeNames();
        while (attributeEnumeration.hasMoreElements()) {
            String elementName = (String) attributeEnumeration.nextElement();
            logger.info(elementName + " = " + request.getAttribute(elementName));
        }

        logger.info(PARAMETERS);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            StringBuilder parameterValues = new StringBuilder();
            if (entry.getValue() != null)
                for (String value : entry.getValue()) {
                    parameterValues.append(value);
                    parameterValues.append(' ');
                }
            logger.info(entry.getKey() + " = " + parameterValues);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Session = " + session.getId());
            logger.info(SESSION_ATTRIBUTES);

            Enumeration sessionAttrEnumeration = session.getAttributeNames();
            while (sessionAttrEnumeration.hasMoreElements()) {
                String elementName = (String) sessionAttrEnumeration.nextElement();
                logger.info(elementName + " = " + session.getAttribute(elementName));
            }
        }
        logger.info("Remote ip = " + request.getRemoteAddr());
        logger.info("Server name = " + request.getServerName());
        logger.info("Server port = " + request.getServerPort());
    }

    @Override
    public void destroy() {
    }
}
