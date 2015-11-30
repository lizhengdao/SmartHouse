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
import cn.com.zzwfang.bean.HouseRecommendedToMeBean;

public class HousesRecommendedToMeAdapter extends BaseAdapter {

	
	private Context context;
	private ArrayList<HouseRecommendedToMeBean> recommendedHouses;
	
	public HousesRecommendedToMeAdapter(Context context, ArrayList<HouseRecommendedToMeBean> recommendedHouses) {
		this.context = context;
		this.recommendedHouses = recommendedHouses;
	}
	
	@Override
	public int getCount() {
		if (recommendedHouses == null) {
			return 0;
		}
		return recommendedHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return recommendedHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_house_recommended_to_me, null);
		}
		
		HouseRecommendedToMeBean recommendedHouseBean = recommendedHouses.get(position);
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_house_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_house_recommended_to_me_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_house_recommended_to_me_desc);
		TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_house_recommended_to_me_date);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_house_recommended_to_me_price);
		
		tvTitle.setText(recommendedHouseBean.getEstateName());
		
//		String desc = recommendedHouseBean.getTypeF() + "室" + recommendedHouseBean.getTypeT()
//				+ "厅    " + recommendedHouseBean.getSquare() + "㎡   " + recommendedHouseBean.getDirection();
		
		String desc = recommendedHouseBean.getHouseType() + "   "  + recommendedHouseBean.getSquare() + "㎡   ";
		tvDesc.setText(desc);
		tvDate.setText(recommendedHouseBean.getTrustDate());
		tvPrice.setText(recommendedHouseBean.getPrice() + "万");
		
		String url = recommendedHouseBean.getPhoto();
		ImageAction.displayImage(url, img);
		
		return convertView;
	}

}
