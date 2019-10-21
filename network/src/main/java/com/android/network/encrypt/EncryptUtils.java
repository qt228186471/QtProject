package com.android.network.encrypt;

import android.text.TextUtils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by yangbing 2017/3/30.
 */
public class EncryptUtils {

    /**加密方式*/
    private static final String RSA_NONE_PKCS1Padding = "RSA/None/PKCS1Padding";

    private final static String DES = "DES";
    private static final String CIPHER = "DES/CBC/PKCS5Padding";
    public static String IV_PARAM = "12345678";
    public static String IV_PARAM_KEY = "ivParam";
    
    public static void main(String[] args) throws Exception {
        String data = "123 456";
        String key = "wang!@#$%";
//        System.err.println(encrypt(data, key, IV_PARAM));
//        System.err.println(decrypt(encrypt(data, key, IV_PARAM), key));
        
    }
    
    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key, String ivParam) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes(), ivParam.getBytes());
        String strs = Base64Tool.encode(bt);
        return strs;
    }
    
    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key, String ivParam) throws
            Exception {
        if (data == null)
            return null;
        byte[] buf = Base64Tool.decode(data);
        byte[] bt = decrypt(buf, key.getBytes(), ivParam.getBytes());
        return new String(bt);
    }
    
    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key, byte[] ivParam) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(CIPHER);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, new IvParameterSpec(ivParam), sr);
        
        return cipher.doFinal(data);
    }
    
    
    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key, byte[] ivParam) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(CIPHER);
        
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, new IvParameterSpec(ivParam), sr);
        
        return cipher.doFinal(data);
    }

    /** 使用公钥加密 */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(RSA_NONE_PKCS1Padding);
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

    /**
     * BCD转ASCII字符串
     * @param bytes BCD byte数组
     * @return ASCII字符串
     */
    public static String bcdToStr(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 获取字符串md5
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
