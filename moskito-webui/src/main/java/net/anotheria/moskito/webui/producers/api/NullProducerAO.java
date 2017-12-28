package net.anotheria.moskito.webui.producers.api;

import java.io.Serializable;

/**
 * This object is used to transport a missing producer in a list of producers.
 *
 * @author lrosenberg
 * @since 20.10.17 17:29
 */
public class NullProducerAO extends ProducerAO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Explanation why this producer has been nullified.
	 */
	private String message;

	public NullProducerAO(String producerId, String message){
		this.message = message;
		setProducerId(producerId);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
