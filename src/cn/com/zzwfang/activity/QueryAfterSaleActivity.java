package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.QueryAfterSaleAdapter;
import cn.com.zzwfang.bean.ClientInfoChangeBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.PathImage;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 售后查询
 * @author lzd
 *
 */
public class QueryAfterSaleActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_ESTATE_NAME = "estate_name";
	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
    private TextView tvBack;
    private TextView tvTitle;
    private PathImage avatar1, avatar2, avatar3;
    private TextView tvName1, tvName2, tvName3;
    private TextView tvComplain1, tvComplain2, tvComplain3;
    private TextView tvSupport1, tvSupport2, tvSupport3;
    private ListView listView;
    
    private QueryAfterSaleAdapter houseInfoChangeAdapter;
    
    private String estateName;
    private String houseSourceId;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        Intent intent = getIntent();
        estateName = intent.getStringExtra(INTENT_ESTATE_NAME);
        houseSourceId = intent.getStringExtra(INTENT_HOUSE_SOURCE_ID);
        initView();
        getHouseSourceInfoChange();
    }
    
    private void initView() {
        
        setContentView(R.layout.act_query_after_sale);
        tvBack = (TextView) findViewById(R.id.act_query_after_sale_back);
        tvTitle = (TextView) findViewById(R.id.act_query_after_sale_title);
        
        avatar1 = (PathImage) findViewById(R.id.act_query_after_sale_avatar1);
        avatar2 = (PathImage) findViewById(R.id.act_query_after_sale_avatar2);
        avatar3 = (PathImage) findViewById(R.id.act_query_after_sale_avatar3);
        
        tvName1 = (TextView) findViewById(R.id.act_query_after_sale_name1);
        tvName2 = (TextView) findViewById(R.id.act_query_after_sale_name2);
        tvName3 = (TextView) findViewById(R.id.act_query_after_sale_name3);
        
        tvComplain1 = (TextView) findViewById(R.id.act_query_after_sale_complain1);
        tvComplain2 = (TextView) findViewById(R.id.act_query_after_sale_complain2);
        tvComplain3 = (TextView) findViewById(R.id.act_query_after_sale_complain3);
        
        tvSupport1 = (TextView) findViewById(R.id.act_query_after_sale_support1);
        tvSupport2 = (TextView) findViewById(R.id.act_query_after_sale_support2);
        tvSupport3 = (TextView) findViewById(R.id.act_query_after_sale_support3);
        
        listView = (ListView)findViewById(R.id.act_query_after_sale_lst);
        
        if (TextUtils.isEmpty(estateName)) {
        	tvTitle.setText("售后查询");
        } else {
        	tvTitle.setText(estateName + "-售后查询");
        }
        
        
        tvBack.setOnClickListener(this);
        tvComplain1.setOnClickListener(this);
        tvComplain2.setOnClickListener(this);
        tvComplain3.setOnClickListener(this);
        tvSupport1.setOnClickListener(this);
        tvSupport2.setOnClickListener(this);
        tvSupport3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_query_after_sale_back:
            finish();
            break;
        case R.id.act_query_after_sale_complain1:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, ComplainActivity.class);
        	break;
        case R.id.act_query_after_sale_complain2:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, ComplainActivity.class);
        	break;
        case R.id.act_query_after_sale_complain3:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, ComplainActivity.class);
        	break;
        case R.id.act_query_after_sale_support1:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, SupportActivity.class);
        	break;
        case R.id.act_query_after_sale_support2:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, SupportActivity.class);
        	break;
        case R.id.act_query_after_sale_support3:
        	Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jump(this, SupportActivity.class);
        	break;
        }
    }
    
    /**
	 * 房源信息变动   跟客户信息变动返回的数据结构一样
	 */
	private void getHouseSourceInfoChange() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceInfoChange(houseSourceId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO 房源信息变动
				ArrayList<ClientInfoChangeBean> houseInfoChanges = (ArrayList<ClientInfoChangeBean>) JSON.parseArray(result.getData(), ClientInfoChangeBean.class);
				houseInfoChangeAdapter = new QueryAfterSaleAdapter(QueryAfterSaleActivity.this, houseInfoChanges);
				listView.setAdapter(houseInfoChangeAdapter);
			}
		});
	}
    
    private void seeHouseExperience() {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        String userId = ContentUtils.getUserId(this);
        actionImpl.seeHouseExperience(userId, new ResultHandlerCallback() {
            
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
