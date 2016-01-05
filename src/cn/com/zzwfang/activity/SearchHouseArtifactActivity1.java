package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

public class SearchHouseArtifactActivity1 extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	private TextView tvBuySecondHandHouse;
	private TextView tvFindRentHouse;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house_artifact1);
		
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact1_back);
		tvBuySecondHandHouse = (TextView) findViewById(R.id.act_house_artifact1_buy_second_hand_house_tv);
		tvFindRentHouse = (TextView) findViewById(R.id.act_house_artifact1_find_rent_house_tv);
		
		tvBack.setOnClickListener(this);
		tvBuySecondHandHouse.setOnClickListener(this);
		tvFindRentHouse.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact1_back:
			finish();
			break;
		case R.id.act_house_artifact1_buy_second_hand_house_tv: // 买二手房
			// Excalibur/MatchingList 这个接口新增参数：Trade    0是买房那个  1是租房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactActivity.INTENT_SEARCH_HOUSE_TRADE_TYPE, 0)
	        .jump(this, SearchHouseArtifactActivity.class);
			break;
		case R.id.act_house_artifact1_find_rent_house_tv:  // 租房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactActivity.INTENT_SEARCH_HOUSE_TRADE_TYPE, 1)
	        .jump(this, SearchHouseArtifactActivity.class);
			break;
		}
	}
	
}
