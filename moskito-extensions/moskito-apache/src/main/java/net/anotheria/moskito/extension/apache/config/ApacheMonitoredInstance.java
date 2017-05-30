package net.anotheria.moskito.extension.apache.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Configuration bean for single apache instance.
 * Username and password fields are optional, name and location - mandatory for
 * correct producer functioning.
 *
 * @author dzhmud
 */
@SuppressWarnings("unused")
@ConfigureMe
public class ApacheMonitoredInstance {

    /**
     * Name for the apache instance represented by the {@link #location}.
     * Will be part of created producer Id.
     */
    @Configure
    private String name;

    /**
     * URL to fetch apache status from.
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
        return "ApacheMonitoredInstance{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
