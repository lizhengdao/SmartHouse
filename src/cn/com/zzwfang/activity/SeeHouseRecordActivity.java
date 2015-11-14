package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.SeeHouseRecordAdapter;
import cn.com.zzwfang.bean.InqFollowListBean;

public class SeeHouseRecordActivity extends BaseActivity implements OnClickListener,
OnItemClickListener {

	public static final String INTENT_RECORD = "intent_record";
	
	private TextView tvBack, tvRecentRecord;
	
	private ListView lstRecord;
	
	private SeeHouseRecordAdapter adapter;
	
	private ArrayList<InqFollowListBean> inqFollowList;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		inqFollowList = (ArrayList<InqFollowListBean>) getIntent().getSerializableExtra(INTENT_RECORD);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_see_house_record);
		
		tvBack = (TextView) findViewById(R.id.act_see_house_record_back);
		lstRecord = (ListView) findViewById(R.id.act_see_house_record_lst);
		tvRecentRecord = (TextView) findViewById(R.id.act_see_house_record_recent);
		
		int times = 0;
		if (inqFollowList != null) {
			times = inqFollowList.size();
		}
		String str1 = "<font color=#000000>最近看房记录为</font>";
		String str2 = "<font color=#de6843>" + times + "</font>";
		String str3 = "<font color=#000000>次</font>";
		
		tvRecentRecord.setText(Html.fromHtml(str1 + str2 + str3));
		
		adapter = new SeeHouseRecordAdapter(this, inqFollowList);
		lstRecord.setAdapter(adapter);
		tvBack.setOnClickListener(this);
		lstRecord.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_see_house_record_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		InqFollowListBean temp = inqFollowList.get(position);
		String phone = temp.getTel();
		if (!TextUtils.isEmpty(phone)) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phone));
			startActivity(intent);
		}
	}
}
