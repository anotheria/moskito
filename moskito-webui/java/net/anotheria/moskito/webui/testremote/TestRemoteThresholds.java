package net.anotheria.moskito.webui.testremote;

import net.anotheria.moskito.webui.shared.api.MoskitoAPIInitializer;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 09:39
 */
public class TestRemoteThresholds {
	public static void main(String a[]) throws Exception{
		MoskitoAPIInitializer.initialize();
		ThresholdAPI api = APILookupUtility.getThresholdAPI();
		System.out.println("Remote status is "+api.getWorstStatus());
	}
}
