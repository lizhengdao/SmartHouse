package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import android.os.Bundle;

public class FeeHunterRegisterActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_register);
	}
}
