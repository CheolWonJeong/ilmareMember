package com.ilmare.carbonbank.cmn.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import lombok.Value;

@Component
public class AES256Util {

	private static String KEY = "ZQm8ihwnayZJ5DdMWWUnPCKK3ntQLzUWoBUkVG56PjY=";
	private static String IV = "UKfnV8Gq90SL11tlRKraOA==";

	public static void main(String[] args) {
		Generate_256SecretKey genKey = new Generate_256SecretKey();
        Generate_IVKey genIV = new Generate_IVKey();
        String key = genKey.generateEncryptionKey();
        String iv = genIV.generateIVKey();

        String plainText = "incheonadm";

        //default key
        System.out.println("plainText Text: " + plainText);

        System.out.println("default key Text: " + KEY);
        System.out.println("default iv Text: " + IV);
        String deFaultKeyEncryptedText = encrypt(plainText);
        System.out.println("default Key Encrypted Text: " + deFaultKeyEncryptedText);
        System.out.println("default Key Decrypted Text: " + decrypt(deFaultKeyEncryptedText));

        //new key
        System.out.println("new key Text: " + key);
        System.out.println("new iv Text: " + iv);
        String encryptedText = encrypt(key, iv, plainText);
        System.out.println("new Key Encrypted Text: " + encryptedText);
        String decryptedText = decrypt(key, iv, encryptedText);
        System.out.println("new Key Decrypted Text: " + decryptedText);

    }
	
	   // Encryption
    public static String encrypt(String plainText) {
        // AES-256 Encryption
        try {
        	return encrypt(KEY, IV, plainText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Decryption
    public static String decrypt(String encryptedText) {
        // AES-256 Decryption
        try {
        	return decrypt(KEY, IV, encryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	   // Encryption Key
    public static String encrypt(String key, String iv, String plainText) {
	     // AES-256 Encryption
	     try {
	         byte[] decodedKey = Base64.getDecoder().decode(key);
	         byte[] decodedIV = Base64.getDecoder().decode(iv);
	
	         SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");
	         IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIV);
	
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	         cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	
	         byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	
	         return Base64.getEncoder().encodeToString(encryptedBytes);
	
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return null;
	}

	 // Decryption Key
	public static String decrypt(String key, String iv, String encryptedText) {
		// AES-256 Decryption
		try {
		     byte[] decodedKey = Base64.getDecoder().decode(key);
		     byte[] decodedIV = Base64.getDecoder().decode(iv);
		     byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);
		
		     SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");
		     IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIV);
		
		     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		         cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		
		         byte[] decryptedBytes = cipher.doFinal(decodedEncryptedText);
		
		         return new String(decryptedBytes);
		
		     } catch (Exception e) {
		         e.printStackTrace();
		     }
		     return null;
		}

}
