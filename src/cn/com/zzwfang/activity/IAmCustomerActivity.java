package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.ViewHolder;
import cn.com.zzwfang.bean.AttentionBean;
import cn.com.zzwfang.bean.MyBoughtHouseBean;
import cn.com.zzwfang.bean.MyDemandInfoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SeeHouseBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;

/**
 * 我是客户
 * @author lzd
 *
 */
public class IAmCustomerActivity extends BaseActivity implements OnClickListener {

	public static final int CODE_EDIT_PROXY_BUY_HOUSE_INFO = 100;
	
	private TextView tvBack;
	private TextView tvCourtName, tvTotalPrice, tvSquare, tvHouseType;
	private LinearLayout lltMyBuyHouseContainer;
	private LinearLayout lltSeeHouseExperienceContainer;
	private LinearLayout lltMyConcernHouseSourceContianer;
	
	/**
     * 我买的房(通过Erp线下买的)
     */
    private ArrayList<MyBoughtHouseBean> myBoughthouses = new ArrayList<MyBoughtHouseBean>();
    private ArrayList<SeeHouseBean> seeHouseExperiences = new ArrayList<SeeHouseBean>();
    private ArrayList<AttentionBean> attentions = new ArrayList<AttentionBean>();
    
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
		getMyDemandInfo();
		getMyBoughtHouses();
		seeHouseExperience();
		getMyAttentionList();
	}
	
	private void initView() {
		setContentView(R.layout.act_i_am_customer);
		
		tvBack = (TextView) findViewById(R.id.act_i_am_customer_back);
		tvCourtName = (TextView) findViewById(R.id.act_i_am_customer_court_name);
		tvTotalPrice = (TextView) findViewById(R.id.act_i_am_customer_total_price);
		tvSquare = (TextView) findViewById(R.id.act_i_am_customer_square);
		tvHouseType = (TextView) findViewById(R.id.act_i_am_customer_house_type);
		
		lltMyBuyHouseContainer = (LinearLayout) findViewById(R.id.act_i_am_customer_already_buy_container);
		lltSeeHouseExperienceContainer = (LinearLayout) findViewById(R.id.act_i_am_customer_buy_house_experence_container);
		lltMyConcernHouseSourceContianer = (LinearLayout) findViewById(R.id.act_i_am_customer_concern_container);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_i_am_customer_back:
			finish();
			break;
		}
	}
	
	private void rendUI(MyDemandInfoBean myDemandInfoBean) {
		if (myDemandInfoBean != null) {
			tvCourtName.setText(myDemandInfoBean.getEstateName());
			tvTotalPrice.setText(myDemandInfoBean.getMinPrice() + "万元  至  " + myDemandInfoBean.getMaxPrice() + "万元");
			tvSquare.setText(myDemandInfoBean.getSquareMin() + "㎡  至  " + myDemandInfoBean.getSquareMax() + "㎡");
			tvHouseType.setText(myDemandInfoBean.getHouseType());
		}
	}
	
	/**
	 * 获取自己委托买房信息
	 */
	private void getMyDemandInfo() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.getMyDemandInfo(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				MyDemandInfoBean myDemandInfoBean = JSON.parseObject(result.getData(), MyDemandInfoBean.class);
				if (myDemandInfoBean == null) {  // 跳转委托买房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jumpForResult(IAmCustomerActivity.this, IWantBuyHouseActivity.class, CODE_EDIT_PROXY_BUY_HOUSE_INFO);
				} else {
					rendUI(myDemandInfoBean);
				}
				
			}
		});
	}
	
	private void refreshMyBuyHouseUI() {
	    if (myBoughthouses != null && myBoughthouses.size() > 0) {
	        lltMyBuyHouseContainer.removeAllViews();
	        for (final MyBoughtHouseBean myBoughtHouseBean : myBoughthouses) {
	            LinearLayout lltMyBuyHouse = (LinearLayout) View.inflate(this, R.layout.view_my_buy_houses_item, null);
	            ImageView photo = (ImageView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_photo);
	            TextView tvTitle = (TextView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_title);
	            TextView tvDesc = (TextView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_desc);
	            TextView tvPrice = (TextView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_money);
	            AutoDrawableTextView action1 = (AutoDrawableTextView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_action1);
	            AutoDrawableTextView action2 = (AutoDrawableTextView) lltMyBuyHouse.findViewById(R.id.view_my_buy_house_action2);
	            
	            tvTitle.setText(myBoughtHouseBean.getTitle());
	            String desc = "";
	            if (!TextUtils.isEmpty(myBoughtHouseBean.getTypeF())) {
	                desc += myBoughtHouseBean.getTypeF() + "室";
	            }
	            if (!TextUtils.isEmpty(myBoughtHouseBean.getTypeT())) {
	                desc += myBoughtHouseBean.getTypeT() + "厅    ";
	            }
	            if (!TextUtils.isEmpty(myBoughtHouseBean.getSquare())) {
	                desc += myBoughtHouseBean.getSquare() + "㎡   ";
	            }
	            if (!TextUtils.isEmpty(myBoughtHouseBean.getDirection())) {
	                desc += myBoughtHouseBean.getDirection();
	            }
	            tvDesc.setText(desc);
	            if (!TextUtils.isEmpty(myBoughtHouseBean.getPrice())) {
	                tvPrice.setText(myBoughtHouseBean.getPrice() + "万");
	            }
	            
	            String url = myBoughtHouseBean.getPhoto();
	            ImageAction.displayImage(url, photo);
	            
	            lltMyBuyHouseContainer.addView(lltMyBuyHouse);
	            
	            action1.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .putString(QueryAfterSaleActivity.INTENT_ESTATE_NAME, myBoughtHouseBean.getTitle())
                        .putString(QueryAfterSaleActivity.INTENT_HOUSE_SOURCE_ID, myBoughtHouseBean.getId())
                        .jump(IAmCustomerActivity.this, QueryAfterSaleActivity.class);
                    }
                });
	            
	            action2.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                     // 跳财务明细
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .putString(IncomeStatementActivity.INTENT_HOUSE_SOURCE_ID, myBoughtHouseBean.getId())
                        .jump(IAmCustomerActivity.this, IncomeStatementActivity.class);
                    }
                });
	        }
	    }
	}
	
	/**
     * 获取 我的购房数据(我的购房)
     * @param isRefresh
     */
    private void getMyBoughtHouses() {
        String userId = ContentUtils.getUserId(this);
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getMyBoughtHouses(userId, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<MyBoughtHouseBean> temp = (ArrayList<MyBoughtHouseBean>) JSON.parseArray(result.getData(), MyBoughtHouseBean.class);
                myBoughthouses.clear();
                if (temp != null) {
                    myBoughthouses.addAll(temp);
                }
                refreshMyBuyHouseUI();
            }
        });
    }
    
    private void refreshSeeHouseExperienceUI() {
        if (seeHouseExperiences != null && seeHouseExperiences.size() > 0) {
            lltSeeHouseExperienceContainer.removeAllViews();
            
            for (final SeeHouseBean seeHouseBean : seeHouseExperiences) {
                LinearLayout lltSeeHouseItem = (LinearLayout) View.inflate(this, R.layout.view_see_house_experience_item, null);
                lltSeeHouseExperienceContainer.addView(lltSeeHouseItem);
                
                ImageView photo = (ImageView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_photo);
                TextView tvTitle = (TextView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_title);
                TextView tvDesc = (TextView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_desc);
                TextView tvMoney = (TextView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_money);
                TextView tvDate = (TextView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_date);
                TextView tvSeeRecord = (TextView) lltSeeHouseItem.findViewById(R.id.view_see_house_experience_action1);
                
                tvTitle.setText(seeHouseBean.getTitle());
                String desc = "";
                if (!TextUtils.isEmpty(seeHouseBean.getHouseType())) {
                    desc += seeHouseBean.getHouseType();
                }
                if (!TextUtils.isEmpty(seeHouseBean.getSquare())) {
                    desc += "   " + seeHouseBean.getSquare() + "㎡";
                }
                tvDesc.setText(desc);
                if (!TextUtils.isEmpty(seeHouseBean.getPrice())) {
                    tvMoney.setText(seeHouseBean.getPrice() + "万");
                }
                if (!TextUtils.isEmpty(seeHouseBean.getDate())) {
                    tvDate.setText(seeHouseBean.getDate());
                }
                
                ImageAction.displayImage(seeHouseBean.getPhoto(), photo);
                tvSeeRecord.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // 跳带看记录
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .putString(SeeHouseRecordActivity.INTENT_HOUSE_SOURCE_ID, seeHouseBean.getId())
                        .jump(IAmCustomerActivity.this, SeeHouseRecordActivity.class);
                    }
                });
            }
            
            
        }
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
                ArrayList<SeeHouseBean> temp = (ArrayList<SeeHouseBean>) JSON.parseArray(result.getData(), SeeHouseBean.class);
                seeHouseExperiences.addAll(temp);
                refreshSeeHouseExperienceUI();
            }
        });
    }
	
	private void refreshMyAttentionUI() {
	    lltMyConcernHouseSourceContianer.removeAllViews();
	    if (attentions != null && attentions.size() > 0) {
	        for (final AttentionBean attentionBean : attentions) {
	            final LinearLayout lltMyConcernItem = (LinearLayout) View.inflate(this, R.layout.adapter_my_attention, null);
	            lltMyConcernHouseSourceContianer.addView(lltMyConcernItem);
	            
	            ImageView img = (ImageView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_img);
	            TextView tvTitle = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_title);
	            TextView tvDesc = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_desc);
	            TextView tvEstateName = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_estate_name);
	            TextView tvPrice = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_price);
	            TextView tvOnLineConsult = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_online_consult);
	            TextView tvCancelCollection = (TextView) lltMyConcernItem.findViewById(R.id.adapter_my_attention_cancel_collection);
	            
	            if (!TextUtils.isEmpty(attentionBean.getTitel())) {
	                tvTitle.setText(attentionBean.getTitel());
	            }
	            if (!TextUtils.isEmpty(attentionBean.getEstName())) {
	                tvEstateName.setText(attentionBean.getEstName());
	            }
	            if (!TextUtils.isEmpty(attentionBean.getPrice())) {
	                tvPrice.setText(attentionBean.getPrice() + "万");
	            }
	            
	            String desc = attentionBean.getTypeF() + "室" + attentionBean.getTypeT() + "厅    "
	            + attentionBean.getSquare() + "㎡   " + attentionBean.getDiretion();
	            tvDesc.setText(desc);
	            
	            ImageAction.displayImage(attentionBean.getPhoto(), img);
	            tvCancelCollection.setOnClickListener(new OnClickListener() {
	                
	                @Override
	                public void onClick(View v) {
	                    ActionImpl actionImpl = ActionImpl.newInstance(IAmCustomerActivity.this);
	                    String userId = ContentUtils.getUserId(IAmCustomerActivity.this);
	                    
	                    actionImpl.deleteCollection(userId, attentionBean.getPropertyId(), new ResultHandlerCallback() {
	                        
	                        @Override
	                        public void rc999(RequestEntity entity, Result result) {
	                            
	                        }
	                        
	                        @Override
	                        public void rc3001(RequestEntity entity, Result result) {
	                            
	                        }
	                        
	                        @Override
	                        public void rc0(RequestEntity entity, Result result) {
	                            attentions.remove(attentionBean);
	                            lltMyConcernHouseSourceContianer.removeView(lltMyConcernItem);
	                        }
	                    });
	                }
	            });
	            
	            tvOnLineConsult.setOnClickListener(new OnClickListener() {
	                
	                @Override
	                public void onClick(View v) {
	                    
	                    Jumper.newJumper()
	                    .setAheadInAnimation(R.anim.activity_push_in_right)
	                    .setAheadOutAnimation(R.anim.activity_alpha_out)
	                    .setBackInAnimation(R.anim.activity_alpha_in)
	                    .setBackOutAnimation(R.anim.activity_push_out_right)
	                    .putString(ChatActivity.INTENT_MESSAGE_TO_ID, attentionBean.getAgentId())
//	                    .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, "经纪人")
	                    .jump(IAmCustomerActivity.this, ChatActivity.class);
	                }
	            });
	            
	        }
	    }
	}
    
    private void getMyAttentionList() {
        
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        String userId = ContentUtils.getUserId(this);
        actionImpl.getAttentionList(userId, 10000, 1, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<AttentionBean> temp = (ArrayList<AttentionBean>) JSON.parseArray(result.getData(), AttentionBean.class);
                attentions.clear();
                attentions.addAll(temp);
                refreshMyAttentionUI();
            }
        });
    }
	
	
}
