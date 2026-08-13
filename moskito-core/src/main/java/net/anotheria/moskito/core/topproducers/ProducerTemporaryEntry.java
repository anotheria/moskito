package net.anotheria.moskito.core.topproducers;

import java.util.LinkedList;
import java.util.List;

/**
 * A temporary, single-interval ranking structure. For each interval update the producers are inserted into this
 * structure which keeps them sorted descending by their raw value. Every element knows how many elements are ranked
 * below it ({@link #getScore()}), which is used as the score a producer collects for being ranked in this interval.
 * Producers with an equal value share the same rank via the {@link #getSame() same} list.
 *
 * @author lrosenberg
 * @since 18.05.16 00:56
 */
public class ProducerTemporaryEntry {

	private String producerId;

	private long value = 0;
	/**
	 * Number of elements below this element.
	 */
	private int size = 0;

	private ProducerTemporaryEntry next = null;

	private List<ProducerTemporaryEntry> same = new LinkedList<>();

	public ProducerTemporaryEntry(String aProducerId, long aValue) {
		producerId = aProducerId;
		value = aValue;
	}

	private void insertNotRoot(ProducerTemporaryEntry anotherEntry) {
		size++;
		if (next == null) {
			next = anotherEntry;
			return;
		}

		if (next.value < anotherEntry.value) {
			anotherEntry.insert(next);
			next = anotherEntry;
			next.size = size - 1; //it now hangs directly under us, so it should get our score -1.
			return;
		}

		next.insert(anotherEntry);

	}

	public void insert(ProducerTemporaryEntry first, ProducerTemporaryEntry... followup) {
		insert(first);
		if (followup != null) {
			for (ProducerTemporaryEntry entry : followup) {
				insert(entry);
			}
		}
	}

	public void insert(ProducerTemporaryEntry anotherEntry) {

		if (anotherEntry.value > this.value) {
			//in this case we need to swap the root
			final long oldRootValue = value;
			final String oldProducerId = producerId;
			final List<ProducerTemporaryEntry> old = same;
			value = anotherEntry.value;
			producerId = anotherEntry.producerId;
			same = anotherEntry.same;
			anotherEntry.value = oldRootValue;
			anotherEntry.producerId = oldProducerId;
			anotherEntry.next = next;
			anotherEntry.size = size;
			anotherEntry.same = old;
			next = anotherEntry;
			size++;
			return;
		}
		if (anotherEntry.value == this.value) {
			same.add(anotherEntry);
			return;
		}
		insertNotRoot(anotherEntry);
	}

	public long getValue() {
		return value;
	}

	public int getSize() {
		return size;
	}

	/**
	 * test scope
	 **/
	ProducerTemporaryEntry getNext() {
		return next;
	}

	public String toDetails() {
		return getProducerId() + "=" + getValue() + " (" + size + ", " + same.size() + ")" + (next == null ? "" : " -> " + next.toDetails());
	}

	public String getProducerId() {
		return producerId;
	}

	public int getScore() {
		return size;
	}

	List<ProducerTemporaryEntry> getSame() {
		return same;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ProducerTemporaryEntry{");
		sb.append("producerId='").append(producerId).append('\'');
		sb.append(", value=").append(value);
		sb.append(", size=").append(size);
		sb.append(", next=").append(next);
		sb.append(", same=").append(same);
		sb.append('}');
		return sb.toString();
	}
}
