package net.anotheria.extensions.php.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Stats data transfer object.
 * Used to pass producer data from connectors to mapper
 */
public class PHPStatsDTO {

    /**
     * Name of stats
     */
    private String name;

    /**
     * Stats values
     */
    private Map<String, String> values = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

}
