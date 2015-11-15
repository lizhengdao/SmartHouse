package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 赏金猎人   购买进度页
 * @author lzd
 *
 */
public class FeeHunterBuyProgressActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_buy_progress);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_buy_progress_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_buy_progress_back:
			finish();
			break;
		}
	}
	
}
