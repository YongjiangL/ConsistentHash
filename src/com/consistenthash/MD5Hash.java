package com.consistenthash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash implements HashAlgorithm {
	MessageDigest md;
	
	public MD5Hash() {
		try {
			md = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e) {
			
		}
	}
	
	public long hash(String key) {
		md.reset();
		md.update(key.getBytes());
		byte[] digest = md.digest();

        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
	}
}
