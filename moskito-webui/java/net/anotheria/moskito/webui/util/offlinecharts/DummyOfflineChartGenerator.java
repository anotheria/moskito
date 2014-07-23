package net.anotheria.moskito.webui.util.offlinecharts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.07.14 00:56
 */
public class DummyOfflineChartGenerator implements OfflineChartGenerator{

	private static String _pathToFile1 = "webapps";
	private static String _pathToFile2 = "/moskito/int/img/ExampleAccumulatorChart.png";


	public static String PATH_TO_FILE = "";

	public static void setContextPath(String contextPath){
		PATH_TO_FILE = _pathToFile1 + contextPath + _pathToFile2;
	}

	@Override
	public byte[] generateAccumulatorChart(OfflineChart chart) throws OfflineChartGeneratorException {
		System.out.println("Have to generate chart "+chart+" --- will return dummy picture ");

		File dummyPic = new File (PATH_TO_FILE);
		if (!dummyPic.exists())
			throw new OfflineChartGeneratorException("Can't find "+PATH_TO_FILE);
		try {
			FileInputStream fIn = new FileInputStream(dummyPic);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(fIn.available());
			byte[] data = new byte[fIn.available()];
			fIn.read(data);
			byteOut.write(data);
			byteOut.flush();
			return byteOut.toByteArray();
		}catch(IOException e){
			throw new OfflineChartGeneratorException("Can't read file", e);
		}
	}
}
