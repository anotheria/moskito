package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;
import net.anotheria.moskito.core.config.errorhandling.ErrorHandlingConfig;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.helper.AutoTieAbleProducer;
import net.anotheria.moskito.core.predefined.ErrorStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.AbstractBuiltInProducer;
import net.anotheria.moskito.core.util.AfterStartTasks;
import net.anotheria.moskito.core.util.BuiltInProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This producer registers all errors in the system. Since this producer must be available at first error and can only be present once, we made it singleton using holder-class pattern.
 *
 * @author lrosenberg
 * @since 01.06.17 16:34
 */
public final class BuiltInErrorProducer extends AbstractBuiltInProducer<ErrorStats> implements IStatsProducer<ErrorStats>, BuiltInProducer, AutoTieAbleProducer {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(BuiltInErrorProducer.class);
	/**
	 * Map with ErrorStats object for ExceptionTypes. Used for fast access to the proper ErrorStats object.
	 */
	private ConcurrentHashMap<Class, ErrorStats> statsMap = null;
	/**
	 * Stats list for getStats method.
	 */
	private CopyOnWriteArrayList<ErrorStats> statsList = null;
	/**
	 * Cumulated stats over all exceptions.
	 */
	private ErrorStats cumulatedStats;

	/**
	 * Logger for global error catching. If ErrorHandlingConfig.isLogErrors() is enabled every error will be logged into this logger.
	 */
	private static Logger globalErrorLogger = LoggerFactory.getLogger("MoSKitoCaughtErrors");

	private ConcurrentMap<String, List<ErrorCatcher>> catchers = new ConcurrentHashMap<>();
	private List<ErrorCatcher> defaultCatchers = new CopyOnWriteArrayList<>();

	/**
	 * Custom configured catchers.
	 */
	private List<ErrorCatcher> customCatchers = new CopyOnWriteArrayList<>();



	private ErrorHandlingConfig errorHandlingConfig = null;

	/**
	 * Constructor.
	 */
	private BuiltInErrorProducer(){
		init();
	}


	/**
	 * Initialization. Moved out to be reused in unit-tests.
	 */
	private void init(){
		statsMap = new ConcurrentHashMap<>();
        statsList = new CopyOnWriteArrayList<>();

		cumulatedStats = new ErrorStats("cumulated");
		statsList.add(cumulatedStats);

		catchers = new ConcurrentHashMap<>();
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);


		//add charts for cumulated errors.
		AfterStartTasks.submitTask(new Runnable() {
			@Override
			public void run() {
				AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors.Cumulated.Total", "total", "cumulated"));
			}
		});
		AfterStartTasks.submitTask(new Runnable() {
			@Override
			public void run() {
				AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors.Cumulated.Initial", "initial", "cumulated"));
			}
		});

		errorHandlingConfig = null;
	}

	/**
	 * Helper method to create a accumulator definiton.
	 * @param name name of the accumulator.
	 * @param valueName name of the value (initial or total).
	 * @param statName name of the stat (exception name or 'cumulated').
	 * @return
	 */
	private AccumulatorDefinition createAccumulatorDefinition(String name, String valueName, String statName){
		AccumulatorDefinition definition = new AccumulatorDefinition();
		definition.setName(name);
		definition.setProducerName(getProducerId());
		definition.setStatName(statName);
		definition.setValueName(valueName);
		definition.setIntervalName(MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().getAutoChartErrorsInterval());
		return definition;
	}

	/**
	 * Returns the singleton instance of this producer.
	 * @return
	 */
	public static BuiltInErrorProducer getInstance(){
		return ErrorProducerHolder.instance;
	}

	@Override
	public List<ErrorStats> getStats() {
		return statsList;
	}

	@Override
	public String getProducerId() {
		return "ErrorProducer";
	}

	@Override
	public String getCategory() {
		return "errors";
	}

	public void notifyError(Throwable throwable){
		if (errorHandlingConfig==null)
			errorHandlingConfig = MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig();

		if (errorHandlingConfig.isCountRethrows() && MoSKitoContext.get().seenErrorAlready(throwable)) {
			cumulatedStats.addRethrown();
			Class clazz = throwable.getClass();
			ErrorStats existingStats = statsMap.get(clazz);
			if (existingStats!=null){
				existingStats.addRethrown();
			}else{
				//the code should never be able to be here. If we already seen the error in the same thread we will have created stats by now.
				log.error("This can't happen, existing stats are null for "+throwable.getClass());
			}

			return;
		}

		boolean isInitialError = !MoSKitoContext.get().markErrorAndReturnIfErrorAlreadyHappenedBefore();
		cumulatedStats.addError(isInitialError);

		//is logging enabled?
		if (errorHandlingConfig.isLogErrors()){
			globalErrorLogger.error("auto-caught: " +throwable.getMessage(), throwable);
		}


		//handle catchers, first global catchers
		for (ErrorCatcher catcher : defaultCatchers){
			catcher.add(throwable);
		}

		//handle catchers, now custom global catchers
		for (ErrorCatcher catcher : customCatchers){
			catcher.add(throwable);
		}

		//now special catchers
		List<ErrorCatcher> perExceptionCatchers = catchers.get(throwable.getClass().getName());
		if (perExceptionCatchers!=null && perExceptionCatchers.size()>0){
			for (ErrorCatcher catcher : perExceptionCatchers){
				catcher.add(throwable);
			}
		}

		//first we check if this throwable class is already in the map
		Class clazz = throwable.getClass();
		ErrorStats existingStats = statsMap.get(clazz);
		if (existingStats!=null){
			existingStats.addError(isInitialError);
			return;
		}

		//ok this is a new, yet unseen error.
		ErrorStats newErrorStatsObject = new ErrorStats(clazz.getSimpleName());
		ErrorStats oldErrorStatsObject = statsMap.putIfAbsent(clazz, newErrorStatsObject);
		if (oldErrorStatsObject != null){
			//apparently another thread already added this clazz, we can reuse previously added stats object.
			oldErrorStatsObject.addError(isInitialError);
			return;
		}

		//ok, our new object is now the current object.
		newErrorStatsObject.addError(isInitialError);
		statsList.add(newErrorStatsObject);

		if (errorHandlingConfig.isAutoChartErrors()){
			//means we have to add a new accumulator for this error.
			String chartName = throwable.getClass().getSimpleName();
			AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors."+chartName+".Total", "total", chartName));
			AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors."+chartName+".Initial", "initial", chartName));

		}

	}

	ErrorStats testingGetStatsForError(Class errorClazz){
		return statsMap.get(errorClazz);
	}

	ErrorStats testingGetCumulatedStats(){
		return cumulatedStats;
	}

	void testingReset(){
		init();
	}


	/**
	 * Called from the error handling config instance, after a configuration update or initial configuration.
	 * @param errorHandlingConfig
	 */
	public void afterConfiguration(ErrorHandlingConfig errorHandlingConfig) {
		this.errorHandlingConfig = errorHandlingConfig;

		//first create default-catchers
		ErrorCatcherConfig[] defaultCatcherConfigs = errorHandlingConfig.getDefaultCatchers();
		if (defaultCatcherConfigs != null && defaultCatcherConfigs.length > 0) {
			defaultCatchers = new CopyOnWriteArrayList<>();
			for (ErrorCatcherConfig c : defaultCatcherConfigs) {
				defaultCatchers.add(ErrorCatcherFactory.createErrorCatcher(c));
			}
		}

		//now create regular catchers
		ErrorCatcherConfig[] catcherConfigs = errorHandlingConfig.getCatchers();
		if (catcherConfigs != null && catcherConfigs.length > 0) {
			catchers = new ConcurrentHashMap<>();
			for (ErrorCatcherConfig c : catcherConfigs) {
				ErrorCatcher catcher = ErrorCatcherFactory.createErrorCatcher(c);
				List<ErrorCatcher> catcherList = catchers.get(c.getExceptionClazz());
				if (catcherList == null) {
					catcherList = new LinkedList<>();
					catchers.put(c.getExceptionClazz(), catcherList);
				}
				catcherList.add(catcher);
			}
		}
	}


	/**
	 * Holder class for BuildInErrorProducer instance.
	 */
	private static class ErrorProducerHolder{
		/**
		 * Instance.
		 */
		static BuiltInErrorProducer instance = new BuiltInErrorProducer();
	}

	/**
	 * Used for testing returns true if this error class would run though catchers.
	 */
	/** testing **/ boolean wouldCatch(Class c){
		if (defaultCatchers!=null && defaultCatchers.size()>0)
			return true;
		List<ErrorCatcher> errorCatcherList = catchers.get(c.getName());
		if (errorCatcherList!=null && errorCatcherList.size()>0)
			return true;
		return false;
	}

	public List<ErrorCatcherBean> getErrorCatcherBeans(){
		LinkedList<ErrorCatcherBean> ret = new LinkedList<>();

		for (ErrorCatcher c : defaultCatchers){
			ret.add(makeErrorCatcherBean(c, ErrorCatcherBean.ErrorCatcherType.DEFAULT));
		}

		for (ErrorCatcher c : customCatchers){
			ret.add(makeErrorCatcherBean(c, ErrorCatcherBean.ErrorCatcherType.CUSTOM));
		}

		for (Map.Entry<String,List<ErrorCatcher>> entry : catchers.entrySet()){
			for (ErrorCatcher c : entry.getValue()){
				ret.add(makeErrorCatcherBean(c, ErrorCatcherBean.ErrorCatcherType.EXCEPTION_BOUND));
			}
		}
		

		return ret;
	}

	private ErrorCatcherBean makeErrorCatcherBean(ErrorCatcher c, ErrorCatcherBean.ErrorCatcherType type){
		ErrorCatcherBean ret = new ErrorCatcherBean();

		ret.setType(type);
		ret.setName(c.getName());
		ret.setNumberOfCaughtErrors(c.getNumberOfCaughtErrors());
		ret.setTarget(c.getConfig().getTarget());
		ret.setParameter(c.getConfig().getParameter());

		return ret;
	}

	public ErrorCatcher getCatcher(String name, String type){
		ErrorCatcherBean.ErrorCatcherType catcherType = ErrorCatcherBean.ErrorCatcherType.valueOf(type);
		switch (catcherType){
			case DEFAULT:
				return getCatcherFromDefaultCatchers(name);
			case EXCEPTION_BOUND:
				return getCatcherForException(name);
			case CUSTOM:
				default:
					throw new IllegalArgumentException("Returning catchers of type "+type+",name: "+name+" not supported yet");

		}

	}

	//TODO this implementation is actually very ... optimistic, we should think about replacing it with something that actually does support multiple catchers in the future.
	private ErrorCatcher getCatcherForException(String name) {
		List<ErrorCatcher> catchersByName = catchers.get(name);
		if (catchersByName==null)
			throw new IllegalArgumentException("Catcher: "+name+" not found");
		return catchersByName.get(0);
	}

	private ErrorCatcher getCatcherFromDefaultCatchers(String name) {
		for (ErrorCatcher c : defaultCatchers){
			if (c.getName()!=null && c.getName().equals(name))
				return c;
		}
		throw new IllegalArgumentException("Catcher: "+name+" not found");

	}

	public void addCustomErrorCatcher(ErrorCatcher catcher){
		customCatchers.add(catcher);
	}


}
