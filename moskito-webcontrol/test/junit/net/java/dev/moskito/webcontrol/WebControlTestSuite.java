package net.java.dev.moskito.webcontrol;

import net.java.dev.moskito.webcontrol.repository.DummyFeedRepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={DummyFeedRepository.class} )
public class WebControlTestSuite {
	
}
