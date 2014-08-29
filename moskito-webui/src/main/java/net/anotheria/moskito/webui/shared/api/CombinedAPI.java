package net.anotheria.moskito.webui.shared.api;

import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import org.distributeme.annotation.CombinedService;
import org.distributeme.annotation.DistributeMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 22:03
 */

@DistributeMe(agentsSupport=false)
@CombinedService(services={ThresholdAPI.class, AccumulatorAPI.class, ThreadAPI.class, ProducerAPI.class, JourneyAPI.class, AdditionalFunctionalityAPI.class})
public class CombinedAPI {
}
