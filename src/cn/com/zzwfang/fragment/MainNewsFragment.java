package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.NewsDetailActivity;
import cn.com.zzwfang.adapter.NewsTypeListPagerAdapter;
import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.UnScrollableViewPager;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnNewsMoreSelectedListener;

import com.alibaba.fastjson.JSON;

/**
 * 主页  咨询   Fragment
 * @author MISS-万
 *
 */
public class MainNewsFragment extends BaseFragment implements OnCheckedChangeListener,
OnClickListener, OnItemClickListener {

	private TextView tvBack;
//	private ViewPager pagerTop;
	private RadioButton rbNewsTypeOne, rbNewsTypeTwo, rbNewsTypeThree, rbMore;
	private View lineOne, lineTwo, lineThree;
	private UnScrollableViewPager viewPager;
	private NewsTypeListPagerAdapter pagerAdapter;
	
	private ArrayList<IdTitleBean> newsTypes = new ArrayList<IdTitleBean>();
	
	private OnNewsMoreSelectedListener onNewsMoreSelectedListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_consultation, null);
		initView(view);
		getNewsType();
		return view;
	}
	
	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_consultation_back);
//		pagerTop = (ViewPager) view.findViewById(R.id.pager_consultation);
		rbNewsTypeOne = (RadioButton) view.findViewById(R.id.frag_consultation_one_rb);
		rbNewsTypeTwo = (RadioButton) view.findViewById(R.id.frag_consultation_two_rb);
		rbNewsTypeThree = (RadioButton) view.findViewById(R.id.frag_consultation_three_rb);
		rbMore = (RadioButton) view.findViewById(R.id.frag_consultation_more_rb);
		
		lineOne = view.findViewById(R.id.frag_main_news_line_one);
		lineTwo = view.findViewById(R.id.frag_main_news_line_two);
		lineThree = view.findViewById(R.id.frag_main_news_line_three);
		
		viewPager = (UnScrollableViewPager) view.findViewById(R.id.frag_news_pager);
		viewPager.setCanScroll(false);
		pagerAdapter = new NewsTypeListPagerAdapter(getActivity(), getChildFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		
		tvBack.setOnClickListener(this);
		rbNewsTypeOne.setOnCheckedChangeListener(this);
		rbNewsTypeTwo.setOnCheckedChangeListener(this);
		rbNewsTypeThree.setOnCheckedChangeListener(this);
//		rbMore.setOnCheckedChangeListener(this);
		rbMore.setOnClickListener(this);
		
		onNewsMoreSelectedListener = new OnNewsMoreSelectedListener() {

			@Override
			public void onOnNewsMoreSelected(int position, IdTitleBean newsType) {
				viewPager.setCurrentItem(3 + position);
			}
		};
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.frag_consultation_one_rb:
				viewPager.setCurrentItem(0);
				break;
			case R.id.frag_consultation_two_rb:
				viewPager.setCurrentItem(1);
				break;
			case R.id.frag_consultation_three_rb:
				viewPager.setCurrentItem(2);
				break;
			case R.id.frag_consultation_more_rb:  //  更多
//				if (newsTypes.size() == 4) {
//					viewPager.setCurrentItem(3);
//				} else {
//					ArrayList<IdTitleBean> moreNewsTypes = new ArrayList<IdTitleBean>();
//					moreNewsTypes.addAll(newsTypes);
//					// 移除前三个
//					moreNewsTypes.remove(0);
//					moreNewsTypes.remove(0);
//					moreNewsTypes.remove(0);
//					PopViewHelper.showNewsMorePopWindow(getActivity(), rbMore, moreNewsTypes, onNewsMoreSelectedListener);
//				}
				
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_consultation_back:
			((MainActivity)getActivity()).backToHomeFragment();
			break;
		case R.id.frag_consultation_more_rb:
			if (newsTypes.size() == 4) {
				viewPager.setCurrentItem(3);
			} else {
				ArrayList<IdTitleBean> moreNewsTypes = new ArrayList<IdTitleBean>();
				moreNewsTypes.addAll(newsTypes);
				// 移除前三个
				moreNewsTypes.remove(0);
				moreNewsTypes.remove(0);
				moreNewsTypes.remove(0);
				PopViewHelper.showNewsMorePopWindow(getActivity(), rbMore, moreNewsTypes, onNewsMoreSelectedListener);
			}
			break;
		}
	}
	
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		IdTitleBean temp = newsTypes.get(position);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(NewsDetailActivity.INTENT_NEWS_TYPE_ID, temp.getId())
        .jump((BaseActivity)getActivity(), NewsDetailActivity.class);
	}
	
	private void rendUI() {
		int newsTypeSize = newsTypes.size();
		pagerAdapter.setData(newsTypes);
		switch (newsTypeSize) {
		case 0:
			rbNewsTypeOne.setVisibility(View.GONE);
			rbNewsTypeTwo.setVisibility(View.GONE);
			rbNewsTypeThree.setVisibility(View.GONE);
			rbMore.setVisibility(View.GONE);
			lineOne.setVisibility(View.GONE);
			lineTwo.setVisibility(View.GONE);
			lineThree.setVisibility(View.GONE);
			return;
		case 1:
			rbNewsTypeOne.setVisibility(View.VISIBLE);
			rbNewsTypeTwo.setVisibility(View.GONE);
			rbNewsTypeThree.setVisibility(View.GONE);
			rbMore.setVisibility(View.GONE);
			rbNewsTypeOne.setText(newsTypes.get(0).getTitle());
			lineOne.setVisibility(View.GONE);
			lineTwo.setVisibility(View.GONE);
			lineThree.setVisibility(View.GONE);
			break;
		case 2:
			rbNewsTypeOne.setVisibility(View.VISIBLE);
			rbNewsTypeTwo.setVisibility(View.VISIBLE);
			rbNewsTypeThree.setVisibility(View.GONE);
			rbMore.setVisibility(View.GONE);
			rbNewsTypeOne.setText(newsTypes.get(0).getTitle());
			rbNewsTypeTwo.setText(newsTypes.get(1).getTitle());
			lineTwo.setVisibility(View.GONE);
			lineThree.setVisibility(View.GONE);
			break;
		case 3:
			rbNewsTypeOne.setVisibility(View.VISIBLE);
			rbNewsTypeTwo.setVisibility(View.VISIBLE);
			rbNewsTypeThree.setVisibility(View.VISIBLE);
			rbMore.setVisibility(View.GONE);
			rbNewsTypeOne.setText(newsTypes.get(0).getTitle());
			rbNewsTypeTwo.setText(newsTypes.get(1).getTitle());
			rbNewsTypeThree.setText(newsTypes.get(2).getTitle());
			lineThree.setVisibility(View.GONE);
			break;
		case 4:
			rbNewsTypeOne.setVisibility(View.VISIBLE);
			rbNewsTypeTwo.setVisibility(View.VISIBLE);
			rbNewsTypeThree.setVisibility(View.VISIBLE);
			rbMore.setVisibility(View.VISIBLE);
			rbNewsTypeOne.setText(newsTypes.get(0).getTitle());
			rbNewsTypeTwo.setText(newsTypes.get(1).getTitle());
			rbNewsTypeThree.setText(newsTypes.get(2).getTitle());
			rbMore.setText(newsTypes.get(3).getTitle());
			break;
			default:
				rbNewsTypeOne.setVisibility(View.VISIBLE);
				rbNewsTypeTwo.setVisibility(View.VISIBLE);
				rbNewsTypeThree.setVisibility(View.VISIBLE);
				rbMore.setVisibility(View.VISIBLE);
				rbNewsTypeOne.setText(newsTypes.get(0).getTitle());
				rbNewsTypeTwo.setText(newsTypes.get(1).getTitle());
				rbNewsTypeThree.setText(newsTypes.get(2).getTitle());
				rbMore.setText("更多");
				break;
		}
	}
	
	
	
	private void getNewsType() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getNewsType(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				newsTypes.clear();
				ArrayList<IdTitleBean> temp = (ArrayList<IdTitleBean>) JSON.parseArray(result.getData(), IdTitleBean.class);
				newsTypes.addAll(temp);
				rendUI();
			}
		});
	}

	
}
