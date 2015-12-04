package cn.com.zzwfang.activity;

import java.io.File;
import java.util.ArrayList;




import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.ProvinceCityBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnAvatarOptionsClickListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnBankNameSelectedListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnBankProvinceOrCitySelectedListener;

public class FillBankCardInfoActivity extends BasePickPhotoActivity implements OnClickListener, OnAvatarOptionsClickListener {

    private static final int CODE_BIND_CARD_INFO  = 1200;
    
	private TextView tvBack, tvCommit, tvAddr, tvBankName;
	
	private ImageView imgBank;
	
	private EditText edtUserName, edtBankCode, edtOpenAccountBankName;
	
	private LinearLayout lltBankName, lltOpenAccountBankCity;
	
	private ArrayList<ProvinceCityBean> provinces = new ArrayList<ProvinceCityBean>();
	
	private ArrayList<ProvinceCityBean> cities = new ArrayList<ProvinceCityBean>();
	
	private ArrayList<String> bankNames = new ArrayList<String>();
	
	private OnBankProvinceOrCitySelectedListener onProvinceSelectedListener;
	
	private OnBankProvinceOrCitySelectedListener onCitySelectedListener;
	
	private ProvinceCityBean province;
	
	private ProvinceCityBean city;
	
	private OnBankNameSelectedListener onBankNameSelectedListener;
	
	private String bankNameSelected;
	
	private File bankFile;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
		getProvinceOrCity(null);
		getBankNameList();
	}
	
	private void initView() {
		setContentView(R.layout.act_fill_bank_card_info);
		
		tvBack = (TextView) findViewById(R.id.act_fill_bank_card_info_back);
		tvCommit = (TextView) findViewById(R.id.act_fill_card_info_commit_tv);
		tvAddr = (TextView) findViewById(R.id.act_fill_card_info_addr_tv);
		tvBankName = (TextView) findViewById(R.id.act_fill_card_info_bank_name_tv);
		imgBank = (ImageView) findViewById(R.id.act_fill_card_info_banK_img);
		
		edtUserName = (EditText) findViewById(R.id.act_fill_card_info_name_edt);
		edtBankCode = (EditText) findViewById(R.id.act_fill_card_info_card_num_edt);
		edtOpenAccountBankName = (EditText) findViewById(R.id.act_fill_card_info_open_bank_name_edt);
		
		lltBankName = (LinearLayout) findViewById(R.id.act_fill_card_info_bank_name_llt);
		lltOpenAccountBankCity = (LinearLayout) findViewById(R.id.act_fill_card_info_open_account_city_llt);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		lltBankName.setOnClickListener(this);
		lltOpenAccountBankCity.setOnClickListener(this);
		imgBank.setOnClickListener(this);
		
		onBankNameSelectedListener = new OnBankNameSelectedListener() {
			
			@Override
			public void onBankNameSelect(String bankName) {
				bankNameSelected = bankName;
				tvBankName.setText(bankName);
			}
		};
		
		onProvinceSelectedListener = new OnBankProvinceOrCitySelectedListener() {
			
			@Override
			public void onBankProvinceOrCitySelect(ProvinceCityBean data) {
				province = data;
				getProvinceOrCity(data.getCode());
				if (province != null) {
					tvAddr.setText(province.getName());
				}
			}
		};
		
		onCitySelectedListener = new OnBankProvinceOrCitySelectedListener() {
			
			@Override
			public void onBankProvinceOrCitySelect(ProvinceCityBean data) {
				city = data;
				String addr = "";
				if (province != null) {
					addr += province.getName();
				}
				if (city != null) {
					addr += ("   " + city.getName());
				}
				tvAddr.setText(addr);
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fill_bank_card_info_back:
			finish();
			break;
			
		case R.id.act_fill_card_info_commit_tv:   //  跳转赏金猎人个人中心
			commitBankInfo();
			break;
		case R.id.act_fill_card_info_bank_name_llt:   //  银行名称选择
			// TODO
			PopViewHelper.showSelectBankNamePopWindow(this, lltBankName, bankNames, onBankNameSelectedListener);
			break;
		case R.id.act_fill_card_info_open_account_city_llt:  //  开户行城市选择
			// TODO
			PopViewHelper.showSelectBankProvinceOrCityPopWindow(this, lltOpenAccountBankCity, provinces, onProvinceSelectedListener);
			break;
		case R.id.act_fill_card_info_banK_img:
			PopViewHelper.showUpdateAvatarPopupWindow(this, getWindow().getDecorView(), this);
			break;
		}
	}
	
	private void commitBankInfo() {
		String userId = ContentUtils.getUserId(this);
		String realName = edtUserName.getText().toString();
		if (TextUtils.isEmpty(realName)) {
			ToastUtils.SHORT.toast(this, "请输入收款人姓名");
			return;
		}
		String bankCode = edtBankCode.getText().toString();
        if (TextUtils.isEmpty(bankCode)) {
        	ToastUtils.SHORT.toast(this, "请输入银行卡号");
			return;
		}
        
        if (TextUtils.isEmpty(bankNameSelected)) {
        	ToastUtils.SHORT.toast(this, "请选择银行名称");
			return;
        }
        
        String openAccountBankName = edtOpenAccountBankName.getText().toString();
        if (TextUtils.isEmpty(openAccountBankName)) {
        	ToastUtils.SHORT.toast(this, "请输入开户行");
			return;
		}
        
        if (city == null) {
        	ToastUtils.SHORT.toast(this, "请选择开户行城市");
			return;
        }
        
        if (bankFile == null) {
        	ToastUtils.SHORT.toast(this, "请上传银行卡正面照");
			return;
        }
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.commitFeeHunterBankInfo(userId, realName,
				bankCode, bankNameSelected, city.getCode(),
				bankFile, openAccountBankName, new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
						
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
						
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
					    
					    /**
					     * 用户类型    0经济人，1普通会员，2赏金猎人
					     */
					    ContentUtils.updateUserType(FillBankCardInfoActivity.this, 2);
						Jumper.newJumper()
			            .setAheadInAnimation(R.anim.activity_push_in_right)
			            .setAheadOutAnimation(R.anim.activity_alpha_out)
			            .setBackInAnimation(R.anim.activity_alpha_in)
			            .setBackOutAnimation(R.anim.activity_push_out_right)
			            .jumpForResult(FillBankCardInfoActivity.this, FeeHunterInfoActivity.class, CODE_BIND_CARD_INFO);
					}
				});
	}

	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (resultCode == RESULT_OK) {
	        switch (requestCode) {
	        case CODE_BIND_CARD_INFO:
	            finish();
	            break;
	        }
	    }
	}
	
	@Override
	public void onPickedPhoto(File file, Bitmap bm) {
		// TODO Auto-generated method stub
		imgBank.setScaleType(ScaleType.CENTER_CROP);
		bankFile = file;
		imgBank.setImageBitmap(bm);
	}

	@Override
	public int getDisplayWidth() {
		return 200;
	}

	@Override
	public int getDisplayHeight() {
		// TODO Auto-generated method stub
		return 200;
	}
	
	private void getProvinceOrCity(final String cityCode) {
	    
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getBankProvinceOrCity(cityCode, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<ProvinceCityBean> temp = (ArrayList<ProvinceCityBean>) JSON.parseArray(result.getData(), ProvinceCityBean.class);
                if (TextUtils.isEmpty(cityCode)) {
                    provinces.addAll(temp);
                } else {
                    cities.clear();
                    cities.addAll(temp);
                    PopViewHelper.showSelectBankProvinceOrCityPopWindow(FillBankCardInfoActivity.this, lltOpenAccountBankCity, cities, onCitySelectedListener);
                }
                
                
            }
        });
	}
	
	private void getBankNameList() {
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getBankNameList(new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<String> temp = (ArrayList<String>) JSON.parseArray(result.getData(), String.class);
                bankNames.addAll(temp);
            }
        });
	}

	@Override
	public void onAvatarOptionClick(int action) {
		// TODO Auto-generated method stub
		switch (action) {
        case OnAvatarOptionsClickListener.ACTION_CAMERA:   // 相机
            
            if (isCrop) {
                startPickPhotoFromCameraWithCrop();
            } else {
                startPickPhotoFromCamara();
            }
            
            break;
        case OnAvatarOptionsClickListener.ACTION_ALBUM:   // 相册
            if (isCrop) {
                startPickPhotoFromAlbumWithCrop();
            } else {
                startPickPhotoFromAlbum();
            }
            break;
        }
	}
	
}
