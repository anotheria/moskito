package net.anotheria.moskito.core.shared;

import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.threshold.ThresholdRepository;

/**
 * This class is responsible for 'touching' all required components, so they come up.
 *
 * @author lrosenberg
 * @since 17.09.14 15:32
 */
public class TouchEverything {
	public static void touchEverythingButMe(Class clazz){
		if (!clazz.equals(IProducerRegistry.class))
			ProducerRegistryFactory.getProducerRegistryInstance();
		if (!clazz.equals(AccumulatorRepository.class))
			AccumulatorRepository.getInstance();
		if (!clazz.equals(ThresholdRepository.class))
			ThresholdRepository.getInstance();

	}

	private TouchEverything(){
		;//don't instantiate.
	 }
}
