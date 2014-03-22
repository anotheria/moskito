package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:49
 */
public class ProducerAPIImpl extends AbstractMoskitoAPIImpl implements ProducerAPI{
	private IProducerRegistryAPI producerRegistryAPI;

	@Override
	public void init() throws APIInitException {
		super.init();

		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
	}

	@Override
	public List<UnitCountAO> getCategories() {
		List<String> categories = producerRegistryAPI.getCategories();
		List<UnitCountAO> categoriesAO = new ArrayList<UnitCountAO>(categories.size());
		for (String catName : categories){
			categoriesAO.add(new UnitCountAO(catName, producerRegistryAPI.getAllProducersByCategory(catName).size()));
		}
		return categoriesAO;
	}

	@Override
	public List<UnitCountAO> getSubsystems() {
		List<String> subsystems = producerRegistryAPI.getSubsystems();
		List<UnitCountAO> subsystemsAO = new ArrayList<UnitCountAO>(subsystems.size());
		for (String subName : subsystems){
			subsystemsAO.add(new UnitCountAO(subName, producerRegistryAPI.getAllProducersBySubsystem(subName).size()));
		}
		return subsystemsAO;
	}

	@Override
	public List<ProducerAO> getAllProducers() {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducers());
	}

	private List<ProducerAO> convertStatsProducerListToAO(List<IStatsProducer> producers){
		LinkedList<ProducerAO> ret = new LinkedList<ProducerAO>();
		for (IStatsProducer p : producers){
			ret.add(convertStatsProducerToAO(p));
		}
		return ret;
	}

	private ProducerAO convertStatsProducerToAO(IStatsProducer p){
		ProducerAO ao = new ProducerAO();
		ao.setProducerId(p.getProducerId());
		ao.setCategory(p.getCategory());
		ao.setSubsystem(p.getSubsystem());
		ao.setProducerClassName(p.getClass().getName());
		//TODO this is maybe not that great...
		//we have to check if IStats values are too heavyweigt to be sent via rmi.
		ao.setStats(p.getStats());
		return ao;
	}

	@Override
	public List<ProducerAO> getAllProducersByCategory(String currentCategory) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersByCategory(currentCategory));
	}

	@Override
	public List<ProducerAO> getProducers(IProducerFilter[] iProducerFilters) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<ProducerAO> getAllProducersBySubsystem(String currentSubsystem) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersBySubsystem(currentSubsystem));
	}

	@Override
	public ProducerAO getProducer(String producerId) throws APIException {
		IStatsProducer producer = producerRegistryAPI.getProducer(producerId);
		return convertStatsProducerToAO(producer);
	}
}
