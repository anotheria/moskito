package net.anotheria.moskito.core.config.dashboards;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 00:58
 */
@ConfigureMe
public class DashboardsConfig implements Serializable {
	@Configure private DashboardConfig[] dashboards;

	public DashboardConfig[] getDashboards() {
		return dashboards;
	}

	public void setDashboards(DashboardConfig[] dashboards) {
		this.dashboards = dashboards;
	}

	@Override
	public String toString() {
		return "DashboardsConfig{" +
				"dashboards=" + Arrays.toString(dashboards) +
				'}';
	}
}
