package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 浏览记录
 * @author lzd
 *
 */
public class BrowseRecordActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_browse_record);
		initView();
	}
	
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_browse_record_back);
		
		tvBack.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_browse_record_back:  //  返回
			finish();
			break;
		}
	}
	
}
