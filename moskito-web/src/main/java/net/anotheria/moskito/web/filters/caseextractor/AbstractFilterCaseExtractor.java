package net.anotheria.moskito.web.filters.caseextractor;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.16 19:30
 */
public abstract class AbstractFilterCaseExtractor implements FilterCaseExtractor{
	/**
	 * Overwrite this method to register the filter in a category of your choice. Default is 'filter'.
	 * @return the category of this producer.
	 */
	public String getCategory() {
		return "filter";
	}

	/**
	 * Override this to register the filter as specially defined subsystem. Default is 'default'.
	 * @return the subsystem of this producer.
	 */
	public String getSubsystem(){
		return "default";
	}

	public String getProducerId(){
		String producerId = getClass().getSimpleName();
		if (producerId.endsWith("CaseExtractor")){
			producerId = producerId.substring(0, producerId.indexOf("CaseExtractor"));
		}
		return producerId;
	}

}
