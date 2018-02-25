package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BuiltIn implementation for the error catcher, catches error inmemory.
 *
 * @author lrosenberg
 * @since 23.02.18 14:14
 */
public class BuiltinErrorCatcher implements ErrorCatcher {
	private volatile List<CaughtError> errorList = new CopyOnWriteArrayList<>();


	private ErrorCatcherConfig config;

	private String name;

	private volatile Logger log = null;

	BuiltinErrorCatcher(ErrorCatcherConfig aConfig){
		this.config = aConfig;
		name = extractName(config.getExceptionClazz());
		if (aConfig.getTarget().log()){
			log = LoggerFactory.getLogger(config.getParameter());
		}
	}

	public void add(Throwable throwable) {

		if (config.getTarget().log()){
	   		log.error("caught " + throwable.getClass(), throwable);

		}

		if (config.getTarget().keepInMemory()){
			int limit = MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().getCatchersMemoryErrorLimit();
			int tolerableLimit = (int)(limit*1.1);
			if (errorList.size()>tolerableLimit){
				trimList(limit);
			}
			errorList.add(new CaughtError(throwable));
		}

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
		return name;
	}

	private String extractName(String className){
		String[] tokens = StringUtils.tokenize(className, '.');
		if (tokens.length==1)
			return tokens[0];
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<tokens.length-1; i++){
			ret.append(tokens[i]).append('.');
		}
		ret.append(tokens[tokens.length-1]);
		return ret.toString();
	}

	@Override
	public int getNumberOfCaughtErrors() {
		return errorList.size();
	}

	@Override
	public ErrorCatcherConfig getConfig() {
		return config;
	}
}
