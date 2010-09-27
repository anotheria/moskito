package net.java.dev.moskito.webcontrol.configuration;

public class SourceConfiguration {

	private String name;
	private String url;
    private String username;
    private String password;

	public SourceConfiguration(String aName, String anUrl) {
		name = aName;
		url = anUrl;
	}

    public SourceConfiguration(String aName, String anUrl, String username, String password) {
        this.name = aName;
		this.url = anUrl;
        this.username = username;
        this.password = password;
    }

    public String toString() {
		return name + " = " + url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SourceConfiguration build(String appendToUrl) {
        return new SourceConfiguration(this.getName(),
                this.getUrl() + appendToUrl,
                this.getUsername(),
                this.getPassword());
    }

    @Override
	public boolean equals(Object o) {
		return o instanceof SourceConfiguration && name.equals(((SourceConfiguration) o).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
