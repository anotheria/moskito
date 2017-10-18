package net.anotheria.moskito.webui.accumulators.util;

import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public static JSONArray accumulatorsColorsToJSON(final List<AccumulatedSingleGraphAO> graphAOs) {
        final JSONArray jsonArray = new JSONArray();

        for (AccumulatedSingleGraphAO graphAO : graphAOs) {
            if (StringUtils.isEmpty(graphAO.getName()) || StringUtils.isEmpty(graphAO.getColor()))
                continue;

            final JSONObject jsonObject = graphAO.mapColorDataToJSON();
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}
