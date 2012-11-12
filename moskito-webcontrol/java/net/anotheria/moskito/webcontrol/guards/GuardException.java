package net.anotheria.moskito.webcontrol.guards;

public class GuardException extends Exception {

	public GuardException() {
		super();
	}

	public GuardException(String message) {
		super(message);
	}

	public GuardException(String message, Throwable cause) {
		super(message, cause);
	}

	public GuardException(Throwable cause) {
		super(cause);
	}

}
