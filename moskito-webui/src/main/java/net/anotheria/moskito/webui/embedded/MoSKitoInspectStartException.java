package net.anotheria.moskito.webui.embedded;

/**
 * This exception is thrown if MoSKito inspect is unable to start.
 *
 * @author lrosenberg
 * @since 16.04.14 11:01
 */
public class MoSKitoInspectStartException extends Exception{
	/**
	 * Creates a new exception instance.
	 * @param cause the reason behind this misery.
	 */
	public MoSKitoInspectStartException(Exception cause){
		super("Couldn't start MoSKito Inspect in Embedded Mode due to " + cause.getMessage(), cause);
	}
}
