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
import cn.com.zzwfang.bean.FeeHunterRecommendHouseSourceListItem;

public class FeeHunterRecommendHouseSourceListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<FeeHunterRecommendHouseSourceListItem> houseSources;
	
	public FeeHunterRecommendHouseSourceListAdapter(Context context, ArrayList<FeeHunterRecommendHouseSourceListItem> houseSources) {
		this.context = context;
		this.houseSources = houseSources;
	}
	
	@Override
	public int getCount() {
		if (houseSources == null) {
			return 0;
		}
		return houseSources.size();
	}

	@Override
	public Object getItem(int position) {
		return houseSources.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_fee_hunter_recommend_house_source_list, null);
		}
		
		FeeHunterRecommendHouseSourceListItem data = houseSources.get(position);
		
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_photo);
		ImageAction.displayImage(data.getImagePath(), imgPhoto);
		TextView tvTitle  = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_title);
		tvTitle.setText(data.getEstName());
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_desc);
		String desc = data.getTypeF() + "室" + 
				data.getTypeT() + "厅    " + data.getTypeW() + "卫   "  +
				data.getTypeY() + "阳台    ";
		tvDesc.setText(desc);
		
		TextView tvEstName = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_est_name);
		tvEstName.setText(data.getEstName());
		
		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_price);
		tvTotalPrice.setText(data.getPrice() + "万");
		
		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_fee_hunter_recommend_house_source_list_publish_time);
		tvPublishTime.setText(data.getTrustDate());
		
		return convertView;
	}

}
