package airteam.projects.airnigma.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtility {
	
	public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
    byte[] rawData = secretKey.getEncoded();
    String encodedKey = Base64.getEncoder().encodeToString(rawData);
    return encodedKey;
	}
	
	public static SecretKey convertStringToSecretKeyto(String encodedKey, String alogrithm) {
    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, alogrithm);
    return originalKey;
	}
	
	public static IvParameterSpec generateIv(int blockSize) {
    byte[] iv = new byte[blockSize];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
	}
	
	public static SecretKey generateKey(int blockSize, String alogrithm) throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(alogrithm);
    keyGenerator.init(blockSize);
    SecretKey key = keyGenerator.generateKey();
    return key;
	}
}
