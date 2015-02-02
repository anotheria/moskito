package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * This class is a wrapper which protects the way the registry stores it references to the producers from possible customers.
 * The idea is to be able to create weak references in the future, to prevent memory leaks if someone is registering new beans within the registry.
 * @author lrosenberg
 *
 */
public class ProducerReference {

    /**
     * Producer id reference links to.
     */
    private final String producerId;

    /**
	 * Link to the producer. TODO in the future it should/could be a WeakReference.
	 */
	private final IStatsProducer target;

    /**
     * Constructor.
     *
     * @param aProducer {@link net.anotheria.moskito.core.producers.IStatsProducer} created reference will be linked to
     */
	public ProducerReference(final IStatsProducer aProducer){
        if (aProducer == null) {
            throw new IllegalArgumentException("Parameter aProducer is null");
        }

        this.producerId = aProducer.getProducerId();
        this.target = aProducer;
	}

	/**
	 * Returns the underlying stats producer.
	 *
     * @return referenced {@link net.anotheria.moskito.core.producers.IStatsProducer}
	 */
	public IStatsProducer get(){
		return target;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProducerReference that = (ProducerReference) o;

        return !(producerId != null ? !producerId.equals(that.producerId) : that.producerId != null);
    }

    @Override
    public int hashCode() {
        return producerId != null ? producerId.hashCode() : 0;
    }

    @Override public String toString(){
		return "PR->"+get();
	}
}
