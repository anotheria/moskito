package net.anotheria.moskito.webui.tags.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tagging.CustomTag;
import net.anotheria.moskito.core.config.tagging.TaggingConfig;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Adds new tag config to MoSKito configuration.
 * @author strel
 */
public class AddTagAction extends BaseMoskitoUIAction {

    /**
     * Parameter: tag name
     */
    public static final String PARAM_TAG_NAME = "pTagName";

    /**
     * Parameter: attribute source
     */
    public static final String PARAM_ATTRIBUTE_SOURCE = "pAttributeSource";

    /**
     * Parameter: attribute name
     */
    public static final String PARAM_ATTRIBUTE_NAME = "pAttributeName";


    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Getting parameters for new tag
        String tagName = request.getParameter(PARAM_TAG_NAME);
        String attributeSource = request.getParameter(PARAM_ATTRIBUTE_SOURCE);
        String attributeName = request.getParameter(PARAM_ATTRIBUTE_NAME);

        CustomTag tag = new CustomTag();
        tag.setName(tagName);
        tag.setAttribute(attributeSource + '.' + attributeName);

        TaggingConfig taggingConfig = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig();
        CustomTag[] tags = taggingConfig.getCustomTags();

        // Enlarging tags array
        int tagsArraySize = tags.length;
        tags = Arrays.copyOf(tags, tagsArraySize + 1);
        tags[tagsArraySize] = tag;

        taggingConfig.setCustomTags(tags);

        return mapping.redirect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskAddTag";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.TAGS;
    }
}
