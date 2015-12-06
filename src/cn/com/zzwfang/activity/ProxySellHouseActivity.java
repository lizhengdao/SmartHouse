package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;

/**
 * 委托售房
 * @author lzd
 *
 */
public class ProxySellHouseActivity extends BaseActivity implements OnClickListener {

	
	private static final int REQUEST_ESTATE = 120;
	
	private TextView tvBack, tvSelectEstateName, tvCommit;
	private LinearLayout lltHouseType, lltDirection, lltDecoration;
	private TextView tvHouseType, tvDirection, tvDecoration;
	
	private EditText edtHouseRooms, edtHouseHalls, edtWcs;
	
	private EditText edtWhichFloor, edtTotalFloor;
	private EditText edtSquare;
	
	private EditText edtWhichBuilding, edtWhichUnit, edtWhichNo;
	private EditText edtSourceName; 
	
	private EditText edtOwnerName;
	private TextView tvPhone;
	
	private CheckBox cbxSex;
	
	private IdNameBean idNameBean;
	
	private TextValueBean houseTypeCondition;
	private TextValueBean directionCondition;
	private TextValueBean decoratonCondition;
	
	public static final String HouseType = "PrpUsage";
	public static final String Direction ="Direction";
	public static final String Decoration = "DecorationType";
	/**
	 * 户型
	 */
	private ArrayList<TextValueBean> houseTypes = new ArrayList<TextValueBean>();
	private ArrayList<TextValueBean> directions = new ArrayList<TextValueBean>();
	private ArrayList<TextValueBean> decorations = new ArrayList<TextValueBean>();
	
	/**
	 * 房型
	 */
	private OnConditionSelectListener onHouseTypeSelectListener;
	private OnConditionSelectListener onDirectionSelectListener;
	private OnConditionSelectListener onDecorationSelectListener;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		
		getConditionList(HouseType);
		getConditionList(Direction);
		getConditionList(Decoration);
	}
	
	private void initView() {
		setContentView(R.layout.act_proxy_sell_house);
		
		tvBack = (TextView) findViewById(R.id.act_proxy_sell_house_back);
		tvSelectEstateName = (TextView) findViewById(R.id.act_proxy_sell_house_select_estate_name);
		tvHouseType = (TextView) findViewById(R.id.act_proxy_sell_house_type_tv);
		tvDirection = (TextView) findViewById(R.id.act_proxy_sell_house_direction_tv);
		tvDecoration = (TextView) findViewById(R.id.act_proxy_sell_house_decoration_tv);
		
		lltHouseType = (LinearLayout) findViewById(R.id.act_proxy_sell_house_select_house_type);
		lltDirection = (LinearLayout) findViewById(R.id.act_proxy_sell_house_direction_llt);
		lltDecoration = (LinearLayout) findViewById(R.id.act_proxy_sell_house_decoration_llt);
		
		edtHouseRooms = (EditText) findViewById(R.id.act_proxy_sell_house_rooms);
		edtHouseHalls = (EditText) findViewById(R.id.act_proxy_sell_house_halls);
		edtWcs = (EditText) findViewById(R.id.act_proxy_sell_house_toilets);
		
		edtWhichFloor = (EditText) findViewById(R.id.act_proxy_sell_house_which_floor);
		edtTotalFloor = (EditText) findViewById(R.id.act_proxy_sell_house_total_floors);
		edtSquare = (EditText) findViewById(R.id.act_proxy_sell_house_square);
		
		edtWhichBuilding = (EditText) findViewById(R.id.act_proxy_sell_house_which_building);
		edtWhichUnit = (EditText) findViewById(R.id.act_proxy_sell_house_which_unit);
		edtWhichNo = (EditText) findViewById(R.id.act_proxy_sell_house_which_num);
		
		edtSourceName = (EditText) findViewById(R.id.act_proxy_sell_house_source_title);
		edtOwnerName = (EditText) findViewById(R.id.act_proxy_sell_house_name);
		
		cbxSex = (CheckBox) findViewById(R.id.act_proxy_sell_house_sex_cbx);
		
		tvPhone = (TextView) findViewById(R.id.act_proxy_sell_house_phone);
		
		tvCommit = (TextView) findViewById(R.id.act_proxy_sell_house_commit);
		
		tvBack.setOnClickListener(this);
		tvSelectEstateName.setOnClickListener(this);
		
		tvPhone.setText(ContentUtils.getLoginPhone(this));
		
		lltHouseType.setOnClickListener(this);
		lltDirection.setOnClickListener(this);
		lltDecoration.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		
        onHouseTypeSelectListener = new OnConditionSelectListener() {
			
			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				houseTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
			}
		};
		
		onDirectionSelectListener = new OnConditionSelectListener() {
			
			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				directionCondition = txtValueBean;
				tvDirection.setText(txtValueBean.getText());
			}
		};
		
		onDecorationSelectListener = new OnConditionSelectListener() {
			
			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				decoratonCondition = txtValueBean;
				tvDecoration.setText(txtValueBean.getText());
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_proxy_sell_house_back:
			finish();
			break;
		case R.id.act_proxy_sell_house_select_estate_name:  //  选择小区名称
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jumpForResult(this, SelectEstateActivity.class,
					REQUEST_ESTATE);
			break;
		case R.id.act_proxy_sell_house_select_house_type:
			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType, houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.act_proxy_sell_house_direction_llt:
			PopViewHelper.showSelectHouseTypePopWindow(this, lltDirection, directions, onDirectionSelectListener);
			break;
		case R.id.act_proxy_sell_house_decoration_llt:
			PopViewHelper.showSelectHouseTypePopWindow(this, lltDecoration, decorations, onDecorationSelectListener);
			break;
		case R.id.act_proxy_sell_house_commit:
			entrustSell();
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_ESTATE:
				idNameBean = (IdNameBean) data
				.getSerializableExtra(SelectEstateActivity.INTENT_ESTATE);
		        tvSelectEstateName.setText(idNameBean.getName());
				break;
			}
		}
	}
	
	private void entrustSell() {
		if (idNameBean == null) {
			ToastUtils.SHORT.toast(this, "请选择小区名称");
			return;
		}
		
		// 房屋类型  其实是物业类型
		if (houseTypeCondition == null) {
			ToastUtils.SHORT.toast(this, "请选择房屋类型");
			return;
		}
		
		//  户型
		String countFang = edtHouseRooms.getText().toString();
		if (TextUtils.isEmpty(countFang)) {
			ToastUtils.SHORT.toast(this, "请输入室信息");
			return;
		}
		
		String halls = edtHouseHalls.getText().toString();
		if (TextUtils.isEmpty(halls)) {
			ToastUtils.SHORT.toast(this, "请输入厅信息");
			return;
		}
		String wc = edtWcs.getText().toString();
		if (TextUtils.isEmpty(wc)) {
			ToastUtils.SHORT.toast(this, "请输入厅卫信息");
			return;
		}
		String floor = edtWhichFloor.getText().toString();
		if (TextUtils.isEmpty(floor)) {
			ToastUtils.SHORT.toast(this, "请输入楼层");
			return;
		}
		String totalFloor = edtTotalFloor.getText().toString();
		if (TextUtils.isEmpty(totalFloor)) {
			ToastUtils.SHORT.toast(this, "请输入总楼层");
			return;
		}
		int floorInt = Integer.valueOf(floor);
		int totalFloorInt = Integer.valueOf(totalFloor);
		if (floorInt > totalFloorInt) {
		    ToastUtils.SHORT.toast(this, "楼层应小于等于总楼层"); 
            return;
		}
		
		String square = edtSquare.getText().toString();
		if (TextUtils.isEmpty(square)) {
			ToastUtils.SHORT.toast(this, "请输入房屋面积");
			return;
		}
		
		if (directionCondition != null) {
			ToastUtils.SHORT.toast(this, "请选择朝向");
			return;
		}
		
		if (decoratonCondition != null) {
			ToastUtils.SHORT.toast(this, "请选择装修程度");
			return;
		}
		
		String ridgepole = edtWhichBuilding.getText().toString();
		if (TextUtils.isEmpty(ridgepole)) {
			ToastUtils.SHORT.toast(this, "请输入楼栋号");
			return;
		}
		
		String unit = edtWhichUnit.getText().toString();
		if (TextUtils.isEmpty(unit)) {
			ToastUtils.SHORT.toast(this, "请输入单元号");
			return;
		}
		String roomNo = edtWhichNo.getText().toString();
		if (TextUtils.isEmpty(roomNo)) {
			ToastUtils.SHORT.toast(this, "请输入房间号");
			return;
		}
		
		String title = edtSourceName.getText().toString();
		if (TextUtils.isEmpty(title)) {
			ToastUtils.SHORT.toast(this, "请输入房源标题");
			return;
		}
		
		String name = edtOwnerName.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ToastUtils.SHORT.toast(this, "请输入姓名");
			return;
		}
		
		boolean sex = cbxSex.isChecked();
		
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			ToastUtils.SHORT.toast(this, "您还未选定城市");
			return;
		}
		String userId = ContentUtils.getUserId(this);
		
		String price = null;
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.entrustSellHouse(idNameBean.getId(),
				idNameBean.getName(), ridgepole, unit,
				roomNo, houseTypeCondition.getValue(), price,
				countFang, halls, wc,
				square, directionCondition.getValue(), totalFloor,
				floor, decoratonCondition.getValue(), title,
				cityBean.getSiteId(), userId, name,
				sex, new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
						
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
						
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						ToastUtils.SHORT.toast(ProxySellHouseActivity.this, "提交成功");
					}
				});
	}
	
	public void getConditionList(final String conditionName) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getConditionByName(conditionName, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
				if (temp != null && temp.size() > 0) {
					temp.remove(0);
				}
				if (HouseType.equals(conditionName)) {
					houseTypes.addAll(temp);
				} else if (Decoration.equals(conditionName)) {
					decorations.addAll(temp);
				} else if (Direction.equals(conditionName)) {
					directions.addAll(temp);
				}
			}
		});
	}
}
