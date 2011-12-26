package net.java.dev.moskito.control.configuration;

public enum ConnectorCategory {
	EXTAPI("EXTAPI"), Admintool("Admintool"), REGISTRY("REGISTRY"), SERVICE("SERVICE"), App("App"), PHOTOSERVER("Photoserver"), PAYMENT("Payment"),
	HUDSON("Hudson"), JIRA("Jira");
	
	private ConnectorCategory(String aCategoryName) {
		this.categoryName = aCategoryName;
	}
	
	private String categoryName;	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static ConnectorCategory findByCategoryName(String categoryName) {
		for (ConnectorCategory category : values()) {
			if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
				return category;
			}
		}
		throw new IllegalArgumentException("Unknown category: "+categoryName);
	}

}
