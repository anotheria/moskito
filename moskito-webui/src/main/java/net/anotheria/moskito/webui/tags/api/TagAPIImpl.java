package net.anotheria.moskito.webui.tags.api;

import net.anotheria.moskito.core.tag.Tag;
import net.anotheria.moskito.core.tag.TagRepository;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the TagAPI.
 *
 * @author esmakula
 */
public class TagAPIImpl extends AbstractMoskitoAPIImpl implements TagAPI{

	@Override
	public List<TagAO> getTags() {
		return convert(TagRepository.INSTANCE.getTags());
	}

	private List<TagAO> convert(List<Tag> tags){
		List<TagAO> tagAOS = new ArrayList<>();
		for (Tag tag: tags)
			tagAOS.add(convert(tag));
		return tagAOS;
	}

	private TagAO convert(Tag tag) {
		TagAO tagAO = new TagAO();
		tagAO.setName(tag.getName());
		tagAO.setType(tag.getType());
		tagAO.setSource(tag.getSource());
		tagAO.setLastValues(tag.getLastValues());
		return tagAO;
	}
}
