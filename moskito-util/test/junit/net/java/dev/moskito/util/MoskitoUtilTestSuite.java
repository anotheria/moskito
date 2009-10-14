package net.java.dev.moskito.util;

import net.java.dev.moskito.util.storage.StorageTest;

import org.apache.log4j.BasicConfigurator;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({StorageTest.class})
public class MoskitoUtilTestSuite {
	static{
		BasicConfigurator.configure();
	}
}
