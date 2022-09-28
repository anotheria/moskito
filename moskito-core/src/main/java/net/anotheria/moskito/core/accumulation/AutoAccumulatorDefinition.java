package net.anotheria.moskito.core.accumulation;

import net.anotheria.util.StringUtils;

import java.util.regex.Pattern;

/**
 * The definition class for pattern based automatical accumulation definition.
 * This is basically the same as accumulator definition applied to all matching patterns. The created accumulator will be named
 * according to the name pattern.
 * The name pattern can contain $PRODUCERNAME which will be replaced by the name of the matched producer.
 *
 * @author lrosenberg
 * @since 08.04.18 22:18
 */
public class AutoAccumulatorDefinition extends AccumulatorDefinition{
	/**
	 * Pattern for the accumulator name.
	 */
	private String namePattern;
	/**
	 * Pattern for producer name (id).
	 */
	private String producerNamePattern;

    /**
     * Pattern for stat name.
     * Clashes with startName!.
     * Can be used either statName or statNamePatter.
     * If provided both, so statName is in action.
     */
    private String statNamePattern;

	/**
	 * Compiled pattern.
	 */
	private Pattern pattern;

    /**
     * Compiled pattern for statNamePattern.
     */
    private Pattern statNamePatternCompiled;


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

    public String getStatNamePattern() {
        return statNamePattern;
    }

    public void setStatNamePattern(String statNamePattern) {
        this.statNamePattern = statNamePattern;
		if(statNamePattern != null) {
			statNamePatternCompiled = Pattern.compile(statNamePattern);
		}
    }

    /**
	 * Creates a new AccumulatorDefinition object for matched producer and matched statName.
	 * @param producerId
	 * @return
	 */
	public AccumulatorDefinition toAccumulatorDefinition(String producerId, String statName){
		AccumulatorDefinition ret = new AccumulatorDefinition();
		ret.setProducerName(producerId);
		ret.setName(replaceName(producerId, statName));
		ret.setStatName(statName);
		ret.setValueName(getValueName());
		ret.setIntervalName(getIntervalName());
		ret.setTimeUnit(getTimeUnit());
		return ret;
	}

	/**
	 * Creates a new AccumulatorDefinition object for matched producer.
	 * @param producerId
	 * @return
	 */
	public AccumulatorDefinition toAccumulatorDefinition(String producerId){
		AccumulatorDefinition ret = new AccumulatorDefinition();
		ret.setProducerName(producerId);
		ret.setName(replaceName(producerId, getStatName()));
		ret.setStatName(getStatName());
		ret.setValueName(getValueName());
		ret.setIntervalName(getIntervalName());
		ret.setTimeUnit(getTimeUnit());
		return ret;
	}

	private String replaceName(String producerId, String statName){
		String name = namePattern;
		name = StringUtils.replace(name, "$PRODUCERID", producerId);
		name = StringUtils.replace(name, "$PRODUCERNAME", producerId);
		name = StringUtils.replace(name, "$STATNAME", statName);
		return name;
	}

	public boolean matches(String producerId){
		return pattern.matcher(producerId).matches();
	}

    public boolean statNameMatches(String statName) {
		if (statNamePatternCompiled != null) {
			return statNamePatternCompiled.matcher(statName).matches();
		}
        return false;
    }
}
