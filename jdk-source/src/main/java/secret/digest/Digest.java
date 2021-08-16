package secret.digest;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要：使用MD5 SHA256 SHA512进行摘要计算
 * 特点是不可逆 易变性 无论输入长度多少 输出固定长度
 */
public class Digest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String[] s = {"xuchuanliang","zhanghexiang","xujunmo"};
        for(String ss:s){
            System.out.println("加密前："+ss);
            System.out.println("MD5加密："+MD5(ss));
            System.out.println("SHA256加密："+SHA256(ss));
            System.out.println("SHA512加密："+SHA512(ss));
        }
    }

    public static String MD5(String s) throws NoSuchAlgorithmException {
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Hex.encodeHexString(md5.digest(s.getBytes(StandardCharsets.UTF_8)));
    }

    public static String SHA256(String s) throws NoSuchAlgorithmException {
        final MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        return Hex.encodeHexString(md5.digest(s.getBytes(StandardCharsets.UTF_8)));
    }

    public static String SHA512(String s) throws NoSuchAlgorithmException {
        final MessageDigest md5 = MessageDigest.getInstance("SHA-512");
        return Hex.encodeHexString(md5.digest(s.getBytes(StandardCharsets.UTF_8)));
    }
}
