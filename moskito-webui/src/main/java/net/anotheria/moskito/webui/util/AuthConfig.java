package net.anotheria.moskito.webui.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.ConfigureMe;


/**
 * Configuration class for authorization in moskito-inspect
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"},
        justification = "This is the way configureme works, it provides beans for access")
public class AuthConfig {

	/**
	 * If true authentication is enabled.
	 */
    private boolean authenticationEnabled = false;
	/**
	 * Array with credentials.
	 */
	private AuthCredentialsConfig[] credentials = new AuthCredentialsConfig[0];
    /**
     * Authorization mechanism uses blowfish algorithm to encrypt user authorization cookie.
     * This string is used as key for blowfish encryption.
     */
    private String encryptionKey;

    public boolean isAuthenticationEnabled() {
        return authenticationEnabled;
    }

    public void setAuthenticationEnabled(boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }

    public AuthCredentialsConfig[] getCredentials() {
        return credentials;
    }

    public void setCredentials(AuthCredentialsConfig[] credentials) {
        this.credentials = credentials;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
