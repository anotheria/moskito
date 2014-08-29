package net.anotheria.moskito.core.registry;

/**
 * Thrown if not existing producer is requested..
 *
 * @author lrosenberg
 * @since 02.09.12 11:12
 */
public class NoSuchProducerException extends ProducerRegistryAPIException{
	/**
	 * Creates new NoSuchProducerException.
	 * @param producerId id of the producer that wasn't found.
	 */
	public NoSuchProducerException(String producerId){
		super("No producer with id "+producerId+" registered");
	}
}
