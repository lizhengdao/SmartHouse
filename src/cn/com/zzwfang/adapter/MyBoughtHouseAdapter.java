package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.MyBoughtHouseBean;

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
		
		MyBoughtHouseBean myBoughtHouseBean = houses.get(position);
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_bought_house_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_my_bought_house_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_my_bought_house_desc);
		TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_my_bought_house_date);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_my_bought_house_price);
		
		tvTitle.setText(myBoughtHouseBean.getTitle());
		
		String desc = myBoughtHouseBean.getTypeF() + "室" + myBoughtHouseBean.getTypeT()
				+ "厅    " + myBoughtHouseBean.getSquare() + "㎡   " +myBoughtHouseBean.getDirection();
		tvDesc.setText(desc);
		tvDate.setText(myBoughtHouseBean.getPublishDate());
		tvPrice.setText(myBoughtHouseBean.getPrice() + "万");
		
		String url = myBoughtHouseBean.getPhoto();
		ImageAction.displayImage(url, img);
		return convertView;
	}

}
