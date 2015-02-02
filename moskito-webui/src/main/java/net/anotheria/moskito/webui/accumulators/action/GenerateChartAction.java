package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.offlinecharts.DummyOfflineChartGenerator;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChart;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGenerator;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGeneratorFactory;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartLineDefinition;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartPoint;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.StringUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.IComparable;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.07.14 01:21
 */
public class GenerateChartAction implements Action {

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String idsParam = req.getParameter("ids");
		System.out.println("Generating chart ids: "+idsParam);

		String[] ids = StringUtils.tokenize(idsParam, ',');

		OfflineChart chart = new OfflineChart();
		chart.setCaption("Accumulators "+idsParam);

		AccumulatorAPI accumulatorAPI = APILookupUtility.getAccumulatorAPI();

		//prepare chart data.
		AccumulatedSingleGraphAO[] chartSourceData = new AccumulatedSingleGraphAO[ids.length];
		int i = 0;
		for (String id : ids){
			AccumulatedSingleGraphAO accData = accumulatorAPI.getAccumulatorGraphData(id);
			chart.addLineDefinition(new OfflineChartLineDefinition(accData.getName()));
			chartSourceData[i++] = accData;
			System.out.println("Adding on pos "+(i-1)+" "+accData);
		}

		System.out.println("Preparing data");

		//prepare data
		Map<String, TemporaryPoint> tmppoints = new HashMap<String, TemporaryPoint>();
		for (i = 0; i<chartSourceData.length; i++){
			AccumulatedSingleGraphAO accData = chartSourceData[i];
			System.out.println("Processing "+accData+" wiht values "+accData.getData());
			for (AccumulatedValueAO value : accData.getData()){
				TemporaryPoint point = tmppoints.get(value.getTimestamp());
				if (point == null){
					point = new TemporaryPoint(chartSourceData.length);
					point.timestampAsString = value.getTimestamp();
					point.timestamp = value.getNumericTimestamp();
					tmppoints.put(point.timestampAsString, point);
				}
				point.values[i] = value.getFirstValue();
			}
		}

		System.out.println("TMP POINTS: " + tmppoints);

		//now create the actual chart object
		Collection<TemporaryPoint> points = tmppoints.values();
		points = StaticQuickSorter.sort(points, new DummySortType());
		for (TemporaryPoint point : points){
			OfflineChartPoint ocp = new OfflineChartPoint();
			ocp.setTimestamp(point.timestamp);
			ocp.setTimestampAsString(point.timestampAsString);
			ocp.setValues(Arrays.asList(point.values));
			chart.addPoint(ocp);
		}



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

	class TemporaryPoint implements IComparable{

		private long timestamp;
		private String[] values;
		private String timestampAsString;

		public TemporaryPoint(int numberOfValues){
			values = new String[numberOfValues];
			for (int i=0; i<numberOfValues; i++)
				values[i] = "";
		}

		@Override
		public int compareTo(IComparable iComparable, int i) {
			return BasicComparable.compareLong(timestamp, ((TemporaryPoint)iComparable).timestamp);
		}

		public String toString(){
			return timestampAsString+", "+timestamp+", "+Arrays.asList(values);
		}
	}
}
