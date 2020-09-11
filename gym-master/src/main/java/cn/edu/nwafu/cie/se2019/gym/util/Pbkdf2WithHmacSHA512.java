package cn.edu.nwafu.cie.se2019.gym.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Pbkdf2WithHmacSHA512 {
    private final static String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";
    public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return generateStrongPasswordHash(password, 1000);
    }

    public static String generateStrongPasswordHash(String password, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return generateStrongPasswordHash(password, iterations, generateSalt());
    }

    public static String generateStrongPasswordHash(String password, int iterations, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] chars = password.toCharArray();
        byte[] hash = pbkdf2(chars, iterations, salt);
        return HASH_ALGORITHM + ":" + iterations + ":" + bytesToHex(salt) + ":" + bytesToHex(hash);
    }

    private static byte[] pbkdf2(char[] password, int iterations, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return pbkdf2(password, iterations, salt, HASH_ALGORITHM);
    }

    private static byte[] pbkdf2(char[] password, int iterations, byte[] salt, String algorithm) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String bytesToHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] hexToBytes(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    public static boolean validatePasswordhash(String password, String hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String[] params = hash.split(":");
        int iterations = Integer.parseInt(params[1]);
        byte[] salt = hexToBytes(params[2]);
        byte[] bhash = hexToBytes(params[3]);
        byte[] testHash = pbkdf2(password.toCharArray(), iterations, salt, params[0]);
        return slowEquals(testHash, bhash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
}
