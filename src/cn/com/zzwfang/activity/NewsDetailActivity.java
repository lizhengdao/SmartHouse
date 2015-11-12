package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_NEWS_TYPE_ID = "intent_news_type_id";
	
	private TextView tvBack;
	
	private String newsTypeId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		newsTypeId = getIntent().getStringExtra(INTENT_NEWS_TYPE_ID);
		initView();
		getNewsDetail(newsTypeId);
	}
	
	private void initView() {
		setContentView(R.layout.act_news_detail);
		
		tvBack = (TextView) findViewById(R.id.act_news_detail_back);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_news_detail_back:
			finish();
			break;
		}
	}
	
	private void getNewsDetail(String newsTypeId) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewsDetail(newsTypeId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				
			}
		});
	}
}
