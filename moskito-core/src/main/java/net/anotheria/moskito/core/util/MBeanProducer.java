package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.producers.IStats;

import java.util.List;

public class MBeanProducer extends SimpleStatsProducer<MBeanStats> {


    /**
     * Constructs an instance of GenericMBeanProducer.
     *
     * @param aProducerId
     * @param aCategory
     * @param aSubsystem
     * @param theStatsList the list of {@link IStats}
     */
    public MBeanProducer(String aProducerId, String aCategory, String aSubsystem, List<MBeanStats> theStatsList) {
        super(aProducerId, aCategory, aSubsystem, theStatsList);
    }

}
