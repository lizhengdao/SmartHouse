package cn.com.zzwfang.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {

    public static String md5(String str){
        try {
            byte[] urlData = str != null ? str.getBytes("utf-8") : null;
            int len = (urlData != null ? urlData.length : 0);
            byte[] buf = new byte[len];

            if (urlData != null)
                System.arraycopy(urlData, 0, buf, 0, urlData.length);
            return md5(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }
    
    public static String md5(byte[] data) throws NoSuchAlgorithmException{
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(data);
        byte[] md = mdInst.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md.length; i++) {
            int val = ((int) md[i]) & 0xff;
            if (val < 16)
                sb.append("0");
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }
}
