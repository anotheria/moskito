package net.java.dev.moskito.webui;

import net.java.dev.moskito.webui.decorators.CreateDecoratorsTest;
import net.java.dev.moskito.webui.decorators.DecoratorRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({CreateDecoratorsTest.class, DecoratorRegistryTest.class})
public class MoskitoWebUITestSuite {

}
