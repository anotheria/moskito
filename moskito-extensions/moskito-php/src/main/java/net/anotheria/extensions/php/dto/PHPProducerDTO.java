package net.anotheria.extensions.php.dto;

import java.util.List;

/**
 * Producer data transfer object
 * Used to pass producer data from
 * connectors to mappers
 */
public class PHPProducerDTO {

    /**
     * Id of producer to be updated
     */
    private String producerId;

    /**
     * Producer category.
     * Used only for producer creation.
     */
    private String category;

    /**
     * Producer subsystem.
     * Used only for producer creation.
     */
    private String subsystem;

    /**
     * Id of mapper to be used with this producer
     */
    private String mapperId;

    /**
     * List of producer stats to be updated
     */
    private List<PHPStatsDTO> stats;

    private double timestamp;

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

    public String getMapperId() {
        return mapperId;
    }

    public void setMapperId(String mapperId) {
        this.mapperId = mapperId;
    }

    public List<PHPStatsDTO> getStats() {
        return stats;
    }

    public void setStats(List<PHPStatsDTO> stats) {
        this.stats = stats;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
