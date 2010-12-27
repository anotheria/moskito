package net.java.dev.moskitodemo.threshold.business;

public class GuardedServiceImpl implements GuardedService{

	@Override
	public void guardedMethod() {
		//this service is for demonstration purposes of threshholds only
		
	}

	@Override
	public void guardedAverageMethod(int lengthInSeconds) {
		try{
			Thread.sleep(1000L*lengthInSeconds);
		}catch(Exception e){}
		
	}

}
