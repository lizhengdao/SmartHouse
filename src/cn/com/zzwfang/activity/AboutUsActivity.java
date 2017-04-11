package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

/**
 * 关于我们
 * @author lzd
 *
 */
public class AboutUsActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private WebView webView;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getAboutUsData();
	}
	
	private void initView() {
		setContentView(R.layout.act_about_us);
		
		tvBack = (TextView) findViewById(R.id.act_about_us_back);
		webView = (WebView) findViewById(R.id.act_about_us_webview);
		WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_about_us_back:
			finish();
			break;
		}
	}
	
	private void getAboutUsData() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getAboutUsData(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				Log.i("--->", "getAboutUsData: " + result.toString());
				webView.loadData(result.getData(), "text/html; charset=UTF-8", null);
			}
		});
	}

}
