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
	 * 目标主机地址
	 * http://118.119.254.72:7894/
	 * 
	 * 
	 * http://yangmingbo.cn
	 * 
	 * http://110.189.90.40:7892/     http://www.zzwfang.com:7894/  http://zzwfang.com:9001/  funapi.zzwfang.com:7894
	 * http://funapi.zzwfang.com:7894/
	 */
	public static final String host = "http://192.168.1.115:8004/";  //  http://110.189.90.40:7894/   http://192.168.1.115:8000/

	//   http://zzwfang.com:7894/    http://47.90.54.170:7003/  http://192.168.1.115:8000/  http://47.90.5.4:7003/  192.168.1.115:8004
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
	
	public static final String GET_CITY_BY_GPS = "Other/GetCityByGPS";
    
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
    public static final String GET_MY_ATTENTION_LIST = "User/CollectionList";
    
    /**
     * 关注房源
     */
    public static final String GET_ATTENTION_TO_PROPERTY = "User/CollectionAction";
    
    /**
     * 我委托出售的房源列表
     */
    public static final String GET_MY_HOUSE_LIST = "User/MySellPrp";
    
    /**
     * 获取常见问题列表
     */
    public static final String GET_COMMON_PROBLEM = "Other/Question";
    
    /**
     * 根据userid查看个人钱包
     */
    public static final String GET_WALLET_INFO = "BountyHunter/Wallet";
    
    /**
     * 我的客户-客户状态
     */
    public static final String GET_FEE_HUNTER_MY_CUSTOMER_CONDITION = "BountyHunter/ClientState";
    
    /**
     * 赏金猎人  我的客户
     * 我推荐的客户列表
     */
    public static final String GET_FEE_HUNTER_MY_CUSTOMER = "BountyHunter/RecommendClientList";
    
    /**
     * 返回搜索楼盘搜索列表
     */
    public static final String GET_SEARCH_HOUSE_LIST = "Map/GetEstate";
    
    /**
     * 获取资讯列表
     */
    public static final String GET_NEWS_LIST = "News/Index";
    
    /**
     * 创建即时通讯账号
     */
    public static final String GET_CREATE_IM_ACCOUNT = "Message/CreateAccount";
    
    /**
     * 获取小区历史成交记录
     */
    public static final String GET_RESIDENTIAL_TRANSACTION_HISTORY = "Estate/GetHisRecordJson";
    
    /**
     * 获取地图找房默认显示状态
     */
    public static final String GET_MAP_FIND_HOUSE_DATA = "Map/GetArea";
    
    /**
     * 赏金猎人  推荐客户
     */
    public static final String POST_FEE_HUNTER_RECOMMEND_CUSTOMER = "BountyHunter/RecommendClient";
    
    /**
     *  首先通过“输入关键字获取楼盘列表”接口，返回楼盘的id和名称
     */
    public static final String GET_ESTATE_AUTO_COMPLETE = "Estate/AutoComplete";
    
    /**
     *  赏金猎人    推荐房源
     */
    public static final String POST_FEE_HUNTER_RECOMMEND_HOUSE_SOURCE = "BountyHunter/RecommendPrp";
    
    /**
     * 获取置业经纪人详情
     */
    public static final String GET_AGENT_INFO_DETAIL = "Employee/GetEmployeeDetail";
    
    /**
     * 获取置业经纪人详情
     */
    public static final String GET_FEE_HUNTER_RULE = "Other/Activity";
    
    public static final String GET_FEE_HUNTER_RECOMMEND_HOUSE_SOURCE_LIST = "BountyHunter/RecommendPrpList";
    
    /**
     * 房源的进度查看
     */
    public static final String GET_FEE_HUNTER_HOUSE_SOURCE_PROGRESS = "BountyHunter/PrpProgress";
    
    /**
     * 删除我关注的房源
     */
    public static final String GET_DELETE_COLLECTION = "User/DelCollection";
    
    /**
     * 我的需求条件
     */
    public static final String GET_MY_DEMAND_INFO = "User/MyDeMand";
    
    /**
     * 发送消息
     */
    public static final String SEND_MESSAGE = "Message/Send";
    
    /**
     * 获取月供范围列表
     */
    public static final String GET_MONTHLY_PAY_RANGE = "Other/MonthPay";
    
    /**
     * 填写银行卡信息
     */
    public static final String POST_USER_BANK_INFO_TO_BE_FEE_HUNTER = "User/AddBandInformation";
    
    /**
     * 根据我的需求条件推荐给我的房源列表
     */
    public static final String GET_HOUSES_RECOMMENDED_TO_ME = "User/ToMeProperty";
    
    /**
     * 我的购屋列表
     */
    public static final String GET_MY_BOUGHT_HOUSES = "User/MyBuyPrp";
    
    /**
     * 银行城市列表
     * 第一次请求获取省份列表
     * 第二次请求通过省份代码获取城市列表
     */
    public static final String GET_BANK_PROVINCE_OR_CITY = "Other/City";
    
    /** 
     * 银行城市列表
     * 第一次请求获取省份列表
     * 第二次请求通过省份代码获取城市列表
     */
    public static final String GET_BANK_NAME_LIST = "Other/Bank";
    
    /**
     * 获取联系人列表（含历史聊天记录）
     */
    public static final String GET_CONTACTS_LIST = "Message/GetContacks";
    
    /**
     * 接口：获取单个联系人（含历史聊天记录，发起聊天时调用）
     */
    public static final String GET_MESSGAE_RECORD_WITH_SOMEBODY = "Message/GetContack";
    
    /**
     * 接口：判断联系人在线状态(逗号分隔联系人id)
     */
    public static final String GET_CONTACTS_ONLINE_STATUS = "Message/JudgeOnlineUser";
    
    /**
     * 读消息
     */
    public static final String READ_MESSAGES = "Message/ReadMessages";
    
    /**
     * 委托卖房
     */
    public static final String ENTRUST_SELL_HOUSE = "User/EntrustSell";
    
    /**
     * 委托 我要买房
     */
    public static final String ENTRUST_BUY_HOUSE = "User/EntrustBuy";
    
    /**
     * 关于我们
     */
    public static final String GET_ABOUT_US = "Other/About";
    
    /**
     * 文件上传（填写银行卡上传图片）
     */
    public static final String POST_OTHER_UPLOAD = "Other/Upload";
    
    /**
     * 上传个人资料
     */
    public static final String GET_UPDATE_USER_INFO = "User/UpdateInfo";
    
    /**
     * 引导页数据
     */
    public static final String GET_GUIDER_PAGE_DATA = "Other/Guide";
    
    /**
     * 客户进度
     */
    public static final String GET_CLIENT_PROGRESS = "BountyHunter/ClientProgress";
    
    /**
     * 获取银行卡信息
     */
    public static final String GET_BIND_BANK_CARD_INFO = "User/GetBankInfo";
    
    /**
     * 获取小区详情
     */
    public static final String GET_COURT_DETAIL = "Estate/GetEstateDes";
    
    /**
     * 我的购屋的收支明细
     */
    public static final String GET_INCOME_STATEMENT = "User/Income";
    
    /**
     * 找房神器，几居数据
     */
    public static final String GET_SEARCH_HOUSE_ARTIFACT_HOUSE_TYPE = "Other/HouseType";
    
    /**
     * 客户信息变动
     */
    public static final String GET_CLIENT_INFO_CHANGE = "BountyHunter/ClientChange";
    
    /**
     * 房源信息变动
     */
    public static final String GET_HOUSE_SOURCE_INFO_CHANGE = "BountyHunter/PrpChange";
    
    /**
     * 获取带看记录
     */
    public static final String GET_HOUSE_SOURCE_FOLLOW_LIST = "User/FollowList";
    
    /**
     * 投诉
     */
    public static final String COMPLAIN = "Other/Complaint";
    
    /**
     * 看房经历
     */
    public static final String SEE_HOUSE = "User/SeeHouse";
    
    /**
     * 赞
     */
    public static final String SUPPORT = "Other/Zan";
    
    /**
     * 推荐房源 获取楼栋单元信息
     */
    public static final String GET_ESTATE_BUILDING = "Estate/GetBuilding";
    
    /**
     * 推荐房源 获取楼栋单元信息
     */
    public static final String GET_ESTATE_CELL = "Estate/GetCell";
    
    /**
     * 推荐房源 获取房间号信息
     */
    public static final String GET_ESTATE_ROOM = "Estate/GetRoom";
    
    /**
     * 每次访问或返回至赏金猎人中心的首页则请求这个接口
     * BountyHunter/GetBountyHunterInfo?userId=当前用户ID
       返回结果 {PrpCount=推荐房源数量,InqCount=推荐客户数量,Money=赏金金额}
     */
    public static final String GET_Bounty_Hunter_Info = "BountyHunter/GetBountyHunterInfo";
    
    /**
     * 公司公告列表
     */
    public static final String GET_COMPANY_ANNOCEMENT_LIST = "Notices/Index";
    
    /**
     * 公司公告详情
     */
    public static final String GET_COMPANY_ANNOCEMENT_DETAIL = "Notices/Detail";
    
    /**
     * 评论公司公告详情
     */
    public static final String POST_COMMENT_COMPANY_ANNOCEMENT_DETAIL = "Notices/Common";
    
    /**
     * 获取投诉标签
     */
    public static final String GET_COMPLAIN_LABELS = "Other/Label";
    
    /**
     * 推荐资讯
     */
    public static final String GET_RECOMMEND_NEWS = "News/Recommend";
    
    /**
     * 获取房源参数
     */
    public static final String GET_HOUSE_SOURCE_PARAM = "Other/GetPara";
    
    /**
     * 获取房源排序参数
     */
    public static final String GET_HOUSE_SOURCE_SORT = "Other/GetSort";


}
