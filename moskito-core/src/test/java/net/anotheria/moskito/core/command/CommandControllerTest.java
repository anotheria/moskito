package net.anotheria.moskito.core.command;

import net.anotheria.moskito.core.command.CommandController;
import net.anotheria.moskito.core.command.CommandControllerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommandControllerTest {
	
	private static CommandController controller;

	@BeforeAll public static void createController(){
		controller = CommandControllerFactory.getCommandController();
	}
	
	@Test public void addProcessor(){
		controller.registerCommandProcessor("test", new TestProcessor());
	}
}
