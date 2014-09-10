package com.example.phr.tools;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hasher {

	public static String hash(String string) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hashed = digest.digest(string.getBytes("UTF-8"));
			BigInteger bi = new BigInteger(1, hashed);
			String result = bi.toString(16);
			if (result.length() % 2 != 0) {
				return "0" + result;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
