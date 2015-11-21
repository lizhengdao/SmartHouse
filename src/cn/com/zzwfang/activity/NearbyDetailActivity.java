package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.view.AutoDrawableTextView;

import com.baidu.mapapi.map.MapView;

/**
 * 周边详情
 * @author MISS-万
 *
 */
public class NearbyDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private MapView mapView;
	
	private AutoDrawableTextView tvBank, tvBus, tvSubway, tvSchool, tvHostipal, tvLeisure, tvShopping, tvBodyBuilding, tvFoods;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_nearby_detail);
		tvBack = (TextView) findViewById(R.id.act_nearby_detail_back);
		mapView = (MapView) findViewById(R.id.act_nearby_detail_map_view);
		tvBank = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_bank);
		tvBus = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_bus);
		tvSubway = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_subway);
		tvSchool = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_school);
		tvHostipal = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_hostipal);
		tvLeisure = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_leisure);
		tvShopping = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_shopping);
		tvBodyBuilding = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_body_building);
		tvFoods = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_foods);
		
		tvBack.setOnClickListener(this);
		tvBank.setOnClickListener(this);
		tvBus.setOnClickListener(this);
		tvSubway.setOnClickListener(this);
		tvSchool.setOnClickListener(this);
		tvHostipal.setOnClickListener(this);
		tvLeisure.setOnClickListener(this);
		tvShopping.setOnClickListener(this);
		tvBodyBuilding.setOnClickListener(this);
		tvFoods.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nearby_detail_back:
			finish();
			break;
		case R.id.act_nearby_detail_bank:  // 银行
			break;
		case R.id.act_nearby_detail_bus:  // 公交
			break;
		case R.id.act_nearby_detail_subway:  //  地铁
			break;
		case R.id.act_nearby_detail_school:  // 教育
			break;
		case R.id.act_nearby_detail_hostipal:  //  医院
			break;
		case R.id.act_nearby_detail_leisure:  //  休闲
			break;
		case R.id.act_nearby_detail_shopping:  //  购物
			break;
		case R.id.act_nearby_detail_body_building:  //  健身
			break;
		case R.id.act_nearby_detail_foods:  //  美食
			break;
		
		}
	}
}
