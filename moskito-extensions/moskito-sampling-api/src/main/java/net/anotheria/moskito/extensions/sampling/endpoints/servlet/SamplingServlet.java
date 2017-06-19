package net.anotheria.moskito.extensions.sampling.endpoints.servlet;

import net.anotheria.moskito.extensions.sampling.Sample;
import net.anotheria.moskito.extensions.sampling.SamplingEngine;
import net.anotheria.moskito.web.MoskitoHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.04.15 17:55
 */
@WebServlet(urlPatterns = "/sampling/sample/lol") // TODO : BACK urlPattern to initial state after task done
public class SamplingServlet extends MoskitoHttpServlet{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3131209799814451746L;


	/**
	 * ProducerId parameter.
	 */
	public static final String PARAM_PRODUCER_ID = "id";
	/**
	 * Mapper parameter.
	 */
	public static final String PARAM_STAT_MAPPER_ID = "mapper";

	/**
	 * Engine.
	 */
	private SamplingEngine engine;

	@Override
	public void init() throws ServletException {
		super.init();

		engine = SamplingEngine.getInstance();
	}

	@Override
	protected void moskitoDoGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String producerId = req.getParameter(PARAM_PRODUCER_ID);
		if (producerId==null || producerId.length()==0){
			throw new IllegalArgumentException("ProducerId parameter '"+PARAM_PRODUCER_ID+"' may not be empty.");
		}
		String mapperId = req.getParameter(PARAM_STAT_MAPPER_ID);
		if (mapperId==null || mapperId.length()==0){
			throw new IllegalArgumentException("mapperId parameter '"+PARAM_STAT_MAPPER_ID+"' may not be empty.");
		}

		Map<String, String> parameters = new HashMap<>();
		Enumeration<String> paramNames = req.getParameterNames();
		while(paramNames.hasMoreElements()){
			String pName = paramNames.nextElement();
			if (pName.equals(PARAM_PRODUCER_ID))
				continue;
			if (pName.equals(PARAM_STAT_MAPPER_ID))
				continue;
			parameters.put(pName.toLowerCase(), req.getParameter(pName));
		}
		Sample sample = new Sample();
		sample.setProducerId(producerId);
		sample.setStatMapperId(mapperId);
		sample.setValues(parameters);

		engine.addSample(sample);
		//don't return anything, just status ok.
	}
}
