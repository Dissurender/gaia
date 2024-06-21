package dsd.cohort.application.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

  private static String secret = "icantbelieveitsnotsecret";

     public static String encryptString(String str) {

        Key aesKey = new SecretKeySpec(secret.getBytes(), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            byte[] encrypted = cipher.doFinal(str.getBytes());
            str = new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String decryptString(String str) {

        Key key = new SecretKeySpec(secret.getBytes(), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            String decrypted = new String(cipher.doFinal(str.getBytes()));

            str = decrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
}
