package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.CommonProblemAdapter;
import cn.com.zzwfang.bean.CommonProblemBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

import com.alibaba.fastjson.JSON;

public class CommonProblemsActivity extends BaseActivity implements
		OnClickListener {

	private TextView tvBack;
	
	private ListView lstProblems;
	
	private ArrayList<CommonProblemBean> commonProblems = new ArrayList<CommonProblemBean>();
	
	private CommonProblemAdapter adapter;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getCommonProlemList();
	}

	private void initView() {
		setContentView(R.layout.act_common_problems);

		tvBack = (TextView) findViewById(R.id.act_common_problems_back);
		lstProblems = (ListView) findViewById(R.id.act_common_problems_list);
		
		adapter = new CommonProblemAdapter(this, commonProblems);
		lstProblems.setAdapter(adapter);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_common_problems_back:
			finish();
			break;
		}
	}
	
	private void getCommonProlemList() {
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getCommonProblemList(new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<CommonProblemBean> temp = (ArrayList<CommonProblemBean>) JSON.parseArray(result.getData(), CommonProblemBean.class);
                commonProblems.addAll(temp);
                adapter.notifyDataSetChanged();
            }
        });
	    
	}

}
