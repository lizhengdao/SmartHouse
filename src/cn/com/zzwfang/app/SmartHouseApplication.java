package cn.com.zzwfang.app;

import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.config.AppConfiger;
import android.app.Application;

public class SmartHouseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AppConfiger.registContext(getApplicationContext());
		ImageAction.init(getApplicationContext());
	}
}
