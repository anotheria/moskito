package net.anotheria.moskito.core.entrypoint;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 15:59
 */
public class ActiveMeasurement {
	private final long starttime = System.currentTimeMillis();
	private String producerId;

	public ActiveMeasurement(String aProducerId){
		producerId = aProducerId;
	}

	public String getProducerId() {
		return producerId;
	}
}
