package net.anotheria.moskito.core.helper;

/**
 * This class is a substitute for interval listeners in case there is no such interval.
 *
 * @author lrosenberg
 * @since 2019-04-14 16:54
 */
public final class NoIntervalListener extends IntervalListener {

	/**
	 * The one and only.
	 */
	public static final NoIntervalListener INSTANCE = new NoIntervalListener();

	private NoIntervalListener(){
		//singleton.
	}

	@Override
	public void addTieable(Tieable toAdd) {
		//do nothing.
	}

	@Override
	public void addTieableAutoTieWrapper(AutoTieWrapper toAdd) {
		//do nothing
	}

	@Override
	public void removeTieable(Tieable toRemove) {
		//do nothing
	}
}
