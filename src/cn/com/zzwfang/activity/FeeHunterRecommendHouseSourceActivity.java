package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.IdNameFloorBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnBuildingSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnEstateCellSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnEstateRoomSelectListener;

/**
 * 赏金猎人    推荐房源   页
 * @author lzd
 *
 */
public class FeeHunterRecommendHouseSourceActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private static final int REQUEST_CONTACT = 1;
	
	private static final int REQUEST_ESTATE = 2;
	
	private TextView tvBack, tvSysContacts, tvSelectEstate;
	
	private RadioButton rbRent, rbSell, rbRentSell;
	
	private EditText edtOwnerName, edtOwnerPhone;
	
	private TextView tvWhichBuilding, tvWhichUnit,  tvWhichFloor, tvWhichHouse;
	
	private LinearLayout lltWhichBuilding, lltWhichUnit, lltWhichFloorAndHouse;
	
	private EditText edtMark;
	
	private TextView tvCommit;
	
	private IdNameBean idNameBean;
	
	/**
	 *  出售 是0  出租是1 租售是2
	 */
	private int trade = 1;
	
	/**
	 * 楼盘Id
	 */
	private String estateId = null;
	
	/**
	 * 楼栋
	 */
	private ArrayList<IdNameBean> buildings = null;
	
	private OnBuildingSelectListener onBuildingSelectListener;
	
	/**
	 * 楼栋号
	 */
	private String buildingId = null;
	/**
	 * 几栋
	 */
	private String buildingName = null;
	
	/**
	 * 单元
	 */
	private ArrayList<IdNameBean> cells = null;
	
	private OnEstateCellSelectListener onEstateCellSelectListener;
	
	/**
	 * 单元号
	 */
	private String cellId = null;
	/**
	 * 几单元
	 */
	private String cellName = null;
	/**
	 * 房间号
	 */
	private ArrayList<IdNameFloorBean> rooms = null;
	
//	private String roomId = null;
	/**
	 * 几楼
	 */
	private String floorName = null;
	/**
	 * 几室
	 */
	private String roomName = null;
	
	private OnEstateRoomSelectListener onEstateRoomSelectListener;
	
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
		
		tvWhichBuilding = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_building_tv);
		tvWhichUnit = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_unit_tv);
		tvWhichFloor = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_floor_tv);
		tvWhichHouse = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_which_house_tv);
		
		lltWhichBuilding = (LinearLayout) findViewById(R.id.act_fee_hunter_recommend_owner_which_building_llt);
		lltWhichUnit = (LinearLayout) findViewById(R.id.act_fee_hunter_recommend_owner_which_unit_llt);
		lltWhichFloorAndHouse = (LinearLayout) findViewById(R.id.act_fee_hunter_recommend_owner_which_floor_and_room_llt);
		
		edtMark = (EditText) findViewById(R.id.act_fee_hunter_recommend_owner_mark);
		
		tvCommit = (TextView) findViewById(R.id.act_fee_hunter_recommend_owner_commit);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		tvSysContacts.setOnClickListener(this);
		tvSelectEstate.setOnClickListener(this);
		
		rbRent.setOnCheckedChangeListener(this);
		rbSell.setOnCheckedChangeListener(this);
		rbRentSell.setOnCheckedChangeListener(this);
		
		lltWhichBuilding.setOnClickListener(this);
		lltWhichUnit.setOnClickListener(this);
		lltWhichFloorAndHouse.setOnClickListener(this);
		
		onBuildingSelectListener = new OnBuildingSelectListener() {
            
            @Override
            public void onBuildingSelect(IdNameBean idNameBean) {
                if (idNameBean != null) {
                    buildingId = idNameBean.getId();
                    buildingName = idNameBean.getName();
                    tvWhichBuilding.setText(idNameBean.getName());
                    
                    cellId = null;
                    cellName = null;
                    tvWhichUnit.setText("");
                    
                    floorName = null;
                    roomName = null;
                    tvWhichFloor.setText("");
                    tvWhichHouse.setText("");
                    getEstateCell();
                }
            }
        };
        
        onEstateCellSelectListener = new OnEstateCellSelectListener() {
            
            @Override
            public void onEstateCellSelect(IdNameBean idNameBean) {
                if (idNameBean != null) {
                    cellId = idNameBean.getId();
                    cellName = idNameBean.getName();
                    tvWhichUnit.setText(idNameBean.getName());
                    
                    floorName = null;
                    roomName = null;
                    tvWhichFloor.setText("");
                    tvWhichHouse.setText("");
                    getEstateRoom();
                }
            }
        };
        
        onEstateRoomSelectListener = new OnEstateRoomSelectListener() {
            
            @Override
            public void onEstateRoomSelect(IdNameFloorBean idNameBean) {
                if (idNameBean != null) {
//                    roomId = idNameBean.getId();
                    floorName = idNameBean.getFloor();
                    roomName = idNameBean.getName();
                    tvWhichFloor.setText(idNameBean.getFloor());
                    tvWhichHouse.setText(idNameBean.getName());
                }
            }
        };
		
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
		case R.id.act_fee_hunter_recommend_owner_which_building_llt: // 栋
		    if (buildings != null && buildings.size() > 0) {
		        PopViewHelper.showSelectBuildingPopWindow(this, lltWhichBuilding, buildings, onBuildingSelectListener);
		    } else {
		        if (TextUtils.isEmpty(estateId)) {
		            ToastUtils.SHORT.toast(this, "请选择楼盘");
		        } else {
		            ToastUtils.SHORT.toast(this, "该楼盘没有楼栋信息");
		        }
		    }
		    break;
		case R.id.act_fee_hunter_recommend_owner_which_unit_llt: // 单元
		    if (cells != null && cells.size() > 0) {
		        PopViewHelper.showSelectEstateCellPopWindow(this, lltWhichUnit, cells, onEstateCellSelectListener);
		    } else {
		        if (TextUtils.isEmpty(buildingId)) {
		            ToastUtils.SHORT.toast(this, "请选择楼栋信息");
		        } else {
		            ToastUtils.SHORT.toast(this, "该楼栋没有单元信息");
		        }
		    }
		    break;
		case R.id.act_fee_hunter_recommend_owner_which_floor_and_room_llt: // 层和房间号
		    if (rooms != null && rooms.size() > 0) {
		        PopViewHelper.showSelectEstateRoomPopWindow(this, lltWhichFloorAndHouse, rooms, onEstateRoomSelectListener);
		    } else {
		        if (TextUtils.isEmpty(cellId)) {
                    ToastUtils.SHORT.toast(this, "请选择单元信息");
                } else {
                    ToastUtils.SHORT.toast(this, "该单元没有房号信息");
                }
		    }
		    break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// （0：出售、1：出租、2：租售）
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_fee_hunter_recommend_owner_rb_rent:  // 租房
				trade = 1;
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_sell:  // 售房
				trade = 0;
				break;
			case R.id.act_fee_hunter_recommend_owner_rb_rent_sell:  // 租售
				trade = 2;
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
//		String rigdepole = tvWhichBuilding.getText().toString();
//		//  几单元
//		String unit = tvWhichUnit.getText().toString();
//		//  几层
//		String floor = tvWhichFloor.getText().toString();
//		//   房号
//		String roomNo = tvWhichHouse.getText().toString();
		
//		if (TextUtils.isEmpty(rigdepole)) {
//			ToastUtils.SHORT.toast(this, "请输入楼栋");
//			return;
//		}
		
		if (TextUtils.isEmpty(buildingName)) {
            ToastUtils.SHORT.toast(this, "请选择楼栋");
            return;
        }
		
//		if (TextUtils.isEmpty(unit)) {
//			ToastUtils.SHORT.toast(this, "请输入单元");
//			return;
//		}
		if (TextUtils.isEmpty(cellName)) {
            ToastUtils.SHORT.toast(this, "请选择单元");
            return;
        }
		
//		if (TextUtils.isEmpty(floor)) {
//			ToastUtils.SHORT.toast(this, "请输入楼层");
//			return;
//		}
		
//		if (TextUtils.isEmpty(floorName)) {
//            ToastUtils.SHORT.toast(this, "请选择楼层");
//            return;
//        }
		
//		if (TextUtils.isEmpty(roomNo)) {
//			ToastUtils.SHORT.toast(this, "请输入房号");
//			return;
//		}
		if (TextUtils.isEmpty(roomName)) {
            ToastUtils.SHORT.toast(this, "请选择房号");
            return;
        }
		
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			ToastUtils.SHORT.toast(this, "请选择城市");
			return;
		}
		
		String remark = edtMark.getText().toString();
		String userId = ContentUtils.getUserId(this);
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
//		actionImpl.recommendFeeHunterHouseSource(userId, idNameBean.getId(),
//				rigdepole, unit, roomNo,
//				idNameBean.getName(), cityBean.getSiteId(), floor,
//				trade, contactName, contactPhone,
//				remark, new ResultHandlerCallback() {
//					
//					@Override
//					public void rc999(RequestEntity entity, Result result) {
//					}
//					
//					@Override
//					public void rc3001(RequestEntity entity, Result result) {
//					}
//					
//					@Override
//					public void rc0(RequestEntity entity, Result result) {
//						ToastUtils.SHORT.toast(FeeHunterRecommendHouseSourceActivity.this, "推荐房源成功");
//						finish();
//					}
//				});
		
		actionImpl.recommendFeeHunterHouseSource(userId, idNameBean.getId(),
		        buildingName, cellName, roomName,
                idNameBean.getName(), cityBean.getSiteId(), floorName,
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
                        ToastUtils.SHORT.toast(FeeHunterRecommendHouseSourceActivity.this, "推荐房源成功");
                        finish();
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
				estateId = idNameBean.getId();
				
				buildingId = null;
                buildingName = null;
                tvWhichBuilding.setText("");
                
                cellId = null;
                cellName = null;
                tvWhichUnit.setText("");
                
                floorName = null;
                roomName = null;
                tvWhichFloor.setText("");
                tvWhichHouse.setText("");
                
                buildings = null;
                cells = null;
                rooms = null;
				
				getEstateBuilding();
				break;
			}
		}
	}
	
	private void getEstateBuilding() {
//		estateId = "56cbbf47e00fea2eccdb61de";
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		if (TextUtils.isEmpty(estateId)) {
			return;
		}
		actionImpl.getEstateBuilding(estateId, new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						
						buildings = (ArrayList<IdNameBean>) JSON.parseArray(result.getData(), IdNameBean.class);
					}
				});
	}
	
	private void getEstateCell() {
	    if (TextUtils.isEmpty(buildingId)) {
	        return;
	    }
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getEstateCell(buildingId, new ResultHandlerCallback() {

            @Override
            public void rc0(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                cells = (ArrayList<IdNameBean>) JSON.parseArray(result.getData(), IdNameBean.class);
            }

            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }

            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
	        
	    });
	}
	
	private void getEstateRoom() {
	    if (TextUtils.isEmpty(cellId)) {
	        return;
	    }
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getEstateRoom(cellId, new ResultHandlerCallback() {

            @Override
            public void rc0(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                rooms = (ArrayList<IdNameFloorBean>) JSON.parseArray(result.getData(), IdNameFloorBean.class);
            }

            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }

            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
	        
	    });
	}

}
