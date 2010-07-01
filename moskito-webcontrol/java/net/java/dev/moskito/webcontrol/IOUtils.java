package net.java.dev.moskito.webcontrol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public final class IOUtils {
	
	private IOUtils() {
	}

	/**
	 * converts input stream to string value
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getInputStreamAsString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ReadableByteChannel rbch = Channels.newChannel(is);
		WritableByteChannel wbch = Channels.newChannel(baos);
		fastChannelCopy(rbch, wbch);
		rbch.close();
		wbch.close();
		return new String(baos.toByteArray());
	}

	/**
	 * copy data from input channel to the output channel
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
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
