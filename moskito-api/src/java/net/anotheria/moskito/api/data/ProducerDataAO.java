package net.anotheria.moskito.api.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Producer data container.
 * 
 * @author Alexandr Bolbat
 */
public class ProducerDataAO implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 3800096589240528774L;

	/**
	 * Producer name.
	 */
	private final String producerName;

	/**
	 * Producer data.
	 */
	private final List<StatsDataAO> data = new ArrayList<StatsDataAO>();

	/**
	 * Default constructor.
	 * 
	 * @param aProducerName
	 *            - producer name of this data
	 */
	public ProducerDataAO(final String aProducerName) {
		this.producerName = aProducerName != null ? aProducerName : "";
	}

	public String getProducerName() {
		return producerName;
	}

	/**
	 * Get producer data.
	 * 
	 * @return {@link List} of {@link StatsDataAO}
	 */
	public List<StatsDataAO> getData() {
		if (data == null)
			return Collections.emptyList();

		return new ArrayList<StatsDataAO>(data);
	}

	/**
	 * Add producer data.
	 * 
	 * @param aData
	 *            - producer data
	 */
	public void addData(final StatsDataAO aData) {
		if (aData != null)
			data.add(aData);
	}

}
