package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 房贷计算器
 * @author lzd
 *
 */
public class MortgageCalculatorActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_mortgage_calculator);
		
		initView();
	}
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_mortgage_calculator_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_mortgage_calculator_back:  //  返回
			finish();
			break;
		}
	}
}
