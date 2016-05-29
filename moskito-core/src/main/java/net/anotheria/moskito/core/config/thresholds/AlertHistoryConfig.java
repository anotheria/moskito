package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * This class configures the alert history in thresholds.
 *
 * @author lrosenberg
 * @since 22.10.12 17:30
 */
public class AlertHistoryConfig implements Serializable {
	/**
	 * The max number of items in the history.
	 */
	@Configure private int maxNumberOfItems;
	/**
	 * The max number of tolerated items in the list, this value is usually 10% above the max number of items, to reduce the amount of list cut operations.
	 */
	@Configure private int toleratedNumberOfItems;

	public int getMaxNumberOfItems() {
		return maxNumberOfItems;
	}

	public void setMaxNumberOfItems(int maxNumberOfItems) {
		this.maxNumberOfItems = maxNumberOfItems;
	}

	public int getToleratedNumberOfItems() {
		return toleratedNumberOfItems;
	}

	public void setToleratedNumberOfItems(int toleratedNumberOfItems) {
		this.toleratedNumberOfItems = toleratedNumberOfItems;
	}

	@Override public String toString(){
		return "{maxNumberOfItems: "+maxNumberOfItems+", toleratedNumberOfItems: "+toleratedNumberOfItems+ '}';
	}
}
