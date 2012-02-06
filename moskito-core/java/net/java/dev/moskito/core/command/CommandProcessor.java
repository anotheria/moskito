package net.java.dev.moskito.core.command;

import java.util.Map;
/**
 * This interface defines a processor for a single command (word) which can be used with the command controller.
 * @author lrosenberg
 */
public interface CommandProcessor {
	/**
	 * Starts the command.
	 * @param command name of the command
	 * @param parameters parameter map
	 */
	 void startCommand(String command, Map<String,String[]> parameters);
	
	/**
	 * Stops the command. 
	 * 
	 */
	 void stopCommand(String command, Map<String,String[]> parameters);
}
