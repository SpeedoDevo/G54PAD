package hu.devo.hash;

import hu.devo.util.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    private static MessageDigest sha;

    static {
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            sha = null;
        }
    }

    public static long hash(byte[] data) {
        sha.update(data);
        long res = Util.bytesToLong(sha.digest());
        sha.reset();
        return res;
    }

    public static long hash(byte[] data, long seed) {
        sha.update(Util.longToBytes(seed));
        return hash(data);
    }
}
