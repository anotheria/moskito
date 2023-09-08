package net.anotheria.moskito.core.util;

import java.util.Collections;
import java.util.List;

import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.core.inspection.Inspectable;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * Implements {@link IStatsProducer} and is an {@link Inspectable}.
 * 
 * @author Michael König
 */
public class SimpleStatsProducer<S extends IStats> implements IStatsProducer<S>, Inspectable {

    /**
     * The id of the producer.
     */
    private final String producerId;
    /**
     * The subsystem of the producer.
     */
    private final String subsystem;
    /**
     * The category of the producer.
     */
    private final String category;
    /**
     * The list of {@link IStats}.
     */
    private final List<S> statsList;
    /**
     * {@link CreationInfo} object initialized on startup.
     */
    private final CreationInfo creationInfo;

    /**
     * Constructs an instance of GenericMBeanProducer.
     * 
     * @param aProducerId
     * @param aCategory
     * @param aSubsystem
     * @param theStatsList
     *            the list of {@link IStats}
     */
    public SimpleStatsProducer(final String aProducerId,
                                final String aCategory,
                                final String aSubsystem,
                                final List<S> theStatsList) {
        this.producerId = aProducerId;
        this.category = aCategory;
        this.subsystem = aSubsystem;
        this.statsList = theStatsList;
        creationInfo = new CreationInfo(new Exception().fillInStackTrace().getStackTrace());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreationInfo getCreationInfo() {
        return creationInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProducerId() {
        return producerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<S> getStats() {
        return Collections.unmodifiableList(statsList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSubsystem() {
        return subsystem;
    }

}
