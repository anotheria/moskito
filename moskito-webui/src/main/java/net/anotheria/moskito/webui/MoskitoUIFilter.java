package net.anotheria.moskito.webui;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskito.webui.auth.AuthConstants;
import net.anotheria.moskito.webui.auth.api.AuthApi;
import net.anotheria.moskito.webui.plugins.PluginMappingsConfigurator;
import net.anotheria.moskito.webui.shared.api.MoskitoAPIInitializer;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.VersionUtil;
import net.anotheria.moskito.webui.util.WebUIConfig;
import net.anotheria.net.util.NetUtils;
import net.anotheria.util.maven.MavenVersion;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * MoskitoUI Filter is the main entering point of the Moskito Web User Interface.
 * @author lrosenberg
 *
 */
public class MoskitoUIFilter extends MAFFilter{
	
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(MoskitoUIFilter.class);
	
	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";

	/**
	 * Check, is given url string refers to some of authorization
	 * pages or actions
	 * @param url url string
	 * @return true - url is leads to authorization pages or actions
	 * 		   false - no
	 */
	private boolean isAuthAction(String url){
		return ArrayUtils.contains(AuthConstants.LOGIN_PAGES, url);
	}

	/**
	 * Checks, is request contains authorization cookie
	 * and this cookie is valid.
	 * @param request current servlet request object
	 * @return true - user has valid auth cookie
	 *         false - no
	 */
	private boolean hasAuthCookie(HttpServletRequest request){

		final AuthApi api = APILookupUtility.getAuthApi();

		for(Cookie cookie : request.getCookies())
			if(cookie.getName().equals(AuthConstants.AUTH_COOKIE_NAME)){

				try {
					return api.userExists(
                           cookie.getValue()
                    );
				} catch (APIException e) {
					log.error("Failed to decrypt user authorization cookie", e);
					return false;
				}

			}

		return false;

	}

	/**
	 * Checks user authorization if it enabled in config
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)){
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		HttpServletResponse httpServletResponse = ((HttpServletResponse) response);

		if(WebUIConfig.getInstance().isAuthenticationEnabled() &&
				!isAuthAction(httpServletRequest.getRequestURI()) &&
				!Boolean.TRUE.equals(httpServletRequest.getSession(true).getAttribute(AuthConstants.AUTH_SESSION_ATTR_NAME))){

            if(hasAuthCookie(httpServletRequest)){
				httpServletRequest.getSession().setAttribute(AuthConstants.AUTH_SESSION_ATTR_NAME, true);
				super.doFilter(request, response, chain);
			}
            else {
            	// Reset auth cookie for cases it was corrupted (encryption key is changed or something like that)
            	Cookie authCookie = new Cookie(AuthConstants.AUTH_COOKIE_NAME, "");
            	authCookie.setMaxAge(0);
				httpServletResponse.addCookie(authCookie);

				httpServletRequest.getSession().setAttribute(
						AuthConstants.LOGIN_REFER_PAGE_SESSION_ATTR_NAME,
						// User be redirected to this url after success authorization
						httpServletRequest.getRequestURL().toString()
				);
				// Redirect to login page
				httpServletResponse.sendRedirect("/moskito-inspect/mskSignIn");
			}

		}
		else
			super.doFilter(request, response, chain);

	}

	@Override public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.info("Initializing MoSKito WebUI...");
		MoskitoAPIInitializer.initialize();

		try{
			MavenVersion moskitoVersion = VersionUtil.getWebappLibVersion(config.getServletContext(), "moskito-webui");
			MavenVersion appVersion = VersionUtil.getWebappVersion(config.getServletContext());
			config.getServletContext().setAttribute("application_maven_version", appVersion == null ? "?" : appVersion);
			config.getServletContext().setAttribute("moskito_maven_version", moskitoVersion == null ? "?" : moskitoVersion);
			config.getServletContext().setAttribute("moskito_version_string", (moskitoVersion == null || moskitoVersion.getVersion().length()==0)? "unknown" : moskitoVersion.getVersion());
			String computerName = NetUtils.getComputerName();
			config.getServletContext().setAttribute("servername", computerName==null ? "Unknown" : computerName);
		}catch(Exception e){
			log.error("init("+config+ ')', e);
		}

		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter!=null && pathToImagesParameter.length()>0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);
	}

 
	@Override public String getProducerId() {
		return "moskitoUI";
	}

	@Override public String getSubsystem() {
		return "monitoring";
	}

	@Override public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators(){
		return Arrays.asList(new MoskitoMappingsConfigurator(), new PluginMappingsConfigurator());
	}


	@Override
	protected String getDefaultActionName() {
		return "mskShowAllProducers";
	}

}
