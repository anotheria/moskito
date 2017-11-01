package net.anotheria.moskito.webui.tags.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tagging.CustomTag;
import net.anotheria.moskito.core.config.tagging.TagType;
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
     * Attribute name: tag types.
     */
    public static final String ATTR_TAG_TYPES = "tagTypes";

    /**
     * Attribute name: tag history size.
     */
    public static final String ATTR_TAG_HISTORY_SIZE = "tagHistorySize";

    public static final String TAG_IP = "ip";
    public static final String TAG_REFERER = "referer";
    public static final String TAG_USER_AGENT = "user-agent";
    public static final String TAG_SESSION_ID = "sessionId";


    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<TagBean> tagBeans = new ArrayList<>();
        TaggingConfig taggingConfig = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig();

        //Preparing default tags
        if (taggingConfig.isAutotagIp()) {
            TagBean tag = new TagBean(TAG_IP, TagType.BUILTIN, TAG_IP, TagHistory.INSTANCE.getTagValues(TAG_IP));
            tagBeans.add(tag);
        }
        if (taggingConfig.isAutotagReferer()) {
            TagBean tag = new TagBean(TAG_REFERER, TagType.HEADER, TAG_REFERER, TagHistory.INSTANCE.getTagValues(TAG_REFERER));
            tagBeans.add(tag);
        }
        if (taggingConfig.isAutotagUserAgent()) {
            TagBean tag = new TagBean(TAG_USER_AGENT, TagType.HEADER, TAG_USER_AGENT, TagHistory.INSTANCE.getTagValues(TAG_USER_AGENT));
            tagBeans.add(tag);
        }
        if (taggingConfig.isAutotagSessionId()) {
            TagBean tag = new TagBean(TAG_SESSION_ID, TagType.SESSION, TAG_SESSION_ID, TagHistory.INSTANCE.getTagValues(TAG_SESSION_ID));
            tagBeans.add(tag);
        }

        // Preparing custom tag beans
        for (CustomTag tagConfig : taggingConfig.getCustomTags()) {
            TagBean tag = new TagBean();
            tag.setName(tagConfig.getName());
            tag.setType(TagType.findTagTypeByName(tagConfig.getAttributeSource()));
            tag.setAttributeName(tagConfig.getAttributeName());
            tag.setLastAttributeValues(TagHistory.INSTANCE.getTagValues(tagConfig.getName()));
            tagBeans.add(tag);
        }

        request.setAttribute(ATTR_TAGS, tagBeans);
        request.setAttribute(ATTR_TAG_TYPES, TagType.TYPES);
        request.setAttribute(ATTR_TAG_HISTORY_SIZE, MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize());

        return mapping.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskTags?ts="+System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.TAGS;
    }
}
