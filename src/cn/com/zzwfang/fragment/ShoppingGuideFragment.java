package cn.com.zzwfang.fragment;

import cn.com.zzwfang.R;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShoppingGuideFragment extends BaseFragment implements OnHeaderRefreshListener, OnFooterLoadListener {

	private AbPullToRefreshView pullView;
	private ListView lstHeadline;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_headline, null);

		initView(view);
		return view;
	}
	
	private void initView(View view) {
		pullView = (AbPullToRefreshView) view.findViewById(R.id.pull_headline);
		lstHeadline = (ListView) view.findViewById(R.id.lst_headline);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		
	}
}
