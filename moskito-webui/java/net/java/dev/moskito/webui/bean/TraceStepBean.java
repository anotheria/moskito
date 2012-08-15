package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.util.NumberUtils;
/**
 * This bean represents an element along an use-case path.
 * @author lrosenberg
 */
public class TraceStepBean {
	/**
	 * Method call.
	 */
	private String call;
	/**
	 * Method duration.
	 */
	private long duration;
	/**
	 * Time spent in call.
	 */
	private long timespent;
	/**
	 * If true the call was aborted by an uncaught exception.
	 */
	private boolean aborted;
	/**
	 * Children calls.
	 */
	private List<TraceStepBean> children;
	/**
	 * Ident for representation.
	 */
	private String ident;
	/**
	 * If true this call is the root call.
	 */
	private boolean root;
	private int layer;
	/**
	 * The full call for debug purposes.
	 */
	private String fullCall;
	
	/**
	 * This is a temporarly id within the journey call for linking purposes.
	 */
	private int id;
	
	/**
	 * This is a temporarly id within the journey call for linking purposes for the tree plugin.
	 */
	private int parentId;
	
	public TraceStepBean(){
		children = new ArrayList<TraceStepBean>();
	}
	
	public void addChild(TraceStepBean c){
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

	public List<TraceStepBean> getChildren() {
		return children;
	}

	public void setChildren(List<TraceStepBean> children) {
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
	
	@Override public String toString(){
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

	public String getNiceId() {
		return NumberUtils.itoa(id, 3);
	}
	
	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNiceParentId() {
		return parentId < 0 ? "root" : NumberUtils.itoa(parentId, 3);
	}
	
	public int getParentId(){
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public boolean isParentAvailable(){
		return parentId>=0;
	}
}
