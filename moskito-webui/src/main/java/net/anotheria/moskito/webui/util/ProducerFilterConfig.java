package net.anotheria.moskito.webui.util;

import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 00:08
 */
@ConfigureMe(allfields = true)
public class ProducerFilterConfig {
	private String clazzName;

	private String parameter;

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "ProducerFilterConfig{" +
				"clazzName='" + clazzName + '\'' +
				", parameter='" + parameter + '\'' +
				'}';
	}
}
