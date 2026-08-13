package net.anotheria.moskito.webui.topproducers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.topproducers.Category;
import net.anotheria.moskito.core.topproducers.ProducerEntry;
import net.anotheria.moskito.core.topproducers.ProducerEntryValue;
import net.anotheria.moskito.core.topproducers.TopProducersRepository;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link TopProducersAPI}. Reads the ranking from the core {@link TopProducersRepository} and
 * maps the core entries to transportable {@link TopProducerAO}s.
 *
 * @author lrosenberg
 */
public class TopProducersAPIImpl extends AbstractMoskitoAPIImpl implements TopProducersAPI {

	@Override
	public List<TopProducerAO> getTopProducers(String category, int limit) throws APIException {
		Category targetCategory = parseCategory(category);
		List<ProducerEntry> entries = TopProducersRepository.getInstance().getTopProducers(targetCategory, limit);

		List<TopProducerAO> ret = new ArrayList<>(entries.size());
		for (ProducerEntry entry : entries)
			ret.add(map(entry, targetCategory));
		return ret;
	}

	@Override
	public List<CategoryTopProducersAO> getTopProducersByAllCategories(int limit) throws APIException {
		List<CategoryTopProducersAO> ret = new ArrayList<>(Category.values().length);
		for (Category category : Category.values())
			ret.add(new CategoryTopProducersAO(category.name(), getTopProducers(category.name(), limit)));
		return ret;
	}

	@Override
	public List<String> getCategories() throws APIException {
		List<String> ret = new ArrayList<>(Category.values().length);
		for (Category category : Category.values())
			ret.add(category.name());
		return ret;
	}

	private TopProducerAO map(ProducerEntry entry, Category category) {
		ProducerEntryValue value = entry.getValue(category);

		TopProducerAO ao = new TopProducerAO();
		ao.setProducerId(entry.getProducerId());
		ao.setProducerCategory(entry.getProducerCategory());
		ao.setProducerSubsystem(entry.getProducerSubsystem());
		ao.setRankingCategory(category.name());
		if (value != null) {
			ao.setCumulatedScore(value.getCumulatedScore());
			ao.setTopScore(value.getTopScore());
			ao.setBottomScore(value.getBottomScore());
			ao.setLastScore(value.getLastScore());
			ao.setScoreCount(value.getScoreCount());
			ao.setAverageScore(value.getAverageScore());
		}
		return ao;
	}

	private Category parseCategory(String category) throws APIException {
		if (category == null)
			throw new APIException("No ranking category given, expected one of " + java.util.Arrays.toString(Category.values()));
		try {
			return Category.valueOf(category.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new APIException("Unknown ranking category '" + category + "', expected one of " + java.util.Arrays.toString(Category.values()));
		}
	}
}
