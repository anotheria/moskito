package net.anotheria.moskito.core.accumulation;

import net.anotheria.util.StringUtils;

import java.util.regex.Pattern;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.04.18 22:18
 */
public class AutoAccumulatorDefinition extends AccumulatorDefinition{
	private String namePattern;
	private String producerNamePattern;

	private Pattern pattern;


	public String getNamePattern() {
		return namePattern;
	}

	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}

	public String getProducerNamePattern() {
		return producerNamePattern;
	}

	public void setProducerNamePattern(String producerNamePattern) {
		this.producerNamePattern = producerNamePattern;
		pattern = Pattern.compile(producerNamePattern);
	}

	public AccumulatorDefinition toAccumulatorDefinition(String producerId){
		AccumulatorDefinition ret = new AccumulatorDefinition();
		ret.setProducerName(producerId);
		ret.setName(replaceName(producerId));
		ret.setStatName(getStatName());
		ret.setValueName(getValueName());
		ret.setIntervalName(getIntervalName());
		ret.setTimeUnit(getTimeUnit());
		return ret;

	}

	private String replaceName(String producerId){
		String name = namePattern;
		name = StringUtils.replace(name, "$PRODUCERID", producerId);
		name = StringUtils.replace(name, "$PRODUCERNAME", producerId);
		return name;
	}

	public boolean matches(String producerId){
		return pattern.matcher(producerId).matches();
	}
}
