package net.anotheria.moskito.integration.cdi;

/**
 * Used to transport producer definition in the interceptor.
 *
 * @author lrosenberg
 * @since 19.11.12 16:49
 */
class ProducerRuntimeDefinition {
	private String producerId;
	private String category;
	private String subsystem;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
}
