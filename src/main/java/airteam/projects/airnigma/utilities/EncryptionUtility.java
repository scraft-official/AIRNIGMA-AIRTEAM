package airteam.projects.airnigma.utilities;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptionUtility {

	public static PublicKey convertBytesToPublicKey(byte[] bytes) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		return keyFactory.generatePublic(keySpec);
	}

	public static PrivateKey convertBytesToPrivateKey(byte[] bytes) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		return keyFactory.generatePrivate(keySpec);
	}

	public static SecretKey convertStringToSecretKey(String encodedKey, String algorithm) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
		return originalKey;
	}

	public static String convertSecretKeyToString(SecretKey secretKey) {
		byte[] rawData = secretKey.getEncoded();
		String encodedKey = Base64.getEncoder().encodeToString(rawData);
		return encodedKey;
	}

	public static String generateRandomString(String alphabet, int length) {
		StringBuilder stbuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			stbuilder.append(alphabet.charAt(random.nextInt((alphabet.length() - 1) - 0) + 0));
		}
		return stbuilder.toString();
	}

	public static String encode(String algorithm, String input, PublicKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String encode(String algorithm, String input, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decode(String algorithm, String cipherText, PrivateKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(plainText);
	}

	public static String decode(String algorithm, String cipherText, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(plainText);
	}

	public static KeyPair generateKeyPair(int blockSize, String algorithm) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
			generator.initialize(blockSize);
			return generator.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SecretKey generateKey(int blockSize, String algorithm) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(blockSize);
			SecretKey key = keyGenerator.generateKey();
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SecretKey generateKey(int blockSize, String algorithm, String provider) {
		try {
			if (provider.equals("BC") &&Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		    Security.addProvider(new BouncyCastleProvider());
			}
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, provider);
			keyGenerator.init(blockSize);
			SecretKey key = keyGenerator.generateKey();
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
