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
	 * 注册接口 已检查
	 * 
	 * @param phoneNum
	 *            手机号
	 * @param pwd
	 *            密码
	 * @param captcha
	 *            验证码
	 * @param type
	 *            类型 （1：普通用户、2：赏金猎人）
	 * @param callback
	 */
	void register(String phoneNum, String pwd, String captcha, int type,
			ResultHandlerCallback callback);

	/**
	 * 检查手机号是否已经被注册
	 * 
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
	void resetPwd(String tel, String password, String captcha,
			ResultHandlerCallback callback);

	public void getAppVersionCheck(String version, String client,
			ResultHandlerCallback callback);

	// ----用户相关接口-----完----------------------------------------

	// ----首页相关接口---------------------------------------------

	/**
	 * 获取首页推荐房源列表
	 * 
	 * @param cityId
	 * @param callback
	 */
	void getRecommendHouseSource(String cityId, ResultHandlerCallback callback);

	// ----首页相关接口------完---------------------------------------

	// ----地理位置相关接口---------------------------------------------

	/**
	 * 获取指定城市id的区域列表
	 * 
	 * @param cityId
	 * @param callback
	 */
	void getCityList(ResultHandlerCallback callback);

	// ----地理位置相关接口-----完----------------------------------------

	/**
	 * 返回搜房神器列表
	 * 
	 * @param allPrice
	 *            总价范围
	 * @param PartPrice
	 *            首付范围
	 * @param type
	 *            类型
	 * @param area
	 *            区域
	 * @param rooms
	 *            几房
	 * @param monthlyPay
	 *            月供
	 * @param label
	 *            补充信息
	 * @param pageIndex
	 *            当前页
	 * @param callback
	 */
	void getSearchHouseArtifactResut(String allPrice, String partPrice,
			int type, String area, String rooms, String monthlyPay,
			String label, int tradeType, int pageIndex, ResultHandlerCallback callback);

	/**
	 * 新房列表页
	 * 
	 * @param areaId
	 *            区域ID
	 * @param price
	 *            价格区间
	 * @param roomType
	 *            房型
	 * @param usage
	 *            房屋用途
	 * @param specials
	 *            特色
	 * @param status
	 *            售卖状态
	 * @param cityId
	 *            城市ID
	 * @param pageSize
	 *            分页大小
	 * @param pageNum
	 *            页码
	 * @param callback
	 */
	void getNewHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			TextValueBean usageCondition, TextValueBean labelCondition,
			TextValueBean statusCondition, String keyWords, int pageSize,
			int pageNum, ResultHandlerCallback callback);

	/**
	 * 获取新房详情
	 * 
	 * @param estateId
	 *            小区ID
	 * @param callback
	 */
	void getNewHouseDetail(String estateId, ResultHandlerCallback callback);

	/**
	 * (总价SalePriceRange、户型HouseType、物业类型PrpUsage、特色标签EstateLabel、
	 * 售卖状态EstateStatus)、楼层范围（FloorRange）、租价范围（RentPriceRange）
	 * 返回指定请求的name返回字典列表，用在下拉列表时的选择项
	 * 
	 * @param name
	 * @param callback
	 */
	void getConditionByName(String name, ResultHandlerCallback callback);

	void getAreaList(String cityId, ResultHandlerCallback callback);

	/**
	 * @param cityId
	 *            城市ID
	 * @param areaCondition
	 *            区域ID
	 * @param direction
	 *            朝向
	 * @param squareCondition
	 *            面积范围
	 * @param labelCondition
	 *            标签
	 * @param priceCondition
	 *            价格
	 * @param roomTypeCondition
	 *            户型
	 * @param buildYear
	 *            楼龄 传1（5年以内），2（5-10），3（10-20），4（大于20）
	 * @param floor
	 *            楼层 传1（低楼层）2（中楼层）3（高
	 * @param proNum
	 *            房源编号
	 * @param sort
	 *            排序 1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降）
	 * @param key
	 *            搜索关键字
	 * @param pageSize
	 *            分页大小
	 * @param pageIndex
	 *            页码
	 * @param callback
	 */
	void getSecondHandHouseList(String cityId, TextValueBean areaCondition,
			String direction, TextValueBean squareCondition,
			TextValueBean labelCondition, TextValueBean priceCondition,
			TextValueBean roomTypeCondition, String buildYear, String floor,
			String proNum, String sort, String key, int pageSize,
			int pageIndex, ResultHandlerCallback callback);

	/**
	 * @param proId
	 *            房源ID
	 * @param callback
	 */
	void getSecondHandHouseDetail(String proId, ResultHandlerCallback callback);

	/**
	 * @param cityId
	 *            城市ID
	 * @param areaId
	 *            区域ID
	 * @param rentPriceRange
	 *            租金区间
	 * @param square
	 *            面积范围
	 * @param sort
	 *            排序
	 * @param keyWords
	 *            关键字
	 * @param direction
	 *            朝向
	 * @param roomTypeCondition
	 *            房型
	 * @param pageSize
	 *            分页大小
	 * @param pageIndex
	 *            页码
	 * @param callback
	 */
	void getRentHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean squareCondition,
			String sort, String keyWords, String direction,
			TextValueBean roomTypeCondition, int pageSize, int pageIndex,
			ResultHandlerCallback callback);

	/**
	 * 获取租房详情
	 * 
	 * @param proId
	 *            房源ID
	 * @param callback
	 */
	void getRentHouseDetail(String proId, ResultHandlerCallback callback);

	/**
	 * 资讯类别
	 * 
	 * @param callback
	 */
	void getNewsType(ResultHandlerCallback callback);

	/**
	 * @param newsTypeId
	 * @param callback
	 */
	void getNewsDetail(String newsTypeId, ResultHandlerCallback callback);

	void commitFeedback(String userId, String content, String phone,
			ResultHandlerCallback callback);

	/**
	 * 获取我的关注列表
	 * 
	 * @param userId
	 *            用户Id
	 * @param pageSize
	 *            每页条数
	 * @param pageIndex
	 *            当前页
	 * @param callback
	 */
	void getAttentionList(String userId, int pageSize, int pageIndex,
			ResultHandlerCallback callback);

	/**
	 * 关注房源
	 * 
	 * @param userId
	 *            用户的id
	 * @param propertyId
	 *            房源id
	 * @param callback
	 */
	void attentionToHouse(String userId, String propertyId,
			ResultHandlerCallback callback);

	/**
	 * 获取我委托卖的房子列表
	 * 
	 * @param userPhone
	 *            用户的UserID(手机号)
	 * @param pageSize
	 *            每页条数
	 * @param pageIndex
	 *            当前页
	 * @param callback
	 */
	void getMySellHouseList(String userPhone, String cityId, int pageSize, int pageIndex,
			ResultHandlerCallback callback);

	/**
	 * 获取常见问题列表
	 * 
	 * @param callback
	 */
	void getCommonProblemList(ResultHandlerCallback callback);

	/**
	 * 根据userid查看个人钱包
	 * 
	 * @param userId
	 *            用户名
	 * @param callback
	 */
	void getWalletInfo(String userId, ResultHandlerCallback callback);

	/**
	 * 赏金猎人 我的客户筛选条件
	 * 
	 * @param callback
	 */
	void getFeeHunterMyCustomerCondition(ResultHandlerCallback callback);

	/**
	 * 赏金猎人 我的客户列表
	 * 
	 * @param callback
	 */
	void getFeeHunterMyCustomerList(String userId, String cityId, String status, int pageSize,
			int pageIndex, ResultHandlerCallback callback);

	// void getMyHouseSourceInfoChanges();

	/**
	 * 返回搜索楼盘搜索列表
	 * 
	 * @param area
	 *            区域Id
	 * @param totalPrice
	 *            总价范围
	 * @param house
	 *            房型
	 * @param type
	 *            类型
	 * @param label
	 *            特色
	 * @param status
	 *            售卖状态
	 * @param key
	 *            搜索关键字
	 * @param callback
	 */
	void getSearchHouseList(TextValueBean area, TextValueBean totalPrice,
			TextValueBean house, TextValueBean type, TextValueBean label,
			TextValueBean status, String key, ResultHandlerCallback callback);

	/**
	 * 获取资讯列表
	 * 
	 * @param newsId
	 *            类别ID
	 * @param pageSize
	 *            当前页条数
	 * @param pageIndex
	 *            当前页码
	 * @param callback
	 */
	void getNewsList(String newsId, int pageSize, int pageIndex,
			ResultHandlerCallback callback);

	void createIMAccount(String id, String pwd, ResultHandlerCallback callback);

	/**
	 * 获取小区成交历史
	 * 
	 * @param estId
	 *            小区ID
	 * @param roomType
	 *            房型 0：不限 1：一居 2：两居 3：三居 4：四居
	 * @param callback
	 */
	void getResidentialTransactionHistory(String estId, String roomType,
			ResultHandlerCallback callback);

	/**
	 * 获取 地图找房 默认显示状态
	 * 
	 * @param tradeType
	 *            出售/出租 (0是出售，1是出租)
	 * @param cityId
	 *            城市id
	 * @param callback
	 */
	void getMapFindHouseData(int tradeType, String cityId,
			ResultHandlerCallback callback);

	// void getMapFindHouseEstate(TextValueBean area, TextValueBean totalPrice,
	// TextValueBean house, TextValueBean type, TextValueBean label,
	// TextValueBean status, String key, ResultHandlerCallback callback);

	/**
	 * 赏金猎人推荐业主
	 * 
	 * @param estateId
	 *            楼盘ID
	 * @param minPrice
	 *            最小价格
	 * @param maxPrice
	 *            最大价格
	 * @param monthlPay
	 *            月供
	 * @param contactName
	 *            联系人名称
	 * @param phone
	 *            联系人电话
	 * @param remark
	 *            备注
	 * @param userId
	 *            用户ID
	 * @param citeId
	 *            站点ID
	 * @param callback
	 */
	void recommendFeeHunterCustomer(int trade, String minPrice,
			String maxPrice, String contactName,
			String phone, String remark, String userId, String citeId, String area,
			ResultHandlerCallback callback);

	/**
	 * @param keywords
	 *            关键字
	 * @param top
	 *            显示条数
	 * @param type
	 *            来源类型
	 * @param callback
	 */
	void getAutoCompleteEstate(String ciytId, String keywords, int top, int type,
			ResultHandlerCallback callback);

	/**
	 * 赏金猎人 推荐房源
	 * 
	 * @param estateId
	 *            楼盘ID
	 * @param rigdepole
	 *            楼栋号
	 * @param unit
	 *            单元号
	 * @param roomNo
	 *            房间号
	 * @param eatateName
	 *            楼盘名称
	 * @param cityId
	 *            站点ID
	 * @param floor
	 *            楼层
	 * @param trade
	 *            交易状态 “1”出租，”2”出售，”3”租售
	 * @param contactName
	 *            客户名称
	 * @param telNum
	 *            客户电话
	 * @param remark
	 *            情况介绍
	 * @param callback
	 */
	void recommendFeeHunterHouseSource(String userId, String estateId, String rigdepole,
			String unit, String roomNo, String estateName, String cityId,
			String floor, int trade, String contactName, String telNum,
			String remark, ResultHandlerCallback callback);

	/**
	 * 获取置业经纪人详情，输入经纪人ID
	 * 
	 * @param agentId
	 *            经纪人ID
	 * @param callback
	 */
	void getAgentInfoDetail(String agentId, ResultHandlerCallback callback);

	/**
	 * 赏金猎人 活动规则
	 * 
	 * @param callback
	 */
	void getFeeHunterRule(ResultHandlerCallback callback);

	/**
	 * 我的房源-赏金猎人（推荐房源列表）
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageSize
	 *            显示条数
	 * @param pageIndex
	 *            当前页码
	 * @param callback
	 */
	void getFeeHunterRecommendHouseSourceList(String userId, String cityId, int pageSize,
			int pageIndex, ResultHandlerCallback callback);

	/**
	 * 房源的进度查看
	 * 
	 * @param houseSourceId
	 * @param callback
	 */
	void getHouseSourceProgress(String houseSourceId,
			ResultHandlerCallback callback);

	/**
	 * 删除我关注的房源
	 * 
	 * @param userId
	 *            用户ID
	 * @param sourceId
	 *            房源ID
	 * @param callback
	 */
	void deleteCollection(String userId, String sourceId,
			ResultHandlerCallback callback);

	/**
	 * 我的需求条件
	 * 
	 * @param userId
	 *            用户ID
	 * @param callback
	 */
	void getMyDemandInfo(String userId, ResultHandlerCallback callback);

	/**
	 * 发送消息
	 * 
	 * @param from
	 * @param to
	 * @param message
	 * @param callback
	 */
	void sendMessage(String from, String to, String message,
			ResultHandlerCallback callback);

	/**
	 * 获取月供范围列表
	 * 
	 * @param callback
	 */
	void getMonthlyPayRange(ResultHandlerCallback callback);

	/**
	 * @param userId
	 *            用户ID
	 * @param realName
	 *            开户行用户姓名
	 * @param bankCode
	 *            银行卡号
	 * @param bankName
	 *            银行名称
	 * @param bankCity
	 *            开户行城市
	 * @param bankImage
	 *            身份证图片
	 * @param openAccountBankName
	 *            开户行银行名称
	 * @param callback
	 */
	void commitFeeHunterBankInfo(String userId, String realName,
			String bankCode, String bankName, String bankCity, String bankImage,
			String openAccountBankName, ResultHandlerCallback callback);

	/**
	 * 根据我的需求条件推荐给我的房源列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageSize
	 *            显示条数
	 * @param pageIndex
	 *            当前页码
	 * @param callback
	 */
	void getHousesRecommendedToMe(String userId, int pageSize, int pageIndex,
			ResultHandlerCallback callback);

	/**
	 * 我的购屋列表 用户ID
	 * 
	 * @param userId
	 * @param callback
	 */
	void getMyBoughtHouses(String userId, ResultHandlerCallback callback);

	/**
	 * 第一次请求获取省份列表 第二次请求通过省份代码获取城市列表
	 * 
	 * @param cityCode
	 * @param callback
	 */
	void getBankProvinceOrCity(String cityCode, ResultHandlerCallback callback);

	/**
	 * 银行列表
	 * 
	 * @param callback
	 */
	void getBankNameList(ResultHandlerCallback callback);

	/**
	 * 获取联系人列表（含历史聊天记录） [ { "userId": "53b176e9926da80f4800160f", "userName":
	 * "罗强", "messages": [ { "_id": "563acb76dddc2351f4772f4c", "fromUser":
	 * "635818825799481234", "toUser": "53b176e9926da80f4800160f", "message":
	 * "你好，在吗？", "createDate": "2015-11-05 11:22:30", "isRead": true } ] } ]
	 * 
	 * @param visitorId
	 * @param callback
	 */
	void getContactsList(String visitorId, ResultHandlerCallback callback);

	/**
	 * 获取单个联系人（含历史聊天记录，发起聊天时调用） {"key": "635818825799481234"} { "userId" :
	 * "53b176e9926da80f4800160f", "userName" : "罗强", "messages" : [{ "_id" :
	 * "563acb76dddc2351f4772f4c", "fromUser" : "635818825799481234", "toUser" :
	 * "53b176e9926da80f4800160f", "message" : "你好，在吗？", "createDate" :
	 * "2015-11-05 11:22:30", "isRead" : true }] }]
	 * 
	 * @param contactId  单个联系人Id
	 * @param callback
	 */
	void getMessageRecordsWithSomeBody(String contactId,
			ResultHandlerCallback callback);
	
	/**
	 * 接口：判断联系人在线状态(逗号分隔联系人id)\
	 * {"result":[{"friendId":"53b176e9926da80f4800160f","isOnline":false}]}
	 * @param ids
	 * @param callback
	 */
	void getContactOnLineStatus(String ids, ResultHandlerCallback callback);
	
	/**
	 * 发送消息(逗号分隔消息id)  这个应该是读消息
	 * @param ids
	 * @param callback
	 */
	void readMessage(String ids, ResultHandlerCallback callback);
	
	/**
	 * 委托售房
	 * @param estateId   楼盘ID
	 * @param estateName   楼盘名称
	 * @param ridgepole   楼栋号
	 * @param unit    单元号
	 * @param roomNo   房间号
	 * @param type   房屋类型
	 * @param price   价格
	 * @param countFang   户型-房
	 * @param halls   户型-厅
	 * @param wc  户型-卫
	 * @param square  面积
	 * @param direction  朝向
	 * @param totalFloor   总楼层
	 * @param floor   房源楼层
	 * @param decoration   装修
	 * @param title  标题 
	 * @param cityId  站点ID
	 * @param userId  用户ID
	 * @param Name  用户名称
	 * @param sex  用户性别
	 * @param callback
	 */
	void entrustSellHouse(String estateId, String estateName, String ridgepole,
			String unit, String roomNo, String type,
			String countFang, String halls, String wc, String square,
			String direction, String totalFloor, String floor,
			String decoration, String title, String desc, String cityId,
			String userId, String Name, boolean sex, String phone, ResultHandlerCallback callback);
	
	/**
	 * 委托买房
	 * @param userId  用户ID
	 * @param estateId   楼盘ID
	 * @param budget  买房预算
	 * @param minSquare   最小面积
	 * @param maxSquare    最大面积
	 * @param monthlyPay   月供需求
	 * @param countFang   户型-房
	 * @param hall    户型-厅
	 * @param require  其他要求 
	 * @param name   用户名称
	 * @param sex   性别
	 * @param callback
	 */
	void entrustBuyHouse(String cityId, String userId, String estateId, double budget,
			int minSquare, int maxSquare, double minTotalPrice, double maxTotalPrice, String monthlyPay,
			int countFang, int hall, String require,
			String name, boolean sex, ResultHandlerCallback callback);
	
	/**
	 * 关于我们
	 * @param callback
	 */
	void getAboutUsData(ResultHandlerCallback callback);
	
	/**
	 * 上传文件
	 * @param file
	 * @param callback
	 */
	void otherFileUpload(File file, ResultHandlerCallback callback);
	
	/**
	 * 修改个人信息
	 * @param userId
	 * @param nickName
	 * @param avatarUrl
	 */
	void updateUserInfo(String userId, String nickName, String avatarUrl, ResultHandlerCallback callback);
	
	/**
	 * 获取引导页列表
	 * @param callback
	 */
	void getGuiderPageData(ResultHandlerCallback callback);
	
	/**
	 * 客户进度   客源公用的带看记录
	 * @param id
	 * @param callback
	 */
	void getClientProgress(String id, ResultHandlerCallback callback);
	
	/**
	 * 获取银行卡信息
	 * @param userId  用户ID
	 * @param callback
	 */
	void getBindBankInfo(String userId, ResultHandlerCallback callback);
	
	/**
	 * 获取小区详情
	 * @param courtId
	 * @param callback
	 */
	void getCourtDetail(String courtId, ResultHandlerCallback callback);
	
	/**
	 * 我的购屋的收支明细
	 * @param prpId   房源ID
	 * @param callback
	 */
	void getIncomeStatement(String prpId, String type, ResultHandlerCallback callback);
	
	/**
	 * 找房神器 几居数据
	 * @param callback
	 */
	void getSearchHouseArtifactHouseType(ResultHandlerCallback callback);
	
	/**
	 * 客户信息变动
	 * @param clientId
	 * @param callback
	 */
	void getClientInfoChange(String clientId, ResultHandlerCallback callback);
	
	/**
	 * 房源信息变动
	 * 新增参数 isSale  布尔类型  在我是客户 和我是业主那里点击房源 售后服务 调用的接口传递这个参数，其他调用的可以不用管（赏金猎人 不改）
	 * @param houseSourceId
	 * @param callback
	 */
	void getHouseSourceInfoChange(String houseSourceId, boolean isSale, ResultHandlerCallback callback);
	
	/**
	 * 房源公用的带看记录
	 * @param houseSourceId
	 * @param pageIndex
	 * @param pageSize
	 * @param callback
	 */
	void getHouseSourceFollowList(String houseSourceId, int pageIndex, int pageSize, ResultHandlerCallback callback);
	
	/**
	 * 投诉接口
	 * @param id  代表哪一项的投诉，就传哪一项的ID，比如房源进度投诉 就传这个房源进度的ID
	 * @param type   1(财务)，2（带看），3（进度）
	 * @param userId
	 * @param content
	 */
	void complain(String id, String type, String userId, String content, ResultHandlerCallback callback);
	
	/**
	 * 看房经历
	 * @param userId
	 * @param callback
	 */
	void seeHouseExperience(String userId, ResultHandlerCallback callback);
	
	/**
	 * @param id  宿主ID（房源、客源） 代表哪一项的点赞，就传哪一项的ID，比如房源进度点赞 就传这个房源进度的ID 
	 * @param supportType  点赞类型    1(财务)，2（带看），3（进度）
	 * @param userId  
	 * @param content  
	 * @param callback
	 */
	void support(String id, int supportType, String userId, String content, ResultHandlerCallback callback);
	
	/**
	 * 推荐房源获取楼栋  获取楼栋信息
	 * @param estateId  小区id
	 * @param callback
	 */
	void getEstateBuilding(String estateId, ResultHandlerCallback callback);
	
	/**
	 * 推荐房源  获取楼栋单元信息
	 * @param buildingId 楼栋id
	 * @param callback
	 */
	void getEstateCell(String buildingId, ResultHandlerCallback callback);
	
	/**
	 * 推荐房源  获取房间号信息
	 * @param cellId
	 * @param callback
	 */
	void getEstateRoom(String cellId, ResultHandlerCallback callback);
}
