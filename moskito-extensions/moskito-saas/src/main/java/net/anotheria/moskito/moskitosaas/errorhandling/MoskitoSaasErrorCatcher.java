package net.anotheria.moskito.moskitosaas.errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherTarget;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.errorhandling.CaughtError;
import net.anotheria.moskito.core.errorhandling.ErrorCatcher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.02.18 23:26
 */
public class MoskitoSaasErrorCatcher implements ErrorCatcher{


	ErrorCatcherConfig config;

	private AtomicLong counter = new AtomicLong();

	public MoskitoSaasErrorCatcher(){
		config = new ErrorCatcherConfig();
		config.setTarget(ErrorCatcherTarget.CUSTOM);
		config.setParameter("none");

	}

	@Override
	public void add(Throwable throwable) {
		long number = counter.incrementAndGet();
		System.out.println("Ready for utilization ("+number+") "+throwable.getMessage());

		ErrorWrapper wrapper = new ErrorWrapper();
		wrapper.setExceptionClassName(throwable.getClass().getName());
		wrapper.setExceptionMessage(throwable.getMessage());
		wrapper.setRunningNumber(number);
		wrapper.setTimestamp(System.currentTimeMillis());
		wrapper.setTags(MoSKitoContext.getTags());

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(wrapper);



		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonOutput);
		String prettyJsonString = gson.toJson(je);
		System.out.println("Consume: "+prettyJsonString);

		

	}

	@Override
	public List<CaughtError> getErrorList() {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "MoskitoSaasErrorCatcher";
	}

	@Override
	public int getNumberOfCaughtErrors() {
		return (int)counter.get();
	}

	@Override
	public ErrorCatcherConfig getConfig() {
		return config;
	}
}
