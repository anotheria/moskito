package net.anotheria.moskito.core.tracer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Contains a single trace.
 *
 * @author lrosenberg
 * @since 05.05.15 17:54
 */
public class Trace implements IComparable<Trace>{
	/**
	 * Static counter that provides a new id to each instance.
	 */
	private static AtomicLong counter = new AtomicLong();
	/**
	 * Id of this instance.
	 */
	private long id = counter.incrementAndGet();
	/**
	 * Call for this trace.
	 */
	private String call;
	/**
	 * Duration of this trace.
	 */
	private long duration;
	/**
	 * Elements in the stackTrace prior to Tracer activation.
	 */
	private StackTraceElement[] elements;
    /**
     * Timestamp of the trace creation.
     */
    private long createdTimestamp;

    public Trace() {
        createdTimestamp = System.currentTimeMillis();
    }

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

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public StackTraceElement[] getElements() {
		return elements;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public void setElements(StackTraceElement[] elements) {
		this.elements = elements;
	}

	@Override public String toString(){
		return id+" "+call+ ' ' +(elements==null? "no elements" : Arrays.toString(getElements()))+", dur: "+duration;
	}

	public long getId(){
		return id;
	}

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
	public int compareTo(IComparable<? extends Trace> iComparable, int method) {
		switch(method){
			case TraceSortType.SORT_BY_ID:
				return BasicComparable.compareLong(id, ((Trace)iComparable).getId());
			case TraceSortType.SORT_BY_DURATION:
				return BasicComparable.compareLong(duration, ((Trace)iComparable).getDuration());
			default:
				throw new IllegalArgumentException("Method "+method+" is not supported");
		}
	}
}
