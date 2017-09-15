package net.anotheria.moskito.core.util;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This producer registers all errors in the system. Since this producer must be available at first error and can only be present once, we made it singleton using holder-class pattern.
 *
 * @author lrosenberg
 * @since 01.06.17 16:34
 */
public final class BuiltInErrorProducer extends AbstractBuiltInProducer<ErrorStats>  implements IStatsProducer<ErrorStats>, BuiltInProducer, AutoTieAbleProducer {

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

	private ConcurrentMap<Class, ErrorCatcher> catchers;

	private ConcurrentMap<Class, LoggerWrapper> wrappers;



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
		wrappers = new ConcurrentHashMap<>();

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);

		//add charts for cumulated errors.
		AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors.Cumulated.Total", "total", "cumulated"));
		AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("Errors.Cumulated.Initial", "initial", "cumulated"));
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
		ErrorHandlingConfig config = MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig();

		if (config.isCountRethrows() && MoSKitoContext.get().seenErrorAlready(throwable)) {
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
		if (config.isLogErrors()){
			globalErrorLogger.error("auto-caught: " +throwable.getMessage(), throwable);
		}

		ErrorCatcherConfig catcherConfig = config.getCatcherConfig(throwable.getClass().getName());
		if (catcherConfig!=null){
			if (catcherConfig.getTarget().keepInMemory()){
				ErrorCatcher catcher = catchers.get(throwable.getClass());
				if (catcher == null){
					//try again
					ErrorCatcher newCatcher = new ErrorCatcher(throwable.getClass());
					ErrorCatcher oldCatcher = catchers.putIfAbsent(throwable.getClass(), newCatcher);
					catcher = oldCatcher == null ? newCatcher : oldCatcher;
				}

				catcher.add(throwable);
			}
			if (catcherConfig.getTarget().log()){
				//log errors
				LoggerWrapper wrapper = wrappers.get(throwable.getClass());

				if (wrapper == null){
					wrapper = new LoggerWrapper();
					LoggerWrapper old = wrappers.putIfAbsent(throwable.getClass(), wrapper);
					if (old==null){
						wrapper.setLogger(catcherConfig.getParameter());
					}else{
						wrapper = old;
					}
				}
				//for the very unexpectable case that a wrapper has been put into the map but the logger is not set yet.
				if (wrapper.getLogger()!=null){
					wrapper.getLogger().error("caught "+throwable.getClass(), throwable);
				}
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

		if (config.isAutoChartErrors()){
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
	 * Holder class for BuildInErrorProducer instance.
	 */
	private static class ErrorProducerHolder{
		/**
		 * Instance.
		 */
		static BuiltInErrorProducer instance = new BuiltInErrorProducer();
	}

	public ErrorCatcher getCatcher(Class clazz){
		return catchers.get(clazz);
	}

	public List<ErrorCatcher> getCatchers() {
		List<ErrorCatcher> ret = new ArrayList<>();
		ret.addAll(catchers.values());
		return ret;
	}

	static class LoggerWrapper{
		private volatile Logger log;

		public LoggerWrapper(){

		}

		public void setLogger(String name){
			log = LoggerFactory.getLogger(name);
		}

		Logger getLogger(){
			return log;
		}

		@Override public String toString(){
			return "LoggerWrapper "+log;
		}
	}

}
