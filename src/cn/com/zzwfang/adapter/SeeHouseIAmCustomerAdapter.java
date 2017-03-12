package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.SeeHouseRecordActivity;
import cn.com.zzwfang.bean.SeeHouseBean;
import cn.com.zzwfang.util.Jumper;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SeeHouseIAmCustomerAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<SeeHouseBean> seeHouseExperiences;
	
	
	public SeeHouseIAmCustomerAdapter(Context context) {
		this.context = context;
		seeHouseExperiences = new ArrayList<SeeHouseBean>();
	}
	
	public void refreshData(ArrayList<SeeHouseBean> data) {
		seeHouseExperiences.clear();
		seeHouseExperiences.addAll(data);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (seeHouseExperiences == null) {
			return 0;
		}
		return seeHouseExperiences.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return seeHouseExperiences.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.view_see_house_experience_item, null);
		}
		
		final SeeHouseBean seeHouseBean = seeHouseExperiences.get(position);
		ImageView photo = (ImageView) convertView.findViewById(R.id.view_see_house_experience_photo);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.view_see_house_experience_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.view_see_house_experience_desc);
        TextView tvMoney = (TextView) convertView.findViewById(R.id.view_see_house_experience_money);
        TextView tvDate = (TextView) convertView.findViewById(R.id.view_see_house_experience_date);
        TextView tvSeeRecord = (TextView) convertView.findViewById(R.id.view_see_house_experience_action1);
        
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
                .jump((BaseActivity)context, SeeHouseRecordActivity.class);
            }
        });
		
		
		return convertView;
	}

}
