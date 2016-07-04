package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.IncomeStatementActivity;
import cn.com.zzwfang.activity.QueryAfterSaleActivity;
import cn.com.zzwfang.activity.SeeHouseRecordActivity;
import cn.com.zzwfang.activity.SelectEstateActivity;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IAmOwnerAdapter extends BaseAdapter {

    
    private Context context;
    private ArrayList<MyProxySellHouseBean> mySoldHouses;
    
    public IAmOwnerAdapter(Context context, ArrayList<MyProxySellHouseBean> mySoldHouses) {
        this.context = context;
        this.mySoldHouses = mySoldHouses;
    }
    
    @Override
    public int getCount() {
        if (mySoldHouses == null) {
            return 0;
        }
        return mySoldHouses.size();
    }

    @Override
    public Object getItem(int position) {
        return mySoldHouses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_i_am_owner, null);
        }
        
        final MyProxySellHouseBean temp = mySoldHouses.get(position);
        
        ImageView photo = ViewHolder.get(convertView, R.id.adapter_i_am_owner_photo);
        ImageView statusImg = ViewHolder.get(convertView, R.id.adapter_i_am_owner_status);
        TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_i_am_owner_title);
        TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_i_am_owner_desc);
        TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_i_am_owner_money);
        
        AutoDrawableTextView action1 = ViewHolder.get(convertView, R.id.adapter_i_am_owner_action1);
        AutoDrawableTextView action2 = ViewHolder.get(convertView, R.id.adapter_i_am_owner_action2);
        
        final int status = temp.getStatus();
        if (status == 0) { // 已售
            statusImg.setBackgroundResource(R.drawable.ic_saled);
            action1.setVisibility(View.VISIBLE);
            action2.setVisibility(View.VISIBLE);
            action1.setText("售后查询");
            action2.setText("财务明细");
            Drawable aDrawableAction1 = context.getResources().getDrawable(R.drawable.ic_see_record);
            action1.setCompoundDrawables(aDrawableAction1, null, null, null);
            action1.setBackgroundResource(R.drawable.shape_round_corner_bg_color_app_theme);
            
            Drawable aDrawableAction2 = context.getResources().getDrawable(R.drawable.ic_financial_detial_white);
            action2.setCompoundDrawables(aDrawableAction2, null, null, null);
        } else if (status == 1) { // 未售
            action1.setVisibility(View.VISIBLE);
            action2.setVisibility(View.GONE);
            action1.setText("带看记录");
            statusImg.setBackgroundResource(R.drawable.ic_unsaled);
            
            Drawable aDrawableAction1 = context.getResources().getDrawable(R.drawable.ic_see_record);
            action1.setCompoundDrawables(aDrawableAction1, null, null, null);
            action1.setBackgroundResource(R.drawable.shape_round_corner_color_f7860c);
        }
        tvTitle.setText(temp.getTitle());
        
        String desc = "";
        if (!TextUtils.isEmpty(temp.getTypeF())) {
            desc += temp.getTypeF() + "室";
        }
        if (!TextUtils.isEmpty(temp.getTypeT())) {
            desc += temp.getTypeT() + "厅";
        }
        if (!TextUtils.isEmpty(temp.getTypeW())) {
            desc += temp.getTypeW() + "卫";
        }
        if (!TextUtils.isEmpty(temp.getTypeY())) {
            desc += temp.getTypeY() + "阳台  ";
        }
        if (!TextUtils.isEmpty(temp.getSquare())) {
            desc += temp.getSquare() + "㎡   ";
        }
        if (!TextUtils.isEmpty(temp.getDirection())) {
            desc += temp.getDirection();
        }
        
        tvDesc.setText(desc);
        if (!TextUtils.isEmpty(temp.getPrice())) {
            tvPrice.setText(temp.getPrice() + "万");
        }
        
        ImageAction.displayImage(temp.getImagePath(), photo);
        
        action1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (status == 0) { // 已售
					// 跳售后查询
					Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.putString(QueryAfterSaleActivity.INTENT_ESTATE_NAME, temp.getTitle())
					.putString(QueryAfterSaleActivity.INTENT_HOUSE_SOURCE_ID, temp.getId())
					.jump((BaseActivity)context, QueryAfterSaleActivity.class);
				} else if (status == 1) { // 未售
					// 跳带看记录
	                Jumper.newJumper()
	                .setAheadInAnimation(R.anim.activity_push_in_right)
	                .setAheadOutAnimation(R.anim.activity_alpha_out)
	                .setBackInAnimation(R.anim.activity_alpha_in)
	                .setBackOutAnimation(R.anim.activity_push_out_right)
	                .putString(SeeHouseRecordActivity.INTENT_HOUSE_SOURCE_ID, temp.getId())
	                .jump((BaseActivity)context, SeeHouseRecordActivity.class);
				}
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
		        .putString(IncomeStatementActivity.INTENT_HOUSE_SOURCE_ID, temp.getId())
		        .putString(IncomeStatementActivity.INTENT_TYPE, "业主")
		        .jump((BaseActivity)context, IncomeStatementActivity.class);
			}
		});
        
        return convertView;
    }

}
