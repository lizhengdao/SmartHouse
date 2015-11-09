package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 我的需求
 * @author lzd
 *
 */
public class MyDemandActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private FrameLayout myDemandFlt, advisedHouseFlt, myBuyHouseFlt;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_my_demand);
		
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_my_demand_back);
		myDemandFlt = (FrameLayout) findViewById(R.id.act_my_demand_flt);
		advisedHouseFlt = (FrameLayout) findViewById(R.id.act_my_demand_advised_house_resources_flt);
		myBuyHouseFlt = (FrameLayout) findViewById(R.id.act_my_demand_my_buy_house_flt);
		
		tvBack.setOnClickListener(this);
		myDemandFlt.setOnClickListener(this);
		advisedHouseFlt.setOnClickListener(this);
		myBuyHouseFlt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_demand_back:  // 返回
			finish();
			break;
		case R.id.act_my_demand_flt:   //  我的需求
			break;
		case R.id.act_my_demand_advised_house_resources_flt:  //  推荐房源
			break;
		case R.id.act_my_demand_my_buy_house_flt:   //  我的购房
			break;
		}
	}
}
