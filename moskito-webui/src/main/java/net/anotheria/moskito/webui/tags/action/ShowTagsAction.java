package net.anotheria.moskito.webui.tags.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tagging.CustomTagSource;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.tags.api.TagAO;
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
	private static final String ATTR_TAGS = "tags";

	/**
	 * Attribute name: attribute sources.
	 */
	private static final String ATTR_ATTRIBUTE_SOURCES = "attributeSources";

	/**
	 * Attribute name: tag history size.
	 */
	private static final String ATTR_TAG_HISTORY_SIZE = "tagHistorySize";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<TagBean> tagBeans = new ArrayList<>();

		for (TagAO tag : getTagAPI().getTags()) {
			TagBean tagBean = new TagBean();
			tagBean.setName(tag.getName());
			tagBean.setType(tag.getType());
			tagBean.setSource(tag.getSource());
			tagBean.setLastValues(tag.getLastValues());
			tagBeans.add(tagBean);
		}

		request.setAttribute(ATTR_TAGS, tagBeans);
		request.setAttribute(ATTR_ATTRIBUTE_SOURCES, CustomTagSource.SOURCES);
		request.setAttribute(ATTR_TAG_HISTORY_SIZE, MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize());

		return mapping.success();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskTags?ts=" + System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.TAGS;
	}
}
