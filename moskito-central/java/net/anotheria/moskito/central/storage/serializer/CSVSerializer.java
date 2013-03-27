package net.anotheria.moskito.central.storage.serializer;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.util.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Special serializer for comma-separater-value list files.
 *
 * @author lrosenberg
 * @since 24.03.13 23:06
 */
public class CSVSerializer{

	/**
	 * Cash for value names by producer. This allows to faster process snapshot, without the need to resort each time.
	 */
	private ConcurrentMap<String, List<String>> cachedValueNames = new ConcurrentHashMap<String, List<String>>();

	public byte[] serialize(Snapshot snapshot, String stat){
		List<String> valueNames = getValueNames(snapshot);
		Map<String,String> data = snapshot.getStats().get(stat);
		if (data==null)
			return null;
		StringBuilder ret = new StringBuilder();
		long creationTimestamp = snapshot.getMetaData().getCreationTimestamp();
		for (String s : valueNames){
			boolean special = false;
			if (ret.length()>0)
				ret.append(";");
			if (s.equals("SnapshotTime")){
				special = true;
				ret.append("\""+ NumberUtils.makeTimeString(creationTimestamp)+"\"");
			}
			if (s.equals("SnapshotDate")){
				special = true;
				ret.append("\""+ NumberUtils.makeDigitalDateStringLong(creationTimestamp)+"\"");
			}
			if (s.equals("SnapshotTimestamp")){
				special = true;
				ret.append("\""+ NumberUtils.makeISO8601TimestampString(creationTimestamp)+"\"");

			}
			if (!special)
				ret.append("\""+data.get(s)+"\"");
		}
		try {
			return ret.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 not supported");
		}
	}

	public byte[] getHeader(Snapshot snapshot) {
		List<String> valueNames = getValueNames(snapshot);
		StringBuilder ret = new StringBuilder();
		for (String s : valueNames){
			if (ret.length()>0)
				ret.append(";");
			ret.append("\""+s+"\"");
		}
		try {
			return ret.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 not supported");
		}
	}

	private List<String> getValueNames(Snapshot snapshot){
		@SuppressWarnings("unchecked")
		ArrayList<String> valueNames = ((ArrayList<String>)cachedValueNames.get(snapshot.getMetaData().getProducerId()));
		if (valueNames!=null)
			return valueNames;
		valueNames = new ArrayList<String>();
		Set<Map.Entry<String, Map<String,String>> > entries = snapshot.getStats().entrySet();
		if (entries.size()==0){
			List<String> old = cachedValueNames.putIfAbsent(snapshot.getMetaData().getProducerId(), valueNames);
			return old == null ? valueNames : old;
		}

		Map<String,String> oneStat = entries.iterator().next().getValue();
		valueNames.addAll(oneStat.keySet());
		Collections.sort(valueNames);
		//add special values
		valueNames.add(0, "SnapshotTime");
		valueNames.add(0, "SnapshotDate");
		valueNames.add(0, "SnapshotTimestamp");
		List<String> old = cachedValueNames.putIfAbsent(snapshot.getMetaData().getProducerId(), valueNames);
		return old == null ? valueNames : old;
	}
}
