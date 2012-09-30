package net.anotheria.moskito.core.registry;

/**
 * Thrown if not existing producer is requested..
 *
 * @author lrosenberg
 * @since 02.09.12 11:12
 */
public class NoSuchProducerException extends ProducerRegistryAPIException{
	public NoSuchProducerException(String producerId){
		super("No producer with id "+producerId+" registered");
	}
}
