package util;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    // Turn a plain password into a salted hash string
    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12)); // 12 = good default cost
    }

    // Check if the plain password matches the stored hash from DB
    public static boolean matches(String plain, String storedHash) {
        return BCrypt.checkpw(plain, storedHash);
    }
}

