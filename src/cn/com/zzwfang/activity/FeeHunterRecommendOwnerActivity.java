package cn.com.zzwfang.activity;

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

/**
 * 赏金猎人    推荐房源   页
 * @author lzd
 *
 */
public class FeeHunterRecommendOwnerActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private static final int REQUEST_CONTACT = 1;
	
	private static final int REQUEST_ESTATE = 2;
	
	private TextView tvBack, tvSysContacts, tvSelectEstate;
	
	private RadioButton rbRent, rbSell, rbRentSell;
	
	private EditText edtOwnerName, edtOwnerPhone;
	
	private EditText edtWhichBuilding, edtWhichUnit,  edtWhichFloor, edtWhichHouse;
	
	private EditText edtMark;
	
	private TextView tvCommit;
	
	private IdNameBean idNameBean;
	
	private int trade = 1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_owner);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_back);
		tvSysContacts = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_sys_contacts);
		rbRent = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_rent);
		rbSell = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_sell);
		rbRentSell = (RadioButton) findViewById(R.id.act_fee_hunter_recommend_owner_rb_rent_sell);
		
		edtOwnerName = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_name);
		edtOwnerPhone = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_phone);
		tvSelectEstate = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_select_estate);
		
		edtWhichBuilding = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_which_building);
		edtWhichUnit = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_which_unit);
		edtWhichFloor = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_which_floor);
		edtWhichHouse = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_which_house);
		
		edtMark = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_mark);
		
		tvCommit = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_commit);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		tvSysContacts.setOnClickListener(this);
		tvSelectEstate.setOnClickListener(this);
		
		rbRent.setOnCheckedChangeListener(this);
		rbSell.setOnCheckedChangeListener(this);
		rbRentSell.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_owner_back:
			finish();
			break;
			
		case R.id.act_fee_hunter_recommend_owner_sys_contacts:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setData(ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, REQUEST_CONTACT);
			break;
			
		case R.id.act_fee_hunter_recommend_owner_select_estate:
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jumpForResult(this, SelectEstateActivity.class,
					REQUEST_ESTATE);
			break;
			
		case R.id.act_fee_hunter_recommend_owner_commit:   //  提交
			recommendHouseSource();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_fee_hunter_recommend_owner_rb_rent:
				trade = 1;
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_sell:
				trade = 2;
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_rent_sell:
				trade = 3;
				break;
			}
		}
		
	}
	
	private void recommendHouseSource() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		
		String contactName = edtOwnerName.getText().toString();
		if (TextUtils.isEmpty(contactName)) {
			ToastUtils.SHORT.toast(this, "请输入被推荐人姓名");
			return;
		}
		
		String contactPhone = edtOwnerPhone.getText().toString();
		if (TextUtils.isEmpty(contactPhone)) {
			ToastUtils.SHORT.toast(this, "请输入被推荐人手机号码");
			return;
		}
		
		if (idNameBean == null) {
			ToastUtils.SHORT.toast(this, "请选择楼盘名称");
			return;
		}
		
		//  几栋
		String rigdepole = edtWhichBuilding.getText().toString();
		//  几单元
		String unit = edtWhichUnit.getText().toString();
		//  几层
		String floor = edtWhichFloor.getText().toString();
		//   房号
		String roomNo = edtWhichHouse.getText().toString();
		
		if (TextUtils.isEmpty(rigdepole)) {
			ToastUtils.SHORT.toast(this, "请输入楼栋");
			return;
		}
		
		if (TextUtils.isEmpty(unit)) {
			ToastUtils.SHORT.toast(this, "请输入单元");
			return;
		}
		
		if (TextUtils.isEmpty(floor)) {
			ToastUtils.SHORT.toast(this, "请输入楼层");
			return;
		}
		
		if (TextUtils.isEmpty(roomNo)) {
			ToastUtils.SHORT.toast(this, "请输入房号");
			return;
		}
		
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			ToastUtils.SHORT.toast(this, "请选择城市");
			return;
		}
		
		String remark = edtMark.getText().toString();
		
		/**
		 * 赏金猎人  推荐房源
		 * @param estateId   楼盘ID
		 * @param rigdepole  楼栋号
		 * @param unit   单元号
		 * @param roomNo   房间号
		 * @param eatateName  楼盘名称
		 * @param cityId  站点ID
		 * @param floor   楼层
		 * @param trade   交易状态    （0：出售、1：出租）
		 * @param contactName   客户名称
		 * @param telNum   客户电话
		 * @param remark   情况介绍
		 * @param callback
		 */
		actionImpl.recommendFeeHunterHouseSource(idNameBean.getId(),
				rigdepole, unit, roomNo,
				idNameBean.getName(), cityBean.getSiteId(), floor,
				trade, contactName, contactPhone,
				remark, new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						ToastUtils.SHORT.toast(FeeHunterRecommendOwnerActivity.this, "推荐房源成功");
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
				edtOwnerName.setText(username);
				
				if (!TextUtils.isEmpty(userPhone)) {
					userPhone = userPhone.replace(" ", "");
					int len = userPhone.length();
					if (len > 11) {
						userPhone = userPhone.substring(len - 11, len);
					}
				}
				edtOwnerPhone.setText(userPhone);
				break;

			case REQUEST_ESTATE:
				idNameBean = (IdNameBean) data
						.getSerializableExtra(SelectEstateActivity.INTENT_ESTATE);
				tvSelectEstate.setText(idNameBean.getName());
				break;
			}
		}
	}
}
