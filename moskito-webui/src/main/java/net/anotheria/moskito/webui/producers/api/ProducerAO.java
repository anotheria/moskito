package net.anotheria.moskito.webui.producers.api;

import net.anotheria.moskito.core.decorators.DecoratorName;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Producer API Object.
 *
 * @author lrosenberg
 * @since 22.03.14 14:03
 */
public class ProducerAO implements Serializable, IComparable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 3635009586142588362L;


	/**
	 * Id of the producer.
	 */
	private String producerId;
	/**
	 * Category of the producer.
 	 */
	private String category;
	/**
	 * Subsystem producer belongs too.
	 */
	private String subsystem;
	/**
	 * Class name, short (class.getSimpleName());
	 */
	private String producerClassName;
	/**
	 * Class name, long (class.getName());
	 */
	private String fullProducerClassName;

	private List<StatValueAO> firstStatsValues;

	/**
	 * CreationInfo is set for all inspectable producers.
	 */
	private CreationInfo creationInfo;

	/**
	 * All stat values as list of lists. The included list contains one stat - line with multiple values.
	 */
	private final List<StatLineAO> lines;

	/**
	 * Producer stats decorator name
	 */
	private DecoratorName decoratorName;

	/**
	 * Clazz name of the stats object contained in this producer (for decorator selection).
	 */
	private String statsClazzName;

	private boolean traceable;

	private boolean traced;

	/**
	 * If true logging is generally supported by this producer.
	 */
	private boolean loggingSupported;

	/**
	 * If true and loggingEnabled==true logging is currently enabled for this producer.
	 */
	private boolean loggingEnabled;

	/**
	 * Is source monitoring generally supported by this producer (does the producer implement SourceMonitoringAware interface).
	 */
	private boolean sourceMonitoringSupported;

	/**
	 * Is source monitoring currently enabled;
	 */
	private boolean sourceMonitoringEnabled;


	public ProducerAO(){
		lines = new LinkedList<StatLineAO>();
	}

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

	public String getProducerClassName() {
		return producerClassName;
	}

	public void setProducerClassName(String producerClassName) {
		this.producerClassName = producerClassName;
	}

	/**
	 * Is inspectable, true if there is a creation info available.
	 * @return
	 */
	public boolean isInspectable() {
		return creationInfo!=null;
	}

	public String getStatsClazzName() {
		return statsClazzName;
	}

	public void setStatsClazzName(String aStatsClazzName) {
		statsClazzName = aStatsClazzName;
	}


	public List<StatValueAO> getFirstStatsValues() {
		return firstStatsValues;
	}

	public void setFirstStatsValues(List<StatValueAO> firstStatsValues) {
		this.firstStatsValues = firstStatsValues;
	}

	public void addStatLine(StatLineAO line){
		lines.add(line);
	}
	public List<StatLineAO> getLines() {
		return lines;
	}
	public CreationInfo getCreationInfo() {
		return creationInfo;
	}

	public void setCreationInfo(CreationInfo creationInfo) {
		this.creationInfo = creationInfo;
	}

	private int compareByValue(ProducerAO anotherProducer, int method){
		return firstStatsValues.get(method).compareTo(anotherProducer.firstStatsValues.get(method), method);

	}


	@Override public int compareTo(IComparable anotherComparable, int method) {
		ProducerAO anotherAO = (ProducerAO)anotherComparable;
		if (method< ProducerAOSortType.DYN_SORT_TYPE_LIMIT)
			return compareByValue(anotherAO, method);
		switch(method){
			case ProducerAOSortType.SORT_BY_ID:
				return BasicComparable.compareString(getProducerId(), anotherAO.getProducerId());
			case ProducerAOSortType.SORT_BY_CATEGORY:
				return BasicComparable.compareString(category, anotherAO.category);
			case ProducerAOSortType.SORT_BY_SUBSYSTEM:
				return BasicComparable.compareString(subsystem, anotherAO.subsystem);
			case ProducerAOSortType.SORT_BY_CLASS_NAME:
				return BasicComparable.compareString(getProducerClassName(), anotherAO.getProducerClassName());
			default:
				throw new RuntimeException("Unsupported sort method: "+method);
		}
	}

	public String getFullProducerClassName() {
		return fullProducerClassName;
	}

	public void setFullProducerClassName(String fullProducerClassName) {
		this.fullProducerClassName = fullProducerClassName;
	}

	@Override
	public String toString() {
		return "ProducerAO{" +
				"producerId='" + producerId + '\'' +
				", subsystem='" + subsystem + '\'' +
				", category='" + category + '\'' +
				'}';
	}

	public boolean isTraceable() {
		return traceable;
	}

	public void setTraceable(boolean traceable) {
		this.traceable = traceable;
	}

	public boolean isTraced() {
		return traced;
	}

	public void setTraced(boolean traced) {
		this.traced = traced;
	}

	public String getProducerIdEncoded() {
		try {
			return URLEncoder.encode(producerId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return producerId;
		}
	}

	public DecoratorName getDecoratorName() {
		return decoratorName;
	}

	public void setDecoratorName(DecoratorName decoratorName) {
		this.decoratorName = decoratorName;
	}

	public boolean isLoggingSupported() {
		return loggingSupported;
	}

	public void setLoggingSupported(boolean loggingSupported) {
		this.loggingSupported = loggingSupported;
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public boolean isSourceMonitoringSupported() {
		return sourceMonitoringSupported;
	}

	public void setSourceMonitoringSupported(boolean sourceMonitoringSupported) {
		this.sourceMonitoringSupported = sourceMonitoringSupported;
	}

	public boolean isSourceMonitoringEnabled() {
		return sourceMonitoringEnabled;
	}

	public void setSourceMonitoringEnabled(boolean sourceMonitoringEnabled) {
		this.sourceMonitoringEnabled = sourceMonitoringEnabled;
	}
}
