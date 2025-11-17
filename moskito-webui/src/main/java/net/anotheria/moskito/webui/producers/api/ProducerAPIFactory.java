package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for creating and managing {@link ProducerAPI} instances.
 *
 * <p>This factory is responsible for instantiating the ProducerAPI, which provides programmatic
 * access to monitoring producer data (statistics, metrics, and performance information). The factory
 * integrates with both the ANO-PLASS API framework and the ANO-PRISE service factory pattern.
 *
 * <p><b>Factory Pattern Integration:</b>
 * <ul>
 *   <li><b>APIFactory:</b> Implements the API factory pattern for web UI access</li>
 *   <li><b>ServiceFactory:</b> Implements the service factory pattern for programmatic access</li>
 * </ul>
 *
 * <p><b>API Lookup:</b> The factory automatically registers itself with the {@link APIFinder}
 * when {@link #create()} is called, enabling lookup-based API access throughout the application.
 *
 * <p><b>Singleton Behavior:</b> While the factory can create multiple instances via {@link #createAPI()},
 * the {@link #create()} method uses APIFinder which typically provides singleton-like behavior for
 * API instances within an application context.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Method 1: Direct instantiation
 * ProducerAPIFactory factory = new ProducerAPIFactory();
 * ProducerAPI api = factory.createAPI();
 *
 * // Method 2: Via service lookup (preferred)
 * ProducerAPI api = APIFinder.findAPI(ProducerAPI.class);
 * </pre>
 *
 * @author lrosenberg
 * @since 14.02.13 11:48
 * @see ProducerAPI
 * @see ProducerAPIImpl
 */
public class ProducerAPIFactory implements APIFactory<ProducerAPI>, ServiceFactory<ProducerAPI> {
	@Override
	public ProducerAPI createAPI() {
		return new ProducerAPIImpl();
	}

	@Override
	public ProducerAPI create() {
		APIFinder.addAPIFactory(ProducerAPI.class, this);
		return APIFinder.findAPI(ProducerAPI.class);
	}
}
