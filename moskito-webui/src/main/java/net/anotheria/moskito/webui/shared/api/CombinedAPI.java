package net.anotheria.moskito.webui.shared.api;

import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.tags.api.TagAPI;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;
import org.distributeme.annotation.CombinedService;
import org.distributeme.annotation.DistributeMe;

/**
 * This interface is used for DistributeMe as Marker of all required services. If a new API is added to the project, it MUST be added here too.
 *
 * @author lrosenberg
 * @since 21.03.14 22:03
 */

@DistributeMe(agentsSupport = false, moskitoSupport=false)
@CombinedService(
		services = {ThresholdAPI.class, AccumulatorAPI.class, ThreadAPI.class, ProducerAPI.class, JourneyAPI.class, AdditionalFunctionalityAPI.class, GaugeAPI.class,
				DashboardAPI.class, TracerAPI.class, TagAPI.class})
public class CombinedAPI {
}
