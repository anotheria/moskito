package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.util.sorter.IComparable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a generic stats-decorator implementation - will be used by default for all 'not build-in' stats.
 *
 * @author Michael König
 */
public class GenericStatsDecorator implements IDecorator<GenericStats> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericStatsDecorator.class);

	/**
	 * Name.
	 */
	private final String name;
	/**
	 * Caption.
	 */
    private final List<StatCaptionBean> captions = new ArrayList<>();


    /**
     * Constructs an instance of GenericStatsDecorator.
     */
    public GenericStatsDecorator(final String name) {
        this.name = name;
    }

    @Override
    public int compareTo(final IComparable anotherComparable, final int method) {
        return getName().compareToIgnoreCase(((IDecorator) anotherComparable).getName());
    }

    @Override
    public List<StatCaptionBean> getCaptions() {
        return Collections.unmodifiableList(captions);
    }

	/**
	 * can be used to determine if decorator was initialized already.
	 *
	 * @return TRUE | FALSE
	 */
	public boolean isInitialized() {
		return !captions.isEmpty();
	}

    /**
     * Add a caption.
     * @param captionBean caption bean to add
     */
    public void addCaption(StatCaptionBean captionBean) {
        captions.add(captionBean);
    }


    /**
     * Add a caption value.
     *
     * @param name the caption
     * @param type short description
     */
    public void addCaption(String name, String type) {
        captions.add(new StatCaptionBean(name, name + " as " + type, ""));
    }

    @Override
    public String getExplanation(final String caption) {
        for (StatCaptionBean scb : captions) {
            if (scb.getCaption().equals(caption)) {
                return scb.getExplanation();
            }
        }
        return "n.a.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<StatValueAO> getValues(final GenericStats stats,
                                         final String interval,
                                         final TimeUnit unit) {
        final List<String> names = stats.getAvailableValueNames();
        final List<StatValueAO> ret = new ArrayList<StatValueAO>(names.size());

        for (final String name : names) {
            final TypeAwareStatValue sValue = stats.getValueByName(name);
            if (sValue == null) {
                LOGGER.info("unable to determine value for name: " + name);

            } else {
                switch (sValue.getType()) {
                    case DOUBLE:
                        ret.add(new DoubleValueAO(name, sValue.getValueAsDouble(interval)));
                        break;
                    case INT:
                    case LONG:
                        ret.add(new LongValueAO(name, sValue.getValueAsLong(interval)));
                        break;
                    default:
                        ret.add(new StringValueAO(name, sValue.getValueAsString(interval)));
                        break;
                }
            }
        }

        return ret;
    }

    @Override
    public String toString() {
	    final StringBuilder sb = new StringBuilder();
	    sb.append("GenericStatsDecorator: ").append(name).append(" - ");

	    for (StatCaptionBean c : captions) {
		    sb.append(c.getCaption()).append("; ");
	    }

	    return sb.toString();
    }
}
