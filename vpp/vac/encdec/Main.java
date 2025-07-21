package vpp.vac.encdec;

import java.util.Scanner;

import javax.crypto.SecretKey;

public class Main {
	
	private static String ASCIIArt = "_____________________         ________________________.___._____________________\r\n"
			+ "\\_   _____/\\____    /         \\_   ___ \\______   \\__  |   |\\______   \\__    ___/\r\n"
			+ " |    __)_   /     /   ______ /    \\  \\/|       _//   |   | |     ___/ |    |   \r\n"
			+ " |        \\ /     /_  /_____/ \\     \\___|    |   \\\\____   | |    |     |    |   \r\n"
			+ "/_______  //_______ \\          \\______  /____|_  // ______| |____|     |____|   \r\n"
			+ "        \\/         \\/                 \\/       \\/ \\/                           ";
	
	
	
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println(ASCIIArt);
	    System.out.println("EZ-Encrypt");
	    System.out.println("1. Generate New Key");
	    System.out.println("2. Use Existing Base64 Key");
	    System.out.print("Choose option (1 or 2): ");
	    String keyOption = scanner.nextLine();

	    SecretKey key = null;
	    String base64Key = null;

	    if (keyOption.equals("1")) {
	        key = EncryptorDecryptor.generateAESKey(128);
	        base64Key = EncryptorDecryptor.keyToBase64(key);
	        System.out.println("Generated AES Key (Base64): " + base64Key);

	        System.out.print("Enter text to encrypt: ");
	        String plainText = scanner.nextLine();

	        String encrypted = EncryptorDecryptor.encrypt(plainText, key);
	        System.out.println("Encrypted (Base64): " + encrypted);
	        System.out.println("Save this key to decrypt later: " + base64Key);

	        scanner.close();
	        return;

	    } else if (keyOption.equals("2")) {
	        System.out.print("Enter Base64-encoded key: ");
	        base64Key = scanner.nextLine();
	        try {
	            key = EncryptorDecryptor.base64ToKey(base64Key);
	        } catch (Exception e) {
	            System.out.println("Invalid key format.");
	            scanner.close();
	            return;
	        }

	    } else {
	        System.out.println("Invalid option.");
	        scanner.close();
	        return;
	    }

	    // At this point, key is loaded (from input)
	    System.out.println("\nWhat do you want to do?");
	    System.out.println("1. Encrypt");
	    System.out.println("2. Decrypt");
	    System.out.print("Choose option (1 or 2): ");
	    String action = scanner.nextLine();

	    if (action.equals("1")) {
	        System.out.print("Enter text to encrypt: ");
	        String plainText = scanner.nextLine();

	        String encrypted = EncryptorDecryptor.encrypt(plainText, key);
	        System.out.println("Encrypted (Base64): " + encrypted);
	        System.out.println("Use this key to decrypt: " + base64Key);

	    } else if (action.equals("2")) {
	        System.out.print("Enter Base64-encoded encrypted text: ");
	        String encryptedText = scanner.nextLine();

	        try {
	            String decrypted = EncryptorDecryptor.decrypt(encryptedText, key);
	            System.out.println("Decrypted: " + decrypted);
	        } catch (RuntimeException e) {
	            System.out.println("Decryption failed: " + e.getMessage());
	        }

	    } else {
	        System.out.println("Invalid option.");
	    }

	    scanner.close();
	}



}
