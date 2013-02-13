package net.anotheria.moskito.webui.shared.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 13.02.13 15:26
 */
@XmlRootElement(name="reply")
public class ReplyObject {
	private boolean success;
	@XmlElement(required = false,nillable = true)
	private String message;

	public ReplyObject(){

	}

	public static ReplyObject success(Object... results){
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
