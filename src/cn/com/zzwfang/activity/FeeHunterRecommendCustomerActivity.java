package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 赏金猎人 推荐客户
 * 
 * @author MISS-万
 * 
 */
public class FeeHunterRecommendCustomerActivity extends BaseActivity
		implements OnClickListener, OnCheckedChangeListener {

	private static final int REQUEST_CONTACT = 1;

	private static final int REQUEST_ESTATE = 2;

	private TextView tvBack, tvSysContacts, tvSelectEstateName, tvCommit;

	private EditText edtContactName, edtContactPhone, edtMinPrice, edtMaxPrice,
			edtMonthlyPay, edtRemark;

	private RadioButton rbBuy, rbRent;

	private IdNameBean idNameBean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		initView();
	}

	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_second_hand_house);

		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_back);
		rbBuy = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_rb_buy);
		rbRent = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_rb_rent);

		tvSysContacts = (TextView) findViewById(R.id.act_fee_hunter_recommend_customer_sys_contacts);
		edtContactName = (EditText) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_name);
		edtContactPhone = (EditText) findViewById(R.id.act_fee_hunter_recommend_second_hand_house_phone1);
		edtMinPrice = (EditText) findViewById(R.id.act_fee_hunter_recommend_customer_min_price);
		edtMaxPrice = (EditText) findViewById(R.id.act_fee_hunter_recommend_customer_max_price);
		edtMonthlyPay = (EditText) findViewById(R.id.act_fee_hunter_recommend_customer_monthly_pay);
		tvSelectEstateName = (TextView) findViewById(R.id.act_fee_hunter_recommend_customer_estate_name);
		tvCommit = (TextView) findViewById(R.id.act_fee_hunter_recommend_customer_commit);
		edtRemark = (EditText) findViewById(R.id.act_fee_hunter_recommend_customer_mark);

		tvBack.setOnClickListener(this);
		rbBuy.setOnCheckedChangeListener(this);
		rbRent.setOnCheckedChangeListener(this);
		tvSysContacts.setOnClickListener(this);
		tvSelectEstateName.setOnClickListener(this);
		tvCommit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_second_hand_house_back:
			finish();
			break;
		case R.id.act_fee_hunter_recommend_customer_sys_contacts:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setData(ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, REQUEST_CONTACT);
			break;
		case R.id.act_fee_hunter_recommend_customer_estate_name:
			Jumper.newJumper()
					.setAheadInAnimation(R.anim.activity_push_in_right)
					.setAheadOutAnimation(R.anim.activity_alpha_out)
					.setBackInAnimation(R.anim.activity_alpha_in)
					.setBackOutAnimation(R.anim.activity_push_out_right)
					.jumpForResult(this, SelectEstateActivity.class,
							REQUEST_ESTATE);

			break;

		case R.id.act_fee_hunter_recommend_customer_commit:
			recommendCustomer();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_fee_hunter_recommend_second_hand_house_rb_buy: // 求购
				break;

			case R.id.act_fee_hunter_recommend_second_hand_house_rb_rent: // 租求购
				break;
			}
		}
	}

	private void recommendCustomer() {

		String contactName = edtContactName.getText().toString();
		if (TextUtils.isEmpty(contactName)) {
			ToastUtils.SHORT.toast(this, "请输入被推荐人姓名");
			return;
		}
		String contactPhone = edtContactPhone.getText().toString();
		if (TextUtils.isEmpty(contactPhone)) {
			ToastUtils.SHORT.toast(this, "请输入被推荐人手机号码");
			return;
		}
		String minPrice = edtMinPrice.getText().toString();
		if (TextUtils.isEmpty(minPrice)) {
			ToastUtils.SHORT.toast(this, "请输入最低总价");
			return;
		}
		String maxPrice = edtMaxPrice.getText().toString();
		if (TextUtils.isEmpty(maxPrice)) {
			ToastUtils.SHORT.toast(this, "请输入最高总价");
			return;
		}
		
		float minPriceFloat = Integer.valueOf(minPrice);
		float maxPriceFloat = Integer.valueOf(maxPrice);
		if (minPriceFloat >= maxPriceFloat) {
		    ToastUtils.SHORT.toast(this, "最低总价应小于最高总价");
		    return;
		}

		String monthlyPay = edtMonthlyPay.getText().toString();
		if (TextUtils.isEmpty(monthlyPay)) {
			ToastUtils.SHORT.toast(this, "请输入月供");
			return;
		}

		String remark = edtRemark.getText().toString();

		if (idNameBean == null) {
			ToastUtils.SHORT.toast(this, "请选择楼盘名称");
			return;
		}
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			ToastUtils.SHORT.toast(this, "请选择城市");
			return;
		}
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.recommendFeeHunterCustomer(idNameBean.getId(), minPrice,
				maxPrice, monthlyPay, contactName, contactPhone, remark,
				userId, cityBean.getSiteId(), new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						ToastUtils.SHORT.toast(FeeHunterRecommendCustomerActivity.this, "推荐成功");
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CONTACT:
				if (data == null) {
					return;
				}
				ContentResolver reContentResolverol = getContentResolver();
				Uri contactData = data.getData();
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(contactData, null, null, null,
						null);
				cursor.moveToFirst();
				String username = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				Cursor phone = reContentResolverol.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				String userPhone = "";
				while (phone.moveToNext()) {
					userPhone = phone
							.getString(phone
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				}
				edtContactName.setText(username);
				if (!TextUtils.isEmpty(userPhone)) {
					userPhone = userPhone.replace(" ", "");
					int len = userPhone.length();
					if (len > 11) {
						userPhone = userPhone.substring(len - 11, len);
					}
				}
				
				edtContactPhone.setText(userPhone);
				break;

			case REQUEST_ESTATE:
				idNameBean = (IdNameBean) data
						.getSerializableExtra(SelectEstateActivity.INTENT_ESTATE);
				tvSelectEstateName.setText(idNameBean.getName());
				break;
			}
		}
	}
}
