package net.java.dev.moskito.central;

import net.java.dev.moskito.central.storages.JdbcTest;
import net.java.dev.moskito.central.storages.XmlTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({JdbcTest.class, XmlTest.class})
public class MoskitoCentralTestSuite {

}
