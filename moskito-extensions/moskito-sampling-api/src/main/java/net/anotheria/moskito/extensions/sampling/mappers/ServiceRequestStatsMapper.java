package net.anotheria.moskito.extensions.sampling.mappers;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.extensions.sampling.Sample;
import net.anotheria.moskito.extensions.sampling.StatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.04.15 23:42
 */
public class ServiceRequestStatsMapper implements StatsMapper{

	private static Logger log = LoggerFactory.getLogger(ServiceRequestStatsMapper.class);

	@Override
	public IOnDemandStatsFactory getFactory() {
		return ServiceStatsFactory.DEFAULT_INSTANCE;
	}

	public void updateStats(IStats statsObject, Sample sample){
		ServiceStats stats = (ServiceStats)statsObject;
		String totalTimeValue = getTotalTimeValue(sample);
		if (totalTimeValue == null) {
			log.warn("Can't retrieve total time from sample " + sample + ", ignoring sample");
			return;
		}
		try {
			long totalTime = Long.parseLong(totalTimeValue);
			stats.addExecutionTime(totalTime);
			stats.addRequest();
			stats.notifyRequestFinished();
			String errorFlag = getErrorFlag(sample);
			if (errorFlag != null && errorFlag.equals("true"))
				stats.notifyError();

		}catch(NumberFormatException e){
			log.warn("can't parse total time value "+totalTimeValue, e);
		}
	}

	protected String getTotalTimeValue(Sample sample){
		String value = sample.getValues().get("tt");
		if (value == null )
			value = sample.getValues().get("time");
		if (value == null )
			value = sample.getValues().get("totaltime");
		return value;
	}

	protected String getErrorFlag(Sample sample){
		String value = sample.getValues().get("error");
		if (value == null){
			value = sample.getValues().get("err");
		}
		return value;
	}
}
