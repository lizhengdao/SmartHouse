package cn.com.zzwfang.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.MainActivity;

public class MainConsultationFragment extends BaseFragment implements OnCheckedChangeListener, OnClickListener {

	private TextView tvBack;
	
	private RadioButton rbHeadline, rbShoppingGuider, rbQandA, rbMore;
	
	private FrameLayout fltContainer;
	
	private HeadLineFragment headlineFragment;
	private ShoppingGuideFragment shoppingGuideFragment;
	private QAFragment qaFragment;
	private android.support.v4.app.FragmentManager fm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_consultation, null);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_consultation_back);
		rbHeadline = (RadioButton) view.findViewById(R.id.frag_consultation_headline_rb);
		rbShoppingGuider = (RadioButton) view.findViewById(R.id.frag_consultation_shopping_guide_rb);
		rbQandA = (RadioButton) view.findViewById(R.id.frag_consultation_q_and_a_rb);
		rbMore = (RadioButton) view.findViewById(R.id.frag_consultation_more_rb);
		fltContainer = (FrameLayout) view.findViewById(R.id.frag_consultation_container);
		
		tvBack.setOnClickListener(this);
		rbHeadline.setOnCheckedChangeListener(this);
		rbShoppingGuider.setOnCheckedChangeListener(this);
		rbQandA.setOnCheckedChangeListener(this);
		rbMore.setOnCheckedChangeListener(this);
		
		headlineFragment = new HeadLineFragment();
		shoppingGuideFragment = new ShoppingGuideFragment();
		qaFragment = new QAFragment();
		
		fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.frag_consultation_container, headlineFragment);
		ft.commit();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			FragmentTransaction ft = fm.beginTransaction();
			switch (buttonView.getId()) {
			case R.id.frag_consultation_headline_rb:   // 头条
				ft.replace(R.id.frag_consultation_container, headlineFragment);
				ft.commit();
				break;
			case R.id.frag_consultation_shopping_guide_rb:  // 导购
				ft.replace(R.id.frag_consultation_container, shoppingGuideFragment);
				ft.commit();
				break;
			case R.id.frag_consultation_q_and_a_rb:  //  问答
				ft.replace(R.id.frag_consultation_container, qaFragment);
				ft.commit();
				break;
			case R.id.frag_consultation_more_rb:  //  更多
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
		}
	}

}
