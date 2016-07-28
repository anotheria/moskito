package net.anotheria.moskito.core.exceptions;

public interface IExcService {
	void throwDeclaredException() throws ExcServiceException;
		
	void throwRuntimeException() throws ExcServiceException, RuntimeException;
	
}
