package net.anotheria.moskito.webui.journey.api;

import net.anotheria.util.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This bean represents a single step of a traced call.
 * @author lrosenberg
 */
public class TracedCallStepAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -8088335286863272686L;

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
	private List<TracedCallStepAO> children;
	/**
	 * Ident for representation.
	 */
	private String ident;
	/**
	 * If true this call is the root call.
	 */
	private boolean root;
	/**
	 * Layer is used for tree presentation.
	 */
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

	private int level;


	public TracedCallStepAO(){
		children = new ArrayList<>();
	}
	
	public void addChild(TracedCallStepAO c){
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

	public List<TracedCallStepAO> getChildren() {
		return children;
	}

	public void setChildren(List<TracedCallStepAO> children) {
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
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
