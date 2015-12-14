package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

/**
 * 找房神器
 * 付款方式一次性或者按揭（Type,值为1或者0）
 * @author lzd
 *
 */
public class SearchHouseArtifactActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack, tvDisposable, tvMortgage;
	
	private RadioButton rbLiveHouse, rbCommercialHouse;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_search_house_artifact);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_back);
		rbLiveHouse = (RadioButton) findViewById(R.id.act_house_artifact_live_house_rb);
		rbCommercialHouse = (RadioButton) findViewById(R.id.act_house_artifact_commercial_house_rb);
		tvDisposable = (TextView) findViewById(R.id.act_house_artifact_disposable_tv);
		tvMortgage = (TextView) findViewById(R.id.act_house_artifact_mortgage_tv);
		
		tvBack.setOnClickListener(this);
		rbLiveHouse.setOnCheckedChangeListener(this);
		rbCommercialHouse.setOnCheckedChangeListener(this);
		tvDisposable.setOnClickListener(this);
		tvMortgage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_back:  // 返回
			finish();
			break;
		case R.id.act_house_artifact_disposable_tv:  // 一次性  付款方式一次性或者按揭（Type,值为1或者0）
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactRequirementActivity.INTENT_PAY_TYPE, 1)
	        .jump(this, SearchHouseArtifactRequirementActivity.class);
			break;
		case R.id.act_house_artifact_mortgage_tv:  //  按揭贷款  付款方式一次性或者按揭（Type,值为1或者0）
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .putInt(SearchHouseArtifactRequirementActivity.INTENT_PAY_TYPE, 0)
	        .jump(this, SearchHouseArtifactRequirementActivity.class);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_house_artifact_live_house_rb:  // 住宅 
				break;
			case R.id.act_house_artifact_commercial_house_rb:  // 商业
				break;
			}
		}
	}

}
