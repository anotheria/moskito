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
	 * Map of total calls by producer.
	 */
	private AnalyzedProducerCallsMapAO totalByProducerId;
	/**
	 * Map of total calls by category.
	 */
	private AnalyzedProducerCallsMapAO totalByCategoryId;
	/**
	 * Map of total calls by subsystem.
	 */
	private AnalyzedProducerCallsMapAO totalBySubsystemId;

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

	public AnalyzedProducerCallsMapAO getTotalByProducerId() {
		return totalByProducerId;
	}

	public void setTotalByProducerId(AnalyzedProducerCallsMapAO totalByProducerId) {
		this.totalByProducerId = totalByProducerId;
	}

	public AnalyzedProducerCallsMapAO getTotalByCategoryId() {
		return totalByCategoryId;
	}

	public void setTotalByCategoryId(AnalyzedProducerCallsMapAO totalByCategoryId) {
		this.totalByCategoryId = totalByCategoryId;
	}

	public AnalyzedProducerCallsMapAO getTotalBySubsystemId() {
		return totalBySubsystemId;
	}

	public void setTotalBySubsystemId(AnalyzedProducerCallsMapAO totalBySubsystemId) {
		this.totalBySubsystemId = totalBySubsystemId;
	}
}
