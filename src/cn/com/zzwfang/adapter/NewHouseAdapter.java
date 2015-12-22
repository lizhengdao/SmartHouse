package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.NewHouseBean;
import cn.com.zzwfang.bean.NewHouseLabelBean;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<NewHouseBean> newHouses;
	
	public NewHouseAdapter(Context context, ArrayList<NewHouseBean> newHouses) {
		this.context = context;
		this.newHouses = newHouses;
	}
	@Override
	public int getCount() {
		if (newHouses == null) {
			return 0;
		}
		return newHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return newHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_new_house, null);
		}
		NewHouseBean temp = newHouses.get(position);
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_new_house_photo);
		TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_new_house_title);
//		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_new_house_desc);
		TextView tvSquare = ViewHolder.get(convertView, R.id.adapter_new_house_square);
		
		TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_new_house_price);
		TextView tvLabelOne = ViewHolder.get(convertView, R.id.adapter_new_house_label_one);
		TextView tvLabelTwo = ViewHolder.get(convertView, R.id.adapter_new_house_label_two);
		TextView tvLabelThree = ViewHolder.get(convertView, R.id.adapter_new_house_label_three);
		
		if (!TextUtils.isEmpty(temp.getName())) {
			tvTitle.setText(temp.getName());
		}
		if (!TextUtils.isEmpty(temp.getSquare())) {
			tvSquare.setText(temp.getSquare() + "㎡");
		}
		if (!TextUtils.isEmpty(temp.getPrice())) {
			tvPrice.setText(temp.getPrice() + "元/㎡");
		}
		
		ArrayList<NewHouseLabelBean> labels = temp.getLabel();
		int size = labels.size();
		if (size == 1) {
			tvLabelOne.setText(labels.get(0).getValue());
		} else if (size == 2) {
			tvLabelOne.setText(labels.get(0).getValue());
			tvLabelTwo.setText(labels.get(1).getValue());
		} else if (size == 3) {
			tvLabelOne.setText(labels.get(0).getValue());
			tvLabelTwo.setText(labels.get(1).getValue());
			tvLabelThree.setText(labels.get(2).getValue());
		}
		
		String url = temp.getPhoto();
		if (!TextUtils.isEmpty(url)) {
			ImageAction.displayImage(url, imgPhoto);
		}
		
		return convertView;
	}

}
