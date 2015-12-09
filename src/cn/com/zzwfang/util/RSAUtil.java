package cn.com.zzwfang.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import cn.com.zzwfang.rsa.Base64Helper;
import cn.com.zzwfang.rsa.RsaHelper;
import android.util.Base64;
import android.util.Log;
//  http://blog.csdn.net/zeng622peng/article/details/5957012
//  http://blog.csdn.net/yanzi1225627/article/details/26508035
//  http://www.eoeandroid.com/thread-102614-1-1.html?_dsign=8f994c77
//  http://bbs.csdn.net/topics/390923441
//  http://blog.csdn.net/centralperk/article/details/8558678

public class RSAUtil {

    private static final String ALGORITHM = "RSA";

    // public static final String RSA_PUBLIC =
    // "<RSAKeyValue><Modulus>xLqsSIDFkdZy"
    // + "0vNeP+qCK8cq7SIU3QhgmYTrM0dtRuM"
    // + "k8FjAY59l2NPMGdDI3BuIc/ViPDS+e3"
    // + "n5oHQXT+F2XTDMweg4tUDPx306VALcm"
    // + "svlF9SHlLbAXbjsKkM9mTYMV1AndreW"
    // + "jVKUoTWrERCVfo0MRzUp8nnMh94T60l"
    // + "yD4s=</Modulus><Exponent>AQAB</"
    // + "Exponent></RSAKeyValue>";

    public static final String RSA_PUBLIC = "<RSAKeyValue><Modulus>xLqsSIDFkd"
            + "Zy0vNeP+qCK8cq7SIU3QhgmYTrM0dtRuMk"
            + "8FjAY59l2NPMGdDI3BuIc/ViPDS+e3n5oH"
            + "QXT+F2XTDMweg4tUDPx306VALcmsvlF9SH"
            + "lLbAXbjsKkM9mTYMV1AndreWjVKUoTWrER"
            + "CVfo0MRzUp8nnMh94T60lyD4s=</Modulu"
            + "s><Exponent>AQAB</Exponent></RSAKeyValue>";

    // private static final String RSA_PRIVATE =
    // "<RSAKeyValue><Modulus>xLqsSIDFkdZy0vNeP+qC"
    // + "K8cq7SIU3QhgmYTrM0dtRuMk8FjAY59l2NPMG"
    // + "dDI3BuIc/ViPDS+e3n5oHQXT+F2XTDMweg4tU"
    // + "DPx306VALcmsvlF9SHlLbAXbjsKkM9mTYMV1A"
    // + "ndreWjVKUoTWrERCVfo0MRzUp8nnMh94T60ly"
    // + "D4s=</Modulus><Exponent>AQAB</Exponen"
    // + "t><P>ye7s9ZOE1YmCnAsipYBlb/uPTzuD7D/n"
    // + "YFcwSTVzfSxrucxQV8emxInCbJySk7Ko7UFl+"
    // + "9zLICmonHBzImZfSw==</P><Q>+WcK/sc+3Nr"
    // + "VFABr9pHMvEosO4tQIhI8UNr8CqXe7t+rtZEd"
    // + "bPdC3UnCShWKN7ew1/kj9ucp612z8a2Q71Gow"
    // + "Q==</Q><DP>ifhvexNCDUyZKtkI4R+rLyooLr"
    // + "CxYHgBNPgdi8ezkvEI4bfRpUhKj7q4VtFb7QK"
    // + "DHSgz8DRZrS4JgGnsoLJlpQ==</DP><DQ>RfX"
    // + "xz6Z0q993BjVYE8Kye98SqWFbCTO0V7oWGaaN"
    // + "8tQrhrM+XeK+jvSx27ZPH3IX2X256PjPeYa/g"
    // + "3v8vCVcAQ==</DQ><InverseQ>Lvo30GDopR6"
    // + "LOqUI/tzpKhPjmkrANMjEADfP4Tsk3GTvqDrF"
    // + "VjxUt2KXu+JnFx8sCKxKSAwYgC2b3RMSUNqpe"
    // + "w==</InverseQ><D>B7WDrla+j/V+wE27kUJx"
    // + "UvM2AuqVe1mBvEmbzmpLn6DpTmyG0jTvbbnfV"
    // + "3yLIbYHxFYMnknPX8tn4O6ek8mKl78LJU1vb6"
    // + "U2qzRmR6lfd2t1JrZYQIjQZa7uSDfSRktpUS7"
    // + "EQ0bfC+wD1IIr+qVlPNJT52nAFx4588E6zR4i"
    // + "JcE=</D></RSAKeyValue>";

    private static final String RSA_PRIVATE = "<RSAKeyValue><Modulus>xLqsSIDFk"
            + "dZy0vNeP+qCK8cq7SIU3QhgmYTrM0dtRuMk8FjAY59l2"
            + "NPMGdDI3BuIc/ViPDS+e3n5oHQXT+F2XTDMweg4tUDPx"
            + "306VALcmsvlF9SHlLbAXbjsKkM9mTYMV1AndreWjVKUo"
            + "TWrERCVfo0MRzUp8nnMh94T60lyD4s=</Modulus><Ex"
            + "ponent>AQAB</Exponent><P>ye7s9ZOE1YmCnAsipYB"
            + "lb/uPTzuD7D/nYFcwSTVzfSxrucxQV8emxInCbJySk7K"
            + "o7UFl+9zLICmonHBzImZfSw==</P><Q>+WcK/sc+3NrV"
            + "FABr9pHMvEosO4tQIhI8UNr8CqXe7t+rtZEdbPdC3UnC"
            + "ShWKN7ew1/kj9ucp612z8a2Q71GowQ==</Q><DP>ifhv"
            + "exNCDUyZKtkI4R+rLyooLrCxYHgBNPgdi8ezkvEI4bfR"
            + "pUhKj7q4VtFb7QKDHSgz8DRZrS4JgGnsoLJlpQ==</DP"
            + "><DQ>RfXxz6Z0q993BjVYE8Kye98SqWFbCTO0V7oWGaa"
            + "N8tQrhrM+XeK+jvSx27ZPH3IX2X256PjPeYa/g3v8vCV"
            + "cAQ==</DQ><InverseQ>Lvo30GDopR6LOqUI/tzpKhPj"
            + "mkrANMjEADfP4Tsk3GTvqDrFVjxUt2KXu+JnFx8sCKxK"
            + "SAwYgC2b3RMSUNqpew==</InverseQ><D>B7WDrla+j/"
            + "V+wE27kUJxUvM2AuqVe1mBvEmbzmpLn6DpTmyG0jTvbb"
            + "nfV3yLIbYHxFYMnknPX8tn4O6ek8mKl78LJU1vb6U2qz"
            + "RmR6lfd2t1JrZYQIjQZa7uSDfSRktpUS7EQ0bfC+wD1I"
            + "Ir+qVlPNJT52nAFx4588E6zR4iJcE=</D></RSAKeyValue>";


    public static void test() {
        String plainString = "123456";
        Log.i("--->", "明文：" + plainString);
//        String a = 
                encryptByPublic(plainString);
//        Log.i("--->", "加密后：" + a);
//        String b = decryptByPrivateKey(a.getBytes());
//        Log.i("--->", "解密后：" + b);
    }

    /**
     * 使用公钥加密
     * 
     * @param content
     * @return
     */
    public static String encryptByPublic(String plaintString) {
         PublicKey pubKey = RsaHelper.decodePublicKeyFromXml(RSA_PUBLIC);
         String encryptedString = RsaHelper.encryptDataFromStr(plaintString,
         pubKey);
         return encryptedString;

//        PublicKey pubKey3 = RsaHelper.decodePublicKeyFromXml(RSA_PUBLIC);
//
//        PrivateKey priKey3 = RsaHelper.decodePrivateKeyFromXml(RSA_PRIVATE);
//
//        byte[] dataByteArray;
//        try {
//            dataByteArray = plaintString.getBytes("utf-8");
//            byte[] encryptedDataByteArray = RsaHelper.encryptData(
//                    dataByteArray, pubKey3);
//
//            System.out.println("encryptedData的Base64表示："
//                    + Base64Helper.encode(encryptedDataByteArray));
//            System.out.println((new Date()).toLocaleString() + ": 解密中。。。"); // 解密
//                                                                            // byte[]
//            byte[] decryptedDataByteArray = RsaHelper.decryptData(
//                    encryptedDataByteArray, priKey3);
//            
//            System.out.println((new Date()).toLocaleString() + ": 解密结果：" + new String(decryptedDataByteArray, "gb2312"));
//
//            System.out.println(new String(decryptedDataByteArray, "utf-8"));// 签名
//            System.out.println((new Date()).toLocaleString() + ": 签名中。。。");
//
//            byte[] signDataByteArray = RsaHelper.signData(dataByteArray,
//                    priKey3);
//            System.out.println("signData的Base64表示："
//                    + Base64Helper.encode(signDataByteArray)); // 验签
//            System.out.println((new Date()).toLocaleString() + ": 验签中。。。");
//            boolean isMatch = RsaHelper.verifySign(dataByteArray,
//                    signDataByteArray, pubKey3);
//            System.out.println("验签结果：" + isMatch);
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    public static String decryptByPrivateKey(byte[] encryptedData) {
        PrivateKey privateKey = RsaHelper.decodePrivateKeyFromXml(RSA_PRIVATE);
        byte[] plainString = RsaHelper.decryptData(encryptedData, privateKey);
        return new String(plainString);
    }



}
