package net.anotheria.moskito.webui.nowrunning.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.entrypoint.EntryPoint;
import net.anotheria.moskito.core.entrypoint.EntryPointRepository;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

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
		System.out.println("EntryPoints "+entryPoints);
		for (EntryPoint ep : entryPoints){
			EntryPointAO bean = new EntryPointAO();
			bean.setProducerId(ep.getProducerId());
			bean.setCurrentRequestCount(ep.getCurrentRequestCount());
			bean.setTotalRequestCount(ep.getTotalRequestCount());
			ret.add(bean);
		}
		System.out.println("EntryPointsBEANS "+ret);
		return ret;
	}
}
