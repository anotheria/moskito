package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.predefined.GenericStatsDecorator;
import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.util.sorter.IComparable;

import java.util.List;

/**
 * Object-specific decorator for mbean stats
 */
public class MBeanDecorator extends GenericStatsDecorator {

    /**
     * Default explanation for stats value if specific explanation was not found
     */
    private static final String DEFAULT_EXPLANATION = "Explanations is not available";
    /**
     * List of captions for mbean stats
     */
    private List<StatCaptionBean> captions;

    @Override
    public List<StatCaptionBean> getCaptions() {
        return captions;
    }

    /**
     * Constructor that applies list of captions for mbean
     * @param captions captions of mbean attributes
     */
    MBeanDecorator(List<StatCaptionBean> captions) {
        super("MBeans");

        for(StatCaptionBean captionBean : captions)
            addCaption(captionBean);

        this.captions = captions;

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
        return 0;
    }

}
