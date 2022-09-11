package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChart;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGenerator;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGeneratorException;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartGeneratorFactory;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartLineDefinition;
import net.anotheria.moskito.webui.util.offlinecharts.OfflineChartPoint;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.StringUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.IComparable;
import net.anotheria.util.sorter.StaticQuickSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This action generates a chart for storage.
 */
public class GenerateChartAction implements Action {
    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(GenerateChartAction.class);

    private static final String PNG_CONTENT_TYPE = "image/png";
    private static final String ZIP_CONTENT_TYPE = "application/zip";
    private static final String ZIP_FILE_NAME = "accumulators.zip";

    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String namesParam = req.getParameter("names");
        String isZip = req.getParameter("zip");
        log.debug("Generating chart names: "+namesParam);

        AccumulatorAPI accumulatorAPI = APILookupUtility.getAccumulatorAPI();

        if (isZip == null || isZip.equals("false")) {
            ChartData offlineChart = getOfflineChart(namesParam, accumulatorAPI);

            res.setContentType(PNG_CONTENT_TYPE);

            res.setHeader("Content-Disposition", "attachment;filename=" + offlineChart.getName());
            res.getOutputStream().write(offlineChart.getData());

        }else if (isZip.equals("true")) {
            String[] names = StringUtils.tokenize(namesParam, ',');

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (String id : names) {
                ChartData offlineChart = getOfflineChart(id, accumulatorAPI);
                addToZipFile(offlineChart.getName(), zos, offlineChart.getData());
            }

            zos.close();
            res.setContentType(ZIP_CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + ZIP_FILE_NAME);
            res.getOutputStream().write(baos.toByteArray());
        }

        return null;

    }

    private ChartData getOfflineChart(String namesParam, AccumulatorAPI accumulatorAPI) throws APIException, OfflineChartGeneratorException {
        String[] accNames = StringUtils.tokenize(namesParam, ',');
        OfflineChart chart = new OfflineChart();
        AccumulatedSingleGraphAO[] chartSourceData = new AccumulatedSingleGraphAO[accNames.length];

        String fileName = "";
        int i = 0;
        for (String name : accNames) {
            AccumulatedSingleGraphAO accData = accumulatorAPI.getAccumulatorGraphDataByName(name);
            chart.addLineDefinition(new OfflineChartLineDefinition(accData.getName()));
            chartSourceData[i++] = accData;

            log.debug("Adding on pos " + (i - 1) + ' ' + accData);
        }

        if (accNames.length > 1) fileName = "CombinedChart";
        else if (chartSourceData.length == 1) fileName = chartSourceData[0].getName();

        log.debug("Preparing data");
        //prepare data
        Map<String, TemporaryPoint> tmppoints = new HashMap<>(chartSourceData.length);
        for (i = 0; i < chartSourceData.length; i++) {
            AccumulatedSingleGraphAO accData = chartSourceData[i];
            log.debug("Processing " + accData + " with values " + accData.getData());
            for (AccumulatedValueAO value : accData.getData()) {
                TemporaryPoint point = tmppoints.get(value.getTimestamp());
                if (point == null) {
                    point = new TemporaryPoint(chartSourceData.length);
                    point.timestampAsString = value.getTimestamp();
                    point.timestamp = value.getNumericTimestamp();
                    tmppoints.put(point.timestampAsString, point);
                }
                point.values[i] = value.getFirstValue();
            }
        }

        log.debug("TMP POINTS: " + tmppoints);

        //now create the actual chart object
        Collection<TemporaryPoint> points = tmppoints.values();
        points = StaticQuickSorter.sort(points, new DummySortType());
        for (TemporaryPoint point : points) {
            OfflineChartPoint ocp = new OfflineChartPoint();
            ocp.setTimestamp(point.timestamp);
            ocp.setTimestampAsString(point.timestampAsString);
            ocp.setValues(Arrays.asList(point.values));
            chart.addPoint(ocp);
        }

        chart.setCaption(fileName);
        OfflineChartGenerator generator = OfflineChartGeneratorFactory.getGenerator();
        return new ChartData(generator.generateAccumulatorChart(chart), fileName + ".png");
    }

    private void addToZipFile(String fileName, ZipOutputStream zos, byte[] bytes) throws IOException {

        log.debug("Writing '" + fileName + "' to zip file");

        ZipEntry zipEntry = new ZipEntry(fileName);
        zipEntry.setSize(bytes.length);
        zos.putNextEntry(zipEntry);
        zos.write(bytes);

//        zos.write(bytes, 0, bytes.length);
        zos.closeEntry();
    }


    @Override
    public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }


    @Override
    public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

    }

    static class TemporaryPoint implements IComparable{
        /**
         * Timestamp of the point.
         */
        private long timestamp;
        /**
         * Values at that point.
         */
        private String[] values;
        /**
         * Timestamp as string for presentation.
         */
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

    private class ChartData {
        private byte[] data;
        private String name;

        public ChartData(byte[] data, String name) {
            this.data = data;
            this.name = name;
        }

        public byte[] getData() {
            return data;
        }

        public String getName() {
            return name;
        }
    }
}