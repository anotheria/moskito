package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.api.LibAO;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.maven.MavenVersionReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.BadAttributeValueExpException;
import javax.management.BadBinaryOpValueExpException;
import javax.management.BadStringOperationException;
import javax.management.InvalidApplicationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * This action scans and displays current libs.
 *
 * @author lrosenberg
 * @since 28.10.12 23:41
 */
public class ShowLibsAction extends BaseAdditionalAction{
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(ShowLibsAction.class);

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<URL> classpath = getClassPathUrls(req.getContextPath());
		List<LibAO> beans = new ArrayList<LibAO>();

		for (URL url : classpath){
			String fileName = url.getFile();
			if (!fileName.endsWith(".jar"))
				continue;
			File f = new File(fileName);
			LibAO bean = new LibAO();
			int lastSlash = fileName.lastIndexOf('/');
			try{
				bean.setName(fileName.substring(lastSlash + 1));
				bean.setMavenVersion(MavenVersionReader.readVersionFromJar(f));
				if (bean.getMavenVersion()==null){
					bean.setLastModified(NumberUtils.makeISO8601TimestampString(f.lastModified()));
				}
			}catch(Exception e){
				log.warn("couldn't obtain lib version, skipped this url "+url, e);
			}
			beans.add(bean);

		}
		req.setAttribute("beansCount", beans.size());
		req.setAttribute("beans", beans);

		return mapping.success();

	}

	private List<URL> getClassPathUrls(final String context){
		List<URL> forTomcat7 = getClassPathUrlsForTomcat7(context);
		if (forTomcat7!=null && forTomcat7.size()>0)
			return forTomcat7;
		List<URL> forTomcat6 = getClassPathUrlsForTomcat6(context);
		if (forTomcat6!=null && forTomcat6.size()>0)
			return forTomcat6;
		//add another lookup methods here.
		return new ArrayList<URL>();

	}

	private List<URL> getClassPathUrlsForTomcat7(final String context){
		return getClassPathUrlsForTomcat(context, "context");
	}

	private List<URL> getClassPathUrlsForTomcat6(final String context){
		return getClassPathUrlsForTomcat(context, "path");
	}

	private List<URL> getClassPathUrlsForTomcat(final String context, final String contextPropertyName){
		List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
		for (MBeanServer s : servers){
			Set<ObjectInstance> instances = s.queryMBeans(null, new QueryExp() {
				@Override
				public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
					String type = name.getKeyProperty("type");
					if (!type.equals("WebappClassLoader"))
						return false;
					if (!name.getDomain().equals("Catalina"))
						return false;
					if (!name.getKeyProperty(contextPropertyName).equals(context))
						return false;
					return true;
				}

				@Override
				public void setMBeanServer(MBeanServer s) {
				}
			});
			if (instances.size()>0){
				try{
					URL[] urls = (URL[])s.getAttribute(instances.iterator().next().getObjectName(), "URLs");
					return Arrays.asList(urls);
				}catch(Exception e){}

			}
		}
		return null;
	}

	@Override
	protected String getPageName() {
		return "libs";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_LIBS;
	}



}
