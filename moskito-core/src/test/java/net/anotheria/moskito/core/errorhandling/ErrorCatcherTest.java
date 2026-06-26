package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherTarget;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.errorhandling.BuiltInErrorProducer;
import net.anotheria.moskito.core.errorhandling.BuiltinErrorCatcher;
import net.anotheria.moskito.core.errorhandling.ErrorCatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ErrorCatcherTest {

	@BeforeEach
	public void before(){
		BuiltInErrorProducer.getInstance().testingReset();
		MoskitoConfigurationHolder.resetConfiguration();
		MoSKitoContext.get().reset();
	}


	@Test public void testOverwrite(){
		MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().setCatchersMemoryErrorLimit(100);

		ErrorCatcherConfig config = new ErrorCatcherConfig();
		config.setExceptionClazz(IllegalArgumentException.class.getName());
		config.setTarget(ErrorCatcherTarget.MEMORY);
		ErrorCatcher catcher = new BuiltinErrorCatcher(config);

		assertNotNull(catcher.getErrorList());
		assertEquals(0, catcher.getErrorList().size());

		//now add 150 elements.
		for (int i=0; i<110; i++){
			catcher.add(new IllegalArgumentException(""+i));
		}

		assertEquals(110, catcher.getErrorList().size());
		catcher.add(new IllegalArgumentException("final"));
		assertEquals(111, catcher.getErrorList().size());
		//now add one more this should lead to trim.
		catcher.add(new IllegalArgumentException("very final"));

		assertEquals(101, catcher.getErrorList().size()); //trimed to 100 + 1
		assertEquals("11", catcher.getErrorList().get(0).getThrowable().getMessage());
	}
}
