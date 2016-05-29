package net.anotheria.moskito.webui.util.offlinecharts;

import com.xeiam.xchart.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class OfflineChartGeneratorImpl implements OfflineChartGenerator{

    @Override
    public byte[] generateAccumulatorChart(OfflineChart chartData) throws OfflineChartGeneratorException {

        // Create Chart
        Chart chart = new ChartBuilder().width(1200).height(400).yAxisTitle("G").title(chartData.getCaption()).build();

        //Chart Axes
        ChartAxes chartAxes = new ChartAxes(chartData.getLineDefinitions().size());
        chartAxes.CalculateAxis(chartData);

        if (chartAxes.getDataX() != null && !chartAxes.getDataX().isEmpty()) {
            int i = 0;
            for (List<Double> yData : chartAxes.getDataListY()) {
                chart.addSeries(chartData.getLineDefinitions().get(i).getLineName(), chartAxes.getDataX(), yData);
                i++;
            }
        }
        else {
            chart.addSeries("empty", new double[]{0}, new double[]{0});
            chart.getStyleManager().setLegendVisible(false);
        }

        customizeChart(chart);
        return getPictureBytes(chart);
    }

    private void customizeChart(Chart chart) {
        chart.getStyleManager().setLegendPosition(StyleManager.LegendPosition.InsideSE);
        chart.getStyleManager().setLegendVisible(true);
        chart.getStyleManager().setChartBackgroundColor(Color.WHITE);
        chart.getStyleManager().setDatePattern("HH:mm:ss");
    }


    private byte[] getPictureBytes(Chart chart) throws OfflineChartGeneratorException {
        BufferedImage lBufferedImage = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Graphics2D lGraphics2D = lBufferedImage.createGraphics();
        chart.paint(lGraphics2D);
        try {
            ImageIO.write(lBufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
            throw new OfflineChartGeneratorException("Can't convert chart to byte array", e);
        }
    }


    private class ChartAxes {
        // data for x axis
        private List<Date> xData = new ArrayList<Date>();

        //List with data for y axis
        private List<List<Double>>  yDataList = new LinkedList<List<Double>>();

        public ChartAxes(int lineAmounts) {
            for (int i = 0; i < lineAmounts; i++) {
                yDataList.add(new LinkedList<Double>());
            }
        }

        public List<Date> getDataX() {
            return xData;
        }

        public List<List<Double>> getDataListY() {
            return yDataList;
        }

        public void CalculateAxis(OfflineChart chartData) throws OfflineChartGeneratorException {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = null;
            for (OfflineChartPoint point : chartData.getPoints()) {
                try {
                    date = sdf.parse(point.getTimestampAsString()+":00");
                    int k = 0;
                    for (String s : point.getValues()) {
                        yDataList.get(k).add(getPointY(s));
                        k++;
                    }
                } catch (ParseException e) {
                    throw new OfflineChartGeneratorException("Can't parse date={" +
                            point.getTimestampAsString() + "} for chart series, ", e);
                }
                xData.add(date);
            }


        }

        private double getPointY(String s) {
            int toG = 1000000000;
            return !s.isEmpty() ? Double.parseDouble(s)/ toG : 0;
        }

    }
}