package net.anotheria.moskito.webui.gauges.api;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.04.15 08:49
 */
public class GaugeZoneAO implements Serializable{
	private String color;
	private float left;
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
		return getColor()+": ["+left+", "+right+"]";
	}


}
