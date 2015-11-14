package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.MyHouseBean;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<MyHouseBean> myHouses;
	
	public MyHouseAdapter(Context context, ArrayList<MyHouseBean> myHouses) {
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
			
			convertView = View.inflate(context, R.layout.adapter_my_house, null);
		}
		MyHouseBean myHouseBean = myHouses.get(position);
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_house_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_my_house_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_my_house_desc);
		TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_my_house_date);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_my_house_price);
		
		tvTitle.setText(myHouseBean.getTitle());
		
		String desc = myHouseBean.getTypeF() + "室" + myHouseBean.getTypeT()
				+ "厅    " + myHouseBean.getSquare() + "㎡   " + myHouseBean.getDirection();
		tvDesc.setText(desc);
		tvDate.setText(myHouseBean.getPublishDate());
		tvPrice.setText(myHouseBean.getPrice() + "万");
		
		String url = myHouseBean.getImagePath();
	    if (TextUtils.isEmpty(url)) {
	    	
	    }
		ImageAction.displayImage(url, img);
		return convertView;
	}

}
