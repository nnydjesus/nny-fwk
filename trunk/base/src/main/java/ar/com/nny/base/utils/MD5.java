package ar.com.nny.base.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class MD5 {

    private MessageDigest md = null;
    static private MD5 md5 = null;
    private static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Constructor is private so you must use the getInstance method
     */
    private MD5() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    /**
     * This returns the singleton instance
     */
    public static MD5 getInstance() throws NoSuchAlgorithmException {
        if (md5 == null) {
            md5 = new MD5();
        }
        return (md5);
    }

    public String hashData(final byte[] dataToHash) {
        return hexStringFromBytes((calculateHash(dataToHash)));
    }

    public String hashFile(final File fileToHash) throws IOException {
    	InputStream is = new FileInputStream(fileToHash);
    	try {
    		is = new BufferedInputStream(new DigestInputStream(is, md));
    		while(is.read() != -1) {
    			//Do nothing
    		}
		} finally {
			is.close();
		}
		return hexStringFromBytes(md.digest());
    }
    
    private byte[] calculateHash(final byte[] dataToHash) {
        md.update(dataToHash, 0, dataToHash.length);

        return (md.digest());
    }

    public String hexStringFromBytes(final byte[] b) {
        String hex = "";

        int msb;
        int lsb = 0;
        int i;

        // MSB maps to idx 0
        for (i = 0; i < b.length; i++) {
            msb = (b[i] & 0x000000FF) / 16;
            lsb = (b[i] & 0x000000FF) % 16;
            hex = hex + hexChars[msb] + hexChars[lsb];
        }
        return (hex);
    }

} 
