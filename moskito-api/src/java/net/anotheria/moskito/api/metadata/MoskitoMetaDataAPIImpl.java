package net.anotheria.moskito.api.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.anotheria.moskito.api.exception.MoskitoAPIRuntimeException;
import net.anotheria.util.StringUtils;
import net.anotheria.util.log.LogMessageUtil;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.IntervalInfo;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

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
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoskitoMetaDataAPIImpl.class);
	/**
	 * {@link IProducerRegistryAPI} instance.
	 */
	private static IProducerRegistryAPI producerRegistryAPI;

	/**
	 * Default constructor.
	 */
	protected MoskitoMetaDataAPIImpl() {
		try {
			producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re);
			LOGGER.error(MarkerFactory.getMarker("FATAL"), message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}
	}

	@Override
	public List<String> getIntervals() {
		try {
			List<IntervalInfo> intervalsInfo = producerRegistryAPI.getPresentIntervals();
			if (intervalsInfo == null || intervalsInfo.isEmpty())
				return Collections.emptyList();

			List<String> result = new ArrayList<String>(intervalsInfo.size());
			for (IntervalInfo intervalInfo : intervalsInfo)
				result.add(intervalInfo.getIntervalName());

			return result;
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}
	}

	@Override
	public List<String> getSubsystems() {
		try {
			List<String> subsystems = producerRegistryAPI.getSubsystems();
			if (subsystems == null || subsystems.isEmpty())
				return Collections.emptyList();

			return new ArrayList<String>(subsystems);
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}
	}

	@Override
	public List<String> getCategories() {
		try {
			List<String> categories = producerRegistryAPI.getCategories();
			if (categories == null || categories.isEmpty())
				return Collections.emptyList();

			return new ArrayList<String>(categories);
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}
	}

	@Override
	public List<String> getProducers() {
		try {
			List<IStatsProducer> producers = producerRegistryAPI.getAllProducers();
			if (producers == null || producers.isEmpty())
				return Collections.emptyList();

			List<String> result = new ArrayList<String>(producers.size());
			for (IStatsProducer producer : producers)
				result.add(producer.getProducerId());

			return result;
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}
	}

	@Override
	public List<String> getProducersBySubsystems(List<String> subsystems) {
		if (subsystems == null || subsystems.size() == 0)
			return Collections.emptyList();

		List<String> result = new ArrayList<String>();
		try {
			for (String subsystem : subsystems) {
				List<IStatsProducer> producers = producerRegistryAPI.getAllProducersBySubsystem(subsystem);
				if (producers == null || producers.isEmpty())
					continue;

				for (IStatsProducer producer : producers)
					result.add(producer.getProducerId());
			}
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re, (Object) subsystems);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
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
		try {
			for (String category : categories) {
				List<IStatsProducer> producers = producerRegistryAPI.getAllProducersByCategory(category);
				if (producers == null || producers.isEmpty())
					continue;

				for (IStatsProducer producer : producers)
					result.add(producer.getProducerId());
			}
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re, (Object) categories);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
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
		try {
			producers = producerRegistryAPI.getAllProducersBySubsystem(subsystem);
			if (producers == null || producers.isEmpty())
				return Collections.emptyList();
		} catch (RuntimeException re) {
			String message = LogMessageUtil.failMsg(re, subsystem, categories);
			LOGGER.error(message, re);
			throw new MoskitoAPIRuntimeException(message, re);
		}

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
