package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 赏金猎人   我的客户页
 * @author lzd
 *
 */
public class FeeHunterMyCustomerActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_my_customer);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_my_customer_back);
		
		tvBack.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_my_customer_back:
			finish();
			break;
		}
	}
}
