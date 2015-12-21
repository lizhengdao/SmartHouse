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
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_photo);
		TextView tvTitle  = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_desc);
		TextView tvEstName = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_est_name);
		TextView tvTotalPrice = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_price);
		TextView tvPublishTime = ViewHolder.get(convertView, R.id.adapter_fee_hunter_recommend_house_source_list_publish_time);
		
		ImageAction.displayImage(data.getImagePath(), imgPhoto);
		if (!TextUtils.isEmpty(data.getEstName())) {
			tvTitle.setText(data.getEstName());
		}
		
		String desc = "";
		if (!TextUtils.isEmpty(data.getTypeF())) {
			desc += data.getTypeF() + "室";
		}
		if (!TextUtils.isEmpty(data.getTypeT())) {
			desc += data.getTypeT() + "厅    ";
		}
		if (!TextUtils.isEmpty(data.getTypeW())) {
			desc += data.getTypeW() + "卫   ";
		}
		if (!TextUtils.isEmpty(data.getTypeY())) {
			desc += data.getTypeY() + "阳台    ";
		}
		tvDesc.setText(desc);
		if (!TextUtils.isEmpty(data.getEstName())) {
			tvEstName.setText(data.getEstName());
		}
		if (!TextUtils.isEmpty(data.getPrice())) {
			tvTotalPrice.setText(data.getPrice() + "万");
		}
		if (!TextUtils.isEmpty(data.getTrustDate())) {
			tvPublishTime.setText(data.getTrustDate());
		}
		
		return convertView;
	}

}
