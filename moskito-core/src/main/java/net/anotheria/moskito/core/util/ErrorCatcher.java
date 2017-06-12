package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is used by the BuiltInErrorProducer to held caught errors.
 *
 * @author lrosenberg
 * @since 04.06.17 20:49
 */
public class ErrorCatcher {

	private volatile List<CaughtError> errorList = new CopyOnWriteArrayList<>();

	private Class clazz;

	ErrorCatcher(Class aClazz){
		clazz = aClazz;
	}

	public void add(Throwable throwable) {
		int limit = MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().getCatchersMemoryErrorLimit();
		int tolerableLimit = (int)(limit*1.1);
		if (errorList.size()>tolerableLimit){
			trimList(limit);
		}
		errorList.add(new CaughtError(throwable));

	}

	public List<CaughtError> getErrorList(){
		return errorList;
	}

	/*unit test*/ synchronized void trimList(int toLimit){
		int listSize = errorList.size();
		List<CaughtError> newList = new CopyOnWriteArrayList<CaughtError>();
		for (int i=listSize - toLimit; i<listSize; i++){
			newList.add(errorList.get(i));
		}
		errorList = newList;

	}

	public String getName(){
		return clazz.getName();
	}
}
