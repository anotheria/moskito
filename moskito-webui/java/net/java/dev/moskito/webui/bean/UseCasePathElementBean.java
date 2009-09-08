package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class UseCasePathElementBean {
	private String call;
	private long duration;
	private long timespent;
	private boolean aborted;
	private List<UseCasePathElementBean> children;
	private String ident;
	
	private boolean root;
	private int layer;
	
	private String fullCall;
	
	public UseCasePathElementBean(){
		children = new ArrayList<UseCasePathElementBean>();
	}
	
	public void addChild(UseCasePathElementBean c){
		children.add(c);
	}

	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String aCall) {
		if (aCall==null)
			aCall = "";
		setFullCall(aCall);
		if (aCall.length()>100)
			aCall = aCall.substring(0,97)+"...";
		call = aCall;
	}

	public List<UseCasePathElementBean> getChildren() {
		return children;
	}

	public void setChildren(List<UseCasePathElementBean> children) {
		this.children = children;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getTimespent() {
		return timespent;
	}

	public void setTimespent(long timespent) {
		this.timespent = timespent;
	}
	
	public String toString(){
		return "Call: "+call+" Duration: "+duration+" time: "+timespent;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getFullCall() {
		return fullCall;
	}

	public void setFullCall(String fullCall) {
		this.fullCall = fullCall;
	}
}
