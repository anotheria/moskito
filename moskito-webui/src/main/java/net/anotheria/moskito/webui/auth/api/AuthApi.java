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
     * Restores user data bean from string,
     * acquired by encrypting this bean.
     * @param encryptedString encrypted user data
     * @throws APIException on decryption fail
     * @return user bean with data restored from encrypted string
     */
    UserAO decryptUserCredentials(String encryptedString) throws APIException;

    /**
     * Check, does user with username and password from bean
     * is noted in moskito webui configuration.
     * @param user user data
     * @return true - config contains that user
     *         false - no
     */
    boolean userExists(UserAO user);

}
