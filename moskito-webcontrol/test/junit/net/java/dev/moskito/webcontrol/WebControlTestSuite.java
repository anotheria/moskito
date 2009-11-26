package net.java.dev.moskito.webcontrol;

import net.java.dev.moskito.webcontrol.repository.BasicTestRepository;
import net.java.dev.moskito.webcontrol.repository.DummyFeedRepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={DummyFeedRepository.class,BasicTestRepository.class} )
public class WebControlTestSuite {
	
}
