package net.anotheria.moskito.core.command;

import net.anotheria.moskito.core.command.CommandController;
import net.anotheria.moskito.core.command.CommandControllerFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommandControllerTest {
	
	private static CommandController controller;

	@BeforeClass public static void createController(){
		controller = CommandControllerFactory.getCommandController();
	}
	
	@Test public void addProcessor(){
		controller.registerCommandProcessor("test", new TestProcessor());
	}
}
