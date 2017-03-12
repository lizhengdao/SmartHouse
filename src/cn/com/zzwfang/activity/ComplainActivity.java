package cn.com.zzwfang.activity;


import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ComplainLabelAdapter;
import cn.com.zzwfang.bean.ComplainLabelBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

public class ComplainActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_COMPLAIN_TYPE = "intent_complain_type";
	public static final String INTENT_COMPLAIN_ID = "intent_complain_id";
	public static final String INTENT_COMPLAIN_PERSON = "intent_complain_person";
	
	private TextView tvBack, tvTitle, tvCommt;
	private EditText edtContent;
	private GridView gridLabels;
	private ComplainLabelAdapter labelAdapter;
	
	private String complaiType;
	private String complainId;
	private String complainWho;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		complaiType = intent.getStringExtra(INTENT_COMPLAIN_TYPE);
		complainId = intent.getStringExtra(INTENT_COMPLAIN_ID);
		complainWho= intent.getStringExtra(INTENT_COMPLAIN_PERSON);
		
		initView();
		getComplainLabels();
	}
	
	private void initView() {
		setContentView(R.layout.act_complain);
		tvBack = (TextView) findViewById(R.id.act_complain_back);
		tvTitle = (TextView) findViewById(R.id.act_complain_title);
		
		edtContent = (EditText) findViewById(R.id.act_complain_content_edt);
		tvCommt = (TextView) findViewById(R.id.act_complain_commit);
		
		gridLabels = (GridView) findViewById(R.id.grid_complain_label);
		
		if (!TextUtils.isEmpty(complainWho)) {
			tvTitle.setText("投诉" + complainWho);
		} else {
			tvTitle.setText("投诉");
		}
		
		labelAdapter = new ComplainLabelAdapter(this);
		gridLabels.setAdapter(labelAdapter);
		
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
	
	private void getComplainLabels() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getComplainLabels(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
//				Log.i("--->", "getComplainLabels result: " + result.getData());
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
//				Log.i("--->", "getComplainLabels result: " + result.getData());
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
//				Log.i("--->", "getComplainLabels result: " + result.getData());
				
				ArrayList<ComplainLabelBean> complainLabels = (ArrayList<ComplainLabelBean>) JSON.parseArray(result.getData(), ComplainLabelBean.class);
				if (complainLabels != null) {
					labelAdapter.refreshData(complainLabels);
				}
			}
		});
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
