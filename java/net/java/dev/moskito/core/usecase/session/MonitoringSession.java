package net.java.dev.moskito.core.usecase.session;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

public class MonitoringSession {
	private String name;
	private List<ExistingRunningUseCase> useCases;
	private boolean active;
	private long createdTimestamp;
	private long lastActivityTimestamp;
	
	public MonitoringSession(String aName){
		name = aName;
		createdTimestamp = System.currentTimeMillis();
		active = true;
		useCases = new ArrayList<ExistingRunningUseCase>();
	}
	
	public void addUseCase(ExistingRunningUseCase aUseCase){
		useCases.add(aUseCase);
		lastActivityTimestamp = System.currentTimeMillis();
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public long getLastActivityTimestamp() {
		return lastActivityTimestamp;
	}
	public void setLastActivityTimestamp(long lastActivityTimestamp) {
		this.lastActivityTimestamp = lastActivityTimestamp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ExistingRunningUseCase> getUseCases() {
		return useCases;
	}
}
