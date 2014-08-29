package net.anotheria.moskito.core.predefined;

public class TestServiceImpl implements TestService{
	@Override
	public void throwException() {
		throw new RuntimeException("foo");
	}

	private int count;

	public TestServiceImpl(){
		count = 0;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void increase(){
		count++;
	}
	
	public String operationWithParameter(int aParam, String bParam, boolean cParam){
		return "resultofoperationWithParameter";
	}
}
