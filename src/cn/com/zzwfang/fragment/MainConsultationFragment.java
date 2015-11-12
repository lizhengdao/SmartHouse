package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.NewsDetailActivity;
import cn.com.zzwfang.activity.SecondHandHouseActivity;
import cn.com.zzwfang.adapter.NewsTypeAdapter;
import cn.com.zzwfang.bean.IdTitleBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;

public class MainConsultationFragment extends BaseFragment implements OnCheckedChangeListener,
OnClickListener, OnItemClickListener {

	private TextView tvBack;
	
	private RadioButton rbHeadline, rbShoppingGuider, rbQandA, rbMore;
	
	private ListView listNews;
	private NewsTypeAdapter adapter;
	
	private FrameLayout fltContainer;
	
	private HeadLineFragment headlineFragment;
	private ShoppingGuideFragment shoppingGuideFragment;
	private QAFragment qaFragment;
	private android.support.v4.app.FragmentManager fm;
	
	private ArrayList<IdTitleBean> newsTypes = new ArrayList<IdTitleBean>();
	
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
		rbHeadline = (RadioButton) view.findViewById(R.id.frag_consultation_headline_rb);
		rbShoppingGuider = (RadioButton) view.findViewById(R.id.frag_consultation_shopping_guide_rb);
		rbQandA = (RadioButton) view.findViewById(R.id.frag_consultation_q_and_a_rb);
		rbMore = (RadioButton) view.findViewById(R.id.frag_consultation_more_rb);
		fltContainer = (FrameLayout) view.findViewById(R.id.frag_consultation_container);
		
		listNews = (ListView) view.findViewById(R.id.frag_consultation_list);
		
		adapter = new NewsTypeAdapter(getActivity(), newsTypes);
		listNews.setAdapter(adapter);
		listNews.setOnItemClickListener(this);
		
		tvBack.setOnClickListener(this);
		rbHeadline.setOnCheckedChangeListener(this);
		rbShoppingGuider.setOnCheckedChangeListener(this);
		rbQandA.setOnCheckedChangeListener(this);
		rbMore.setOnCheckedChangeListener(this);
		
//		headlineFragment = new HeadLineFragment();
//		shoppingGuideFragment = new ShoppingGuideFragment();
//		qaFragment = new QAFragment();
//		
//		fm = getChildFragmentManager();
//		FragmentTransaction ft = fm.beginTransaction();
//		ft.replace(R.id.frag_consultation_container, headlineFragment);
//		ft.commit();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		if (isChecked) {
//			FragmentTransaction ft = fm.beginTransaction();
//			switch (buttonView.getId()) {
//			case R.id.frag_consultation_headline_rb:   // 头条
//				ft.replace(R.id.frag_consultation_container, headlineFragment);
//				ft.commit();
//				break;
//			case R.id.frag_consultation_shopping_guide_rb:  // 导购
//				ft.replace(R.id.frag_consultation_container, shoppingGuideFragment);
//				ft.commit();
//				break;
//			case R.id.frag_consultation_q_and_a_rb:  //  问答
//				ft.replace(R.id.frag_consultation_container, qaFragment);
//				ft.commit();
//				break;
//			case R.id.frag_consultation_more_rb:  //  更多
//				break;
//			}
//		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_consultation_back:
			((MainActivity)getActivity()).backToHomeFragment();
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
				// TODO Auto-generated method stub
				ArrayList<IdTitleBean> temp = (ArrayList<IdTitleBean>) JSON.parseArray(result.getData(), IdTitleBean.class);
				newsTypes.addAll(temp);
				adapter.notifyDataSetChanged();
			}
		});
	}

	
}
