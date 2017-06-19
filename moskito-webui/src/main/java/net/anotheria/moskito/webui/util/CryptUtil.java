package net.anotheria.moskito.webui.util;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utility for decrypting/encrypting using blowfish algorithm
 * implemented by bouncy castle library.
 *
 * Class initializes with key parameter, that be used as a
 * key for cryptography.
 *
 */
public class CryptUtil {

    /**
     * Key for decrypting/encrypting
     */
    private KeyParameter key;

    public CryptUtil(KeyParameter key) {
        this.key = key;
    }

    /**
     * Decrypts string, encrypted by blowfish algorithm.
     * Method takes encrypted input in Base64 representation.
     * Returns string with charset, noted as second method parameter.
     * @param encryptedString Base64 input for decryption
     * @param outCharset charset of output string
     * @return decrypted string
     * @throws InvalidCipherTextException by bouncy castle library
     */
    public String decrypt(String encryptedString, Charset outCharset) throws InvalidCipherTextException {

        PaddedBufferedBlockCipher cipher =
                new PaddedBufferedBlockCipher( new BlowfishEngine());

        cipher.init(false, key);

        byte in[] = Base64.decode(encryptedString);
        byte out[] = new byte[cipher.getOutputSize(in.length)];

        int len = cipher.processBytes(in, 0, in.length, out, 0);
        cipher.doFinal(out, len);

        // out string may contain 0 bytes at the end.
        return new String(out, outCharset).replaceAll("\0+$", "");

    }

    /**
     * Decrypts string with UTF-8 encoding of output
     * {@link CryptUtil#decrypt(String, Charset)}
     */
    public String decrypt(String encryptedString) throws InvalidCipherTextException {
        return decrypt(encryptedString, StandardCharsets.UTF_8);
    }

    /**
     * Encrypts given string using blowfish algorithm implemented by
     * bouncy castle library. Returns bytes in Base64 representation.
     * @param stringToEncrypt string data to be encrypted.
     * @param inCharset charset of string to be encrypted
     * @return Base64 string representation of encryption output
     * @throws InvalidCipherTextException by bouncy castle library
     */
    public String encrypt(String stringToEncrypt, Charset inCharset) throws InvalidCipherTextException {

        PaddedBufferedBlockCipher cipher =
                new PaddedBufferedBlockCipher( new BlowfishEngine());

        cipher.init(true, key);

        byte in[] = stringToEncrypt.getBytes(inCharset);
        byte out[] = new byte[cipher.getOutputSize(in.length)];

        int len1 = cipher.processBytes(in, 0, in.length, out, 0);
        cipher.doFinal(out, len1);

        return new String(Base64.encode(out));

    }

    /**
     * Encrypts string wth UTF-8 input charset
     * {@link CryptUtil#encrypt(String, Charset)}
     */
    public String encrypt(String stringToEncrypt) throws InvalidCipherTextException {
        return encrypt(stringToEncrypt, StandardCharsets.UTF_8);
    }

}
