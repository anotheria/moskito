package net.java.dev.moskito.webui.bean;

public enum ProducerVisibility {
	SHOW,
	HIDE;

	public boolean isShown(){
		return this==SHOW;
	}

	public boolean isHidden(){
		return this==HIDE;
	}
	
	public static ProducerVisibility fromString(String parameter){
		ProducerVisibility p = ProducerVisibility.valueOf(parameter);
		return p==null ? SHOW : p;
	}
	
	public String getOpposite(){
		return this == SHOW ? HIDE.toString() : SHOW.toString();
	}
}
