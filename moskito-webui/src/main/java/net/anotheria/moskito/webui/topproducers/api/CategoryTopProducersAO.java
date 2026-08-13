package net.anotheria.moskito.webui.topproducers.api;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Transfer object bundling the top producers of a single ranking category. Used to return the whole ranking (all
 * categories at once) over the api layer.
 *
 * @author lrosenberg
 */
public class CategoryTopProducersAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ranking category (one of {@link net.anotheria.moskito.core.topproducers.Category}).
	 */
	private String category;
	/**
	 * Top producers of this category, ordered descending by their accumulated score.
	 */
	private List<TopProducerAO> producers = new LinkedList<>();

	public CategoryTopProducersAO() {
	}

	public CategoryTopProducersAO(String category, List<TopProducerAO> producers) {
		this.category = category;
		this.producers = producers;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<TopProducerAO> getProducers() {
		return producers;
	}

	public void setProducers(List<TopProducerAO> producers) {
		this.producers = producers;
	}

	@Override
	public String toString() {
		return "CategoryTopProducersAO{category='" + category + "', producers=" + producers.size() + '}';
	}
}
