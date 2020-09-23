package net.anotheria.moskito.webui.loadfactors.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.loadfactors.LoadFactorConfiguration;
import net.anotheria.moskito.core.config.loadfactors.LoadFactorParticipantConfiguration;
import net.anotheria.moskito.core.config.loadfactors.LoadFactorsConfiguration;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.filters.CategoryFilter;
import net.anotheria.moskito.core.registry.filters.ProducerNameFilter;
import net.anotheria.moskito.core.registry.filters.SubsystemFilter;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation for the LoadFactorsAPI.
 *
 * @author lrosenberg
 * @since 22.07.20 16:45
 */
public class LoadFactorsAPIImpl extends AbstractMoskitoAPIImpl implements LoadFactorsAPI{

	/**
	 * RegistryAPI.
	 */
	private IProducerRegistryAPI producerRegistryAPI;

	@Override
	public void init() throws APIInitException {
		super.init();
		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
	}

	@Override
	public List<LoadFactorAO> getLoadFactors() throws APIException {
		MoskitoConfiguration configuration = MoskitoConfigurationHolder.getConfiguration();

		LoadFactorsConfiguration factorsConfiguration = configuration.getLoadFactorsConfig();
		LinkedList<LoadFactorAO> ret = new LinkedList<>();
		for (LoadFactorConfiguration lfConfig :  factorsConfiguration.getFactors()){
			LoadFactorAO ao = new LoadFactorAO(lfConfig);

			//filter producers for left and right side.
			List<IStatsProducer> leftProducers = producerRegistryAPI.getProducers(createFilters(lfConfig.getLeft()));
			List<IStatsProducer> rightProducers = producerRegistryAPI.getProducers(createFilters(lfConfig.getRight()));

			ao.setLeftNumberOfProducers(leftProducers.size());
			ao.setRightNumberOfProducers(rightProducers.size());

			double leftValue = extractValueFromProducers(leftProducers, lfConfig.getMetric(), lfConfig.getInterval());
			double rightValue = extractValueFromProducers(rightProducers, lfConfig.getMetric(), lfConfig.getInterval());

			ao.setLeftValue(leftValue);
			ao.setRightValue(rightValue);

			double ratio = rightValue / leftValue;
			ao.setRatio(ratio);


			ret.add(ao);
		}

		return ret;
	}

	private double extractValueFromProducers(List<IStatsProducer> producers, String metric, String interval){
		if (producers==null || producers.size()==0)
			return 0.0;
		double ret = 0.0;
		for (IStatsProducer<IStats> producer : producers){
			IStats stats = producer.getStats().get(0);
			String valueAsString = stats.getValueByNameAsString(metric, interval, TimeUnit.MILLISECONDS);
			if (valueAsString!=null){
				ret += Double.parseDouble(valueAsString);
			}
		}
		return ret;
	}

	private IProducerFilter[] createFilters(LoadFactorParticipantConfiguration participantConfiguration){
		LinkedList<IProducerFilter> filters = new LinkedList<>();
		if (participantConfiguration==null)
			return filters.toArray(new IProducerFilter[filters.size()]);
		
		if (participantConfiguration.getCategory()!=null && participantConfiguration.getCategory().length()>0)
			filters.add(new CategoryFilter(participantConfiguration.getCategory()));
		if (participantConfiguration.getSubsystem()!=null && participantConfiguration.getSubsystem().length()>0)
			filters.add(new SubsystemFilter(participantConfiguration.getSubsystem()));
		if (participantConfiguration.getProducerName()!=null && participantConfiguration.getProducerName().length()>0)
			filters.add(new ProducerNameFilter(participantConfiguration.getProducerName()));
		return filters.toArray(new IProducerFilter[filters.size()]);
	}
}
