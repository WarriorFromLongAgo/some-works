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
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic="appTokenUtil")
public class ShareAppTokenUtil {
	
	private static final String KEY_ALGORITHM = "AES";
	
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    private static final Integer RANDOM_LENGTH = 8;
    
    private static final String KEY = "abcdefgh12345678";
	
	public static String createToken(String personId, String expireSecond) {
		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime + (Integer.valueOf(expireSecond) * 1000);
		String random = ShareAppTokenUtil.genRandomNum(RANDOM_LENGTH);
		String content = personId + "|" + currentTime + "|" + expireTime + "|" + random;
		String token =  ShareAppTokenUtil.encrypt(content,KEY);
		try {
			return Base64.encodeBase64String(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
            throw new RuntimeException(e.getMessage());
		}
	}
	
	public static String getUserIdByToken(String token) {
		
		if ( StringUtils.isEmpty(token)) {
			throw new RuntimeException("token为空.");
		}
		
		String result = ShareAppTokenUtil.decrypt(token , KEY);
		String[] properties = result.split("\\|");
		if ( properties.length != 4) {
			throw new RuntimeException("token格式错误.");
		}
		
		String personId =  properties[0];
		log.info("the user is {}", personId);
		
		return personId;
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

		String token = ShareAppTokenUtil.createToken("1", "30");
		
		log.info("expire time = 30s : generate token is : {}" , token);
		
		String result = ShareAppTokenUtil.decrypt(token , KEY);
		
		log.info("decrpt result : {}" , result);
		
		String[] properties = result.split("\\|");
		
		log.info("splited count : {}", properties.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		log.info("the personId is {}", properties[0]);
		
		log.info("token create time is : {}", sdf.format(new Date(Long.valueOf(properties[1]))));
		
		log.info("token expire time is : {}", sdf.format(new Date(Long.valueOf(properties[2]))));
		
		log.info("the random value is : {}", properties[3]);
		
		log.info("客户端发送请求时在http的header中加Authorization, 值为:{}", token);
		
		log.info("服务端接收客户端请求之后首先需要判断,header中是否包含Authorization,没有则返回错误代码以及错误描述");
		
		log.info("然后调用AppTokenUtil.decrypt方法, 如果解码过程中出现异常则表示Token非法");
		
		log.info("如果解码成功,则以[|]分割字符串并判断数组长度是否等于双方约定好的长度");
		
		log.info("最后判断,");
	}
}
