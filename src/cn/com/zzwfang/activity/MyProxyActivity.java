package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

public class MyProxyActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvSellHouse, tvIBuyHouse;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_my_proxy);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_my_proxy_back);
		tvSellHouse = (TextView) findViewById(R.id.act_my_proxy_sell_house);
		tvIBuyHouse = (TextView) findViewById(R.id.act_my_proxy_i_buy_house);
		
		tvBack.setOnClickListener(this);
		tvSellHouse.setOnClickListener(this);
		tvIBuyHouse.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_proxy_back:  //  返回
			finish();
			break;
		case R.id.act_my_proxy_sell_house:  //委托售房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, ProxySellHouseActivity.class);
			break;
		case R.id.act_my_proxy_i_buy_house:  // 我要买房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, IWantBuyHouseActivity.class);
			break;
		}
	}
	
	
}
