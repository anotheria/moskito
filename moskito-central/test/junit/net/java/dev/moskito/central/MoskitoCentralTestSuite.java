package net.java.dev.moskito.central;

import net.java.dev.moskito.central.starages.JdbcTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({JdbcTest.class})
public class MoskitoCentralTestSuite {

}
