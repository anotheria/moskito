package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.dynamic.ProxyUtils;


public class ErrorTestSecondServiceImpl implements ErrorTestSecondService {

	private ErrorTestService testService = ProxyUtils.createServiceInstance(new ErrorTestServiceImpl(), "foo", "foo", ErrorTestService.class);

	@Override
	public void nonCatchingEcho() {
		testService.echo();
	}

	@Override
	public void catchingEcho() {
		try{
			testService.echo();
		}catch(IllegalArgumentException e){}
	}
}
