package net.anotheria.moskito.core.config.gauges;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Config for a gauge zone, a coloured zone in the gauge.
 *
 * @author lrosenberg
 * @since 21.04.15 08:43
 */
@ConfigureMe
public class GaugeZoneConfig implements Serializable{
	/**
	 * Zones color. Can be one of green, orange, yellow, red.
	 */
	@Configure
	private String color;
	/**
	 * Left limit in between 0 and 1.
	 */
	@Configure
	private float left;
	/**
	 * Right limit between 0 and 1.
	 */
	@Configure
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
