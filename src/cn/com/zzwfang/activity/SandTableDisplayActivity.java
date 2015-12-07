package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;
import cn.com.zzwfang.R;

/**
 * 360沙盘展示
 * @author lzd
 *
 */
public class SandTableDisplayActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_SAND_TABLE_DISPLAY_URL = "sand_table_display_url";
	public static final String INTENT_ESTATE_NAME = "intent_estate_name";
	private TextView tvBack, tvTitle;
	private WebView webView;
	
	private String sandTableUrl;
	private String title;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		title = getIntent().getStringExtra(INTENT_ESTATE_NAME);
		sandTableUrl = getIntent().getStringExtra(INTENT_SAND_TABLE_DISPLAY_URL);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_sand_table_display);
		tvBack = (TextView) findViewById(R.id.act_sand_table_display_back);
		tvTitle = (TextView) findViewById(R.id.act_sand_table_display_title);
		webView = (WebView) findViewById(R.id.act_sand_table_display_webview);
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		
		tvTitle.setText(title);
		webView.loadUrl(sandTableUrl);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_sand_table_display_back:
			finish();
			break;
		}
	}
}
