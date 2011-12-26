package net.java.dev.moskito.control.monitor.core.connector.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum ConfigFileReader {

	INSTANCE;
	
	public static JSONArray readConfigFile(String configFile, String jsonConfigName) throws IOException, JSONException {
		InputStream connectorsIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);			
		String content = getInputStreamAsString(connectorsIS);
		return new JSONObject(content).getJSONArray(jsonConfigName);
	}

	private static String getInputStreamAsString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ReadableByteChannel rbch = Channels.newChannel(is);
		WritableByteChannel wbch = Channels.newChannel(baos);
		fastChannelCopy(rbch, wbch);
		rbch.close();
		wbch.close();
		return new String(baos.toByteArray());
	}
	
	private static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while (src.read(buffer) != -1) {
			buffer.flip();
			dest.write(buffer);
			buffer.compact();
		}
		buffer.flip();
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
}
