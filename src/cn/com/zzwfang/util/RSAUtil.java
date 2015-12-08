package cn.com.zzwfang.util;

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
import java.util.HashMap;

import javax.crypto.Cipher;

import android.util.Base64;
import android.util.Log;
//  http://blog.csdn.net/zeng622peng/article/details/5957012
//  http://blog.csdn.net/yanzi1225627/article/details/26508035
//  http://www.eoeandroid.com/thread-102614-1-1.html?_dsign=8f994c77
//  http://bbs.csdn.net/topics/390923441
//  http://blog.csdn.net/centralperk/article/details/8558678
public class RSAUtil {
    
    private static final String ALGORITHM = "RSA";
    
    public static final String RSA_PUBLIC = "xLqsSIDFkdZy0vNeP+qCK8cq"
            + "7SIU3QhgmYTrM0dtRuMk8FjAY59l2NPM"
            + "GdDI3BuIc/ViPDS+e3n5oHQXT+F2XTDM"
            + "weg4tUDPx306VALcmsvlF9SHlLbAXbjs"
            + "KkM9mTYMV1AndreWjVKUoTWrERCVfo0M"
            + "RzUp8nnMh94T60lyD4s=";
    
    private static final String RSA_PRIVATE = "xLqsSIDFkdZy0vNeP+qCK8cq7SIU3Qhg"
            + "mYTrM0dtRuMk8FjAY59l2NPMGdDI3BuIc/ViP"
            + "DS+e3n5oHQXT+F2XTDMweg4tUDPx306VALcms"
            + "vlF9SHlLbAXbjsKkM9mTYMV1AndreWjVKUoTW"
            + "rERCVfo0MRzUp8nnMh94T60lyD4s=AQABye7s"
            + "9ZOE1YmCnAsipYBlb/uPTzuD7D/nYFcwSTVzf"
            + "SxrucxQV8emxInCbJySk7Ko7UFl+9zLICmonH"
            + "BzImZfSw==+WcK/sc+3NrVFABr9pHMvEosO4t"
            + "QIhI8UNr8CqXe7t+rtZEdbPdC3UnCShWKN7ew"
            + "1/kj9ucp612z8a2Q71GowQ==ifhvexNCDUyZK"
            + "tkI4R+rLyooLrCxYHgBNPgdi8ezkvEI4bfRpU"
            + "hKj7q4VtFb7QKDHSgz8DRZrS4JgGnsoLJlpQ="
            + "=RfXxz6Z0q993BjVYE8Kye98SqWFbCTO0V7oW"
            + "GaaN8tQrhrM+XeK+jvSx27ZPH3IX2X256PjPe"
            + "Ya/g3v8vCVcAQ==Lvo30GDopR6LOqUI/tzpKh"
            + "PjmkrANMjEADfP4Tsk3GTvqDrFVjxUt2KXu+J"
            + "nFx8sCKxKSAwYgC2b3RMSUNqpew==B7WDrla+"
            + "j/V+wE27kUJxUvM2AuqVe1mBvEmbzmpLn6DpT"
            + "myG0jTvbbnfV3yLIbYHxFYMnknPX8tn4O6ek8"
            + "mKl78LJU1vb6U2qzRmR6lfd2t1JrZYQIjQZa7"
            + "uSDfSRktpUS7EQ0bfC+wD1IIr+qVlPNJT52nA"
            + "Fx4588E6zR4iJcE=";
    
    
    
    private static PublicKey getPublicKeyFromX509(String algorithm,
            String bysKey) throws NoSuchAlgorithmException, Exception
    {
        byte[] decodeKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }
    
    private static PrivateKey getPrivateKeyFrom509(String algorithm, String bysKey) throws NoSuchAlgorithmException, Exception {
        byte[] decodeKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(x509);
    }
    
    public static void test() {
        
    }
    
    
    
    /**
     * 使用公钥加密
     * 
     * @param content
     * @return
     */
    public static String encryptByPublic(String content)
    {
        try
        {
            PublicKey publicKey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLIC);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plaintext = content.getBytes();
            byte[] output = cipher.doFinal(plaintext);

            String str = Base64.encodeToString(output, Base64.DEFAULT);
            return str;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    
    
    

	/**
	 * 生成公钥和私钥
	 * @throws NoSuchAlgorithmException 
	 *
	 */
	public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
	}
	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		//如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		//模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		System.err.println(bcd.length);
		//如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for(byte[] arr : arrays){
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
	}
	/**
	 * ASCII码转BCD码
	 * 
	 */
	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}
	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
	/**
	 * BCD转字符串
	 */
	public static String bcd2Str(byte[] bytes) {
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
	 * 拆分字符串
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i=0; i<x+z; i++) {
			if (i==x+z-1 && y!=0) {
				str = string.substring(i*len, i*len+y);
			}else{
				str = string.substring(i*len, i*len+len);
			}
			strings[i] = str;
		}
		return strings;
	}
	/**
	 *拆分数组 
	 */
	public static byte[][] splitArray(byte[] data,int len){
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if(y!=0){
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];
		byte[] arr;
		for(int i=0; i<x+z; i++){
			arr = new byte[len];
			if(i==x+z-1 && y!=0){
				System.arraycopy(data, i*len, arr, 0, y);
			}else{
				System.arraycopy(data, i*len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}
	
	public static void testRsa() {
		HashMap<String, Object> map;
		try {
			map = getKeys();
			//生成公钥和私钥
//			RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
//			RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
			
			RSAPublicKey publicKey = (RSAPublicKey) getPublicKeyFromX509(ALGORITHM, RSA_PUBLIC);
			RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKeyFrom509(ALGORITHM, RSA_PRIVATE);
			
			//模
			String modulus = publicKey.getModulus().toString();
			//公钥指数
			String public_exponent = publicKey.getPublicExponent().toString();
			//私钥指数
			String private_exponent = privateKey.getPrivateExponent().toString();
			//明文
			String ming = "123456789";
			Log.i("--->", "明文：  " + ming);
			//使用模和指数生成公钥和私钥
			RSAPublicKey pubKey = getPublicKey(modulus, public_exponent);
			RSAPrivateKey priKey = getPrivateKey(modulus, private_exponent);
			//加密后的密文
			String mi = encryptByPublicKey(ming, pubKey);
			Log.i("--->", "密文： " + mi);
			//解密后的明文
			ming = decryptByPrivateKey(mi, priKey);
			Log.i("--->", "解密后： " + ming);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
