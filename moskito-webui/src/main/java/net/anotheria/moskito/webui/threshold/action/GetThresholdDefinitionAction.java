package net.anotheria.moskito.webui.threshold.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.threshold.api.ThresholdDefinitionAO;
import net.anotheria.moskito.webui.util.APILookupUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This action returns the threshold definition for a threshold as json object.
 *
 * @author lrosenberg
 * @since 21.05.14 17:35
 */
public class GetThresholdDefinitionAction implements Action {

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = req.getParameter("pId");
		ThresholdDefinitionAO definition = APILookupUtility.getThresholdAPI().getThresholdDefinition(id);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(definition);
		res.getOutputStream().write(jsonOutput.getBytes());
		res.getOutputStream().flush();

		return null;

	}
}
