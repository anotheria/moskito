package net.anotheria.moskito.central.storage.psql;

import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Represents statistics entry as json string.
 * 
 * @author dagafonov
 * 
 */
@Entity
@Table(name = "jsonstats")
@DiscriminatorValue("json")
public class JSONStatisticsEntity extends StatisticsEntity {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -4852715108573829451L;

	/**
	 * JSON string entry representation.
	 */
	@Basic
	@Column(name = "json", length = 20000, nullable = false)
	private String json;
	
	@Override
	public void setStats(Map<String, String> stats) {
		Gson gson = new GsonBuilder().create();
		this.json = gson.toJson(stats);
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "JSONStatisticsEntity [json=" + json + "]";
	}

}
