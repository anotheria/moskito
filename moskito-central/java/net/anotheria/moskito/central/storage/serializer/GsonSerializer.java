package net.anotheria.moskito.central.storage.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.storage.SnapshotSerializer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 15:00
 */
public class GsonSerializer implements SnapshotSerializer {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public byte[] serialize(Snapshot snapshot){
		String jsonOutput = gson.toJson(snapshot);
		return jsonOutput.getBytes();
	}
}
