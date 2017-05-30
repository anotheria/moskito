package net.anotheria.moskito.extension.nginx.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Configuration bean for single nginx instance.
 * Username and password fields are optional, name and location - mandatory for
 * correct producer functioning.
 *
 * @author dzhmud
 */
@SuppressWarnings("unused")
@ConfigureMe
public class NginxMonitoredInstance {

    /**
     * Name for the nginx instance represented by the {@link #location}.
     * Will be part of created producer Id.
     */
    @Configure
    private String name;

    /**
     * URL to fetch nginx stub status from.
     */
    @Configure
    private String location;

    /**
     * Login to use if {@link #location} is protected with credentials.
     */
    @Configure
    private String username;

    /**
     * Password to use if {@link #location} is protected with credentials.
     */
    @Configure
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "NginxMonitoredInstance{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NginxMonitoredInstance that = (NginxMonitoredInstance) o;

        return areSame(name, that.name)
                && areSame(location, that.location)
                && areSame(username, that.username)
                && areSame(password, that.password);
    }

    private static boolean areSame(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + location.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
