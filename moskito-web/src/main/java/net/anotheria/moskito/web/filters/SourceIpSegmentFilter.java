package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.web.MoskitoFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This filter separates the traffic by first part of the ip address.
 *
 * @author lrosenberg
 * @since 25.04.13 18:21
 */
public class SourceIpSegmentFilter extends MoskitoFilter {
	/**
	 * Limit for the ip length. This limit is simple precaution in case someone is sending constructed source ip header
	 * or X_FORWARDED_FOR header.
	 */
	public static final int IP_LENGTH_LIMIT = 20;

	@Override
	protected String extractCaseName(ServletRequest req, ServletResponse res) {
		if (!(req instanceof HttpServletRequest))
			return null;
		String ip = req.getRemoteAddr();
		if (ip==null || ip.isEmpty())
			ip = "Unknown";
		int indexOfDot = ip.indexOf('.');
		String ret = null;
		if (indexOfDot==-1){
			ret = ip;
		}else{
			ret = ip.substring(0, indexOfDot);
		}
		if (ret.length()>IP_LENGTH_LIMIT){
			ret = ret.substring(0, IP_LENGTH_LIMIT);
		}
		return ret;
	}
}