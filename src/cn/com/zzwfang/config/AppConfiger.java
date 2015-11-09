package cn.com.zzwfang.config;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class AppConfiger {
    
    private static Context context;
    
//    public static final String BaiDu_Map_Key = "5VG5Q73NBNgY2G6LQy5sPFse";
    
    private AppConfiger(Context context){
    }
    
    /**
     * 注册应用配置，此方法只在Application OnCreate()时调用
     */
    public static void registContext(Context context){
        if(AppConfiger.context == null){
            AppConfiger.context = context;
        }
    }
    
    /**
     * 返回应用的根目录路径
     */
    public static String root(){
        if(context == null){
            return null;
        }
//        return Environment.getExternalStorageDirectory().getAbsolutePath() + 
//                "/Scpii/BestSearch/";
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			cachePath = context.getExternalCacheDir().getPath();
			
		} else {
			cachePath = context.getCacheDir().getPath();
		}
        return cachePath;
    }
    
    /**
     * 返回crash的log文件路径
     */
//    public static String crashFile(){
//        String root = root();
//        if(root == null){
//            return null;
//        }
//        return root + "Log/crash.txt";
//    }
    
    /**
     * true:当记录crash日志到新的一个文件中，文件名从方法crashFile()中获取
     * false:当记录crash日志时，追加到原有文件后面
     */
    public static boolean crashIsNewFile(){
        return false;
    }
    
//    public static String getDiskCacheDir(){
//        return getDiskCacheDir("dfc");
//    }
    
    /**
     * 获取应用本地文件缓存的目录
     */
    public static String getDiskCacheDir(String fileName){
        String root = root();
        if(root == null){
            return null;
        }
//        return root + "Cache/" + fileName;
        return root + File.separator + fileName;
    }
    
//    public static File getDiskCacheDir(String placeHolder, String uniqueName) {
//		String cachePath;
//		if (Environment.MEDIA_MOUNTED.equals(Environment
//				.getExternalStorageState())) {
//			cachePath = context.getExternalCacheDir().getPath();
//		} else {
//			cachePath = context.getCacheDir().getPath();
//		}
//		return new File(cachePath + File.separator + uniqueName);
//	}
    
    /**
     * 得到设备的id
     */
//    public static String getDeviceId(){
//        if(context == null){
//            return null;
//        }
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return tm.getDeviceId();
//    }
    
    /**
     * 获取设备操作系统
     */
//    public static String getDeviceOs(){
//        return "Android";
//    }
    
    /**
     * 获取session文件的路径
     */
    public static String getSessionFile(Context context){
        String root = root();
        if(root == null){
            return null;
        }
        return root + "Session/session.xml";
    }
    
    /**
     * 获取拍照后转存的目录
     */
    public static String getCameraStoreFileDir(){
        String root = root();
        if(root == null){
            return null;
        }
        return root + "Csf/";
    }
    
    /**
     *获取临时文件的存放目录
     */
    public static String getTempFileDir(){
        String root = root();
        if(root == null){
            return null;
        }
        return root + "Temp/";
    }
}
