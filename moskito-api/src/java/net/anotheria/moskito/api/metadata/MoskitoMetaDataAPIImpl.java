package net.anotheria.moskito.api.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.anotheria.util.StringUtils;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.IntervalInfo;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;

/**
 * {@link MoskitoMetaDataAPITest} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoMetaDataAPIImpl implements MoskitoMetaDataAPI {
	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -7730093168932365894L;
	/**
	 * {@link IProducerRegistryAPI} instance.
	 */
	private static IProducerRegistryAPI producerRegistryAPI;

	/**
	 * Default constructor.
	 */
	protected MoskitoMetaDataAPIImpl() {
		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
	}

	@Override
	public List<String> getIntervals() {
		List<IntervalInfo> intervalsInfo = producerRegistryAPI.getPresentIntervals();
		if (intervalsInfo == null || intervalsInfo.isEmpty())
			return Collections.emptyList();

		List<String> result = new ArrayList<String>(intervalsInfo.size());
		for (IntervalInfo intervalInfo : intervalsInfo)
			result.add(intervalInfo.getIntervalName());

		return result;
	}

	@Override
	public List<String> getSubsystems() {
		List<String> subsystems = producerRegistryAPI.getSubsystems();
		if (subsystems == null || subsystems.isEmpty())
			return Collections.emptyList();

		return new ArrayList<String>(subsystems);
	}

	@Override
	public List<String> getCategories() {
		List<String> categories = producerRegistryAPI.getCategories();
		if (categories == null || categories.isEmpty())
			return Collections.emptyList();

		return new ArrayList<String>(categories);
	}

	@Override
	public List<String> getProducers() {
		List<IStatsProducer> producers = producerRegistryAPI.getAllProducers();
		if (producers == null || producers.isEmpty())
			return Collections.emptyList();

		List<String> result = new ArrayList<String>(producers.size());
		for (IStatsProducer producer : producers)
			result.add(producer.getProducerId());

		return result;
	}

	@Override
	public List<String> getProducersBySubsystems(List<String> subsystems) {
		if (subsystems == null || subsystems.size() == 0)
			return Collections.emptyList();

		List<String> result = new ArrayList<String>();
		for (String subsystem : subsystems) {
			List<IStatsProducer> producers = producerRegistryAPI.getAllProducersBySubsystem(subsystem);
			if (producers == null || producers.isEmpty())
				continue;

			for (IStatsProducer producer : producers)
				result.add(producer.getProducerId());
		}

		if (result.isEmpty())
			return Collections.emptyList();

		return result;
	}

	@Override
	public List<String> getProducersByCategories(List<String> categories) {
		if (categories == null || categories.size() == 0)
			return Collections.emptyList();

		List<String> result = new ArrayList<String>();
		for (String category : categories) {
			List<IStatsProducer> producers = producerRegistryAPI.getAllProducersByCategory(category);
			if (producers == null || producers.isEmpty())
				continue;

			for (IStatsProducer producer : producers)
				result.add(producer.getProducerId());
		}

		if (result.isEmpty())
			return Collections.emptyList();

		return result;
	}

	@Override
	public List<String> getProducers(String subsystem, List<String> categories) {
		if (StringUtils.isEmpty(subsystem) || categories == null || categories.size() == 0)
			return Collections.emptyList();

		List<IStatsProducer> producers = null;
		producers = producerRegistryAPI.getAllProducersBySubsystem(subsystem);
		if (producers == null || producers.isEmpty())
			return Collections.emptyList();

		Set<String> categoriesSet = new HashSet<String>(categories);
		List<String> result = new ArrayList<String>();
		for (IStatsProducer producer : producers)
			if (categoriesSet.contains(producer.getCategory()))
				result.add(producer.getProducerId());

		if (result.isEmpty())
			return Collections.emptyList();

		return result;
	}

}
