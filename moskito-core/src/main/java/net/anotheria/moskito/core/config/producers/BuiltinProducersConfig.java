package net.anotheria.moskito.core.config.producers;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Builtin producers config. This config determines which built-in producers are started automagically.
 *
 * @author lrosenberg
 * @since 26.03.14 00:52
 */
@ConfigureMe(allfields = true)
public class BuiltinProducersConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 4634451164879956071L;

	/**
	 * Start Java Memory Producers.
	 */
	@Configure
	private boolean javaMemoryProducers = true;
	/**
	 * Start Java Memory Pool Producers.
	 */
	@Configure
	private boolean javaMemoryPoolProducers = true;
	/**
	 * Start Java Threading Producers.
	 */
	@Configure
	private boolean javaThreadingProducers = true;
	/**
	 * Start OS Producer.
	 */
	@Configure
	private boolean osProducer = true;
	/**
	 * Start producer using runtime mbean. It is used for CPU load.
	 */
	@Configure
	private boolean runtimeProducer = true;
	@Configure
	private boolean mbeanProducers = true;
	/**
	 * Start gc producer. This producer creates a gc producer for every garbage collector mbean found in system.
	 */
	@Configure
	private boolean gcProducer = true;

	@Configure
	private boolean errorProducer = true;

	@Configure
	private boolean requestStatisticProducer = true;

	public boolean isJavaMemoryProducers() {
		return javaMemoryProducers;
	}

	public void setJavaMemoryProducers(boolean javaMemoryProducers) {
		this.javaMemoryProducers = javaMemoryProducers;
	}

	public boolean isJavaMemoryPoolProducers() {
		return javaMemoryPoolProducers;
	}

	public void setJavaMemoryPoolProducers(boolean javaMemoryPoolProducers) {
		this.javaMemoryPoolProducers = javaMemoryPoolProducers;
	}

	public boolean isJavaThreadingProducers() {
		return javaThreadingProducers;
	}

	public void setJavaThreadingProducers(boolean javaThreadingProducers) {
		this.javaThreadingProducers = javaThreadingProducers;
	}

	public boolean isOsProducer() {
		return osProducer;
	}

	public void setOsProducer(boolean osProducer) {
		this.osProducer = osProducer;
	}

	public boolean isRuntimeProducer() {
		return runtimeProducer;
	}

	public void setRuntimeProducer(boolean runtimeProducer) {
		this.runtimeProducer = runtimeProducer;
	}

	public boolean isMbeanProducers() {
		return mbeanProducers;
	}

	public void setMbeanProducers(boolean mbeanProducers) {
		this.mbeanProducers = mbeanProducers;
	}

	public boolean isGcProducer() {
		return gcProducer;
	}

	public void setGcProducer(boolean gcProducer) {
		this.gcProducer = gcProducer;
	}

	@Override public String toString(){
		return "memory: " + javaMemoryProducers + ", "+
				"memoryPool: " + javaMemoryPoolProducers + ", "+
				"threading: " + javaThreadingProducers + ", "+
				"osProducer: " + osProducer + ", "+
				"runtimeProducer: " + runtimeProducer + ", "+
				"mbeanProducers: " + mbeanProducers + ", "+
				"requestStatisticProducer: "+requestStatisticProducer +", "+ 
				"gcProducer: " + gcProducer;
	}

	public boolean isErrorProducer() {
		return errorProducer;
	}

	public void setErrorProducer(boolean errorProducer) {
		this.errorProducer = errorProducer;
	}

	//this method is for unit-test.
	public void disableAll(){
		javaMemoryProducers =
		javaMemoryPoolProducers =
		javaThreadingProducers =
		osProducer =
		runtimeProducer =
		mbeanProducers =
		gcProducer =
		errorProducer =
		requestStatisticProducer =
			false;

	}

	public boolean isRequestStatisticProducer() {
		return requestStatisticProducer;
	}

	public void setRequestStatisticProducer(boolean requestStatisticProducer) {
		this.requestStatisticProducer = requestStatisticProducer;
	}
}
