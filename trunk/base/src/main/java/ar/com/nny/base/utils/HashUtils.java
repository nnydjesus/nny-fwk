package ar.com.nny.base.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ar.com.nny.base.exception.NonBusinessException;

public final class HashUtils {

    // More secure
    private static final String ALGORITHM = "SHA-256";

    // Just to convert to hex
    private static final char DECIMAL_HEX[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * Hashes the given text
     *
     * @param text Text to create the hash from
     * @return Hash of the text. It's returned as a string representing the bytes in hex.
     */
    public static String hash(final String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(text.getBytes());
            final byte[] digest = md.digest();
            // Lets translate it into something prettier
            final StringBuffer sb = new StringBuffer(digest.length * 2);
            for ( int i = 0; i < digest.length; i++ ) {
                final byte b = digest[i];
                final int high = (b & 0xF0) >> 4;
                final int low = (b & 0x0F);
                sb.append(DECIMAL_HEX[high]);
                sb.append(DECIMAL_HEX[low]);
            }
            return sb.toString();
        }
        catch ( NoSuchAlgorithmException e ) {
            throw new NonBusinessException("Error hashing string", e);
        }
    }
}

