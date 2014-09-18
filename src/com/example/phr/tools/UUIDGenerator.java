package phr.tools;

import java.util.UUID;

public class UUIDGenerator {

	public static String generateUniqueString() {
		return UUID.randomUUID().toString();
	}
}
