package net.anotheria.moskito.webui.accumulators.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author strel
 */
public class AccumulatorUtility {

    /**
     * Maps collection of {@link AccumulatedSingleGraphAO} to JSON representation.
     * Accumulator will be mapped only if accumulator has the preconfigured color.
     *
     * @param graphAOs collection of {@link AccumulatedSingleGraphAO}
     * @return JSON array with accumulators colors
     */
    public static JsonArray accumulatorsColorsToJSON(final List<AccumulatedSingleGraphAO> graphAOs) {
        final JsonArray jsonArray = new JsonArray();

        for (AccumulatedSingleGraphAO graphAO : graphAOs) {
            if (StringUtils.isEmpty(graphAO.getName()) || StringUtils.isEmpty(graphAO.getColor()))
                continue;

            final JsonObject jsonObject = graphAO.mapColorDataToJSON();
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
