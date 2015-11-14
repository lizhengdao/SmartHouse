package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

public class FeedbackActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCommt;
	private EditText edtContent, edtPhone;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_feedback);
		
		initView();
	}
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_feedback_back);
		
		edtContent = (EditText) findViewById(R.id.act_feedback_content_edt);
		edtPhone = (EditText) findViewById(R.id.act_feedback_phone_edt);
		tvCommt = (TextView) findViewById(R.id.act_feedback_commit);
		
		tvBack.setOnClickListener(this);
		tvCommt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_feedback_back:
			finish();
			break;
		case R.id.act_feedback_commit:
			commitFeedBck();
			break;
		}
	}
	
	private void commitFeedBck() {
		String content = edtContent.getText().toString();
		String phone = edtPhone.getText().toString();
		String userId = ContentUtils.getUserId(this);
		
		if (TextUtils.isEmpty(content)) {
			ToastUtils.SHORT.toast(FeedbackActivity.this, "请输入反馈内容");
			return;
		}
		
//		if (TextUtils.isEmpty(phone)) {
//			ToastUtils.SHORT.toast(FeedbackActivity.this, "请输入电话号码");
//			return;
//		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.commitFeedback(userId, content, phone, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(FeedbackActivity.this, "反馈成功");
			}
		});
	}
}
