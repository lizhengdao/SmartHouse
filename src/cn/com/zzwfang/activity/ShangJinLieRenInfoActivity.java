package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;

public class ShangJinLieRenInfoActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_shangjinlieren_info);
		
		tvBack = (TextView) findViewById(R.id.act_shangjinlieren_info_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_shangjinlieren_info_back:
			finish();
			break;
		}
	}
}
