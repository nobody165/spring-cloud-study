package com.radlly.util.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 
 * @author Weilin
 *加密解密工具类
 */

public class CipherTools {
	private final static String DES_ALGORITHM = "DES";  
	private final static String KEY = "1234567890";
	/**
	 * DES加密
	 * @param plainData
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String encryption(String plainData,String key) throws Exception{

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			if(key==null||"".equals(key)){
				cipher.init(Cipher.ENCRYPT_MODE, generateKey(KEY));
			}else{
				cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch(InvalidKeyException e){
			
		}
		
		try {
			// 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
			// 不能把加密后的字节数组直接转换成字符串
			byte[] buf = cipher.doFinal(plainData.getBytes());
			
			return Base64Utils.encode(buf);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}
	    
	}

	/**
	 * DES解密
	 * @param secretData
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String decryption(String secretData,String key) throws Exception{
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			if(key==null||"".equals(key)){
			    cipher.init(Cipher.DECRYPT_MODE, generateKey(KEY));
			}else{
				cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("NoSuchAlgorithmException", e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("NoSuchPaddingException", e);
		}catch(InvalidKeyException e){
			e.printStackTrace();
			throw new Exception("InvalidKeyException", e);
			
		}
		
		try {
			
			byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
			
			return new String(buf);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("BadPaddingException", e);
		}
	}
	
	
	/**
	 * 获得秘密密钥
	 * 
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException{
		//SecureRandom secureRandom = new SecureRandom(secretKey.getBytes());
		  SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
		  secureRandom.setSeed(secretKey.getBytes());	
		// 为我们选择的DES算法生成一个KeyGenerator对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(DES_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
		}
		kg.init(secureRandom);
		//kg.init(56, secureRandom);
		
		// 生成密钥
		return kg.generateKey();
	}
			

		
	static class Base64Utils {

		static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
		static private byte[] codes = new byte[256];
		static {
			for (int i = 0; i < 256; i++)
				codes[i] = -1;
			for (int i = 'A'; i <= 'Z'; i++)
				codes[i] = (byte) (i - 'A');
			for (int i = 'a'; i <= 'z'; i++)
				codes[i] = (byte) (26 + i - 'a');
			for (int i = '0'; i <= '9'; i++)
				codes[i] = (byte) (52 + i - '0');
			codes['+'] = 62;
			codes['/'] = 63;
		}
		
		/**
		 * 将原始数据编码为base64编码
		 */
		static public String encode(byte[] data) {
			char[] out = new char[((data.length + 2) / 3) * 4];
			for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
				boolean quad = false;
				boolean trip = false;
				int val = (0xFF & (int) data[i]);
				val <<= 8;
				if ((i + 1) < data.length) {
					val |= (0xFF & (int) data[i + 1]);
					trip = true;
				}
				val <<= 8;
				if ((i + 2) < data.length) {
					val |= (0xFF & (int) data[i + 2]);
					quad = true;
				}
				out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 1] = alphabet[val & 0x3F];
				val >>= 6;
				out[index + 0] = alphabet[val & 0x3F];
			}
			
			return new String(out);
		}

		/**
		 * 将base64编码的数据解码成原始数据
		 */
		static public byte[] decode(char[] data) {
			int len = ((data.length + 3) / 4) * 3;
			if (data.length > 0 && data[data.length - 1] == '=')
				--len;
			if (data.length > 1 && data[data.length - 2] == '=')
				--len;
			byte[] out = new byte[len];
			int shift = 0;
			int accum = 0;
			int index = 0;
			for (int ix = 0; ix < data.length; ix++) {
				int value = codes[data[ix] & 0xFF];
				if (value >= 0) {
					accum <<= 6;
					shift += 6;
					accum |= value;
					if (shift >= 8) {
						shift -= 8;
						out[index++] = (byte) ((accum >> shift) & 0xff);
					}
				}
			}
			if (index != out.length)
				throw new Error("miscalculated data length!");
			return out;
		}
	}
	
}
