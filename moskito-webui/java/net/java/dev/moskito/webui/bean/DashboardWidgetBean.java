package net.java.dev.moskito.webui.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing widget config.
 *
 * @author dsilenko
 */
public class DashboardWidgetBean {

	private String name;
	private WidgetType type;
	private List<ProducerDecoratorBean> producerDecoratorBeans;
	private List<String> configAttributes;
	private List<ProducerGroup> producerGroups;

	public void setProducerGroups(List<ProducerGroup> producerGroups) {
		this.producerGroups = producerGroups;
	}

	public List<ProducerGroup> getProducerGroups() {
		return producerGroups;
	}

	public DashboardWidgetBean() {
	}

	public DashboardWidgetBean(String aName) {
		name = aName;
	}

	public DashboardWidgetBean(String aName, WidgetType aType, List<DashboardWidgetBean.ProducerGroup> aProducerGroups) {
		name = aName;
		type = aType;
		producerGroups = aProducerGroups;
	}

	public DashboardWidgetBean(String aName, WidgetType aType, List<DashboardWidgetBean.ProducerGroup> aProducerGroups, List<String> aConfigAttributes) {
		this(aName, aType, aProducerGroups);
		configAttributes = aConfigAttributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WidgetType getType() {
		return type;
	}

	public List<ProducerDecoratorBean> getProducerDecoratorBeans() {
		return producerDecoratorBeans;
	}

	public void setProducerDecoratorBeans(List<ProducerDecoratorBean> aProducerDecoratorBeans) {
		producerDecoratorBeans = aProducerDecoratorBeans;
	}

	public List<String> getConfigAttributes() {
		return configAttributes;
	}

	public void setConfigAttributes(List<String> aConfigAttributes) {
		configAttributes = aConfigAttributes;
	}

	/**
	 * Represents a group of producers.
	 */
	public static class ProducerGroup {
		private String groupName;
		private List<Producer> producers;
		private List<Caption> captions;
		private boolean selectedGroup;

		public ProducerGroup(String aGroupName) {
			groupName = aGroupName;
			producers = new ArrayList<Producer>();
			captions = new ArrayList<Caption>();
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String aGroupName) {
			groupName = aGroupName;
		}

		public List<Producer> getProducers() {
			return producers;
		}

		public void setProducers(List<Producer> aProducers) {
			producers = aProducers;
		}

		public List<Caption> getCaptions() {
			return captions;
		}

		public void setCaptions(List<Caption> aCaptions) {
			captions = aCaptions;
		}

		public boolean isSelectedGroup() {
			return selectedGroup;
		}

		public void setSelectedGroup(boolean selectedGroup) {
			this.selectedGroup = selectedGroup;
		}

	}

	public static class Producer {
		private String id;
		private List<String> values = new ArrayList<String>();
		private boolean selectedProducer;

		public Producer(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setId(String aId) {
			id = aId;
		}

		public List<String> getValues() {
			return values;
		}

		public void setValues(List<String> aValues) {
			values = aValues;
		}

		public void addValue(String aValue){
			values.add(aValue);
		}

		public boolean isSelectedProducer() {
			return selectedProducer;
		}

		public void setSelectedProducer(boolean aSelectedProducer) {
			selectedProducer = aSelectedProducer;
		}

	}
	
	public static class Caption {
		private String captionName;
		private String description;
		private boolean selectedCaption;

		public Caption(String aCaptionName) {
			captionName = aCaptionName;
		}

		public Caption(String aCaptionName, String aDescription) {
			this(aCaptionName);
			description = aDescription;
		}

		public String getCaptionName() {
			return captionName;
		}

		public void setCaptionName(String aCaptionName) {
			captionName = aCaptionName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String aDescription) {
			description = aDescription;
		}

		public boolean isSelectedCaption() {
			return selectedCaption;
		}

		public void setSelectedCaption(boolean selectedCaption) {
			this.selectedCaption = selectedCaption;
		}

	}

}
