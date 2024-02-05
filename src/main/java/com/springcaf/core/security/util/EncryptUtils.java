package com.springcaf.core.security.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.springcaf.core.exception.SpringcafException;

/**
 * Basic symmetric encryption example
 */
public final class EncryptUtils {
	
	public static final String sampleKey = "sample16Byte1234"; // 128 bit key
    public static final String sampleInitVector = "sample16ByteInit"; // 16 bytes IV

    /**
     * Encrypt a String value
     * @param key
     * @param initVector
     * @param value
     * @return
     * @throws SpringcafException
     */
    public static String encrypt(String key, String initVector, String value) throws SpringcafException 
    {
    	try
    	{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
    	}
    	catch(Exception ex)
    	{
    		throw new SpringcafException(ex);
    	}
    }

    /**
     * Decrypt a String
     * @param key
     * @param initVector
     * @param encrypted
     * @return
     * @throws SpringcafException
     */
    public static String decrypt(String key, String initVector, String encrypted) throws SpringcafException 
    {
    	try
    	{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
    	}
    	catch(Exception ex)
    	{
    		throw new SpringcafException(ex);
    	}
    }
    
    /**
     * Base64 encode a String
     * @param str
     * @return
     */
	public static String base64Encode(String str)
	{
		byte[] byteArray = Base64.encodeBase64(str.getBytes());
		String encodedString = new String(byteArray);
		
		return encodedString;
	}
	
	/**
	 * Base64 decode a String
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str)
	{
		byte[] byteArray = Base64.decodeBase64(str.getBytes());
		String decodedString = new String(byteArray);
		
		return decodedString;
	}
}
