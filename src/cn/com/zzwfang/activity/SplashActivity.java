package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.util.AppUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ResLoader;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

	private Handler handler;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_splash);
		
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (AppUtils.isFirstLuanch(SplashActivity.this)) {
					AppUtils.createShortcut(SplashActivity.this,
							ResLoader.loadString(SplashActivity.this, R.string.app_name));
					Jumper.newJumper()
		            .setAheadInAnimation(R.anim.activity_alpha_appear_in)
		            .setAheadOutAnimation(R.anim.activity_alpha_out)
		            .jump(SplashActivity.this, FirstActivity.class);
					finish();
				} else {
					Jumper.newJumper()
		            .setAheadInAnimation(R.anim.activity_alpha_appear_in)
		            .setAheadOutAnimation(R.anim.activity_alpha_out)
		            .jump(SplashActivity.this, LoginActivity.class);
				}
			}
		}, 800);
	}
}
