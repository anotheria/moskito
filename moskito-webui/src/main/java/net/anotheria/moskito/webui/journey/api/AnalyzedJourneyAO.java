package net.anotheria.moskito.webui.journey.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents results of producer calls analysis.
 * @author strel
 */
public class AnalyzedJourneyAO implements Serializable {

    private static final long serialVersionUID = 552406840820013735L;
    /**
     * Journey name.
     */
    private String name;

    /**
     * List of {@link AnalyzedProducerCallsMapAO}.
     */
    private List<AnalyzedProducerCallsMapAO> calls;


    public AnalyzedJourneyAO() {
        calls = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnalyzedProducerCallsMapAO> getCalls() {
        return calls;
    }

    public void setCalls(List<AnalyzedProducerCallsMapAO> calls) {
        this.calls = calls;
    }
}
