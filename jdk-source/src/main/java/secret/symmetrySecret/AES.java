package secret.symmetrySecret;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * 对称加密 AES
 */
public class AES {
    static String string = "wen-min";
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //设置密钥长度
        keyGenerator.init(128);
        //生成密钥对象
        SecretKey secretKey = keyGenerator.generateKey();
        //获取密钥
        byte[] keyBytes = secretKey.getEncoded();
        //key转换
        Key key = new SecretKeySpec(keyBytes,"AES");

        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        //初始化，设置为加密
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(string.getBytes());
        System.out.println("jdk aes encrypt: " + Base64.encodeBase64String(result));


        //初始化,设置为解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(result);
        System.out.println("jdk aes desrypt:" + new String(result));
    }
}
