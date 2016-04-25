package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration which allow to specify color of the accumulator's graph.
 *
 * @author Illya Bogatyrchuk
 */
@ConfigureMe(allfields = true)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class AccumulatorGraphColor implements Serializable{
	/**
	 * Name of the accumulator.
	 */
	@Configure
	private String name;
	/**
	 * Graph color.
	 * Html color value, e.g. #RRGGBB.
	 */
	@Configure
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "AccumulatorGraphColor{" +
				"name='" + name + '\'' +
				", color='" + color + '\'' +
				'}';
	}
}
