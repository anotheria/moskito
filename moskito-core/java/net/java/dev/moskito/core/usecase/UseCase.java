package net.java.dev.moskito.core.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;

/**
 * Recorded use-case pattern, that contains multiple use-cases.
 * @author lrosenberg
 *
 */
public class UseCase {
	
	private List<UseCasePath> _cachedPathesList;
	/**
	 * Pathes that are detected along the use-cases.
	 */
	private Map<String, UseCasePath> pathes;
	/**
	 * Name of the use cases.
	 */
	private String name;
	
	public UseCase(String aName){
		name = aName;
		pathes = new HashMap<String,UseCasePath>();
		_cachedPathesList = new ArrayList<UseCasePath>();
	}
	/**
	 * Adds an executed use case path.
	 * @param finishedUseCase
	 */
	public void addExecution(CurrentlyTracedCall finishedUseCase){
		String path = finishedUseCase.getTrace();
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
	
	@Override public String toString(){
		return getName()+": "+_cachedPathesList.toString();
	}
	
	/**
	 * Returns the name of this use-case object.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
}
