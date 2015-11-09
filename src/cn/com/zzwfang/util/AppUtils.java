/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.util;

import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.FirstActivity;
import cn.com.zzwfang.activity.SplashActivity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * @author Soo
 */
public class AppUtils {
    
    private static final String SPF_BASIC = "spf_basic";
    
    private static final String SPF_BASIC_DOUBLECLICK = "double_click";
    
    /**判断是否为两次的连续点击
     * @param context
     * @return
     */
    public static boolean isDoubleClick(Context context) {
        SharedPreferences spf = context.getSharedPreferences(SPF_BASIC, Context.MODE_PRIVATE);
        long lastclick = spf.getLong(SPF_BASIC_DOUBLECLICK, 0);
        long cur = System.currentTimeMillis();

        if ((cur - lastclick) > 2000) {
            Editor editor = spf.edit();
            editor.putLong(SPF_BASIC_DOUBLECLICK, cur);
            editor.commit();
            return false;
        }
        return true;
    }
    
    private static final String SPF_BASIC_FIRSTLUANCH = "first_luanch";

    /**
     * 获取应用是否第一次启动
     * 
     * @return true 第一次启动，false 不是第一次启动
     */
    public static boolean isFirstLuanch(Context context) {
        SharedPreferences spf = context.getSharedPreferences(SPF_BASIC, Context.MODE_PRIVATE);
        boolean flag = spf.getBoolean(SPF_BASIC_FIRSTLUANCH, false);

        if (flag == false) {
            Editor editor = spf.edit();
            editor.putBoolean(SPF_BASIC_FIRSTLUANCH, true);
            editor.commit();
        }

        return !flag;
    }

    /**
     * 创建应用桌面快捷方式
     */
    public static void createShortcut(Context context, String name) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        shortcut.putExtra("duplicate", false);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, SplashActivity.class);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        ShortcutIconResource iconRes = ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        context.sendBroadcast(shortcut);
    }
    
}
