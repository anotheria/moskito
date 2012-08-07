package net.java.dev.moskito.webui.bean;

import java.util.List;

public class JourneyCallDuplicateStepBean {
	private String call;
	private List<String> positions;
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public List<String> getPositions() {
		return positions;
	}
	public void setPositions(List<String> positions) {
		this.positions = positions;
	}
}
