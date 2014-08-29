package net.anotheria.moskito.webui.shared.api;

import net.anotheria.util.maven.MavenVersion;

import java.io.Serializable;

/**
 * Represents a library entry.
 *
 * @author lrosenberg
 * @since 13.03.13 14:57
 */
public class LibAO implements Serializable{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the lib.
	 */
	private String name;
	/**
	 * Version of the lib.
	 */
	private MavenVersion mavenVersion;

	/**
	 * Last modified timestamp.
	 */
	private String lastModified;


	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MavenVersion getMavenVersion() {
		return mavenVersion;
	}

	public void setMavenVersion(MavenVersion mavenVersion) {
		this.mavenVersion = mavenVersion;
	}

	public String getTimestamp(){
		return mavenVersion==null? lastModified : mavenVersion.getFileTimestamp();
	}

	public String getGroup(){
		return mavenVersion==null? "-" : mavenVersion.getGroup();
	}
	public String getArtifact(){
		return mavenVersion==null? "-" : mavenVersion.getArtifact();
	}
	public String getVersion(){
		return mavenVersion==null? "-" : mavenVersion.getVersion();
	}

	@Override
	public String toString() {
		return "LibAO{" +
				"name='" + name + '\'' +
				", mavenVersion=" + mavenVersion +
				", lastModified='" + lastModified + '\'' +
				'}';
	}
}
