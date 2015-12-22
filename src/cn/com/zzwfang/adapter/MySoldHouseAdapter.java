package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.FeeHunterProgressDetailActivity;
import cn.com.zzwfang.activity.SeeHouseRecordActivity;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的 -> 我的房源  Adapter
 * @author doer06
 *
 */
public class MySoldHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<MyProxySellHouseBean> myHouses;
	
	public MySoldHouseAdapter(Context context, ArrayList<MyProxySellHouseBean> myHouses) {
		this.context = context;
		this.myHouses = myHouses;
	}
	 
	@Override
	public int getCount() {
		if (myHouses == null) {
			return 0;
		}
		return myHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return myHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			
			convertView = View.inflate(context, R.layout.adapter_my_sell_house, null);
		}
		final MyProxySellHouseBean myHouseBean = myHouses.get(position);
		
//		TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_my_house_date);
		
		ImageView img = ViewHolder.get(convertView, R.id.adapter_my_house_img);
		TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_my_house_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_my_house_desc);
		TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_my_house_price);
		AutoDrawableTextView houseProgress = ViewHolder.get(convertView, R.id.adapter_my_house_source_progress);
		AutoDrawableTextView seeHouseRecord = ViewHolder.get(convertView, R.id.adapter_my_house_source_see_house_record);
		
		tvTitle.setText(myHouseBean.getTitle());
		String desc = "";
		if (!TextUtils.isEmpty(myHouseBean.getTypeF())) {
		    desc += myHouseBean.getTypeF() + "室";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeT())) {
		    desc += myHouseBean.getTypeT() + "厅";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeW())) {
		    desc += myHouseBean.getTypeW() + "卫";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeY())) {
		    desc += myHouseBean.getTypeY() + "阳台  ";
		}
		if (!TextUtils.isEmpty(myHouseBean.getSquare())) {
		    desc += myHouseBean.getSquare() + "㎡   ";
		}
		if (!TextUtils.isEmpty(myHouseBean.getDirection())) {
		    desc += myHouseBean.getDirection();
		}
		
		tvDesc.setText(desc);
		
//		if (!TextUtils.isEmpty(myHouseBean.getPublishDate())) {
//		    tvDate.setText("接盘时间：" + myHouseBean.getPublishDate());
//		} else {
//		    tvDate.setText("接盘时间：");
//		}
		
		if (!TextUtils.isEmpty(myHouseBean.getPrice())) {
		    tvPrice.setText(myHouseBean.getPrice() + "万");
		}
		
		String url = myHouseBean.getImagePath();
		ImageAction.displayImage(url, img);
	    
	    // 跳转 房源进度
	    houseProgress.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO 房源进度
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(FeeHunterProgressDetailActivity.INTENT_HOUSE_SOURCE_ID, myHouseBean.getId())
                .jump((BaseActivity)context, FeeHunterProgressDetailActivity.class);
            }
        });
	    
	    // 带看记录
	    seeHouseRecord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 带看记录
				Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(SeeHouseRecordActivity.INTENT_HOUSE_SOURCE_ID, myHouseBean.getId())
                .jump((BaseActivity)context, SeeHouseRecordActivity.class);
				
			}
		});
		return convertView;
	}

}
