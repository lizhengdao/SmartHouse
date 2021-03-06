package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

/**
 * 我的房源  (现在此页没用了 2015-12-07)
 * @author lzd
 *
 */
public class MyHouseResourcesActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private FrameLayout myHouseFlt, seeHouseDetailFlt, msgChangesFlt;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_my_house_resources);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_my_house_resources_back);
		myHouseFlt = (FrameLayout) findViewById(R.id.act_my_house_resources_my_house);
		seeHouseDetailFlt = (FrameLayout) findViewById(R.id.act_my_house_resources_see_house_detail);
		msgChangesFlt = (FrameLayout) findViewById(R.id.act_my_house_resources_msg_changes);
		
		tvBack.setOnClickListener(this);
		myHouseFlt.setOnClickListener(this);
		seeHouseDetailFlt.setOnClickListener(this);
		msgChangesFlt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_house_resources_back:  //  返回
			finish();
			break;
		case R.id.act_my_house_resources_my_house:   // 我的房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyHouseListActivity.class);
			break;
		case R.id.act_my_house_resources_see_house_detail:  //  看房明细
			break;
		case R.id.act_my_house_resources_msg_changes:   //  信息变动
			break;
		}
	}

}
