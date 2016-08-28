package net.anotheria.moskito.webui.gauges.api;

import java.io.Serializable;

/**
 * Represents a gauge zone (area of color in a gauge).
 *
 * @author lrosenberg
 * @since 21.04.15 08:49
 */
public class GaugeZoneAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -1389237916263089040L;

	/**
	 * Zones color.
	 */
	private String color;
	/**
	 * Start of the zone in percent (0.0 - 1.0).
	 */
	private float left;
	/**
	 * End of the zone in percent (0.0 - 1.0).
	 */
	private float right;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	@Override public String toString(){
		return getColor()+": ["+left+", "+right+ ']';
	}


}
