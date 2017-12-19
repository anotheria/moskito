package net.anotheria.moskito.webui.tracers.api;

import net.anotheria.moskito.webui.journey.api.TagEntryAO;
import net.anotheria.moskito.webui.tags.api.TagAO;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a single trace - a call through the tracer.
 *
 * @author lrosenberg
 * @since 05.05.15 00:43
 */
public class TraceAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2678431427945812133L;


	/**
	 * Id.
	 */
	private String id;
	/**
	 * Call description.
	 */
	private String call;
	/**
	 * Stack trace of the call.
	 */
	private List<StackTraceElement> elements;
	/**
	 * Duration of the call in nanoseconds.
	 */
	private long duration;
    /**
     * Date of trace creation.
     */
    private String created;

    private List<TagEntryAO> tags = new LinkedList<>();

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public List<StackTraceElement> getElements() {
		return elements;
	}

	public void setElements(List<StackTraceElement> elements) {
		this.elements = elements;
	}

	public int getElementCount(){
		return elements == null ? 0 : elements.size();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<TagEntryAO> getTags() {
		return Collections.unmodifiableList(tags);
	}

	public void setTags(List<TagEntryAO> tags) {
		this.tags = tags;
	}

}
