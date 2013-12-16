package net.anotheria.moskito.webui.more.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import javax.management.MBeanOperationInfo;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper for mbean that contains multiple information pieces relevant for mbean view.
 *
 * @author lrosenberg
 * @since 11.03.13 12:31
 */
public class MBeanWrapperBean implements IComparable<MBeanWrapperBean>{
	/**
	 * Name of the implementing class.
	 */
	private String className;
	/**
	 * Description of the mbean.
	 */
	private String description;
	/**
	 * Attributes of the mbean.
	 */
	private List<MBeanAttributeWrapper> attributes = Collections.emptyList();
	/**
	 * Operations of the mbean.
	 */
	private List<MBeanOperationInfo> operations = Collections.emptyList();
	/**
	 * Domain of the mbean.
	 */
	private String domain;

	/**
	 * Name.
	 */
	private String canonicalName;


	/**
	 * Mbean type property which actually corresponds with a human-understandable name.
	 */
	private String type = "";

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MBeanAttributeWrapper> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MBeanAttributeWrapper> attributes) {
		this.attributes = attributes;
	}

	public List<MBeanOperationInfo> getOperations() {
		return operations;
	}

	public void setOperations(List<MBeanOperationInfo> operations) {
		this.operations = operations;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public int getAttributesCount(){
		return attributes.size();
	}

	public int getOperationsCount(){
		return operations.size();
	}

	public String getAttributesInfo(){
		return attributes.toString();
	}

	public String getOperationsInfo(){
		return operations.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(IComparable<? extends MBeanWrapperBean> iComparable, int i) {
		int domainCompare = BasicComparable.compareString(getDomain(), ((MBeanWrapperBean)iComparable).getDomain());
		return domainCompare == 0 ?
				BasicComparable.compareString(getType(), ((MBeanWrapperBean)iComparable).getType()) :
				domainCompare;
	}
}
