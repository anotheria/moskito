package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.util.offlinecharts.DummyOfflineChartGenerator;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChart;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGenerator;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGeneratorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.OutputStream;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.07.14 01:21
 */
public class GenerateChartAction implements Action {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String ids = req.getParameter("ids");
		System.out.println("Generating chart ids: "+ids);

		OfflineChart chart = new OfflineChart();
		OfflineChartGenerator generator = OfflineChartGeneratorFactory.getGenerator();
		//this line is a hack because we need to submit a path for the file and i was too lazy to make something good for it.
		((DummyOfflineChartGenerator)generator).setContextPath(req.getContextPath());
		byte[] chartData = generator.generateAccumulatorChart(chart);
		res.setContentType("image/png");
		OutputStream out = res.getOutputStream();
		out.write(chartData);
		out.flush();

		return null;

	}


	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}


	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}
}
