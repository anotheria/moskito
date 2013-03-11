package net.anotheria.moskito.webui.more.bean;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import java.util.Collections;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.03.13 12:31
 */
public class MBeanWrapperBean {
	private String className;
	private String description;
	private List<MBeanAttributeInfo> attributes = Collections.emptyList();
	private List<MBeanOperationInfo> operations = Collections.emptyList();
	private String domain;

	private String canonicalName;

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

	public List<MBeanAttributeInfo> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MBeanAttributeInfo> attributes) {
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



}
