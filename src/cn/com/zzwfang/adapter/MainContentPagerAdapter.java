package cn.com.zzwfang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.fragment.MainHomeFragment;
import cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener;
import cn.com.zzwfang.fragment.MainMapFindHouseFragment;
import cn.com.zzwfang.fragment.MainMineFragment;
import cn.com.zzwfang.fragment.MainNewsFragment;

public class MainContentPagerAdapter extends FragmentStatePagerAdapter implements OnCitySelectedListener {
	
	private MainHomeFragment homeFragment = null;
	private MainMapFindHouseFragment mapFragment = null;
	private MainNewsFragment consultationFragment = null;
	private MainMineFragment mineFragment = null;

//	ArrayList<String> title = new ArrayList<String>();
	public MainContentPagerAdapter(FragmentManager fm) {
		super(fm);
//		title.add("首页");
//		title.add("地图找房");
//		title.add("咨询");
//		title.add("我的");
	}
	
	public void updateMessageCount(MessageBean msg) {
	    if (mineFragment != null) {
	        mineFragment.updateMessageCount(msg);
	    }
    }

	@Override
	public Fragment getItem(int position) {
		
		Fragment fragment = null;
		switch (position) {
		case 0:
			if (homeFragment == null) {
				homeFragment = new MainHomeFragment();
			}
			fragment = homeFragment;
			break;
		case 1:
			if (mapFragment == null) {
				mapFragment = new MainMapFindHouseFragment();
			}
			fragment = mapFragment;
			break;
		case 2:
			if (consultationFragment == null) {
				consultationFragment = new MainNewsFragment();
			}
			fragment = consultationFragment;
			break;
		case 3:
			if (mineFragment == null) {
				mineFragment = new MainMineFragment();
			}
			fragment = mineFragment;
			break;
		}
		return fragment;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
//		ViewGroup parent = (ViewGroup) container.getParent();
//		if (parent != null) {
//			parent.removeAllViews();
//		}
		return super.instantiateItem(container, position);
	}

	@Override
	public int getCount() {
		return 4;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return "title";
	}

    @Override
    public void onCitySelected(CityBean cityBean) {
        if (mapFragment != null) {
            mapFragment.onCitySelected(cityBean);
        }
    }

}
