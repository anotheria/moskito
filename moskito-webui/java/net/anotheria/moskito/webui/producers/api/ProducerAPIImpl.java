package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.ArrayList;
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
}
