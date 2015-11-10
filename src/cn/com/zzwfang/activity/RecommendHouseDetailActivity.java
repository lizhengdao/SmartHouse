package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 推荐房源详情页
 * @author lzd
 *
 */
public class RecommendHouseDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_recommend_house_detail);
		
		tvBack = (TextView) findViewById(R.id.act_recommend_house_detail_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_recommend_house_detail_back:
			finish();
			break;
		}
	}
}
