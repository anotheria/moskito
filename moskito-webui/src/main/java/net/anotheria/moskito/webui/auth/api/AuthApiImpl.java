package net.anotheria.moskito.webui.auth.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.webui.util.AuthCredentialsConfig;
import net.anotheria.moskito.webui.util.CryptUtil;
import net.anotheria.moskito.webui.util.WebUIConfig;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.KeyParameter;

import java.util.Arrays;

/**
 * Implementation of Auth API
 */
public class AuthApiImpl implements AuthApi {

    /**
     * Returns user authorization credentials
     * @return array of user credentials
     */
    private AuthCredentialsConfig[] getAuthCredentials(){
        return WebUIConfig.getInstance().getAuthCredentials();
    }

    /**
     * Provides decryption/encryption methods.
     */
    private CryptUtil cryptUtil;

    @Override
    public String encryptUserCredentials(UserAO user) throws APIException {
        try {
            return cryptUtil.encrypt(user.getUsername() + "#" + user.getPassword());
        } catch (InvalidCipherTextException e) {
            throw new APIException("Failed to encrypt user authorization data", e);
        }
    }

    @Override
    public UserAO decryptUserCredentials(String encryptedString) throws APIException {
        try {

            String decryptedUserDataString =  cryptUtil.decrypt(
                    encryptedString
            );
            String[] decryptedUserDataStringComponents = decryptedUserDataString.split("#");
            return new UserAO(decryptedUserDataStringComponents[0], decryptedUserDataStringComponents[1]);


        } catch (InvalidCipherTextException e) {
            throw new APIException("Failed to decrypt user authorization data", e);
        }
    }

    @Override
    public boolean userExists(UserAO user) {
        return user != null && Arrays.stream(getAuthCredentials()).anyMatch(
                credentials -> credentials.getUsername().equals(user.getUsername()) &&
                        credentials.getPassword().equals(user.getPassword())
        );
    }

    @Override
    public void init() throws APIInitException {

        WebUIConfig config = WebUIConfig.getInstance();

        if(config.isAuthenticationEnabled()) {

            String key = config.getEncryptionKey();

            if(key != null)
                cryptUtil = new CryptUtil(
                        new KeyParameter(WebUIConfig.getInstance().getEncryptionKey().getBytes())
                );
            else
                throw new APIInitException("Key for encryption is not specified in webUI configuration" +
                        " despite auth is enabled");

        }

    }

    @Override
    public void deInit() {

    }

}
