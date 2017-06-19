package net.anotheria.moskito.webui.util;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CryptUtilTest {

    private static KeyParameter SAMPLE_KEY = new KeyParameter("SAMPLE_TEST_KEY".getBytes());

    @Test
    public void testDecryptAndEncrypt() throws InvalidCipherTextException {

        final String testString = "test_string";
        final CryptUtil cryptUtil = new CryptUtil(SAMPLE_KEY);

        String encryptedString = cryptUtil.encrypt(testString);
        assertEquals(testString, cryptUtil.decrypt(encryptedString));

    }

}
