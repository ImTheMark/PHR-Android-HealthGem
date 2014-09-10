package com.example.phr.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler {

	public static String encrypt(String text) {
		String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";

		byte[] raw;
		String encryptedString;
		SecretKeySpec skeySpec;
		byte[] encryptText = text.getBytes();
		Cipher cipher;
		try {
			raw = Base64.decode(secretKey);
			skeySpec = new SecretKeySpec(raw, "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encryptedString = Base64.encode(cipher.doFinal(encryptText));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return encryptedString;
	}

	public static String decrypt(String text) {
		String secretKey = "XMzDdG4D03CKm2IxIWQw7g==";

		Cipher cipher;
		String encryptedString;
		byte[] encryptText = null;
		byte[] raw;
		SecretKeySpec skeySpec;
		try {
			raw = Base64.decode(secretKey);
			skeySpec = new SecretKeySpec(raw, "AES");
			encryptText = Base64.decode(text);
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			encryptedString = new String(cipher.doFinal(encryptText));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return encryptedString;
	}

}