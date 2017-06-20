package net.anotheria.moskito.webui.auth.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.webui.util.AuthCredentialsConfig;
import net.anotheria.moskito.webui.util.WebUIConfig;
import net.anotheria.util.crypt.CryptTool;

/**
 * Implementation of Auth API
 */
public class AuthApiImpl implements AuthApi {

    private volatile CryptTool cryptTool;
    private volatile String cryptKey;

    private boolean isAuthEnabled(){
        WebUIConfig config = WebUIConfig.getInstance();
        return config.isAuthenticationEnabled() && config.getEncryptionKey() != null;
    }

    private void refreshCryptKey(){
        if(cryptKey == null || !cryptKey.equals(WebUIConfig.getInstance().getEncryptionKey()))
            initCryptTool();
    }

    /**
     * Returns user authorization credentials
     * @return array of user credentials
     */
    private AuthCredentialsConfig[] getAuthCredentials(){
        return WebUIConfig.getInstance().getAuthCredentials();
    }

    @Override
    public synchronized String encryptUserCredentials(UserAO user) throws APIException {
        if(isAuthEnabled()) {
            refreshCryptKey();
            try {
                return cryptTool.encryptToHex(user.getUsername() + "#" + user.getPassword());
            } catch (IllegalArgumentException e) {
                throw new APIException("Failed to encrypt user authorization data", e);
            }
        }
        else
            throw new APIException("Trying to call encryptUserCredentials(UserAO) while authorization" +
                    " is not enabled or not properly configured");
    }

    @Override
    public synchronized boolean userExists(String encryptedUserData) throws APIException {
        if(isAuthEnabled()) {
            refreshCryptKey();
            UserAO user = decryptUserCredentials(encryptedUserData);
            return userExists(user);
        }
        else
            throw new APIException("Trying to call encryptUserCredentials(UserAO) while authorization" +
                    " is not enabled or not properly configured");
    }

    @Override
    public synchronized boolean userExists(UserAO user) {

        if(user == null)
            return false;

        for(AuthCredentialsConfig credentials : getAuthCredentials())
            if(
                    credentials.getUsername().equals(user.getUsername()) &&
                            credentials.getPassword().equals(user.getPassword())
                    ) return true; // User with given username/password found in config

        return false; // User not found in config due loop

    }

    private UserAO decryptUserCredentials(String encryptedString) throws APIException {
        try {
            String decryptedUserDataString =  cryptTool.decryptFromHexTrim(
                    encryptedString
            );
            String[] decryptedUserDataStringComponents = decryptedUserDataString.split("#");
            return new UserAO(decryptedUserDataStringComponents[0], decryptedUserDataStringComponents[1]);
        } catch (IllegalArgumentException e) {
            throw new APIException("Failed to decrypt user authorization data", e);
        }
    }


    @Override
    public void init() throws APIInitException {
       initCryptTool();
    }

    private void initCryptTool(){
        String cryptKey = WebUIConfig.getInstance().getEncryptionKey();

        if(cryptKey != null){
            this.cryptKey = cryptKey;
            this.cryptTool = new CryptTool(cryptKey);
        }
    }

    @Override
    public void deInit() {

    }

}
