package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.FeeHunterProgressDetailActivity;
import cn.com.zzwfang.activity.IncomeStatementActivity;
import cn.com.zzwfang.bean.MyBoughtHouseBean;
import cn.com.zzwfang.util.Jumper;

public class MyBoughtHouseAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<MyBoughtHouseBean> houses;
	
	public MyBoughtHouseAdapter(Context context, ArrayList<MyBoughtHouseBean> houses) {
		this.context = context;
		this.houses = houses;
	}
	@Override
	public int getCount() {
		if (houses == null) {
			return 0;
		}
		return houses.size();
	}

	@Override
	public Object getItem(int position) {
		return houses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_my_bought_house, null);
		}
		
		final MyBoughtHouseBean myBoughtHouseBean = houses.get(position);
		
		ImageView img = ViewHolder.get(convertView, R.id.adapter_my_bought_house_img);
		TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_my_bought_house_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_my_bought_house_desc);
		TextView tvDate = ViewHolder.get(convertView, R.id.adapter_my_bought_house_date);
		TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_my_bought_house_price);
		TextView tvProgress = ViewHolder.get(convertView, R.id.adapter_my_bought_house_progress_check);
		
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
		if (!TextUtils.isEmpty(myBoughtHouseBean.getPublishDate())) {
			tvDate.setText(myBoughtHouseBean.getPublishDate());
		}
		if (!TextUtils.isEmpty(myBoughtHouseBean.getPrice())) {
			tvPrice.setText(myBoughtHouseBean.getPrice() + "万");
		}
		
		String url = myBoughtHouseBean.getPhoto();
		ImageAction.displayImage(url, img);
		
		// 我的需求 -> 我的购房 -> 财务明细 （收支明细）
		TextView tvFinicialDetail = (TextView) convertView.findViewById(R.id.adapter_my_bought_house_progress_finacial_detail);
		tvFinicialDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .putString(IncomeStatementActivity.INTENT_HOUSE_SOURCE_ID, myBoughtHouseBean.getId())
		        .jump((BaseActivity)context, IncomeStatementActivity.class);
			}
		});
		
		// 进度查询
		tvProgress.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(FeeHunterProgressDetailActivity.INTENT_HOUSE_SOURCE_ID, myBoughtHouseBean.getId())
                .jump((BaseActivity)context, FeeHunterProgressDetailActivity.class);
            }
        });
		
		
		return convertView;
	}

}
