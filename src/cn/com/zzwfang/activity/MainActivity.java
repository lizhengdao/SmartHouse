package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MainContentPagerAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener;
import cn.com.zzwfang.util.AppUtils;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.GuiderView;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity implements OnPageChangeListener, OnClickListener, OnCitySelectedListener {
    

	private ViewPager contentPager;
	
	private MainContentPagerAdapter contentAdapter;
	
	private GuiderView guiderHome, guiderMapFindHouse, guiderConsultant, guiderMine;
	
	private ArrayList<GuiderView> guidIndicators = new ArrayList<GuiderView>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.act_main);
		
		initView();
	}
	
	private void initView() {
		contentPager = (ViewPager) findViewById(R.id.act_main_content_pager);
		guiderHome = (GuiderView) findViewById(R.id.act_main_guider_home);
		guiderMapFindHouse = (GuiderView) findViewById(R.id.act_main_guider_map);
		guiderConsultant = (GuiderView) findViewById(R.id.act_main_guider_consultant);
		guiderMine = (GuiderView) findViewById(R.id.act_main_guider_mine);
		
		guidIndicators.add(guiderHome);
		guidIndicators.add(guiderMapFindHouse);
		guidIndicators.add(guiderConsultant);
		guidIndicators.add(guiderMine);
		
		guiderHome.setIconAlpha(1.0f);
		
		contentAdapter = new MainContentPagerAdapter(getSupportFragmentManager());
		contentPager.setAdapter(contentAdapter);
		
		contentPager.setOnPageChangeListener(this);
		guiderHome.setOnClickListener(this);
		guiderMapFindHouse.setOnClickListener(this);
		guiderConsultant.setOnClickListener(this);
		guiderMine.setOnClickListener(this);;
	}
	
	
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		 if (positionOffset > 0) { 
			 GuiderView left = guidIndicators.get(position);
			 GuiderView right = guidIndicators.get(position + 1);
			 left.setIconAlpha(1 - positionOffset);
			 right.setIconAlpha(positionOffset);
		 }
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) { 
		
		case R.id.act_main_guider_home:
			clickTab(0);
			break;
		case R.id.act_main_guider_map:
			clickTab(1);
			break;
		case R.id.act_main_guider_consultant:
			clickTab(2);
			break;
		case R.id.act_main_guider_mine:
			clickTab(3);
			break;
		}
	}
	
	private void clickTab(int position) { 
		for (GuiderView guider : guidIndicators) {
			guider.setIconAlpha(0.0f);
		}
		guidIndicators.get(position).setIconAlpha(1.0f);
		contentPager.setCurrentItem(position, false);
	}
	
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		if (AppUtils.isDoubleClick(this)) {
			exitApplication(true);
//			moveTaskToBack(true);
        } else {
            ToastUtils.SHORT.toast(this, R.string.act_main_exitapp);
        }
		
	}
	
	public void backToHomeFragment() {
		clickTab(0);
	}

    @Override
    public void onCitySelected(CityBean cityBean) {
        contentAdapter.onCitySelected(cityBean);
    }
}
