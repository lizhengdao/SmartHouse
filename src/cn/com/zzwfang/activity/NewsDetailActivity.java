package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.NewsDetailBean;
import cn.com.zzwfang.bean.NewsItemBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_NEWS_TYPE_ID = "intent_news_type_id";
	
	public static final String INTENT_NEWS_DATA = "intent_news_data";
	
	private TextView tvBack;
	private WebView webView;
	
//	private String newsTypeId;
	
	private NewsItemBean newsItemBean;
	
	private NewsDetailBean newsDetailBean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		newsTypeId = getIntent().getStringExtra(INTENT_NEWS_TYPE_ID);
		newsItemBean = (NewsItemBean) getIntent().getSerializableExtra(INTENT_NEWS_DATA);
		initView();
//		getNewsDetail(newsTypeId);
		if (newsItemBean != null) {
		    getNewsDetail(newsItemBean.getId());
		}
	}
	
	private void initView() {
		setContentView(R.layout.act_news_detail);
		tvBack = (TextView) findViewById(R.id.act_news_detail_back);
		webView = (WebView) findViewById(R.id.act_news_detail_webview);
		WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
//		if (newsItemBean != null) {
//			webView.loadDataWithBaseURL(null, newsItemBean.getContent(), "text/html", "utf-8", null);
//		}
		
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
			     
			    newsDetailBean = JSON.parseObject(result.getData(), NewsDetailBean.class);
			    if (newsDetailBean != null) {
			        webView.loadDataWithBaseURL(null, newsDetailBean.getContent(), "text/html", "utf-8", null);
			    }
			}

		});
	}
}
