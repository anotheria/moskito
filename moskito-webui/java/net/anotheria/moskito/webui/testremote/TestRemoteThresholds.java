package net.anotheria.moskito.webui.testremote;

import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.generated.RemoteThresholdAPIStub;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 09:39
 */
public class TestRemoteThresholds {
	public static void main(String a[]) throws Exception{
		ThresholdAPI api = new RemoteThresholdAPIStub();
		System.out.println("Remote status is "+api.getWorstStatus());
	}
}
