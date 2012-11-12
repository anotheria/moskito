package net.anotheria.moskito.test.proxytime;

public class TestImpl implements Test{

	@Override
	public void doSomething() {
		//spend some cycles
		int sum = 0;
		for (int i=0; i<1000; i++){
			sum += i;
		}		
	}

}
