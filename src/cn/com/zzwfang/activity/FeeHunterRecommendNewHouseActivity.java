package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 赏金猎人  推荐买房卖房  推荐新房  页
 * @author lzd
 *
 */
public class FeeHunterRecommendNewHouseActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvSelectHouseResource, tvCommit;
	
	private EditText edtName, edtPhone1, edtPhone2;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_new_house);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_new_house_back);
		tvSelectHouseResource = (TextView) findViewById(R.id.act_fee_hunter_recommend_new_house_select);
		tvCommit = (TextView) findViewById(R.id.act_fee_hunter_recommend_new_house_commit);
		
		edtName = (EditText) findViewById(R.id.act_fee_hunter_recommend_new_house_name);
		edtPhone1 = (EditText) findViewById(R.id.act_fee_hunter_recommend_new_house_phone1);
		edtPhone2 = (EditText) findViewById(R.id.act_fee_hunter_recommend_new_house_phone2);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_new_house_back:
			finish();
			break;
		case R.id.act_fee_hunter_recommend_new_house_commit:   //  提交
			break;
		}
	}

	
}
