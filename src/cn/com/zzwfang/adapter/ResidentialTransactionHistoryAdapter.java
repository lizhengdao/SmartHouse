package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.PhotoBean;
import cn.com.zzwfang.bean.ResidentialTransactionHistoryBean;
import cn.com.zzwfang.util.DateUtils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResidentialTransactionHistoryAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<ResidentialTransactionHistoryBean> historys;
	
	public ResidentialTransactionHistoryAdapter(Context context, ArrayList<ResidentialTransactionHistoryBean> historys) {
		this.context = context;
		this.historys = historys;
	}
	@Override
	public int getCount() {
		if (historys == null) {
			return 0;
		}
		return historys.size();
	}

	@Override
	public Object getItem(int position) {
		return historys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_residential_transaction_history, null);
		}
		
		ResidentialTransactionHistoryBean temp = historys.get(position);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_residential_transaction_history_photo);
		ArrayList<PhotoBean> photos = temp.getPhoto();
		if (photos != null && photos.size() > 0) {
			PhotoBean photo = photos.get(0);
			ImageAction.displayImage(photo.getPath(), imgPhoto);
		}
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_residential_transaction_history_title);
		String title = temp.getTypeF() + "室" + temp.getTypeT() + "厅   " + temp.getSquare() + "平";
		tvTitle.setText(title);
		
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_residential_transaction_history_desc);
		String desc = temp.getDirection() + "  " + temp.getFloor() + "层/" + temp.getTotalFloor() + "层";
		tvDesc.setText(desc);
		
		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_residential_transaction_history_total_price);
		tvTotalPrice.setText(temp.getPrice() + "万");
		
		TextView tvTime = (TextView) convertView.findViewById(R.id.adapter_residential_transaction_history_publish_time);
		String time = DateUtils.formatDate(temp.getSaleDate());
		tvTime.setText(time);
		
		TextView tvBrokerName = (TextView) convertView.findViewById(R.id.adapter_residential_transaction_history_broker_name);
		tvBrokerName.setText(temp.getAgentName());
		
		return convertView;
	}

}
