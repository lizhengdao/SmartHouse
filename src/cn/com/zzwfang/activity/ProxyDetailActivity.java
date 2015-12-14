package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.view.AutoDrawableTextView;

/**
 * 跟  BrokerInfoActivity 重了
 * @author doer06
 *
 */
public class ProxyDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	private AutoDrawableTextView dialTop;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_proxy_detail);
		
		tvBack = (TextView) findViewById(R.id.act_proxy_detail_back);
		dialTop = (AutoDrawableTextView) findViewById(R.id.act_proxy_detail_dial_top);
		
		tvBack.setOnClickListener(this);
		dialTop.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_proxy_detail_back:
			finish();
			break;
		case R.id.act_proxy_detail_dial_top:
			
			break;
		}
	}

}
