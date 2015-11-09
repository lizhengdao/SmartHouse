package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.NewHouseActivity;
import cn.com.zzwfang.activity.RentHouseActivity;
import cn.com.zzwfang.activity.SearchHouseArtifactActivity;
import cn.com.zzwfang.activity.SecondHandHouseActivity;
import cn.com.zzwfang.bean.RecommendHouseSourceBean;
import cn.com.zzwfang.util.Jumper;

public class HomeRecommendHouseAdapter extends BaseAdapter implements OnClickListener {
	
	public static final String INTENT_CITY_ID = "cityid";

	private Context context;
	
	private ArrayList<RecommendHouseSourceBean> recommendSources;
	
	private String cityId = "";
	
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	/**
     * 布局类型标识。
     */
    private final int TPYE1 = 0;

    /**
     * 布局类型标识。
     */
    private final int TPYE2 = 1;
	
	public HomeRecommendHouseAdapter(Context context, ArrayList<RecommendHouseSourceBean> recommendSources) {
		this.context = context;
		this.recommendSources = recommendSources;
	}
	@Override
	public int getCount() {
		if (recommendSources == null) {
			return 1;
		}
		return recommendSources.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		if (position == 0) {
			return null;
		} else {
			return recommendSources.get(position - 1);
		}
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return TPYE1;
		} else {
			return TPYE2;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TPYE1:
				convertView = View.inflate(context, R.layout.adapter_frag_home_position_one, null);
				TextView tvSecondHandHouse = (TextView) convertView.findViewById(R.id.frag_home_second_hand_house);
				TextView tvNewHouse = (TextView) convertView.findViewById(R.id.frag_home_new_house);
				TextView tvRentHouse = (TextView) convertView.findViewById(R.id.frag_home_rent_house);
				TextView tvSearchHouse = (TextView) convertView.findViewById(R.id.frag_home_search_house);
				
				tvSecondHandHouse.setOnClickListener(this);
				tvNewHouse.setOnClickListener(this);
				tvRentHouse.setOnClickListener(this);
				tvSearchHouse.setOnClickListener(this);
				break;
			case TPYE2:
				convertView = View.inflate(context, R.layout.adapter_home_recommend_house, null);
				ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.adapter_home_recommend_photo);
				TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_home_recommend_title);
				TextView tvAddr = (TextView) convertView.findViewById(R.id.adapter_home_recommend_addr);
				TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_home_recommend_desc);
				TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_home_recommend_price);
				
				RecommendHouseSourceBean sourceBean = recommendSources.get(position - 1);
				tvTitle.setText(sourceBean.getTitle());
				tvAddr.setText(sourceBean.getAreaName() + " " + sourceBean.getEstateName());
				String desc = sourceBean.getTypeF() + "房" + sourceBean.getTypeT() + "厅" + "  " + sourceBean.getSquare() + "平米";
				tvDesc.setText(desc);
				tvPrice.setText(sourceBean.getPrice() + "万");
				String url = sourceBean.getPhoto();
				if (!TextUtils.isEmpty(url)) {
					ImageAction.displayImage(url, ivPhoto);
				}
				break;
			}
		}
		return convertView;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_home_second_hand_house:   //  二手房
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(INTENT_CITY_ID, cityId)
        .jump((BaseActivity)context, SecondHandHouseActivity.class);
		break;
	case R.id.frag_home_new_house:   //  新房(目前跟二手房的界面一样)
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(INTENT_CITY_ID, cityId)
        .jump((BaseActivity)context, NewHouseActivity.class);
		break;
	case R.id.frag_home_rent_house:   //   租房(目前跟二手房的界面一样)
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(INTENT_CITY_ID, cityId)
        .jump((BaseActivity)context, RentHouseActivity.class);
		break;
	case R.id.frag_home_search_house:    //  找房神器
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .jump((BaseActivity)context, SearchHouseArtifactActivity.class);
		break;
		}
		
	}

}
