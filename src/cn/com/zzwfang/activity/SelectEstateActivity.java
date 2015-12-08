package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.EstateSelectAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

/**
 * 赏金猎人  推荐客户  选择楼盘列表
 * @author MISS-万
 *
 */
public class SelectEstateActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	public static final String INTENT_ESTATE = "estate_selected";
	private TextView tvBack;
	private EditText edtKeyWords;
	private ListView lstEstate;
	
	private ArrayList<IdNameBean> estates = new ArrayList<IdNameBean>();
	private EstateSelectAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_select_estate);
		tvBack = (TextView) findViewById(R.id.act_select_estate_back);
		edtKeyWords = (EditText) findViewById(R.id.act_select_estate_keywords_edt);
		lstEstate = (ListView) findViewById(R.id.fact_select_estate_list);
		
		tvBack.setOnClickListener(this);
		
		adapter = new EstateSelectAdapter(this, estates);
		lstEstate.setAdapter(adapter);
		
		lstEstate.setOnItemClickListener(this);
		
		edtKeyWords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					getEstate();
					return true;
				}
				return false;
			}

		});
	}
	
	private void getEstate() {
		String keywords = edtKeyWords.getText().toString();
		if (TextUtils.isEmpty(keywords)) {
			ToastUtils.SHORT.toast(this, "输入小区名称或楼盘名称");
			return;
		}
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean != null) {
		    ActionImpl actionImpl = ActionImpl.newInstance(this);
	        actionImpl.getAutoCompleteEstate(cityBean.getSiteId(), keywords, 20, -1, new ResultHandlerCallback() {
	            
	            @Override
	            public void rc999(RequestEntity entity, Result result) {
	            }
	            
	            @Override
	            public void rc3001(RequestEntity entity, Result result) {
	            }
	            
	            @Override
	            public void rc0(RequestEntity entity, Result result) {
	                estates.clear();
	                ArrayList<IdNameBean> temp = (ArrayList<IdNameBean>) JSON.parseArray(result.getData(), IdNameBean.class);
	                estates.addAll(temp);
	                adapter.notifyDataSetChanged();
	            }
	        });
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_select_estate_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		IdNameBean idNameBean =  estates.get(position);
		Intent intent = new Intent();
		intent.putExtra(INTENT_ESTATE, idNameBean);
		setResult(RESULT_OK, intent);
		finish();
	}
	
}
