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
	private List<DetailedDataAO> data;

	/**
	 * Default constructor.
	 * 
	 * @param aProducerName
	 *            - producer name of this data
	 */
	public ProducerDataAO(String aProducerName) {
		this.producerName = aProducerName;
	}

	public String getProducerName() {
		return producerName;
	}

	public List<DetailedDataAO> getData() {
		if (data == null)
			return Collections.emptyList();

		return new ArrayList<DetailedDataAO>(data);
	}

	/**
	 * Add producer detailed data.
	 * 
	 * @param data
	 *            - detailed producer data
	 */
	public void addData(DetailedDataAO detailedData) {
		if (data == null)
			synchronized (producerName) {
				if (data == null)
					data = new ArrayList<DetailedDataAO>();
			}

		data.add(detailedData);
	}

}
