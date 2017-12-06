package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPIFactory;
import net.anotheria.moskito.webui.auth.api.AuthApi;
import net.anotheria.moskito.webui.auth.api.AuthApiFactory;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPIFactory;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPIFactory;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPIFactory;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPIFactory;
import net.anotheria.moskito.webui.tags.api.TagAPI;
import net.anotheria.moskito.webui.tags.api.TagAPIFactory;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.threads.api.ThreadAPIFactory;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPIFactory;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;
import net.anotheria.moskito.webui.tracers.api.TracerAPIFactory;

/**
 * Initializer for APIFactory for the APILayer. Must be called once at application start. Future versions of ano-plass
 * promise automatical api binding, but for now this class is needed.
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
		APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
		APIFinder.addAPIFactory(JourneyAPI.class, new JourneyAPIFactory());
		APIFinder.addAPIFactory(ThreadAPI.class,  new ThreadAPIFactory());
		APIFinder.addAPIFactory(ProducerAPI.class, new ProducerAPIFactory());
		APIFinder.addAPIFactory(AdditionalFunctionalityAPI.class, new AdditionalFunctionalityAPIFactory());
		APIFinder.addAPIFactory(GaugeAPI.class, new GaugeAPIFactory());
		APIFinder.addAPIFactory(DashboardAPI.class, new DashboardAPIFactory());
		APIFinder.addAPIFactory(TracerAPI.class, new TracerAPIFactory());
		APIFinder.addAPIFactory(AuthApi.class, new AuthApiFactory());
		APIFinder.addAPIFactory(TagAPI.class, new TagAPIFactory());

	}
}
