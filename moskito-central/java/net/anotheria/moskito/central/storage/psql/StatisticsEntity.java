package net.anotheria.moskito.central.storage.psql;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 * @author dagafonov
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "stattype", discriminatorType = DiscriminatorType.STRING)
public abstract class StatisticsEntity implements Serializable {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 5471551888973481871L;

	/**
	 * Id column.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "statId", nullable = false, unique = true)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @param stats
	 */
	public abstract void setStats(Map<String, String> stats);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticsEntity other = (StatisticsEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Statistics [id=" + id + "]";
	}

}
