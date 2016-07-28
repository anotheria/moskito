package net.anotheria.moskito.sql.stats;

import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;

/**
 * Stats object for sql queries.
 *
 * @author lrosenberg
 * @since 10.08.14 23:32
 */
public class QueryStats extends RequestOrientedStats {

	/**
	 * Creates a new QueryStats object with the given query.
	 * @param query
	 */
	public QueryStats(String query){
		super(query);
	}

	public QueryStats(){
    }

	public QueryStats(String query, Interval[] intervals){
		super(query, intervals);
	}

	/* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
	static {
		if(MoskitoWebUi.isPresent()) {
			new StatsDecoratorRegistrator().register();
		}
	}

	/* will be initialized only if MoSKito WebUI is embedded into application */
	private static final class StatsDecoratorRegistrator {
		public void register() {
			DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(QueryStats.class, new QueryStatsDecorator());
		}
	}

}
