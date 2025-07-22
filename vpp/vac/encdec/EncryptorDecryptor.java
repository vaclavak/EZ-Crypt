package vpp.vac.encdec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class EncryptorDecryptor {

	public static SecretKey generateAESKey(int keySize) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(keySize);
			return keyGen.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("Error generating AES key", e);
		}
	}

	public static String keyToBase64(SecretKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static SecretKey base64ToKey(String base64Key) {
		byte[] decoded = Base64.getDecoder().decode(base64Key);
		return new SecretKeySpec(decoded, 0, decoded.length, "AES");
	}

	public static String encrypt(String plainText, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error encrypting", e);
		}
	}

	public static String decrypt(String encryptedText, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
			byte[] decryptedBytes = cipher.doFinal(decodedBytes);
			return new String(decryptedBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error decrypting", e);
		}
	}

}
