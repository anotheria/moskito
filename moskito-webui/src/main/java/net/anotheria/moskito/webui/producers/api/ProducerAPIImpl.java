package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.IDecoratorRegistry;
import net.anotheria.moskito.core.inspection.Inspectable;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.TracingAwareProducer;
import net.anotheria.moskito.webui.Features;
import net.anotheria.moskito.webui.producers.api.filters.ProducerFilter;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.util.DecoratorConfig;
import net.anotheria.moskito.webui.util.ProducerFilterConfig;
import net.anotheria.moskito.webui.util.WebUIConfig;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.ConfigureMe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the producer api.
 *
 * @author lrosenberg
 * @since 14.02.13 11:49
 */
public class ProducerAPIImpl extends AbstractMoskitoAPIImpl implements ProducerAPI{
	private IProducerRegistryAPI producerRegistryAPI;
	private IDecoratorRegistry decoratorRegistry = DecoratorRegistryFactory.getDecoratorRegistry();

	private volatile List<ProducerFilter> producerFilters;

	/**
	 * Called after the configuration has been read.
	 */
	private void apiAfterConfiguration(){
		List<ProducerFilter> newProducerFilters = new ArrayList<ProducerFilter>();
		ProducerFilterConfig filterConfig[] = WebUIConfig.getInstance().getFilters();
		if (filterConfig==null || filterConfig.length==0)
			return;
		for (ProducerFilterConfig pfc : filterConfig){
			try {
				ProducerFilter filter = (ProducerFilter)Class.forName(pfc.getClazzName()).newInstance();
				filter.customize(pfc.getParameter());
				newProducerFilters.add(filter);
			} catch (InstantiationException e) {
				log.warn("Can't initialize filter of class "+pfc.getClazzName());
			} catch (IllegalAccessException e) {
				log.warn("Can't initialize filter of class " + pfc.getClazzName());
			} catch (ClassNotFoundException e) {
				log.warn("Can't initialize filter of class " + pfc.getClazzName());
			}
		}

		DecoratorConfig[] decoratorConfigs = WebUIConfig.getInstance().getDecorators();
		if (decoratorConfigs != null){
			log.debug("Configuring decorator configs "+ Arrays.toString(decoratorConfigs));
			for (DecoratorConfig config : decoratorConfigs){
				try{
					Class decoratorClass = Class.forName(config.getDecoratorClazzName());
					IDecorator decorator = (IDecorator) decoratorClass.newInstance();
					DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(config.getStatClazzName(), decorator);

				}catch (ClassNotFoundException e){
					log.warn("can't configure decorator "+config+" due ", e);
				} catch (InstantiationException e) {
					log.warn("can't configure decorator " + config + " due ", e);
				} catch (IllegalAccessException e) {
					log.warn("can't configure decorator "+config+" due ", e);
				}
			}
		}

		producerFilters = newProducerFilters;

	}

	@ConfigureMe(name="moskito-inspect")
	public class ConfigurationHook{
		@AfterConfiguration public void afterConfiguration(){
			apiAfterConfiguration();
		}
	}

	@Override
	public void init() throws APIInitException {
		super.init();

		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		//force load of configuration.
		WebUIConfig.getInstance();
		ConfigurationHook configurationHook = new ConfigurationHook();
		try{
			ConfigurationManager.INSTANCE.configure(configurationHook);
		}catch(IllegalArgumentException e){
			log.warn("Can't register configuration hook for moskito-inspect.json, re-configuration will not be supported");
		}
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

	private List<ProducerAO> convertStatsProducerListToAO(List<IStatsProducer> producers, String intervalName, TimeUnit timeUnit){
		LinkedList<ProducerAO> ret = new LinkedList<ProducerAO>();
		for (IStatsProducer p : producers){
			ret.add(convertStatsProducerToAO(p, intervalName, timeUnit));
		}
		return ret;
	}

	private ProducerAO convertStatsProducerToAO(IStatsProducer<? extends IStats> p, String intervalName, TimeUnit timeUnit){
		return convertStatsProducerToAO(p, intervalName, timeUnit, false);
	}

	private ProducerAO convertStatsProducerToAO(IStatsProducer<? extends IStats> p, String intervalName, TimeUnit timeUnit, boolean createAllStats){
		ProducerAO ao = new ProducerAO();
		ao.setProducerId(p.getProducerId());
		ao.setCategory(p.getCategory());
		ao.setSubsystem(p.getSubsystem());
		ao.setProducerClassName(p.getClass().getSimpleName());
		ao.setFullProducerClassName(p.getClass().getName());
		if (p instanceof Inspectable)
			ao.setCreationInfo(((Inspectable)p).getCreationInfo());
		boolean traceable = false;
		if (p instanceof TracingAwareProducer){
			traceable = ((TracingAwareProducer)p).tracingSupported();
		}
		ao.setTraceable(traceable);
		if (traceable){
			ao.setTraced(TracerRepository.getInstance().isTracingEnabledForProducer(p.getProducerId()));
		}

		IStats firstStats = p.getStats().get(0);
		ao.setStatsClazzName(firstStats.getClass().getName());

		//ao.setStats(p.getStats());

		IDecorator decorator = decoratorRegistry.getDecorator(ao.getStatsClazzName());
		ao.setFirstStatsValues(decorator.getValues(firstStats, intervalName, timeUnit));

		if (createAllStats){
			//if create all stats are set, we have to create all stats, not just the first stat.
			List<? extends IStats> allStats = p.getStats();
			for (IStats statObject : allStats){
				//lets assume that all stats of the same producer are of the same type.
				StatLineAO line = new StatLineAO();
				line.setStatName(statObject.getName());
				line.setValues(decorator.getValues(statObject, intervalName, timeUnit));
				ao.addStatLine(line);
			}
		}

		return ao;
	}

	private List<ProducerAO> filterProducersAndConvertToAO(List<IStatsProducer> producers, String intervalName, TimeUnit timeUnit){
		ArrayList<ProducerAO> ret = new ArrayList<ProducerAO>();
		for (IStatsProducer<?> p : producers){
			boolean mayPass = true;
			for (ProducerFilter filter : producerFilters){
				if (!filter.mayPass(p)){
					mayPass = false;
					break;
				}
			}
			if (mayPass)
				ret.add(convertStatsProducerToAO(p, intervalName, timeUnit));
		}

		return ret;

	}

	@Override
	public List<ProducerAO> getAllProducers(String intervalName, TimeUnit timeUnit) {

		if (producerFilters==null || producerFilters.size()==0 || !Features.PRODUCER_FILTERING.isEnabled())
			return convertStatsProducerListToAO(producerRegistryAPI.getAllProducers(), intervalName, timeUnit);

		List<IStatsProducer> allProducers =  producerRegistryAPI.getAllProducers();
		return filterProducersAndConvertToAO(allProducers, intervalName, timeUnit);
	}


	@Override
	public List<ProducerAO> getAllProducersByCategory(String currentCategory, String intervalName, TimeUnit timeUnit) {
		if (producerFilters==null || producerFilters.size()==0 || !Features.PRODUCER_FILTERING.isEnabled())
			return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersByCategory(currentCategory), intervalName, timeUnit);

		return filterProducersAndConvertToAO(producerRegistryAPI.getAllProducersByCategory(currentCategory), intervalName, timeUnit);
	}


	@Override
	public List<ProducerAO> getAllProducersBySubsystem(String currentSubsystem, String intervalName, TimeUnit timeUnit) {
		return convertStatsProducerListToAO(producerRegistryAPI.getAllProducersBySubsystem(currentSubsystem), intervalName, timeUnit);
	}

	@Override
	public List<ProducerAO> getProducers(IProducerFilter[] iProducerFilters, String intervalName, TimeUnit timeUnit) {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ProducerAO getProducer(String producerId, String intervalName, TimeUnit timeUnit) throws APIException {
		IStatsProducer producer = producerRegistryAPI.getProducer(producerId);
		return convertStatsProducerToAO(producer, intervalName, timeUnit, true);
	}
}
