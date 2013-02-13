package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.13 09:53
 */
public class MoskitoAPIInitializer {
	private static volatile boolean initialized = false;

	public static synchronized void initialize(){
		if (initialized)
			return;
		initialized = true;

		APIFinder.addAPIFactory(ThresholdAPI.class, new ThresholdAPIFactory());
	}
}
