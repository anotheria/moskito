package net.java.dev.moskito.core.producers;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;

public class DefaultStatsSnapshot implements IStatsSnapshot, Serializable {
	
	private String name;
    private String interfaceName;
    private String producerId;
    private Date dateCreated;
	private Map<String, Number> properties;
	
	@Override
	public Map<String, Number> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Number> properties) {
		this.properties = properties;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }
}
