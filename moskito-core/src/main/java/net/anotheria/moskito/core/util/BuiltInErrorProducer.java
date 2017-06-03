package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.helper.AutoTieAbleProducer;
import net.anotheria.moskito.core.predefined.ErrorStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This producer registers all errors in the system. Since this producer must be available at first error and can only be present once, we made it singleton using holder-class pattern.
 *
 * @author lrosenberg
 * @since 01.06.17 16:34
 */
public final class BuiltInErrorProducer extends AbstractBuiltInProducer<ErrorStats>  implements IStatsProducer<ErrorStats>, BuiltInProducer, AutoTieAbleProducer {

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

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);

		//add charts for cumulated errors.
		AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("ErrorsCumulatedTotal", "total", "cumulated"));
		AccumulatorRepository.getInstance().createAccumulator(createAccumulatorDefinition("ErrorsCumulatedInitial", "initial", "cumulated"));
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
		boolean isInitialError = !MoSKitoContext.get().markErrorAndReturnIfErrorAlreadyHappenedBefore();
		cumulatedStats.addError(isInitialError);

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


}
