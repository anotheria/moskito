package net.anotheria.moskito.core.producers;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * An implementation of a snapshot.
 * @author another
 *
 */
public class DefaultStatsSnapshot implements IStatsSnapshot, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TBD.
	 */
	private String name;
	/**
	 * Name of the interface.
	 */
    private String interfaceName;
    /**
     * Id of the stats producer.
     */
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
