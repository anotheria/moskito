package net.anotheria.moskito.webui.tracers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.tracer.Tracer;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:42
 */
public class TracerAPIImpl extends AbstractMoskitoAPIImpl implements TracerAPI{
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

		return ret;
	}

	@Override
	public TracerAO getTracer(String producerId) throws APIException {
		return null;
	}

	@Override
	public TracerLogAO getTracerLog(String producerId) throws APIException {
		return null;
	}

	@Override
	public void removeTracer(String producerId) throws APIException {
		TracerRepository.getInstance().removeTracer(producerId);
	}

	@Override
	public void disableTracer(String producerId) throws APIException {
		TracerRepository.getInstance().disableTrackingForProducerId(producerId);
	}
}
