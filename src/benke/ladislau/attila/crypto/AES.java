package benke.ladislau.attila.crypto;

import benke.ladislau.attila.crypto.cryptUtils.CryptConverter;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES {
    public static HexString getRandomKey() {
        byte[] rndPwdBytes = new byte[32];
        new SecureRandom().nextBytes(rndPwdBytes);

        try {
            return CryptConverter.toHexString(MessageDigest.getInstance("SHA-256").digest(rndPwdBytes));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new HexString("");
        }
    }

    public static HexString getPasswordKey(final String password) {
        try {
            return CryptConverter.toHexString(MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new HexString("");
        }
    }

    public static HexString encrypt(final String strToEncrypt, final HexString key) {
        try {
            SecretKey secretKey;

            secretKey = new SecretKeySpec(key.toByteArray(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return CryptConverter.toHexString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(final HexString hexToDecrypt, final HexString key) {
        try {
            SecretKey secretKey;

            secretKey = new SecretKeySpec(key.toByteArray(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(hexToDecrypt.toByteArray()));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
