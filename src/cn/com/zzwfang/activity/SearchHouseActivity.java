package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
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
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseTypeSelectListener;

import com.alibaba.fastjson.JSON;

public class SearchHouseActivity extends BaseActivity implements
		OnClickListener, OnHouseTypeSelectListener, OnItemClickListener {

	private TextView tvCancel, tvHouseType;
	private EditText edtKeyWords;
	private ImageView imgClearKeyWords;
	private ListView lstView;

	/**
	 * 默认新房
	 */
	private int houseType = OnHouseTypeSelectListener.HOUSE_TYPE_NEW_HOUSE;
	
	private ArrayList<IdNameBean> estates = new ArrayList<IdNameBean>();
	private EstateSelectAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		setListener();
	}

	private void initView() {
		setContentView(R.layout.act_search_house);

		tvCancel = (TextView) findViewById(R.id.act_search_house_cancel_tv);
		edtKeyWords = (EditText) findViewById(R.id.act_search_house_key_words);
		tvHouseType = (TextView) findViewById(R.id.act_search_house_house_type_tv);
		imgClearKeyWords = (ImageView) findViewById(R.id.act_search_house_clear_key_wrods);
		
		lstView = (ListView) findViewById(R.id.act_search_house_auto_complete);
		adapter = new EstateSelectAdapter(this, estates);
		lstView.setAdapter(adapter);
		
		lstView.setOnItemClickListener(this);

	}

	private void setListener() {
		tvCancel.setOnClickListener(this);
		tvHouseType.setOnClickListener(this);
		imgClearKeyWords.setOnClickListener(this);
		
		edtKeyWords.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                String keyWords = edtKeyWords.getText().toString();
                // 接口定的  0是新房，1是二手房
                if (!TextUtils.isEmpty(keyWords)) {
                    getEstate(keyWords, houseType);
                }
            }
        });

		edtKeyWords
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							String keyWords = edtKeyWords.getText().toString();
							// 接口定的  0是新房，1是二手房
							
							getEstate(keyWords, houseType);
							return true;
						}
						return false;
					}
				});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		IdNameBean idNameBean =  estates.get(position);
		switch (houseType) {
		
		case OnHouseTypeSelectListener.HOUSE_TYPE_NEW_HOUSE:  // 跳新房列表页
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putString(NewHouseActivity.INTENT_KEY_WORDS, idNameBean.getName())
            .jump(SearchHouseActivity.this, NewHouseActivity.class);
			break;
		case OnHouseTypeSelectListener.HOUSE_TYPE_SECOND_HAND_HOUSE:  // 跳二手房列表页
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putString(SecondHandHouseActivity.INTENT_KEYWORDS, idNameBean.getName())
            .jump(SearchHouseActivity.this, SecondHandHouseActivity.class);
			
			break;
		case OnHouseTypeSelectListener.HOUSE_TYPE_RENT_HOUSE:   //  跳租房列表页
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putString(RentHouseActivity.INTENT_KEY_WORDS, idNameBean.getName())
            .jump(SearchHouseActivity.this, RentHouseActivity.class);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_cancel_tv:
			finish();
			break;
		case R.id.act_search_house_house_type_tv:
			PopViewHelper.showSearchHouseTypePopWindow(this, tvHouseType, this);
			break;
		case R.id.act_search_house_clear_key_wrods: // 清除关键字
			edtKeyWords.setText("");
			break;
		}
	}

	@Override
	public void onHouseTypeSelect(int houseType, String houseTypeTxt) {
		tvHouseType.setText(houseTypeTxt);
		this.houseType = houseType;
		String keyWords = edtKeyWords.getText().toString();
		if (!TextUtils.isEmpty(keyWords)) {
		    getEstate(keyWords, houseType);
		}
	}
	
	private void getEstate(String keyWords, int type) {
		if (TextUtils.isEmpty(keyWords)) {
			ToastUtils.SHORT.toast(this, "输入小区名称或楼盘名称");
			return;
		}
		if (type == 2) { // 租房为2，但也传1
		    type = 1;
		}
		
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean != null) {
		    ActionImpl actionImpl = ActionImpl.newInstance(this);
	        actionImpl.getAutoCompleteEstate(cityBean.getSiteId(), keyWords, 20, type, new ResultHandlerCallback() {
	            
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

	

}
