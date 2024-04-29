package net.anotheria.moskito.webui.shared.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.IDecoratorRegistry;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.05.14 11:30
 */
public class GetExplanationsByDecoratorNameAction implements Action{

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String name = req.getParameter("pName");
		IDecoratorRegistry registry = DecoratorRegistryFactory.getDecoratorRegistry();
		List<IDecorator> decorators = registry.getDecorators();

		for (IDecorator decorator : decorators){
			if (decorator.getName().equals(name)){
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String jsonOutput = gson.toJson(decorator.getCaptions());
				res.getOutputStream().write(jsonOutput.getBytes("UTF-8"));
				res.getOutputStream().flush();
				return null;
			}
		}
		return null;
	}
}
