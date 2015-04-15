package net.anotheria.moskito.webui.shared.resource;

import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;

/**
 * Main class for all resources.
 *
 * @author lrosenberg
 * @since 11.02.13 18:22
 */
public abstract class AbstractResource {
	protected ProducerAPI getProducerAPI(){
		return APILookupUtility.getProducerAPI();
	}

	protected ThresholdAPI getThresholdAPI(){
		return APILookupUtility.getThresholdAPI();
	}

	protected JourneyAPI getJourneyAPI(){
		return APILookupUtility.getJourneyAPI();
	}

	protected AccumulatorAPI getAccumulatorAPI(){
		return APILookupUtility.getAccumulatorAPI();
	}

	protected GaugeAPI getGaugeAPI() {
		return APILookupUtility.getGaugeAPI();
	}

	protected DashboardAPI getDashboardAPI(){ return APILookupUtility.getDashboardAPI(); }

	protected AdditionalFunctionalityAPI getAdditionalFunctionalityAPI(){
		return APILookupUtility.getAdditionalFunctionalityAPI();
	}

}
