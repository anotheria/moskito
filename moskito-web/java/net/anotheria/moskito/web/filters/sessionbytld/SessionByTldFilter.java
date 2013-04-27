package net.anotheria.moskito.web.filters.sessionbytld;

import net.anotheria.moskito.web.filters.SourceTldFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.13 23:40
 */
public class SessionByTldFilter implements Filter{
	public static final String ATT_NAME = "_MoSKito_SessionByTldFilter_TLD";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		if (req.getSession(false)==null){
			chain.doFilter(request, response);
			return;
		}

		//Only put attribute once.
		HttpSession session = req.getSession();
		if (session.getAttribute(ATT_NAME)!=null){
			chain.doFilter(request, response);
			return;
		}

		String ip = req.getRemoteAddr();
		//recheck if the attribute is still not there, and if so, put a temporarly attribute to prevent duplicate session counting.
		synchronized (session){
			if (session.getAttribute(ATT_NAME)!=null){
				chain.doFilter(request, response);
				return;
			}
			session.setAttribute(ATT_NAME, ip);
		}

		String hostName;

		try{
			hostName = InetAddress.getByName(ip).getHostName();
			int indexOfTld = hostName.lastIndexOf('.');
			if (indexOfTld!=-1){
				hostName = hostName.substring(indexOfTld+1);
			}
			if (hostName.length()> SourceTldFilter.TLD_LENGTH_LIMIT){
				hostName = hostName.substring(0, SourceTldFilter.TLD_LENGTH_LIMIT);
			}

			if (Character.isDigit(hostName.charAt(hostName.length()-1))){
				hostName = "-unresolved-";
			}
		}catch(Exception e){
			hostName = "ERROR";
		}
		session.setAttribute(ATT_NAME, hostName);
		SessionByTldProducer.INSTANCE.sessionCreated(hostName);

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
	}
}
