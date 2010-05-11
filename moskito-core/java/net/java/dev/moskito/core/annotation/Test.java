package net.java.dev.moskito.core.annotation;

public class Test {
	@Measure
	public static long echo(long param){
		System.out.println("echo "+param);
		return param;
	}
	
	public static void main(String a[]){
		for (int i=0; i<5; i++)
			echo(10);
		System.out.println("done");
	}
}
