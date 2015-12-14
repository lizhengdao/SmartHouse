package cn.com.zzwfang.activity;

import android.os.Bundle;
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
 * 赏金猎人  活动规则页
 * @author lzd
 *
 */
public class FeeHunterRuleActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getRule();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_rule);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_rule_back);
		webView = (WebView) findViewById(R.id.act_fee_hunter_rule_webview);
		WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
//        String url = "http://www.zzwfang.com:7894/Other/Activity?sign=1111&timestamp=2222";
//        webView.loadUrl(url);
        
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_rule_back:
			finish();
			break;
		}
	}
	
	private void getRule() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getFeeHunterRule(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				webView.loadData(result.getData(), "text/html", "utf-8");
			}
		});
	}

}
