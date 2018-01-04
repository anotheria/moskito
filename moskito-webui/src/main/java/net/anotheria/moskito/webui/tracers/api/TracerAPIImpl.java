package net.anotheria.moskito.webui.tracers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.Tracer;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.util.TagsUtil;
import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the TracerAPI.
 *
 * @author lrosenberg
 * @since 05.05.15 00:42
 */
public class TracerAPIImpl extends AbstractMoskitoAPIImpl implements TracerAPI {

	private final static Logger log = LoggerFactory.getLogger(TracerAPIImpl.class);

	private JourneyManager journeyManager;

	@Override
	public void init() throws APIInitException {
		super.init();
		this.journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	@Override
	public void createTracer(String producerId) throws APIException {
		TracerRepository.getInstance().enableTracingForProducerId(producerId);
	}

	@Override
	public List<TracerAO> getTracers() throws APIException {
		List<Tracer> tracers = TracerRepository.getInstance().getTracers();
		if (tracers == null || tracers.size() == 0)
			return Collections.emptyList();

		LinkedList<TracerAO> ret = new LinkedList<TracerAO>();
		for (Tracer t : tracers){
			TracerAO ao = tracer2AO(t);

			ret.add(ao);
		}
		return ret;
	}

	private TracerAO tracer2AO(Tracer t){
		TracerAO ret = new TracerAO();

		ret.setEnabled(t.isEnabled());
		ret.setProducerId(t.getProducerId());
		ret.setEntryCount(t.getEntryCount());
		ret.setTotalEntryCount(t.getTotalEntryCount());

		return ret;
	}

	@Override
	public TracerAO getTracer(String producerId) throws APIException {
		Tracer tracer = TracerRepository.getInstance().getTracer(producerId);
		return tracer == null ? null : tracer2AO(tracer);
	}

	@Override
	public List<TraceAO> getTraces(String producerId, TimeUnit timeUnit) throws APIException {
		List<Trace> traces = TracerRepository.getInstance().getTraces(producerId);
		LinkedList<TraceAO> ret = new LinkedList<TraceAO>();

		Journey producerJourney = null;

		try {
			producerJourney = journeyManager.getJourney(
                    Tracers.getJourneyNameForTracers(producerId)
            );
		} catch (NoSuchJourneyException e) {
			log.debug("Failed to find journey for producer " + producerId +
					". This may occur because producer is not traced yet. ", e);
		}

		for (Trace t : traces) {

			TraceAO ao  = new TraceAO();

			ao.setId(String.valueOf(t.getId()));
			ao.setCall(t.getCall());
			ao.setElements(Arrays.asList(t.getElements()));
			ao.setDuration(timeUnit.transformNanos(t.getDuration()));
            ao.setCreated(NumberUtils.makeISO8601TimestampString(t.getCreatedTimestamp()));

            if(producerJourney != null) {

				CurrentlyTracedCall tracedCall = producerJourney.getTracedCalls().get((int) t.getId() - 1);

				ao.setTags(
						TagsUtil.tagsMapToTagEntries(tracedCall.getTags())
				);

			}

			ret.add(ao);

		}

		return ret;

	}

	@Override
	public void removeTracer(String producerId) throws APIException {
		TracerRepository.getInstance().removeTracer(producerId);
	}

	@Override
	public void disableTracer(String producerId) throws APIException {
		TracerRepository.getInstance().disableTrackingForProducerId(producerId);
	}

	@Override
	public void enableTracer(String producerId) throws APIException {
		TracerRepository.getInstance().enableTracingForProducerId(producerId);
	}
}
