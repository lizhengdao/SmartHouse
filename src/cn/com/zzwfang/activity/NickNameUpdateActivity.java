package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 修改昵称页
 * @author lzd
 *
 */
public class NickNameUpdateActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCommit;
	
	private EditText edtNickName;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_nick_name_update);
		
		tvBack = (TextView) findViewById(R.id.act_nick_name_update_back);
		edtNickName = (EditText) findViewById(R.id.act_nick_name_edt);
		tvCommit = (TextView) findViewById(R.id.act_nick_name_commit);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nick_name_update_back:
			finish();
			break;
		case R.id.act_nick_name_commit:
			break;
		}
	}
}
