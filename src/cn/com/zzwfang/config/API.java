package cn.com.zzwfang.config;

public class API {

	/**
	 * 成功返回有数据
	 */
	public static final int SUCCESS_EXIST = 1;
	
	public static final int ERROR_FAILURE = 0;

	public static final int ERROR_FORM_ERROR = 1002;

	/**
	 * 需要登录错误
	 */
	public static final int ERROR_NEED_LOGIN = 3001;

	/**
	 * token失效，重新返回token后要重新请求一次数据
	 */
	public static final int ERROR_TOKEN_INVALID = 9001;

	/**
	 * 目标主机地址 http://dev.web.gotoapp.cn/
	 * 
	 * http://api-l.lincomb.com
	 * 
	 * http://dev.api.linktown.mezone.me
	 */
	public static final String host = "http://118.119.254.72:7894/";

	/**
	 * 认证授权接口
	 */
	public static final String api_get_token_post = "/auth/login/";

	/**
	 * 登录接口
	 */
	public static final String API_POST_LOGIN = "User/Login";

	/**
	 * 获取短信验证码
	 */
	public static final String POST_SMS_VERIFYCODE = "User/SendSMS";

	/**
	 * 用户注册
	 */
	public static final String POST_REGISTER = "User/Register";

	/**
	 * 修改密码 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9143997
	 */
	public static final String POST_CHANGE_PWD = "/auth/password/change/";
	/**
	 * 忘记密码 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9144000
	 */
	public static final String POST_RESET_PWD = "/auth/password/reset/";
	
	/**
	 * 版本更新getAppVersionCheck
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9144407
	 * 
	 */
	public static final String GET_APP_VERSIONCHECK = "/system/check/version/";
	
	
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * 返回推荐房源
	 */
	public static final String GET_RECOMMEND_HOUSE_SOURCE = "Property/getIndexPropertyList";
	
	/**
	 * 返回城市区域列表
	 */
	public static final String GET_CITY_LIST = "Map/getCityList";
    
    public static final String GET_SEARCH_HOUSE_ARTIFACT = "Excalibur/MatchingList";
    
    public static final String GET_NEW_HOUSE_LIST = "NewEstate/GetEstateListJson";
    
    public static final String GET_SECNOD_HAND_LIST = "Property/GetPropertyListJson";
    
    public static final String GET_CONDITION_LIST_BY_NAME = "Dictionary/Index";
    
    public static final String GET_AREA_LIST = "Dictionary/GetAreaList";
    
    public static final String GET_SECOND_HAND_HOUSE_DETAIL = "Property/GetPropertyDetailJson";
    
    public static final String GET_RENT_HOUSE_LIST = "Property/GetRentPropertyListJson";
    
    public static final String GET_RENT_HOUSE_DETAIL = "Property/GetRentPropertyDetailJson";

}
