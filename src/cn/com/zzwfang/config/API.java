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
	 * 目标主机地址  http://www.zzwfang.com:7894
	 * http://118.119.254.72:7894/
	 */
	public static final String host = "http://www.zzwfang.com:7894/";

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
	 * 修改密码
	 */
	public static final String POST_CHANGE_PWD = "User/UpdatePwd";
	/**
	 * 忘记密码
	 */
	public static final String POST_RESET_PWD = "User/ResetPwd";
	
	/**
	 * 版本更新getAppVersionCheck
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9144407
	 * 
	 */
	public static final String GET_APP_VERSIONCHECK = "/system/check/version/";
	
	
	////////////////////////////////////////////////////////////////////////////////
	
	public static final String CHECK_PHONE_HAS_REGISTERED = "User/CheckPhone";
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
    
    public static final String GET_NEW_HOUSE_DETAIL = "NewEstate/GetEstateDetailJson";
    
    public static final String GET_SECNOD_HAND_LIST = "Property/GetPropertyListJson";
    
    public static final String GET_CONDITION_LIST_BY_NAME = "Dictionary/Index";
    
    public static final String GET_AREA_LIST = "Dictionary/GetAreaList";
    
    public static final String GET_SECOND_HAND_HOUSE_DETAIL = "Property/GetPropertyDetailJson";
    
    public static final String GET_RENT_HOUSE_LIST = "Property/GetRentPropertyListJson";
    
    public static final String GET_RENT_HOUSE_DETAIL = "Property/GetRentPropertyDetailJson";
    
    /**
     * 资讯类别
     */
    public static final String GET_NEWS_TYPE = "News/Type";
    
    public static final String GET_NEWS_DETAIL = "News/Detail";
    
    /**
     * 意见反馈
     */
    public static final String POST_COMMIT_FEEDBACK = "Other/Feedback";
    
    /**
     * 我关注的房源
     */
    public static final String GET_MY_ATTENTION_LIST = "Attention/Detail";
    
    /**
     * 关注房源
     */
    public static final String GET_ATTENTION_TO_PROPERTY = "User/Focus";
    
    /**
     * 获取我的房屋列表
     */
    public static final String GET_MY_HOUSE_LIST = "MyProperty/myProperyty";

}
