package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.SecondHandHouseBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_second_hand_house_photo);
		ImageAction.displayImage(secondHandHouseBean.getPhoto(), imgPhoto);
		TextView tvTitle  = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_title);
		tvTitle.setText(secondHandHouseBean.getTitle());
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_desc);
		String desc = secondHandHouseBean.getTypeF() + "室" + 
		secondHandHouseBean.getTypeT() + "厅    " + secondHandHouseBean.getDirection() + "   "  +
		secondHandHouseBean.getStructure() + "/" + secondHandHouseBean.getFloor() + "层";
		tvDesc.setText(desc);
		
		TextView tvEstName = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_est_name);
		tvEstName.setText(secondHandHouseBean.getEstName());
		
		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_total_price);
		tvTotalPrice.setText(secondHandHouseBean.getPrice() + "万");
		
		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_second_hand_house_publish_time);
		tvPublishTime.setText(secondHandHouseBean.getAddTime());
		
		
		return convertView;
	}

}
