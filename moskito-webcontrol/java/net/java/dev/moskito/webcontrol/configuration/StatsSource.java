package net.java.dev.moskito.webcontrol.configuration;

public class StatsSource {

	private String name;
	private String url;
    private String username;
    private String password;

	public StatsSource(String aName, String anUrl) {
		name = aName;
		url = anUrl;
	}

    public StatsSource(String aName, String anUrl, String username, String password) {
        this.name = aName;
		this.url = anUrl;
        this.username = username;
        this.password = password;
    }

    @Override
	public String toString() {
		return "StatsSource [name=" + name + ", url=" + url + ", username=" + username + ", password=" + password + "]";
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

    public StatsSource build(String appendToUrl) {
        return new StatsSource(this.getName(),
                this.getUrl() + appendToUrl,
                this.getUsername(),
                this.getPassword());
    }

    public boolean needAuth() {
        return username != null && password != null;
    }

    @Override
	public boolean equals(Object o) {
		return o instanceof StatsSource && name.equals(((StatsSource) o).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	
	
}
