package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ShangJinLieRenActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvRegister;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_shangjinlieren);
		
		tvBack = (TextView) findViewById(R.id.act_shangjinlieren_back);
		tvRegister = (TextView) findViewById(R.id.act_shangjinlieren_register_tv);
		
		tvBack.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_shangjinlieren_back:
			finish();
			break;
		case R.id.act_shangjinlieren_register_tv:  // 注册赏金猎人
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, FillBankCardInfoActivity.class);
			break;
		}
	}
}
