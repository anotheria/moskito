package net.anotheria.moskito.webui.more.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.ChartEngine;
import net.anotheria.moskito.webui.util.WebUIConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This action shows current configuration, local or remote.
 *
 * @author lrosenberg
 * @since 28.10.12 23:41
 */
public class ShowConfigAction extends BaseAdditionalAction{
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(config);



		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonOutput);
		String prettyJsonString = gson.toJson(je);
		req.setAttribute("configstring", prettyJsonString);

		if (WebUIConfig.getInstance().getConnectivityMode().isRemote()){
			req.setAttribute("remoteConfig", getAdditionalFunctionalityAPI().getConfigurationAsString());
		}



		///thresholds
		List<Threshold> thresholds = ThresholdRepository.getInstance().getThresholds();
		List<String> thresholdStrings = new ArrayList<>(thresholds.size());
		for (Threshold t : thresholds){
			String jsonT = gson.toJson(t.toConfigObject());
			JsonElement jeT = jp.parse(jsonT);
			String prettyJsonStringT = gson.toJson(jeT);
			thresholdStrings.add(prettyJsonStringT);
		}

		req.setAttribute("thresholdsStrings", thresholdStrings);


		//Available chart engines
		req.setAttribute("availableChartEngines", ChartEngine.values());



		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "config";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_CONFIG;
	}

	@Override
	protected String getSubTitle() {
		return "Configuration";
	}

}
