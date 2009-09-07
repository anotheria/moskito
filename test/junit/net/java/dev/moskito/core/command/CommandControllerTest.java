package net.java.dev.moskito.core.command;

import org.junit.BeforeClass;
import org.junit.Test;

public class CommandControllerTest {
	
	private static ICommandController controller;

	@BeforeClass public static void createController(){
		controller = CommandControllerFactory.getCommandController();
	}
	
	@Test public void addProcessor(){
		controller.registerCommandProcessor("test", new TestProcessor());
	}
}
