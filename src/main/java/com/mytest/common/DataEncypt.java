package com.mytest.common;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author wanwei
 * @Date 2020/3/24 15:22
 * @Description
 * @Reviewer
 */
public class DataEncypt {
    public static String aesEncrypt(String plainText, String password) throws Exception {

        try {
            return toHex(encrypt(plainText.getBytes("UTF-8"), password));
        } catch (Exception var3) {
            throw new Exception("SimpleAES encrypt Exception:" + var3.getMessage());
        }
    }

    private static byte[] encrypt(byte[] byteS, String pwd) throws Exception {
        Object var2 = null;
        Cipher cipher;
        byte[] byteFina;
        try {
            cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(getKey(pwd), "AES");
            cipher.init(1, keySpec);
            byteFina = cipher.doFinal(byteS);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    private static String toHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 3);
        for(int i = 0; i < bytes.length; ++i) {
            int val = bytes[i] & 255;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    private static byte[] getKey(String password) throws UnsupportedEncodingException {
        if (password.length() > 16) {
            password = password.substring(0, 16);
        } else if (password.length() < 16) {
            int count = 16 - password.length();

            for(int i = 0; i < count; ++i) {
                password = password + "0";
            }
        }
        return password.getBytes("UTF-8");
    }

    public static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    public static String toNowTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        format.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return format.format(date);
    }


    public static String HMACSha1(String data, String key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        String result = Base64.encodeBase64String(byteArrayToHex(rawHmac).getBytes());
        return result;
    }

    public static String HMACSha2(String data, String key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        String result = Base64.encodeBase64String(rawHmac);
        return result;
    }

    protected static String byteArrayToHex(byte [] a) {
        int hn, ln, cx;
        String hexDigitChars = "0123456789abcdef";
        StringBuffer buf = new StringBuffer(a.length * 2);
        for(cx = 0; cx < a.length; cx++) {
            hn = ((int)(a[cx]) & 0x00ff) /16 ;
            ln = ((int)(a[cx]) & 0x000f);
            buf.append(hexDigitChars.charAt(hn));
            buf.append(hexDigitChars.charAt(ln));
        }
        return buf.toString();
    }


    private static byte[] encrypt(byte[] encryptBytes, byte[] publicKeyBytes) throws Exception {
        PublicKey publicKey = codeToPublicKey(publicKeyBytes);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKey);
        byte[] enBytes = cipher.doFinal(encryptBytes);
        return enBytes;
    }

    private static PublicKey codeToPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static String AES_Encrypt(Object plaintext, String Key,String EncryptMode) {
        String PlainText=null;
        try {
            PlainText=plaintext.toString();
            if (Key == null) {
                return null;
            }

            SecretKeySpec skeySpec = new SecretKeySpec(getKey(Key), "AES");
            Cipher cipher = Cipher.getInstance("AES/"+EncryptMode+"/PKCS5Padding");
            if(EncryptMode=="ECB") {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            }else {
                IvParameterSpec iv = new IvParameterSpec(getKey(Key));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            }
            byte[] encrypted = cipher.doFinal(PlainText.getBytes("utf-8"));
            String encryptedStr=new String(new BASE64Encoder().encode(encrypted));
            return encryptedStr;
            //return new String(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    private static Map<String, String> StringToBase64(String datas) {
        /*
         * requestBody转码为base64
         */
        Map<String, String> params = new HashMap<String, String>();
        try {
            String requestData = Base64.encodeBase64URLSafeString(datas.getBytes("utf-8"));
            params.put("data", requestData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }
}
