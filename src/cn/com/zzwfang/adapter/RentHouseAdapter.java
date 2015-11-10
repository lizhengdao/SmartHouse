package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.RentHouseBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RentHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<RentHouseBean> rentHouses;
	
	public RentHouseAdapter(Context context, ArrayList<RentHouseBean> rentHouses) {
		this.context = context;
		this.rentHouses = rentHouses;
	}
	
	@Override
	public int getCount() {
		if (rentHouses == null) {
		    return 0;
		}
		return rentHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return rentHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_rent_house, null);
		}
		
		RentHouseBean rentHouseBean = rentHouses.get(position);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_rent_house_photo);
		ImageAction.displayImage(rentHouseBean.getPhoto(), imgPhoto);
		TextView tvTitle  = (TextView) convertView.findViewById(R.id.adapter_rent_house_title);
		tvTitle.setText(rentHouseBean.getTitle());
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_rent_house_desc);
		String desc = rentHouseBean.getTypeF() + "室" + 
				rentHouseBean.getTypeT() + "厅    " + rentHouseBean.getSquare() + "㎡  " + rentHouseBean.getDirection();
		tvDesc.setText(desc); 
		
		TextView tvEstName = (TextView) convertView.findViewById(R.id.adapter_rent_house_est_name);
		tvEstName.setText(rentHouseBean.getEsateName());
		
		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_rent_house_rent_price);
		tvTotalPrice.setText(rentHouseBean.getRentPrice() + "元/月");
		
		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_rent_house_publish_time);
		tvPublishTime.setText(rentHouseBean.getAddTime());
		
		return convertView;
	}

}
