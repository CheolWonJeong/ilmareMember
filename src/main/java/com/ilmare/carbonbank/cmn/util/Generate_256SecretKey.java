package com.ilmare.carbonbank.cmn.util;

import java.security.SecureRandom;
import java.util.Base64;

/*
**
* Google Cloud Platform (GCP) - Generate AES-256 Encryption Key Example
*/
public class Generate_256SecretKey {

   public static void main(String[] args) {
       String key = generateEncryptionKey();
       System.out.println("Generated Base64-encoded AES-256 encryption key: " + key);
   }

   public static String generateEncryptionKey() {
       byte[] key = new byte[32];
       new SecureRandom().nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
   }
}