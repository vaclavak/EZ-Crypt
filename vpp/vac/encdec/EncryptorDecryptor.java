package vpp.vac.encdec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class EncryptorDecryptor {

    // Generate a secure AES key
    public static SecretKey generateAESKey(int keySize) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keySize); // 128, 192, or 256
            return keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating AES key", e);
        }
    }

    // Convert SecretKey to Base64 string
    public static String keyToBase64(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // Convert Base64 string back to SecretKey
    public static SecretKey base64ToKey(String base64Key) {
        byte[] decoded = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decoded, 0, decoded.length, "AES");
    }

    // Encrypt method
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

    // Decrypt method
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate AES-128 key
        SecretKey key = generateAESKey(128);
        String base64Key = keyToBase64(key);

        System.out.println("Generated AES Key (Base64): " + base64Key);

        System.out.print("Enter text to encrypt: ");
        String input = scanner.nextLine();

        String encrypted = encrypt(input, key);
        System.out.println("Encrypted (Base64): " + encrypted);

        // For demonstration: decrypt using same Base64 key
        SecretKey restoredKey = base64ToKey(base64Key);
        String decrypted = decrypt(encrypted, restoredKey);
        System.out.println("Decrypted: " + decrypted);

        scanner.close();
    }
}
