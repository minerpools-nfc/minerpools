package com.dpool.app.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加解密工具类，实现公钥加密私钥解密和私钥解密公钥解密
 */
@Slf4j
public class RSAUtils {
	  /** */
	   /**
	    * 加密算法RSA
	    */
	   public static final String KEY_ALGORITHM = "RSA";

	   /** */
	   /**
	    * 签名算法
	    */
	   public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	   
	   /** */
	   /**
	    * RSA最大加密明文大小
	    */
	   private static final int MAX_ENCRYPT_BLOCK = 117;

	   /** */
	   /**
	    * RSA最大解密密文大小
	    */
	   private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 私钥解密
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }
    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
       try {
          data = Base64Utils.encodeToString(encryptByPublicKey(data.getBytes(), PUBLICKEY));
       } catch (Exception e) {
          log.error("加密出错",e);
       }
       return data;
    }
    
    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
       byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
       X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
       KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
       Key publicK = keyFactory.generatePublic(x509KeySpec);
       // 对数据加密
       Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
       cipher.init(Cipher.ENCRYPT_MODE, publicK);
       int inputLen = data.length;
       ByteArrayOutputStream out = new ByteArrayOutputStream();
       int offSet = 0;
       byte[] cache;
       int i = 0;
       // 对数据分段加密
       while (inputLen - offSet > 0) {
          if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
             cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
          } else {
             cache = cipher.doFinal(data, offSet, inputLen - offSet);
          }
          out.write(cache, 0, cache.length);
          i++;
          offSet = i * MAX_ENCRYPT_BLOCK;
       }
       byte[] encryptedData = out.toByteArray();
       out.close();
       return encryptedData;
    }
}