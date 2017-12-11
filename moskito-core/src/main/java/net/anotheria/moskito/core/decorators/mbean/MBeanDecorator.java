package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.value.*;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.util.sorter.IComparable;

import java.util.ArrayList;
import java.util.List;

public class MBeanDecorator implements IDecorator<MBeanStats> {

    private static final String DEFAULT_EXPLANATION = "Explanations is not available";
    private List<StatCaptionBean> captions;

    @Override
    public String getName() {
        return "MBeans";
    }

    @Override
    public List<StatCaptionBean> getCaptions() {
        return captions;
    }

    public MBeanDecorator(List<StatCaptionBean> captions) {
        this.captions = captions;
    }

    @Override
    public List<StatValueAO> getValues(MBeanStats stats, String interval, TimeUnit unit) {

        final List<String> names = stats.getAvailableValueNames();
        final List<StatValueAO> ret = new ArrayList<StatValueAO>(names.size());

        for (final String name : names) {
            final TypeAwareStatValue sValue = stats.getValueByName(name);
            if (sValue == null) {
                // TODO : LOG IT
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
    public String getExplanation(String caption) {

        for(StatCaptionBean captionBean : captions)
            if(captionBean.getCaption().equals(caption))
                return captionBean.getExplanation();

        return DEFAULT_EXPLANATION;

    }

    @Override
    public int compareTo(IComparable iComparable, int i) {
        return 0; // TODO : implement it
    }

}
