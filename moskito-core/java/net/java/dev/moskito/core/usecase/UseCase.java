package net.java.dev.moskito.core.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

public class UseCase {
	
	private List<UseCasePath> _cachedPathesList;
	private Map<String, UseCasePath> pathes;
	
	private String name;
	
	public UseCase(String aName){
		name = aName;
		pathes = new HashMap<String,UseCasePath>();
		_cachedPathesList = new ArrayList<UseCasePath>();
	}
	
	public void addExecution(ExistingRunningUseCase finishedUseCase){
		String path = finishedUseCase.getUseCasePath();
		UseCasePath useCasePath = pathes.get(path);
		if (useCasePath==null){
			synchronized(pathes){//DLC
				useCasePath = pathes.get(path);
				if (useCasePath==null){
					useCasePath = new UseCasePath(path);
					pathes.put(path, useCasePath);
					rebuildCachedPathList();
				}
			}
		}
		useCasePath.addProcessedUseCase();
	}
	
	private void rebuildCachedPathList(){
		_cachedPathesList = new ArrayList<UseCasePath>(pathes.size());
		for (Iterator<UseCasePath> i = pathes.values().iterator(); i.hasNext();)
			_cachedPathesList.add(i.next());
	}
	
	public List<UseCasePath> getCachedPathList(){
		return _cachedPathesList;
	}
	
	public String toString(){
		return getName()+": "+_cachedPathesList.toString();
	}
	
	public String getName(){
		return name;
	}
	
}
