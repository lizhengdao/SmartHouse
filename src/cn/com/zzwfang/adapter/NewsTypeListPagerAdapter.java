package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.fragment.NewsListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsTypeListPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	private ArrayList<IdTitleBean> newsTypes;
	
	public NewsTypeListPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}
	
	public void setData(ArrayList<IdTitleBean> newsTypes) {
		if (newsTypes != null) {
			this.newsTypes = newsTypes;
			notifyDataSetChanged();
		}
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = new NewsListFragment();
		Bundle bundle = new Bundle();
        bundle.putSerializable(NewsListFragment.NEWS_ARGS, newsTypes.get(arg0));
        fragment.setArguments(bundle);
        return fragment;
	}

	@Override
	public int getCount() {
		if (newsTypes == null) {
			return 0;
		}
		return newsTypes.size();
	}

}
