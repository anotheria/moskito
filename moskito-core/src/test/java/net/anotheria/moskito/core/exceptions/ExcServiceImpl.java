package net.anotheria.moskito.core.exceptions;

public class ExcServiceImpl implements IExcService{

	public void throwDeclaredException() throws ExcServiceException {
		throw new ExcServiceException("detailed exception");
	}

	public void throwRuntimeException() throws ExcServiceException, RuntimeException {
		throw new RuntimeException("A runtime exception");
	}

}
