package net.anotheria.moskito.core.config.dashboards;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration of Dashboards. This is a container class for single dashboards.
 *
 * @author lrosenberg
 * @since 12.02.15 00:58
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class DashboardsConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -5087792254656485967L;

	/**
	 * Contained dashboards.
	 */
	@SerializedName("@dashboards")
	@Configure private DashboardConfig[] dashboards;

	public DashboardConfig[] getDashboards() {
		return dashboards;
	}

	public void setDashboards(DashboardConfig[] dashboards) {
		this.dashboards = dashboards;
	}

	public boolean containsDashboard(String dashboardName){
		if (dashboards==null || dashboards.length==0){
			return false;
		}
		for (DashboardConfig c : dashboards){
			if  (dashboardName.equals(c.getName()))
				return true;
		}
		return false;
	}

	/**
	 * This method allows some part of the code to configure and add custom dashboard. Warning dashboards added this way
	 * would be reset if configuration changes, we will need a mechanism for this later (TODO).
	 * @param toAdd
	 */
	public void addDashboard(DashboardConfig toAdd){
		if (dashboards.length == 0){
			dashboards = new DashboardConfig[1];
			dashboards[0] = toAdd;
			return;
		}

		DashboardConfig[] new_dashboards = Arrays.copyOf(dashboards, dashboards.length+1);
		new_dashboards[dashboards.length] = toAdd;
		dashboards = new_dashboards;
	}

	@Override
	public String toString() {
		return "DashboardsConfig{" +
				"dashboards=" + Arrays.toString(dashboards) +
				'}';
	}
}
