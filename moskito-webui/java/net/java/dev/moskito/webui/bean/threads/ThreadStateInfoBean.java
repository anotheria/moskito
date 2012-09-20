package net.java.dev.moskito.webui.bean.threads;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

/**
 * This beans holds statistics of a thread state. This means cumulated information by threads.
 *
 * @author lrosenberg
 * @since 20.09.12 00:33
 */
public class ThreadStateInfoBean implements IComparable<ThreadStateInfoBean>{
	/**
	 * Name of the state.
	 */
	private String state;
	/**
	 * Count of the occurencies of this state.
	 */
	private int count = 0;

	/**
	 * Creates a new ThreadStateInfoBean.
	 * @param aState name of the associated state.
	 */
	public ThreadStateInfoBean(String aState){
		state = aState;
	}

	/**
	 * Increases the count for this state by one.
	 * Increases the count for this state by one.
	 */
	public void increaseCount(){
		count++;
	}

	public int getCount(){
		return count;
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareString(state, ((ThreadStateInfoBean)anotherObject).state);
	}

	public String getState(){
		return state;
	}

}
