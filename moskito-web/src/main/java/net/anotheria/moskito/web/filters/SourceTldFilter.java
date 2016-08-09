package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.web.MoskitoFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This filter separates the traffic by source top level domain.
 * <b>WARNING:</b> This producer shouldn't be used in production environment on high traffic sites because
 * the reverse lookup of ip addresses can cost time and the system becomes vulnerable to dns attacks or failures.
 * Use AsyncSourceTldFilter instead.
 * This filter requires reverse lookup to be enabled by the servlet container.
 *
 * @author lrosenberg
 * @since 25.04.13 18:21
 */
public class SourceTldFilter extends MoskitoFilter {
	/**
	 * Limit for the ip length. This limit is simple precaution in case someone is sending constructed source ip header
	 * or X_FORWARDED_FOR header.
	 */
	public static final int TLD_LENGTH_LIMIT = 20;

	@Override
	protected String extractCaseName(ServletRequest req, ServletResponse res) {
		if (!(req instanceof HttpServletRequest))
			return null;
		String address = req.getRemoteHost();
		String ip = req.getRemoteAddr();
		if (address==null || address.isEmpty())
			address = "Unknown";
		if (address.equals(ip))
			return "-unresolved-";
		//TODO check if address == ip?
		int indexOfDot = address.lastIndexOf('.');
		String ret = null;
		if (indexOfDot==-1){
			ret = address;
		}else{
			ret = address.substring(indexOfDot+1);
		}
		if (ret.length()>TLD_LENGTH_LIMIT){
			ret = ret.substring(0, TLD_LENGTH_LIMIT);
		}
		return ret;
	}
}