package net.anotheria.moskito.core.exceptions;

import net.anotheria.moskito.core.exceptions.ExcServiceException;

public interface IExcService {
	public void throwDeclaredException() throws ExcServiceException;
		
	public void throwRuntimeException() throws ExcServiceException, RuntimeException;
	
}
