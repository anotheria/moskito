package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.more.bean.MBeanWrapperBean;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

				MBeanInfo info = ManagementFactory.getPlatformMBeanServer().getMBeanInfo(name);
				if (info!=null){
					bean.setDescription(info.getDescription());
					bean.setAttributes(Arrays.asList(info.getAttributes()));
					bean.setOperations(Arrays.asList(info.getOperations()));
				}
				beans.add(bean);
			}
		}

		req.setAttribute("mbeansCount", beans.size());
		req.setAttribute("mbeans", beans);

		return mapping.success();
	}
}
