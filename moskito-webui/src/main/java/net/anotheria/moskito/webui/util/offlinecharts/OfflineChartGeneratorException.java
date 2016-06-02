package net.anotheria.moskito.webui.util.offlinecharts;

public class OfflineChartGeneratorException extends Exception {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1;

	public OfflineChartGeneratorException(String message){
        super(message);
    }

    public OfflineChartGeneratorException(String message, Throwable cause){
        super(message, cause);
    }
}