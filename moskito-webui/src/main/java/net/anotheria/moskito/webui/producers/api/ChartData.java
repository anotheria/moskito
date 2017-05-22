package net.anotheria.moskito.webui.producers.api;

import net.anotheria.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author esmakula
 */
public class ChartData {

    private static final Map<String, String> jsReplaceMap;
    static{
        jsReplaceMap = new HashMap<>(4);
        jsReplaceMap.put(" ", "_");
        jsReplaceMap.put("-", "_");
        jsReplaceMap.put("+", "_");
        jsReplaceMap.put(".", "_");
    }

    private String name;

    private String producerId;

    private String stat;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJson() {
        return '{' +
                "\"name\" : \"" + name + '"' +
                ",\"nameForJS\" : \"" + StringUtils.replace(name, jsReplaceMap) + '"' +
                ", \"producerId\" : \"" + producerId + '"' +
                ", \"stat\" : \"" + stat + '"' +
                ", \"value\" : \"" + value + '"' +
                '}';
    }
}
