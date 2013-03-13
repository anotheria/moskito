package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.more.bean.MBeanAttributeWrapper;
import net.anotheria.moskito.webui.more.bean.MBeanWrapperBean;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.03.13 00:57
 */
public class ShowMBeansAction extends AdditionalSectionAction{
    
    /**
     * the local logger.
     */
    private static final Logger LOG = Logger.getLogger(ShowMBeansAction.class); 
    
	/**
	 * @{inheritDoc}
	 */
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
		List<MBeanWrapperBean> beans = new ArrayList<MBeanWrapperBean>();

		for (MBeanServer s : servers){
			Set<ObjectInstance> instances = s.queryMBeans(null, null);
			for (ObjectInstance oi : instances){
				MBeanWrapperBean bean = new MBeanWrapperBean();
				bean.setClassName(oi.getClassName());
				ObjectName name = oi.getObjectName();
				bean.setDomain(name.getDomain());
				bean.setCanonicalName(name.getCanonicalName());
				String type = name.getKeyProperty("type");
				if (type!=null){
					bean.setType(type);
				}

				MBeanInfo info = ManagementFactory.getPlatformMBeanServer().getMBeanInfo(name);
				if (info!=null){
					bean.setDescription(info.getDescription());
					bean.setAttributes(convert(info.getAttributes(), s, name));
					bean.setOperations(Arrays.asList(info.getOperations()));
				}
				beans.add(bean);
			}
		}

		req.setAttribute("mbeansCount", beans.size());
		beans = StaticQuickSorter.sort(beans, new DummySortType());
		req.setAttribute("mbeans", beans);

		return mapping.success();
	}
	
    /**
     * @param infos
     *            the {@link MBeanAttributeInfo} to wrap
     * @param server
     *            {@link MBeanServer} where to find the MBean values
     * @param name
     *            {@link ObjectName} where to find the MBean values
     * @return the converted list of {@link MBeanAttributeWrapper}s
     */
    private List<MBeanAttributeWrapper> convert(final MBeanAttributeInfo[] infos,
            final MBeanServer server, final ObjectName name) {
        final List<MBeanAttributeWrapper> res = new ArrayList<MBeanAttributeWrapper>();

        for (final MBeanAttributeInfo info : infos) {
            Object value = "-";
            try {
                value = server.getAttribute(name, info.getName());

                // CHECKSTYLE:OFF - we have to catch ALL exceptions
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                LOG.debug("unable to read MBean: " + e.getLocalizedMessage());
            }

            res.add(new MBeanAttributeWrapper(info, value));
        }

        return res;
    }
	
}
