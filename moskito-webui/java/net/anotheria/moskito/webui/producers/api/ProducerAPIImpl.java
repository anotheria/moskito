package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.webui.decorators.IDecorator;
import net.anotheria.moskito.webui.decorators.IDecoratorRegistry;
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
	private IDecoratorRegistry decoratorRegistry = DecoratorRegistryFactory.getDecoratorRegistry();

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
	public List<ProducerAO> getAllProducers(String intervalName, TimeUnit timeUnit) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducers(), intervalName, timeUnit);
	}

	private List<ProducerAO> convertStatsProducerListToAO(List<IStatsProducer> producers, String intervalName, TimeUnit timeUnit){
		LinkedList<ProducerAO> ret = new LinkedList<ProducerAO>();
		for (IStatsProducer p : producers){
			ret.add(convertStatsProducerToAO(p, intervalName, timeUnit));
		}
		return ret;
	}

	private ProducerAO convertStatsProducerToAO(IStatsProducer<? extends IStats> p, String intervalName, TimeUnit timeUnit){
		ProducerAO ao = new ProducerAO();
		ao.setProducerId(p.getProducerId());
		ao.setCategory(p.getCategory());
		ao.setSubsystem(p.getSubsystem());
		ao.setProducerClassName(p.getClass().getName());
		//TODO this is maybe not that great...
		//we have to check if IStats values are too heavyweigt to be sent via rmi.
		IStats firstStats = p.getStats().get(0);
		ao.setStatsClazz((Class<? extends IStats>) firstStats.getClass());

		//ao.setStats(p.getStats());

		IDecorator decorator = decoratorRegistry.getDecorator(ao.getStatsClazz());
		ao.setValues(decorator.getValues(firstStats, intervalName, timeUnit));

		return ao;
	}

	@Override
	public List<ProducerAO> getAllProducersByCategory(String currentCategory, String intervalName, TimeUnit timeUnit) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersByCategory(currentCategory), intervalName, timeUnit);
	}

	@Override
	public List<ProducerAO> getProducers(IProducerFilter[] iProducerFilters, String intervalName, TimeUnit timeUnit) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public List<ProducerAO> getAllProducersBySubsystem(String currentSubsystem, String intervalName, TimeUnit timeUnit) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersBySubsystem(currentSubsystem), intervalName, timeUnit);
	}

	@Override
	public ProducerAO getProducer(String producerId) throws APIException {
		IStatsProducer producer = producerRegistryAPI.getProducer(producerId);
		System.out.println("EXPECT a crash");
		return convertStatsProducerToAO(producer, null, null);
	}
}
