package net.anotheria.moskito.webui.nowrunning.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.entrypoint.ActiveMeasurement;
import net.anotheria.moskito.core.entrypoint.EntryPoint;
import net.anotheria.moskito.core.entrypoint.EntryPointRepository;
import net.anotheria.moskito.core.entrypoint.PastMeasurement;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:23
 */
public class NowRunningAPIImpl extends AbstractMoskitoAPIImpl implements NowRunningAPI {
	@Override
	public List<EntryPointAO> getEntryPoints() throws APIException {

		List<EntryPoint> entryPoints = EntryPointRepository.getInstance().getEntryPoints();
		List<EntryPointAO> ret = new LinkedList<>();

		long now = System.currentTimeMillis();

		for (EntryPoint ep : entryPoints){
			EntryPointAO bean = new EntryPointAO();
			bean.setProducerId(ep.getProducerId());
			bean.setCurrentRequestCount(ep.getCurrentRequestCount());
			bean.setTotalRequestCount(ep.getTotalRequestCount());

			List<ActiveMeasurement> measurements = ep.getCurrentMeasurements();
			if (measurements.size()>0){
				List<MeasurementAO> measurementBeans = new LinkedList<>();
				for (ActiveMeasurement m : measurements){
					MeasurementAO mao = new MeasurementAO();
					mao.setAge(now-m.getStartTime());
					mao.setStarttime(m.getStartTime());
					mao.setDescription(m.getDescription());
					measurementBeans.add(mao);
					bean.setCurrentMeasurements(measurementBeans);
				}
			}

			List<PastMeasurement> pastMeasurements = ep.getPastMeasurements();
			if (pastMeasurements.size()>0){
				List<MeasurementAO> pMeasurementBeans = new LinkedList<>();
				for (PastMeasurement m : pastMeasurements){
					MeasurementAO mao = new MeasurementAO();
					mao.setAge(now-m.getStartTime());
					mao.setStarttime(m.getStartTime());
					mao.setDescription(m.getDescription());
					mao.setEndtime(m.getEndtime());
					mao.setDuration(m.getDuration());
					pMeasurementBeans.add(mao);
					bean.setPastMeasurements(pMeasurementBeans);
				}
			}




			ret.add(bean);
		}
		return ret;
	}

	@Override
	public int getNowRunningCount() throws APIException {
		return EntryPointRepository.getInstance().getNowRunningCount();
	}

	@Override
	public void removePastMeasurement(String producerId, String measurementPosition) throws APIException {
		if(StringUtils.isNotBlank(producerId) && StringUtils.isNotBlank(measurementPosition)) {
			EntryPointRepository.getInstance().removePastMeasurement(producerId, Integer.valueOf(measurementPosition));
		}
	}
}
