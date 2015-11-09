package cn.com.zzwfang.controller;

import java.io.File;
import java.util.ArrayList;

import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;


public interface Action {
	
	// ----用户相关接口---------------------------------------------
	/**
	 * 获取短信验证码
	 */
	void fetchVerifyCode(String phoneNum, ResultHandlerCallback callback);
	
	/**
	 * 注册接口
	 * @param phoneNum  手机号
	 * @param pwd       密码
	 * @param captcha   验证码
	 * @param callback
	 */
	void register(String phoneNum, String pwd, String captcha, ResultHandlerCallback callback);
	
	/**
	 * 检查手机号是否已经被注册
	 * @param phoneNum
	 * @param callback
	 */
	void checkPhoneNumRegistered(String phoneNum, ResultHandlerCallback callback);
	
	
	/**
	 * 登录接口
	 */
	void login(String userName, String pwd, ResultHandlerCallback callback);
	
	
	
	
	/**
	 * 获取token
	 */
	void getToken(ResultHandlerCallback callback);
	/**
	 * 我的
	 */
	void getMine(ResultHandlerCallback callback);

	

	

	

	/**
	 * 修改密码
	 */
	void changePwd(String oldpwd, String newpwd1, String newpwd2,
			ResultHandlerCallback callback);

	/**
	 * 忘记密码（重置密码）
	 */
	void resetPwd(String username, String password_1, String password_2,
			String captcha, String client, ResultHandlerCallback callback);
	
	public void getAppVersionCheck(String version, String client,
			ResultHandlerCallback callback);
	
	
	// ----用户相关接口-----完----------------------------------------

	
	// ----首页相关接口---------------------------------------------

	/**
	 * 获取首页推荐房源列表
	 * @param cityId
	 * @param callback
	 */
	void getRecommendHouseSource(String cityId, ResultHandlerCallback callback);
	// ----首页相关接口------完---------------------------------------
	
	// ----地理位置相关接口---------------------------------------------
	
	
	/**
	 * 获取指定城市id的区域列表
	 * @param cityId
	 * @param callback
	 */
	void getCityList(ResultHandlerCallback callback);

	// ----地理位置相关接口-----完----------------------------------------
	
	/**
	 * 返回搜房神器列表
	 * @param house
	 * @param pageIndex
	 * @param callback
	 */
	void getSearchHouseArtifactResut(String house, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * 新房列表页
	 * @param areaId  区域ID
	 * @param price  价格区间
	 * @param roomType  房型
	 * @param usage  房屋用途
	 * @param specials  特色
	 * @param status  售卖状态
	 * @param cityId  城市ID
	 * @param pageSize  分页大小
	 * @param pageNum 页码
	 * @param callback
	 */
	void getNewHouseList(String areaId, String price, int roomType, int usage,
			int specials, int status, String cityId, int pageSize, int pageNum, ResultHandlerCallback callback);
	
	/**
	 * (总价SalePriceRange、户型HouseType、物业类型PrpUsage、特色标签EstateLabel、售卖状态EstateStatus)、楼层范围（FloorRange）、租价范围（RentPriceRange）
	 * 返回指定请求的name返回字典列表，用在下拉列表时的选择项
	 * @param name
	 * @param callback
	 */
	void getConditionByName(String name, ResultHandlerCallback callback);
	
	void getAreaList(String cityId, ResultHandlerCallback callback);
	
	/**
	 * @param cityId   城市ID
	 * @param areaCondition  区域ID
	 * @param direction  朝向
	 * @param squareCondition   面积范围
	 * @param labelCondition  标签
	 * @param priceCondition  价格
	 * @param roomTypeCondition  户型
	 * @param buildYear  楼龄   传1（5年以内），2（5-10），3（10-20），4（大于20）
	 * @param floor  楼层  传1（低楼层）2（中楼层）3（高
	 * @param proNum   房源编号
	 * @param sort   排序  1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降）
	 * @param pageSize  分页大小
	 * @param pageIndex 页码
	 * @param callback
	 */
	void getSecondHandHouseList(String cityId, TextValueBean areaCondition, String direction,
			TextValueBean squareCondition, TextValueBean labelCondition, 
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			String buildYear, String floor, String proNum, String sort,
			int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * @param proId   房源ID
	 * @param callback
	 */
	void getSecondHandHouseDetail(String proId, ResultHandlerCallback callback);
}
