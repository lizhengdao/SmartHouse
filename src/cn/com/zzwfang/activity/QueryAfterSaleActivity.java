package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.QueryAfterSaleAdapter;
import cn.com.zzwfang.bean.ClientInfoChangeBean;
import cn.com.zzwfang.bean.PrpChangeBean;
import cn.com.zzwfang.bean.PrpChangeEmployeBean;
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
import android.widget.LinearLayout;
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
    
    private LinearLayout lltEmploye1, lltEmploye2, lltEmploye3;
    private View line1, line2, line3;
    
    private QueryAfterSaleAdapter houseInfoChangeAdapter;
    
    private String estateName;
    private String houseSourceId;
    private PrpChangeBean prpChangeBean;
    private ArrayList<PrpChangeEmployeBean> employes;
    
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
        
        lltEmploye1 = (LinearLayout) findViewById(R.id.act_query_after_sale_employe1);
        lltEmploye2 = (LinearLayout) findViewById(R.id.act_query_after_sale_employe2);
        lltEmploye3 = (LinearLayout) findViewById(R.id.act_query_after_sale_employe3);
        
        line1 = findViewById(R.id.act_query_after_sale_line1);
        line2 = findViewById(R.id.act_query_after_sale_line2);
        line3 = findViewById(R.id.act_query_after_sale_line3);
        
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
        	if (employes != null) {
        		PrpChangeEmployeBean employ1 = employes.get(0);
        		if (employ1 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(ComplainActivity.INTENT_COMPLAIN_ID, employ1.getId())
                    .putString(ComplainActivity.INTENT_COMPLAIN_TYPE, "3")
        			.jump(this, ComplainActivity.class);
        		}
        	}
        	
        	break;
        case R.id.act_query_after_sale_complain2:
        	if (employes != null) {
        		PrpChangeEmployeBean employ2 = employes.get(1);
        		if (employ2 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(ComplainActivity.INTENT_COMPLAIN_ID, employ2.getId())
                    .putString(ComplainActivity.INTENT_COMPLAIN_TYPE, "3")
        			.jump(this, ComplainActivity.class);
        		}
        	}
        	break;
        case R.id.act_query_after_sale_complain3:
        	if (employes != null) {
        		PrpChangeEmployeBean employ3 = employes.get(2);
        		if (employ3 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(ComplainActivity.INTENT_COMPLAIN_ID, employ3.getId())
                    .putString(ComplainActivity.INTENT_COMPLAIN_TYPE, "3")
        			.jump(this, ComplainActivity.class);
        		}
        	}
        	break;
        case R.id.act_query_after_sale_support1:
        	if (employes != null) {
        		PrpChangeEmployeBean employ11 = employes.get(0);
        		if (employ11 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(SupportActivity.INTENT_SUPPORT_ID, employ11.getId())
                    .putInt(SupportActivity.INTENT_SUPPORT_TYPE, 3)
        			.jump(this, SupportActivity.class);
        		}
        	}
        	break;
        case R.id.act_query_after_sale_support2:
        	if (employes != null) {
        		PrpChangeEmployeBean employ21 = employes.get(1);
        		if (employ21 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(SupportActivity.INTENT_SUPPORT_ID, employ21.getId())
                    .putInt(SupportActivity.INTENT_SUPPORT_TYPE, 3)
        			.jump(this, SupportActivity.class);
        		}
        	}
        	break;
        case R.id.act_query_after_sale_support3:
        	if (employes != null) {
        		PrpChangeEmployeBean employ31 = employes.get(2);
        		if (employ31 != null) {
        			Jumper.newJumper()
        			.setAheadInAnimation(R.anim.activity_push_in_right)
        			.setAheadOutAnimation(R.anim.activity_alpha_out)
        			.setBackInAnimation(R.anim.activity_alpha_in)
        			.setBackOutAnimation(R.anim.activity_push_out_right)
        			.putString(SupportActivity.INTENT_SUPPORT_ID, employ31.getId())
                    .putInt(SupportActivity.INTENT_SUPPORT_TYPE, 3)
        			.jump(this, SupportActivity.class);
        		}
        	}
        	break;
        }
    }
    
    /**
	 * 房源信息变动   跟客户信息变动返回的数据结构一样
	 */
	private void getHouseSourceInfoChange() {
	    
	    /**
         * 用户类型 0经济人，1普通会员，2赏金猎人
         */
        int userType = ContentUtils.getUserType(this);
        boolean isSale = false;
        if (userType == 1) {
            isSale = true;
        }
        
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceInfoChange(houseSourceId, isSale, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO 房源信息变动
				prpChangeBean = JSON.parseObject(result.getData(), PrpChangeBean.class);
				if (prpChangeBean != null) {
					houseInfoChangeAdapter = new QueryAfterSaleAdapter(QueryAfterSaleActivity.this, prpChangeBean.getFollow());
					listView.setAdapter(houseInfoChangeAdapter);
					employes = prpChangeBean.getEmployes();
				}
				
				refreshEmployeUI();
			}
		});
	}
	
	private void refreshEmployeUI() {
		if (prpChangeBean != null) {
			ArrayList<PrpChangeEmployeBean> employes = prpChangeBean.getEmployes();
			if (employes != null && employes.size() > 0) {
				int size = employes.size();
				switch (size) {
				case 1:
					lltEmploye1.setVisibility(View.VISIBLE);
					lltEmploye2.setVisibility(View.GONE);
					lltEmploye3.setVisibility(View.GONE);
					line1.setVisibility(View.VISIBLE);
					line2.setVisibility(View.GONE);
					line3.setVisibility(View.GONE);
					PrpChangeEmployeBean prpChangeEmployeBean11 = employes.get(0);
					ImageAction.displayAvatar(prpChangeEmployeBean11.getPic(), avatar1);
					tvName1.setText("员工:" + prpChangeEmployeBean11.getName());
					break;
				case 2:
					lltEmploye1.setVisibility(View.VISIBLE);
					lltEmploye2.setVisibility(View.VISIBLE);
					lltEmploye3.setVisibility(View.GONE);
					line1.setVisibility(View.VISIBLE);
					line2.setVisibility(View.VISIBLE);
					line3.setVisibility(View.GONE);
					
					PrpChangeEmployeBean prpChangeEmployeBean21 = employes.get(0);
					ImageAction.displayAvatar(prpChangeEmployeBean21.getPic(), avatar1);
					tvName1.setText("员工:" + prpChangeEmployeBean21.getName());
					
					PrpChangeEmployeBean prpChangeEmployeBean22 = employes.get(1);
					ImageAction.displayAvatar(prpChangeEmployeBean22.getPic(), avatar2);
					tvName2.setText("员工:" + prpChangeEmployeBean22.getName());
					
					break;
				case 3:
					lltEmploye1.setVisibility(View.VISIBLE);
					lltEmploye2.setVisibility(View.VISIBLE);
					lltEmploye3.setVisibility(View.VISIBLE);
					line1.setVisibility(View.VISIBLE);
					line2.setVisibility(View.VISIBLE);
					line3.setVisibility(View.VISIBLE);
					
					PrpChangeEmployeBean prpChangeEmployeBean31 = employes.get(0);
					ImageAction.displayAvatar(prpChangeEmployeBean31.getPic(), avatar1);
					tvName1.setText("员工:" + prpChangeEmployeBean31.getName());
					
					PrpChangeEmployeBean prpChangeEmployeBean32 = employes.get(1);
					ImageAction.displayAvatar(prpChangeEmployeBean32.getPic(), avatar2);
					tvName2.setText("员工:" + prpChangeEmployeBean32.getName());
					
					PrpChangeEmployeBean prpChangeEmployeBean33 = employes.get(2);
					ImageAction.displayAvatar(prpChangeEmployeBean33.getPic(), avatar3);
					tvName3.setText("员工:" + prpChangeEmployeBean33.getName());
					break;
					default:
						lltEmploye1.setVisibility(View.VISIBLE);
						lltEmploye2.setVisibility(View.VISIBLE);
						lltEmploye3.setVisibility(View.VISIBLE);
						line1.setVisibility(View.VISIBLE);
						line2.setVisibility(View.VISIBLE);
						line3.setVisibility(View.VISIBLE);
						
						PrpChangeEmployeBean prpChangeEmployeBean41 = employes.get(0);
						ImageAction.displayAvatar(prpChangeEmployeBean41.getPic(), avatar1);
						tvName1.setText("员工:" + prpChangeEmployeBean41.getName());
						
						PrpChangeEmployeBean prpChangeEmployeBean42 = employes.get(1);
						ImageAction.displayAvatar(prpChangeEmployeBean42.getPic(), avatar2);
						tvName2.setText("员工:" + prpChangeEmployeBean42.getName());
						
						PrpChangeEmployeBean prpChangeEmployeBean43 = employes.get(2);
						ImageAction.displayAvatar(prpChangeEmployeBean43.getPic(), avatar3);
						tvName3.setText("员工:" + prpChangeEmployeBean43.getName());
						break;
				}
			} else {
				lltEmploye1.setVisibility(View.GONE);
				lltEmploye2.setVisibility(View.GONE);
				lltEmploye3.setVisibility(View.GONE);
			}
		}
	}
    
}
