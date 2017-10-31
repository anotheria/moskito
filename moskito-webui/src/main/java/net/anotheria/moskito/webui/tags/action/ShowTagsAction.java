package net.anotheria.moskito.webui.tags.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tagging.CustomTag;
import net.anotheria.moskito.core.config.tagging.TagPrefix;
import net.anotheria.moskito.core.config.tagging.TaggingConfig;
import net.anotheria.moskito.core.tag.TagHistory;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.tags.bean.TagBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Prepares configured custom tags and their last values from history.
 *
 * @author strel
 */
public class ShowTagsAction extends BaseMoskitoUIAction {

    /**
     * Attribute name: tags.
     */
    public static final String ATTR_TAGS = "tags";

    /**
     * Attribute name: tag prefixes.
     */
    public static final String ATTR_TAG_PREFIXES = "tagPrefixes";

    /**
     * Attribute name: tag history size.
     */
    public static final String ATTR_TAG_HISTORY_SIZE = "tagHistorySize";


    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<TagBean> tagBeans = new ArrayList<>();
        TaggingConfig taggingConfig = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig();

        // Preparing tag beans
        for (CustomTag tagConfig : taggingConfig.getCustomTags()) {
            TagBean tag = new TagBean();
            tag.setName(tagConfig.getName());
            tag.setPrefix(TagPrefix.findPrefixByName(tagConfig.getPrefix()));
            tag.setAttributeName(tagConfig.getAttributeName());
            tag.setLastAttributeValues(TagHistory.INSTANCE.getTagValues(tagConfig.getName()));
            tagBeans.add(tag);
        }

        request.setAttribute(ATTR_TAGS, tagBeans);
        request.setAttribute(ATTR_TAG_PREFIXES, TagPrefix.PREFIXES);
        request.setAttribute(ATTR_TAG_HISTORY_SIZE, MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize());

        return mapping.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskShowTags";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.TAGS;
    }
}
