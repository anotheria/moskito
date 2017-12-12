package net.anotheria.moskito.webui.tags.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 *
 * @author esmakula
 */
public class TagAPIFactory implements APIFactory<TagAPI>, ServiceFactory<TagAPI>{
	@Override
	public TagAPI createAPI() {
		return new TagAPIImpl();
	}

	@Override
	public TagAPI create() {
		APIFinder.addAPIFactory(TagAPI.class, this);
		return APIFinder.findAPI(TagAPI.class);
	}
}
