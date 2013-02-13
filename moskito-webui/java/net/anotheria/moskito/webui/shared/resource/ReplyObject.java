package net.anotheria.moskito.webui.shared.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 13.02.13 15:26
 */
@XmlRootElement(name="reply")
public class ReplyObject {
	@XmlElement
	private boolean success;
	@XmlElement(required = false,nillable = false)
	private String message;

	@XmlElement
	private HashMap<String, Object> results = new HashMap<String, Object>();

	public ReplyObject(){

	}

	public ReplyObject(String name, Object result){
		results.put(name, result);
	}

	public void addResult(String name, Object result){
		results.put(name, result);
	}

	public static ReplyObject success(String name, Object result){
		ReplyObject ret = new ReplyObject(name, result);
		ret.success = true;
		return ret;
	}

	public static ReplyObject success(){
		ReplyObject ret = new ReplyObject();
		ret.success = true;
		return ret;
	}

	public static ReplyObject error(String message){
		ReplyObject ret = new ReplyObject();
		ret.success = false;
		ret.message = message;
		return ret;
	}


}
