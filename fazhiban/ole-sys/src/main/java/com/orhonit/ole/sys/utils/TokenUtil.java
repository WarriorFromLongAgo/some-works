package com.orhonit.ole.sys.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class TokenUtil {
	
	private static final String KEY_ALGORITHM = "AES";
	
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    private static final Integer RANDOM_LENGTH = 8;
    
    private static final String KEY = "abcdefgh12345678";
	
	// base64 aes ( userId | currentTime | expireTime | random )

	public static String createToken(Long userId, String expireSecond) {
		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime + (Integer.valueOf(expireSecond) * 1000);
		String random = TokenUtil.genRandomNum(RANDOM_LENGTH);
		String content = userId + "|" + currentTime + "|" + expireTime + "|" + random;
		String token =  TokenUtil.encrypt(content,KEY);
		try {
			return Base64.encodeBase64String(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
            throw new RuntimeException(e.getMessage());
		}
	}
	
	public static String getUserIdByToekn(String token) {
		String result = TokenUtil.decrypt(token , KEY);
		if ( result == null ) {
			throw new RuntimeException("token is null.");
		}
		String[] properties = result.split("\\|");
		if ( properties.length !=  4) {
			throw new RuntimeException("token is error.");
		}
		
		if ( properties[3].length() !=  RANDOM_LENGTH) {
			throw new RuntimeException("token is error.");
		}
		
		if (Long.valueOf(properties[2]) < Long.valueOf(properties[1])) {
			throw new RuntimeException("token is error.");
		}
		
		if (Long.valueOf(properties[2]) < System.currentTimeMillis()) {
			throw new RuntimeException("token is error.");
		}
		
		return properties[0];
	}
	

	public static String getPersonIdByToekn(String token) {
		String result = TokenUtil.decrypt(token , KEY);
		if ( result == null ) {
			throw new RuntimeException("token is null.");
		}
		String[] properties = result.split("\\|");
		if ( properties.length !=  4) {
			throw new RuntimeException("token is error.");
		}
		
		if ( properties[3].length() !=  RANDOM_LENGTH) {
			throw new RuntimeException("token is error.");
		}
		
		if (Long.valueOf(properties[2]) < Long.valueOf(properties[1])) {
			throw new RuntimeException("token is error.");
		}
		
		return properties[0];
	}

	private static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
	
	private static String decrypt(String content, String password) {
        try {
        	content = new String(Base64.decodeBase64(content),"UTF-8");
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "UTF-8");
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
	
	private static SecretKeySpec getSecretKey(final String password) {
        KeyGenerator kg = null;
        try {
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            // kg.init(128, new SecureRandom(password.getBytes()));
            kg.init(128, random);
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

	public static String genRandomNum(int length) {
		final int maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z'};

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	public static void main(String[] args) throws Exception{

		String token = TokenUtil.createToken(1L, "30");
		
		System.out.println("token = " + token);
		
		String result = TokenUtil.decrypt(token , KEY);
		
		System.out.println(result);
		
		String[] properties = result.split("\\|");
		
		System.out.println(properties.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(sdf.format(new Date(Long.valueOf(properties[1]))));
		
		System.out.println(sdf.format(new Date(Long.valueOf(properties[2]))));
		
		
	}

}
