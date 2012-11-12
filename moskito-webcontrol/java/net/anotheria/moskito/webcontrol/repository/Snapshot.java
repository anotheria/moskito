package net.anotheria.moskito.webcontrol.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Snapshot {

	private SnapshotSource source;
	private long created;
	private ConcurrentMap<String, Attribute> attributes;

	public Snapshot(SnapshotSource aSource) {
		source = aSource;
		created = System.currentTimeMillis();
		attributes = new ConcurrentHashMap<String, Attribute>();
	}

	public SnapshotSource getSource() {
		return source;
	}

	public long getCreatedTimestamp() {
		return created;
	}

	public Attribute addAttribute(Attribute a) {
		return attributes.put(a.getName(), a);
	}

	public Attribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public void removeAttribute(Attribute a) {
		attributes.remove(a.getName());
	}

	public List<Attribute> getAttributes(){
		return (List<Attribute>) attributes.values();
	}


	@Override
	public String toString() {
		return source + " " + created + " " + attributes;
	}


	/*@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if ((obj instanceof ProducerSnapshot)==false) return false;

		ProducerSnapshot snapshot = (ProducerSnapshot)obj;

		if (snapshot.getAttributes().size() != getAttributes().size())
			return false;

		for ( Attribute attribute : snapshot.getAttributes() )
			if ( attributes.get(attribute.getName()) == null
					|| attributes.get(attribute.getName()).getValue() != attribute.getValue() )
				return false;


		return true;
	}*/


}
