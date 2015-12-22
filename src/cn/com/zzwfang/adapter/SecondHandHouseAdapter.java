package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.SecondHandHouseBean;

public class SecondHandHouseAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<SecondHandHouseBean> secondHandHouses;
	
	public SecondHandHouseAdapter(Context context, ArrayList<SecondHandHouseBean> secondHandHouses) {
		this.context = context;
		this.secondHandHouses = secondHandHouses;
	}
	
	@Override
	public int getCount() {
		if (secondHandHouses == null) {
			return 0;
		}
		return secondHandHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return secondHandHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_second_hand_house, null);
		}
		SecondHandHouseBean secondHandHouseBean = secondHandHouses.get(position);
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_second_hand_house_photo);
		TextView tvTitle  = ViewHolder.get(convertView, R.id.adapter_second_hand_house_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_second_hand_house_desc);
		TextView tvEstName = ViewHolder.get(convertView, R.id.adapter_second_hand_house_est_name);
		TextView tvTotalPrice = ViewHolder.get(convertView, R.id.adapter_second_hand_house_total_price);
		TextView tvPublishTime = ViewHolder.get(convertView, R.id.adapter_second_hand_house_publish_time);
		
		ImageAction.displayImage(secondHandHouseBean.getPhoto(), imgPhoto);
		tvTitle.setText(secondHandHouseBean.getTitle());
		String desc =  "";
		if  (!TextUtils.isEmpty(secondHandHouseBean.getTypeF() + "")) {
			desc += (secondHandHouseBean.getTypeF() + "室"); 
		}
        if  (!TextUtils.isEmpty(secondHandHouseBean.getTypeT() + "")) {
        	desc += (secondHandHouseBean.getTypeT() + "厅    ");
		}
        if  (!TextUtils.isEmpty(secondHandHouseBean.getDirection())) {
	        desc += (secondHandHouseBean.getDirection() + "   ");
        }
        if  (!TextUtils.isEmpty(secondHandHouseBean.getFloor())) {
        	desc += (secondHandHouseBean.getFloor() + "/");
        }
        desc += (secondHandHouseBean.getTotalFloor() + "层");
//        if  (!TextUtils.isEmpty(secondHandHouseBean.getTotalFloor())) {
//            
//        }
        
		tvDesc.setText(desc);
		tvEstName.setText(secondHandHouseBean.getEstName());
		tvTotalPrice.setText(secondHandHouseBean.getPrice() + "万");
//		String time = DateUtils.formatDate(secondHandHouseBean.getAddTime());
		tvPublishTime.setText(secondHandHouseBean.getAddTime());
		 
		return convertView;
	}

}
