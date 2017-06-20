package net.anotheria.moskito.webui.auth.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;

/**
 * Api provides functionality to decrypt and encrypt
 * user credentials data and check user existence.
 */
public interface AuthApi extends API, Service{

    /**
     * Encrypts user data bean using blowfish algorithm.
     * @param user user authorization data
     * @throws APIException on encryption fail
     * @return string represents encrypted user data
     */
    String encryptUserCredentials(UserAO user) throws APIException;

    /**
     * Check, does user with username and password from encrypted data
     * is noted in moskito webui configuration.
     * @param encryptedUserData user data encrypted by encryptUserCredentials(UserAO) method
     * @return true - config contains that user
     *         false - no
     */
    boolean userExists(String encryptedUserData) throws APIException;

    /**
     * Check, does user with username and password from user object in method parameter
     * is noted in moskito webui configuration.
     * @param user user data
     * @return true - config contains that user
     *         false - no
     */
    boolean userExists(UserAO user);

}
