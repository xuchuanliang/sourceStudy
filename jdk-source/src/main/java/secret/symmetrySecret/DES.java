package secret.symmetrySecret;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * java对称加密算法：DES
 */
public class DES {

    static String string = "wen-min";
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        //生成key//返回生成指定算法密钥的KeyGenerator对象
        final KeyGenerator des = KeyGenerator.getInstance("DES");
        //初始化此密钥生成器,使其具有确定的密钥大小
        des.init(56);
        //生成一个密钥
        final SecretKey secretKey = des.generateKey();
        final byte[] secretKeyEncoded = secretKey.getEncoded();

        // key转换
        //实例化DES密钥规则
        final DESKeySpec desKeySpec = new DESKeySpec(secretKeyEncoded);
        //实例化密钥工厂
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        //生成密钥
        final SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

        // 加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
        byte[] result = cipher.doFinal(string.getBytes());
        System.out.println("jdk des encrypt:" + Hex.encodeHexString(result));

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
        result = cipher.doFinal(result);
        System.out.println("jdk des decrypt:" + new String(result));
    }
}
