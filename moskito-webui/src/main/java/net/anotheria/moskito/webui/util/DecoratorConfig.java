package net.anotheria.moskito.webui.util;

import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.09.15 16:37
 */
@ConfigureMe(allfields = true)
public class DecoratorConfig {
	private String statClazzName;
	private String decoratorClazzName;

	public String getDecoratorClazzName() {
		return decoratorClazzName;
	}

	public void setDecoratorClazzName(String decoratorClazzName) {
		this.decoratorClazzName = decoratorClazzName;
	}

	public String getStatClazzName() {
		return statClazzName;
	}

	public void setStatClazzName(String statClazzName) {
		this.statClazzName = statClazzName;
	}

	@Override
	public String toString() {
		return "DecoratorConfig{" +
				"decoratorClazzName='" + decoratorClazzName + '\'' +
				", statClazzName='" + statClazzName + '\'' +
				'}';
	}
}
