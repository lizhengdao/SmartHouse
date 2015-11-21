package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;

public class DealHistoryActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvTitle;
	private ListView lstDealHistory;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_deal_history);
		tvBack = (TextView) findViewById(R.id.act_deal_history_back);
		tvTitle = (TextView) findViewById(R.id.act_deal_history_title);
		lstDealHistory = (ListView) findViewById(R.id.act_deal_history_lst);
		tvBack.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_deal_history_back:
			finish();
			break;
		}
	}
}
