package net.anotheria.moskito.core.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayLogOutput implements ILogOutput{

	private ByteArrayOutputStream output = new ByteArrayOutputStream();
	
	@Override
	public void out(String message) {
		try{
			output.write(message.getBytes());
			output.write("\n".getBytes());
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("Output failed", e);
		}
	}
	
	public String getMessage(){
		return output.toString();
	}
	
}
