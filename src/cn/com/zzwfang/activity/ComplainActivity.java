package cn.com.zzwfang.activity;


import android.content.Intent;
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

public class ComplainActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_COMPLAIN_TYPE = "intent_complain_type";
	public static final String INTENT_COMPLAIN_ID = "intent_complain_id";
	
	private TextView tvBack, tvCommt;
	private EditText edtContent;
	
	private String complaiType;
	private String complainId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		complaiType = intent.getStringExtra(INTENT_COMPLAIN_TYPE);
		complainId = intent.getStringExtra(INTENT_COMPLAIN_ID);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_complain);
		tvBack = (TextView) findViewById(R.id.act_complain_back);
		
		edtContent = (EditText) findViewById(R.id.act_complain_content_edt);
		tvCommt = (TextView) findViewById(R.id.act_complain_commit);
		
		tvBack.setOnClickListener(this);
		tvCommt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_complain_back:
			finish();
			break;
		case R.id.act_complain_commit:
			commitComplain();
			break;
		}
	}
	
	private void commitComplain() {
		String content = edtContent.getText().toString();
		String userId = ContentUtils.getUserId(this);
		
		if (TextUtils.isEmpty(content)) {
			ToastUtils.SHORT.toast(ComplainActivity.this, "请输入反馈内容");
			return;
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.complain(complainId, complaiType, userId, content, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(ComplainActivity.this, "投诉成功");
				finish();
			}
		});
		
	}
	

}
