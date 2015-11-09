package cn.com.zzwfang.activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;


/**
 * 消息
 * @author lzd
 *
 */
public class MessageActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_message);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_message_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_message_back:   //  返回
			finish();
			break;
		}
	}
}
