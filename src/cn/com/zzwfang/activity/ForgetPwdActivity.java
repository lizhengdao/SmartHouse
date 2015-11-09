package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPwdActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvResendCode, tvNextStep;
	
	private EditText edtPhoneNum, edtInputAuthCode;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_forget_pwd);
		
		tvBack = (TextView) findViewById(R.id.act_forget_pwd_back);
		edtPhoneNum = (EditText) findViewById(R.id.act_forget_pwd_phone_num_edt);
		tvResendCode = (TextView) findViewById(R.id.act_forget_pwd_resend_code);
		edtInputAuthCode = (EditText) findViewById(R.id.act_forget_pwd_input_auth_code_edt);
		tvNextStep = (TextView) findViewById(R.id.act_forget_pwd_next_step_tv);
		
		tvBack.setOnClickListener(this);
		tvResendCode.setOnClickListener(this);
		tvNextStep.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_forget_pwd_back:
			finish();
			break;
		case R.id.act_forget_pwd_resend_code:  //  重新发送验证码
			break;
		case R.id.act_forget_pwd_next_step_tv:  //   下一步
			break;
		}
	}
}
