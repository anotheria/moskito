package net.anotheria.moskito.webui.threshold.util;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;

import java.util.ArrayList;
import java.util.List;

import static net.anotheria.moskito.webui.util.APILookupUtility.getDashboardAPI;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24/11/2016 15:40
 */
public class ThresholdStatusBeanUtility {
	public static List<ThresholdStatusBean> getThresholdBeans(List<ThresholdStatusAO> thresholdStatusAOList) throws APIException {
		List<ThresholdStatusBean> ret = new ArrayList<>();
		if (thresholdStatusAOList == null || thresholdStatusAOList.size() == 0)
			return ret;

		for (ThresholdStatusAO thresholdStatusAO : thresholdStatusAOList) {
			String dashboardNames = getDashboardAPI().getDashboardNamesWhichDoNotIncludeThisThreshold(thresholdStatusAO.getName());
			ret.add(new ThresholdStatusBean(thresholdStatusAO, dashboardNames));
		}

		return ret;
	}

}
