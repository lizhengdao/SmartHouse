package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.NewsItemAdapter;
import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.bean.NewsItemBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NewsListFragment extends BaseFragment implements OnHeaderRefreshListener, OnFooterLoadListener {

	public static final String NEWS_ARGS = "news_type_data";
	private AbPullToRefreshView pullView;
	private ListView lstNews;
	private ArrayList<NewsItemBean> news = new ArrayList<NewsItemBean>();
	private NewsItemAdapter adapter;
	private IdTitleBean idTitleBean;
	private int pageIndex = 1;
	private int pageTotal = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		idTitleBean = (IdTitleBean) bundle.getSerializable(NEWS_ARGS);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_news_list, null);
		initView(view);
		getNewsList(true);
		return view;
	}
	
	private void initView(View view) {
		pullView = (AbPullToRefreshView) view.findViewById(R.id.pull_headline);
		lstNews = (ListView) view.findViewById(R.id.lst_news);
		
		adapter = new NewsItemAdapter(getActivity(), news);
		lstNews.setAdapter(adapter);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex < pageTotal) {
			getNewsList(false);
		} else {
			pullView.onFooterLoadFinish();
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getNewsList(true);
	}
	
	private void getNewsList(final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		}
		if (idTitleBean != null) {
			ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
			actionImpl.getNewsList(idTitleBean.getId(), 10, pageIndex, new ResultHandlerCallback() {
				
				@Override
				public void rc999(RequestEntity entity, Result result) {
				}
				
				@Override
				public void rc3001(RequestEntity entity, Result result) {
				}
				
				@Override
				public void rc0(RequestEntity entity, Result result) {
					pageTotal = result.getTotal();
					if (isRefresh) {
						news.clear();
					}
					ArrayList<NewsItemBean> temp = (ArrayList<NewsItemBean>) JSON.parseArray(result.getData(), NewsItemBean.class);
					news.addAll(temp);
					adapter.notifyDataSetChanged();
					pageIndex++;
					if (isRefresh) {
						pullView.onHeaderRefreshFinish();
					} else {
						pullView.onFooterLoadFinish();
					}
				}
			});
		}
		
	}
}
