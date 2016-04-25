package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.stats.Interval;

/**
 * This stat object gathers three-dimensional stats for portals that have guest traffic (not registered user), member traffic (registered users) and
 * premium traffic (paying user).
 *
 * @author lrosenberg
 * @since 18.11.12 20:02
 */
public class GuestBasicPremiumStats extends GenericCounterStats{
	public GuestBasicPremiumStats(String name){
		super(name, "guest", "basic", "premium");
	}
	public GuestBasicPremiumStats(String name, Interval[] intervals){
		super(name, intervals, "guest", "basic", "premium");
	}
	/**
	 * Increases guest value.
	 */
	public void incGuest(){
		super.inc("guest");
	}
	/**
	 * Increases basic value.
	 */
	public void incBasic(){
		super.inc("basic");
	}
	/**
	 * Increases premium value.
	 */
	public void incPremium(){
		super.inc("premium");
	}

	public long getGuest(String intervalName){
		return get("guest", intervalName);
	}

	public long getBasic(String intervalName){
		return get("basic", intervalName);
	}
	public long getPremium(String intervalName){
		return get("premium", intervalName);
	}
	public long getGuest(){
		return get("guest", null);
	}

	public long getBasic(){
		return get("basic", null);
	}
	public long getPremium(){
		return get("premium", null);
	}
	@Override
	public String describeForWebUI() {
		return "GuestBasicPremium";
	}

}
