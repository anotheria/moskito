package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.webui.journey.api.TagEntryAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Utility class with helper methods for working with tags
 */
public class TagsUtil {

    /**
     * Creates tags info beans from given
     * tags map with tag names as keys and tag values as map values
     *
     * @param tagsMap map of tag names and values
     * @return list of tag entries
     *
     */
    public static List<TagEntryAO> tagsMapToTagEntries(Map<String, String> tagsMap) {

    	if (tagsMap==null)
    		return Collections.EMPTY_LIST;

        List<TagEntryAO> tagEntries = new ArrayList<>(tagsMap.size());

        for (Map.Entry<String,String> entry : tagsMap.entrySet())
            tagEntries.add(
                    new TagEntryAO(entry.getKey(), entry.getValue())
            );

        return tagEntries;

    }

}
