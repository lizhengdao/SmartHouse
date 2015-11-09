package cn.com.zzwfang.activity;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.SystemBarTintManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;


public class BaseActivity extends FragmentActivity {
	
	private int backInAnimationId;
    private int backOutAnimationId;

	public static ArrayList<Activity> activities = new ArrayList<Activity>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		activities.add(this);
		
		Intent intent = getIntent();
        backInAnimationId = intent.getIntExtra(Jumper.JUMPER_ANIMATION_BACK_IN, 0);
        backOutAnimationId = intent.getIntExtra(Jumper.JUMPER_ANIMATION_BACK_OUT, 0);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getStatusBarTintResource());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		activities.remove(this);
	}
	
	@Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(backInAnimationId, backOutAnimationId);
    }
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	protected int getStatusBarTintResource() {
        return R.color.color_app_theme;
    }
	
	public void exitApplication(boolean needKillProcess) {
		
		if (activities != null && activities.size() != 0) {
			for (Activity activity : activities) {
				activity.finish();
			}
			
			if (needKillProcess) {
				Process.killProcess(Process.myPid());
		        System.exit(1);
			}
		}
	}

}
