package net.java.dev.moskito.control.check;

import net.anotheria.util.NumberUtils;

public abstract class MoskitoControlCheck {


	public String toJSON(){
		return "{"+quote("Status")+": "+quote(getStatus())+", "+quote("Message")+": "+quote(getMessage())+
			", "+quote("Timestamp")+": "+quote(NumberUtils.makeISO8601TimestampString(getTimestamp()))+"}";
	}
	
	private long getTimestamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	private Object getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	private String quote(Object o){
		return "\""+o+"\"";
	}
}
