package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.RentHouseBean;
import cn.com.zzwfang.util.DateUtils;
import android.content.Context;
import android.text.TextUtils;
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
			convertView = View.inflate(context, R.layout.adapter_rent_house,
					null);
		}

		RentHouseBean rentHouseBean = rentHouses.get(position);
		ImageView imgPhoto = (ImageView) convertView
				.findViewById(R.id.adapter_rent_house_photo);
		ImageAction.displayImage(rentHouseBean.getPhoto(), imgPhoto);
		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.adapter_rent_house_title);
		tvTitle.setText(rentHouseBean.getTitle());
		TextView tvDesc = (TextView) convertView
				.findViewById(R.id.adapter_rent_house_desc);
		String desc = "";
		if (!TextUtils.isEmpty(rentHouseBean.getTypeF())) {
			desc += rentHouseBean.getTypeF() + "室";
		}
		if (!TextUtils.isEmpty(rentHouseBean.getTypeT())) {
			desc += rentHouseBean.getTypeT() + "厅    ";
		}
		if (!TextUtils.isEmpty(rentHouseBean.getSquare())) {
			desc += rentHouseBean.getSquare() + "㎡   ";
		}
		if (!TextUtils.isEmpty(rentHouseBean.getDirection())) {
			desc += rentHouseBean.getDirection();
		}

		tvDesc.setText(desc);

		TextView tvEstName = (TextView) convertView
				.findViewById(R.id.adapter_rent_house_est_name);
		tvEstName.setText(rentHouseBean.getEsateName());

		TextView tvTotalPrice = (TextView) convertView
				.findViewById(R.id.adapter_rent_house_rent_price);
		tvTotalPrice.setText(rentHouseBean.getRentPrice() + "元/月");

		TextView tvPublishTime = (TextView) convertView
				.findViewById(R.id.adapter_rent_house_publish_time);
		String time = DateUtils.formatDate(rentHouseBean.getAddTime());
		tvPublishTime.setText(time);

		return convertView;
	}

}
