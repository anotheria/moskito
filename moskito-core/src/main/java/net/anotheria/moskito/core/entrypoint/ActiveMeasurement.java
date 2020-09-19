package net.anotheria.moskito.core.entrypoint;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 15:59
 */
public class ActiveMeasurement {
	private long starttime = System.currentTimeMillis();
	private String producerId;
	private String description;

	public ActiveMeasurement(String aProducerId){
		producerId = aProducerId;
	}

	void setStarttime(long aStarttime){
		starttime = aStarttime;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setCallDescription(String description) {
		this.description = description;
	}

	public long getStartTime() {
		return starttime;
	}

	public String getDescription(){
		return description;
	}

	@Override
	public String toString() {
		return "ActiveMeasurement{" +
				"starttime=" + starttime +
				", producerId='" + producerId + '\'' +
				", description='" + description + '\'' +
				'}';
	}

}
