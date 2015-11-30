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
	 * 注册接口     已检查
	 * @param phoneNum   手机号
	 * @param pwd    密码
	 * @param captcha  验证码
	 * @param type  类型   （1：普通用户、2：赏金猎人）
	 * @param callback
	 */
	void register(String phoneNum, String pwd, String captcha, int type, ResultHandlerCallback callback);
	
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
	void changePwd(String phoneNum, String oldPwd, String newPwd,
			ResultHandlerCallback callback);

	/**
	 * 忘记密码（重置密码）
	 */
	void resetPwd(String tel, String password, String captcha, ResultHandlerCallback callback);
	
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
	void getNewHouseList(String cityId, TextValueBean areaCondition, TextValueBean priceCondition,
			TextValueBean roomTypeCondition, TextValueBean usageCondition,
			TextValueBean labelCondition, TextValueBean statusCondition,
			String keyWords, int pageSize, int pageNum, ResultHandlerCallback callback);
	
	/**
	 * 获取新房详情
	 * @param estateId  小区ID
	 * @param callback
	 */
	void getNewHouseDetail(String estateId, ResultHandlerCallback callback);
	
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
	 * @param key    搜索关键字
	 * @param pageSize  分页大小
	 * @param pageIndex 页码
	 * @param callback
	 */
	void getSecondHandHouseList(String cityId, TextValueBean areaCondition, String direction,
			TextValueBean squareCondition, TextValueBean labelCondition, 
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			String buildYear, String floor, String proNum, String sort, String key,
			int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * @param proId   房源ID
	 * @param callback
	 */
	void getSecondHandHouseDetail(String proId, ResultHandlerCallback callback);
	
	/**
	 * @param cityId  城市ID
	 * @param areaId  区域ID
	 * @param rentPriceRange  租金区间
	 * @param square   面积范围
	 * @param sort   排序
	 * @param keyWords   关键字
	 * @param direction   朝向
	 * @param roomTypeCondition   房型
	 * @param pageSize  分页大小
	 * @param pageIndex  页码
	 * @param callback
	 */
	void getRentHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean squareCondition, String sort,
			String keyWords, String direction, TextValueBean roomTypeCondition,
			int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * 获取租房详情
	 * @param proId  房源ID
	 * @param callback
	 */
	void getRentHouseDetail(String proId, ResultHandlerCallback callback);
	
	/**
	 * 资讯类别
	 * @param callback
	 */
	void getNewsType(ResultHandlerCallback callback);
	
	/**
	 * @param newsTypeId
	 * @param callback
	 */
	void getNewsDetail(String newsTypeId, ResultHandlerCallback callback);
	
	void commitFeedback(String userId, String content, String phone, ResultHandlerCallback callback);
	
	/**
	 * 获取我的关注列表
	 * @param userId    用户Id  
	 * @param pageSize   每页条数
	 * @param pageIndex   当前页
	 * @param callback
	 */
	void getAttentionList(String userId, int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * 关注房源
	 * @param userId   用户的id
	 * @param propertyId  房源id
	 * @param callback
	 */
	void attentionToHouse(String userId, String propertyId, ResultHandlerCallback callback);
	
	/**
	 * 获取我的房屋列表
	 * @param userPhone  用户的UserID(手机号)
	 * @param pageSize  每页条数
	 * @param pageIndex  当前页
	 * @param callback
	 */
	void getMyHouseList(String userPhone, int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * 获取常见问题列表
	 * @param callback
	 */
	void getCommonProblemList(ResultHandlerCallback callback);
	
	/**
	 * 根据userid查看个人钱包
	 * @param userId  用户名
	 * @param callback
	 */
	void getWalletInfo(String userId, ResultHandlerCallback callback);
	
	/**
	 * 赏金猎人  我的客户筛选条件
	 * @param callback
	 */
	void getFeeHunterMyCustomerCondition(ResultHandlerCallback callback);
	
	/**
	 * 赏金猎人  我的客户列表
	 * @param callback
	 */
	void getFeeHunterMyCustomerList(String userId, String status, int pageSize, int pageIndex, ResultHandlerCallback callback);
//	void getMyHouseSourceInfoChanges();
	
	/**
	 * 返回搜索楼盘搜索列表
	 * @param area   区域Id
	 * @param totalPrice   总价范围
	 * @param house   房型
	 * @param type   类型
	 * @param label   特色
	 * @param status  售卖状态
	 * @param key  搜索关键字
	 * @param callback
	 */
	void getSearchHouseList(TextValueBean area, TextValueBean totalPrice,
			TextValueBean house, TextValueBean type, TextValueBean label,
			TextValueBean status, String key, ResultHandlerCallback callback);
	
	/**
	 * 获取资讯列表
	 * @param newsId  类别ID
	 * @param pageSize   当前页条数
	 * @param pageIndex  当前页码
	 * @param callback
	 */
	void getNewsList(String newsId, int pageSize, int pageIndex, ResultHandlerCallback callback);
	
	void createIMAccount(String id, String pwd, ResultHandlerCallback callback);
	
	/**
	 * 获取小区成交历史
	 * @param estId    小区ID
	 * @param roomType    房型  0：不限 1：一居 2：两居 3：三居 4：四居
	 * @param callback
	 */
	void getResidentialTransactionHistory(String estId, String roomType, ResultHandlerCallback callback);
	
	/**
	 * 获取   地图找房  默认显示状态
	 * @param tradeType  出售/出租   (0是出售，1是出租)
	 * @param cityId  城市id
	 * @param callback
	 */
	void getMapFindHouseData(int tradeType, String cityId, ResultHandlerCallback callback);
	
//	void getMapFindHouseEstate(TextValueBean area, TextValueBean totalPrice,
//			TextValueBean house, TextValueBean type, TextValueBean label,
//			TextValueBean status, String key, ResultHandlerCallback callback);
	
	/**
	 * 赏金猎人推荐业主
	 * @param estateId    楼盘ID
	 * @param minPrice   最小价格
	 * @param maxPrice  最大价格
	 * @param monthlPay   月供
	 * @param contactName  联系人名称
	 * @param phone   联系人电话
	 * @param remark  备注
	 * @param userId  用户ID
	 * @param citeId  站点ID
	 * @param callback
	 */
	void recommendFeeHunterCustomer(String estateId, String minPrice, String maxPrice,
			String monthlPay, String contactName, String phone,
			String remark, String userId, String citeId, ResultHandlerCallback callback);
	
	/**
	 * @param keywords  关键字
	 * @param top  显示条数
	 * @param type  来源类型
	 * @param callback
	 */
	void getAutoCompleteEstate(String keywords, int top, int type, ResultHandlerCallback callback);
	
	/**
	 * 赏金猎人  推荐房源
	 * @param estateId   楼盘ID
	 * @param rigdepole  楼栋号
	 * @param unit   单元号
	 * @param roomNo   房间号
	 * @param eatateName  楼盘名称
	 * @param cityId  站点ID
	 * @param floor   楼层
	 * @param trade   交易状态    “1”出租，”2”出售，”3”租售
	 * @param contactName   客户名称
	 * @param telNum   客户电话
	 * @param remark   情况介绍
	 * @param callback
	 */
	void recommendFeeHunterHouseSource(String estateId, String rigdepole,
			String unit, String roomNo, String estateName,
			String cityId, String floor, int trade,
			String contactName, String telNum,
			String remark, ResultHandlerCallback callback);
	
	/**
	 * 获取置业经纪人详情，输入经纪人ID
	 * @param agentId   经纪人ID
	 * @param callback
	 */
	void getAgentInfoDetail(String agentId, ResultHandlerCallback callback);
	
	/**
	 * 赏金猎人  活动规则
	 * @param callback
	 */
	void getFeeHunterRule(ResultHandlerCallback callback);
	
	/**
	 * 我的房源-赏金猎人（推荐房源列表）
	 * @param userId   用户ID
	 * @param pageSize   显示条数
	 * @param pageIndex   当前页码
	 * @param callback
	 */
	void getFeeHunterRecommendHouseSourceList(String userId, int pageSize,
			int pageIndex, ResultHandlerCallback callback);
	
	/**
	 * 房源的进度查看
	 * @param houseSourceId
	 * @param callback
	 */
	void getHouseSourceProgress(String houseSourceId, ResultHandlerCallback callback);
	
	/**
	 * 删除我关注的房源
	 * @param userId  用户ID
	 * @param sourceId   房源ID
	 * @param callback
	 */
	void deleteCollection(String userId, String sourceId, ResultHandlerCallback callback);
	
	/**
	 * 我的需求条件
	 * @param userId  用户ID
	 * @param callback
	 */
	void getMyDemandInfo(String userId, ResultHandlerCallback callback);
	
	
	/**
	 * 发送消息
	 * @param from
	 * @param to
	 * @param message
	 * @param callback
	 */
	void sendMessage(String from, String to, String message, ResultHandlerCallback callback);
	
	/**
	 * 获取月供范围列表
	 * @param callback
	 */
	void getMonthlyPayRange(ResultHandlerCallback callback);
	
}
