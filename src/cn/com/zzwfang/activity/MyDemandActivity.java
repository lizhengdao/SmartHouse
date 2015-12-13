package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

/**
 * 我的需求  (这个页面现在没用了)
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
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyDemandInfoActivity.class);
			break;
		case R.id.act_my_demand_advised_house_resources_flt:  //  推荐房源
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, HousesRecommendedToMeActivity.class);
			break;
		case R.id.act_my_demand_my_buy_house_flt:   //  我的购房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyBoughtHousesActivity.class);
			break;
		}
	}
}
