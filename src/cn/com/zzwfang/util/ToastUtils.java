package cn.com.zzwfang.util;

import android.content.Context;
import android.widget.Toast;

public enum ToastUtils {


    SHORT(Toast.LENGTH_SHORT);
    
    private int duration;
    
    ToastUtils(int duration) {
        this.duration = duration;
    }
    
    public void toast(Context context, String msg) {
        Toast.makeText(context, msg, duration).show();
    }
    
    public void toast(Context context, int resId) {
        Toast.makeText(context, resId, duration).show();
    }
}
