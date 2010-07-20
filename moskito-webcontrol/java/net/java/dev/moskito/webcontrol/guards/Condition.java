package net.java.dev.moskito.webcontrol.guards;

public enum Condition {

	DEFAULT("black"),
	GREEN("green"), 
	YELLOW("yellow"), 
	RED("red");
	
	private String color;

	private Condition(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

}
