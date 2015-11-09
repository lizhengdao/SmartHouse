package cn.com.zzwfang.constant;

/**
 * @description 项目所有的网络URL,设置有debug和线上地址开关
 * @author fenfei.she
 * @date 2014年7月17日
 * @since v3.0
 * @notice 所有的接口必须要有注释.要有接口传入什么参数/类似login?name=xxx&pass=yyy;
 */
public final class URL {

	public static boolean IS_DEBUG = false;
	/** 测试地址 */
	public static final String DEBUG_ROOT_URL = "http://118.119.254.72:7894/";
	/** 线上地址 */
	public static final String ONLOINE_ROOT_URL = "http://118.119.254.72:7894/";
	/** 远程服务器的地址 */
	public static final String ROOT = IS_DEBUG ? DEBUG_ROOT_URL
			: ONLOINE_ROOT_URL;
	
	
	public static final String GET_CHECK_REGISTER = "User/CheckPhone";
	
	
	
	
	
	
	
	
	
	
	

	/* =========用户模块========== */
	/** 参考实体 User */
	/** 用户登录 userLogin?name=name&pass=123 method=post */
	public static final String URL_LOGIN = "/auth/login/";

	/**
	 * 该功能用于在标准用户下对用户密码进行检测 http://wiki.lianbi.com.cn/display/MS/Check+password
	 */
	public static final String URL_CHECK_PASS = "/auth/check_password/";

	/**
	 * 通用接口：用户退出登录 http://wiki.lianbi.com.cn/display/MS/logout
	 */
	public static final String URL_LOGOUT = "/auth/logout/";

	/** 用户注册 */
	public static final String URL_REG = "/auth/signup/";

	/** 用户列表 */
	public static final String URL_USERLIST = "/user/userController/userList";

	/** 获取短信验证码 */
	public static final String URL_GET_SMS_VERIFYCODE = "/sms/captcha/";

	/** 忘记密码 */
	public static final String URL_FINDPWD = "/auth/reset_password/";

	/* ========================= */

	// 测试地址
	public static final String url = "/Test/test";

	public static final String url1 = ROOT + url;

	/**
	 * 参数只有一个id，可传的值为200,403,404,500 接口用于测试错误消息返回
	 */
	public static final String GET_URL_ERRORTEST = "/testapi/";

	/**
	 * 获得行业分类列表 http://wiki.lianbi.com.cn/display/MS/Get+industry
	 */
	public static final String GET_URL_INDUSTRY = "/industry/industry/";

	/**
	 * 获取行业下的主营类别 http://wiki.lianbi.com.cn/display/MS/Get+category
	 */
	public static final String GET_URL_CATEGORY = "/industry/category/";

	/**
	 * 获取预定规则信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683611
	 */
	public static final String GET_BOOK_RULE = "/product/bookrule/get/";

	/**
	 * 获取配送规则信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683605
	 */
	public static final String GET_DELIVER_RULE = "/product/deliveryrule/get/";

	/**
	 * 获取分类列表信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683627
	 */
	public static final String GET_PRODUCTCATE_LIST = "/product/productcate/";

	/**
	 * 根据经营项目类别获取经营项目列表
	 * http://wiki.lianbi.com.cn/display/MS/Get+product+list+by+cate
	 */
	public static final String GET_PRODUCT_LIST = "/product/";

	/**
	 * 在修改经营项目时获取详情
	 * http://wiki.lianbi.com.cn/display/MS/Get+detail+for+product+updating
	 */
	public static final String GET_PRODUCT_DETAIL = "/product/detail_for_updating/";

	/**
	 * 获取商家优惠券列表 http://wiki.lianbi.com.cn/display/MS/Coupon+List
	 */
	public static final String GET_COUPON_LIST = "/coupon/coupon/";

	/**
	 * 获取会员列表接口 http://wiki.lianbi.com.cn/display/MS/get+vip+list
	 */
	public static final String GET_VIP_LIST = "/customer/vip/";

	/**
	 * 获取会员卡列表 http://wiki.lianbi.com.cn/display/MS/get+vipcard+list
	 */
	public static final String GET_VIP_CARDS = "/customer/vipcard";

	/**
	 * 获取商家促销列表 http://wiki.lianbi.com.cn/display/MS/Promotion++List
	 */
	public static final String GET_PROMOTION_LIST = "/coupon/promotion/";

	/**
	 * 获取收藏的客户 http://wiki.lianbi.com.cn/display/MS/get+collector+list
	 */
	public static final String GET_COLLECTOR_LIST = "/customer/collector/";

	/**
	 * 获取想去客户信息 http://wiki.lianbi.com.cn/display/MS/get+liker+list
	 */
	public static final String GET_WANTORS_LIST = "/customer/liker/";

	/**
	 * 获取店铺介绍信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683890
	 */
	public static final String GET_SHOPINFO = "/business/business/businessintro/";

	/**
	 * 获取商家套餐列表 http://wiki.lianbi.com.cn/display/MS/Combo+List
	 */
	public static final String GET_COMBO_LIST = "/product/combo/";

	/**
	 * 获取商家优惠券使用记录 http://wiki.lianbi.com.cn/display/MS/Coupon+Track+List
	 */
	public static final String GET_COUPON_HISTORY = "/coupon/coupontrack/";

	/**
	 * 获取商铺标签 http://wiki.lianbi.com.cn/display/MS/get+businesstag
	 */
	public static final String GET_SHOP_TAGS = "/business/businesstag/";

	/**
	 * 获取商家优惠券详情 http://wiki.lianbi.com.cn/display/MS/Coupon+Detail
	 */
	public static final String GET_COUPON_DETAIL = "/coupon/coupon/detail/";

	/**
	 * 获取商家促销活动详情 http://wiki.lianbi.com.cn/display/MS/Promotion+Detail
	 */
	public static final String GET_PROMOTION_DETAIL = "/coupon/promotion/detail/";

	/**
	 * 获取会员卡详情 http://wiki.lianbi.com.cn/display/MS/get+vipcard+detail
	 */
	public static final String GET_CARD_DETAIL = "/customer/vipcard/detail/";

	/**
	 * 获取当前商家的联营优惠 http://wiki.lianbi.com.cn/display/MS/Deal+List
	 */
	public static final String GET_SHOPPROMOTION_DETAIL = "/alliance/deal/";

	/**
	 * 获取联营流量列表 http://wiki.lianbi.com.cn/display/MS/DealTrack+List
	 */
	public static final String GET_UNIONTRACT_LIST = "/alliance/dealtrack/";

	/**
	 * 获取当前联营商户列表 http://wiki.lianbi.com.cn/display/MS/Get+alliances
	 */
	public static final String GET_UNIONED_LIST = "/alliance/alliance/";

	/**
	 * 获取当前向“我”发起的新联营申请数接口
	 * http://wiki.lianbi.com.cn/display/MS/Get+count+of+new+alliance+applies
	 */
	public static final String GET_UNIONAPPLY_COUNT = "/alliance/apply/count/";

	/**
	 * 获取联营店铺详情接口 http://wiki.lianbi.com.cn/display/MS/get+Alliance+Detail
	 */
	public static final String GET_UNIONEDSHOP_DETAIL = "/alliance/alliance/detail/";

	/**
	 * 获取联营请求店铺列表
	 * http://wiki.lianbi.com.cn/display/MS/Get+applies+to+me+for+alliance
	 */
	public static final String GET_APPLYTOME_LIST = "/alliance/apply/";

	/**
	 * 获得货源批发广告 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684335
	 */
	public static final String GET_WHOLESALE_ADS = "/wholesale/wholesale/ads/";

	/**
	 * 获得货源批发列表 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684347
	 */
	public static final String GET_WHOLESALE_LIST = "/wholesale/wholesale/";

	/**
	 * 获得货源批发商品详情 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684349
	 */
	public static final String GET_WHOLESALE_DETAIL = "/wholesale/wholesale/detail/";

	/**
	 * 获得货源批发历史订单详情
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684314
	 */
	public static final String GET_WHOLESALE_HISTORY_DETAIL = "/wholesale/wholesaleorder/history/detail/";

	/**
	 * 二维码扫描或者密码验证 http://wiki.lianbi.com.cn/display/MS/Coupon+Handle
	 */
	public static final String POST_COUPON_DETAIL_FROMCODE = "/coupon/coupon/handle/";

	/**
	 * 获得货源批发订单详情 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684357
	 */
	public static final String GET_WHOLESALE_CART_DETAIL = "/wholesale/cart/detail/";

	/**
	 * 获取评价列表 http://wiki.lianbi.com.cn/display/MS/get+common+list
	 */
	public static final String GET_COMMON_LIST = "/comment/comment/";

	/**
	 * 获取评分 http://wiki.lianbi.com.cn/display/MS/get_commonscore
	 */
	public static final String GET_COMMON_SCORE = "/comment/commentscore/";

	/**
	 * 商铺营业状态开关 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684412
	 */
	public static final String GET_OPENTIME_STATE = "/business/business/status/switch/";

	/**
	 * 获取商铺的logo，店名，经营时间，是否暂停营业
	 * http://wiki.lianbi.com.cn/display/MS/get+business+info+for+homepage
	 */
	public static final String GET_HOME_INFO = "/business/business/detail/home/";

	/**
	 * 未回复评论列表 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684483
	 */
	public static final String GET_COMMENT_LIST_UNREPLY = "/comment/commentsnoreply/";

	/**
	 * 获取正在等待审核或者驳回审核的商家信息
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684506
	 */
	public static final String GET_FILLINFO_DETAIL = "/business/business/apply/detail/";

	// TODO

	/*-----------------------------------------*/

	public static final String POST_URL = "/Test/test?id&file";

	public static final String POST_URL_FILE = "/Conn/fileUploadController/upload/";

	/** 用户列表 */
	public static final String POST_URL_USERLIST = ROOT
			+ "/Conn/user/userController/userList";
	/** aes加密 */
	public static final String POST_URL_AES = ROOT
			+ "/Conn/user/userController/aes";
	/** aes32加密 */
	public static final String POST_URL_AES_32 = "test_EncodeAES/";

	public static final String POST_URL_AES_1 = "http://172.16.118.27/test_DecodeAES_File/";
	/**
	 * 修改头像提交
	 * http://api.mezone.me/v3/business/business/businessinfo/logo/change/
	 */
	public static final String POST_URL_IMAGECOMMIT = "/business/business/businessinfo/logo/change/";
	/**
	 * 完善商户资料 http://wiki.lianbi.com.cn/display/MS/Apply+Business+Info
	 */
	public static final String POST_BUSINESS_UPDATE = "/business/business/apply/";

	/**
	 * 设置预定规则信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683613
	 */
	public static final String POST_BOOK_RULE = "/product/bookrule/set/";

	/**
	 * 设置配送规则信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683607
	 */
	public static final String POST_DELIVER_RULE = "/product/deliveryrule/set/";

	/**
	 * 增加分类列表信息 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683621
	 */
	public static final String POST_PRODUCTCATE_ADD = "/product/productcate/add/";

	/**
	 * 删除列表单个经营项目分类
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683625
	 */
	public static final String POST_PRODUCTCATE_DELETE = "/product/productcate/delete/";

	/**
	 * 编辑经营项目分类 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683623
	 */
	public static final String POST_PRODUCTCATE_EDIT = "/product/productcate/edit/";

	/**
	 * 删除经营项目(产品) http://wiki.lianbi.com.cn/display/MS/Delete+product
	 */
	public static final String POST_PRODUCT_DELETE = "/product/delete/";

	/**
	 * 产品批量上架 http://wiki.lianbi.com.cn/display/MS/Shelve+one+or+more+products
	 */
	public static final String POST_PRODUCT_SHANGJIA = "/product/shelve/";

	/**
	 * 产品批量下架 http://wiki.lianbi.com.cn/display/MS/Unshelve+one+or+more+products
	 */
	public static final String POST_PRODUCT_XIAJIA = "/product/unshelve/";

	/**
	 * 移动经营项目 http://wiki.lianbi.com.cn/display/MS/Move+one+or+more+products+to+
	 * another+category
	 */
	public static final String POST_PRODUCT_YIDONG = "/product/move/";

	/**
	 * 修改经营项目产品 http://wiki.lianbi.com.cn/display/MS/Update+Product
	 */
	public static final String POST_PRODUCT_EDIT = "/product/update/";

	/**
	 * 添加经营项目产品 http://wiki.lianbi.com.cn/display/MS/Add+Product
	 */
	public static final String POST_PRODUCT_NEW = "/product/add/";

	/**
	 * 商家删除优惠券 http://wiki.lianbi.com.cn/display/MS/Coupon+Delete
	 */
	public static final String POST_COUPON_DELETE = "/coupon/coupon/delete/";

	/**
	 * 增加商家优惠券 http://wiki.lianbi.com.cn/display/MS/Coupon+Add
	 */
	public static final String POST_COUPON_ADD = "/coupon/coupon/add/";

	/**
	 * 商家启用优惠活动 http://wiki.lianbi.com.cn/display/MS/Promotion++Enable
	 */
	public static final String POST_PROMOTION_ENABLE = "/coupon/promotion/enable/";

	/**
	 * 商家停止促销活动 http://wiki.lianbi.com.cn/display/MS/Promotion++Disable
	 */
	public static final String POST_PROMOTION_DISABLE = "/coupon/promotion/disable/";

	/**
	 * 商家删除促销活动 http://wiki.lianbi.com.cn/display/MS/Promotion+Delete
	 */
	public static final String POST_PROMOTION_DELETE = "/coupon/promotion/delete/";

	/**
	 * 商家启用优惠券 http://wiki.lianbi.com.cn/display/MS/Coupon+Enable
	 */
	public static final String POST_COUPON_ENABLE = "/coupon/coupon/enable/";

	/**
	 * 商家停止优惠券 http://wiki.lianbi.com.cn/display/MS/Coupon+Disable
	 */
	public static final String POST_COUPON_DISABLE = "/coupon/coupon/disable/";

	/**
	 * 删除会员 http://wiki.lianbi.com.cn/display/MS/delete+vip
	 */
	public static final String POST_MEMBER_DELETE = "/customer/vip/delete/";

	/**
	 * 商家修改营业时间 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683876
	 */
	public static final String POST_OPENTIME = "/business/business/business_hours/change/";

	/**
	 * 商家删除套餐 http://wiki.lianbi.com.cn/display/MS/Combo++Delete
	 */
	public static final String POST_COMBO_DELETE = "/product/combo/delete/";

	/**
	 * 会员批量移动 http://wiki.lianbi.com.cn/display/MS/move+vip
	 */
	public static final String POST_MEMBER_MOVE = "/customer/vip/move/";

	/**
	 * 更新商铺标签 http://wiki.lianbi.com.cn/display/MS/update+businesstag
	 */
	public static final String POST_SHOPINFO = "/business/businesstag/update/";

	/**
	 * 添加促销活动 http://wiki.lianbi.com.cn/display/MS/Promotion++Add
	 */
	public static final String POST_ACTIVITY_ADD = "/coupon/promotion/add/";

	/**
	 * 编辑促销活动
	 */
	public static final String POST_ACTIVITY_EDIT = "/coupon/promotion/update/";

	/**
	 * 添加商家联营优惠 http://wiki.lianbi.com.cn/display/MS/Deal+Add
	 */
	public static final String POST_SHOPUNION_ADD = "/alliance/deal/add/";

	/**
	 * 修改商家联营优惠 http://wiki.lianbi.com.cn/display/MS/Deal+Update
	 */
	public static final String POST_SHOPUNION_EDIT = "/alliance/deal/update/";

	/**
	 * 同意店铺申请 http://wiki.lianbi.com.cn/display/MS/agree+alliance+apply
	 */
	public static final String POST_UNION_AGREE = "/alliance/alliance/agree/";

	/**
	 * 申请店铺联营 http://wiki.lianbi.com.cn/display/MS/apply+alliance
	 */
	public static final String POST_UNION_APPLY = "/alliance/alliance/apply/";

	/**
	 * 拒绝联营店铺申请 http://wiki.lianbi.com.cn/display/MS/refuse+alliance
	 */
	public static final String POST_UNION_REFUSE = "/alliance/alliance/refuse/";

	/**
	 * 取消联营申请 http://wiki.lianbi.com.cn/display/MS/cancle+alliance+apply
	 */
	public static final String POST_UNION_CANCEL = "/alliance/alliance/cancle/";

	/**
	 * 删除已联营信息 http://wiki.lianbi.com.cn/display/MS/delete+alliance
	 */
	public static final String POST_UNION_DELETE = "/alliance/alliance/delete/";

	/**
	 * 商家扫描/密码提交 确认使用优惠 http://wiki.lianbi.com.cn/display/MS/Coupon+Used
	 */
	public static final String POST_USE_COUPON = "/coupon/coupon/used/";

	/**
	 * 将批发商品加入进货单 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684359
	 */
	public static final String POST_WHOLESALE_ADDCART = "/wholesale/cartitem/apply/";

	/**
	 * 删除购物车商品 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684331
	 */
	public static final String POST_CARD_DELETE = "/wholesale/cartitem/delete/";

	/**
	 * 提交订单 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684370
	 */
	public static final String POST_SUBMIT_ORDER = "/wholesale/wholesaleorder/submit/";

	/**
	 * 提交商家回复 http://wiki.lianbi.com.cn/display/MS/bcommon+reply
	 */
	public static final String POST_COMMON_REPLY = "/comment/comment/reply/";

	// TODO

	// 获取服务器的token
	/** 服务器token */
	public static final String URL_TOKEN = "/auth/login/";

	/*
	 * ==========================================================================
	 * ============= | XMPP相关配置 ||
	 * ==============================================
	 * =========================================
	 */
	/** XMPP服务的地址 */
	public static final String XMPP_SERVER_URL = "xmpp-service.lianbi.com.cn";   // 172.16.119.131   172.16.119.146
	/** XMPP服务监听的端口号 */
	public static final int XMPP_SERVER_PORT = 5222;
	/** 服务收件人 */
	public static String RID = "isnail@lianbi.com.cn";

	/**
	 * 进入应用广告图片Url
	 */
	public static final String ADVERTISEMENT_URL = "http://e.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=028368c33887e9504217f76522086820/f703738da97739120798e261fa198618367ae270.jpg";

	public static final String GET_URL_HOT_CITY = "/area/hotcity/";

	public static final String GET_URL_AREA = "/area/area/";

	public static final String GET_AD_IMAGE = "/app_config/adimage/";

	// http://wiki.lianbi.com.cn/display/MS/get+order+list
	public static final String GET_SHOPPING_ORDER = "/shopping/order/";

	// http://wiki.lianbi.com.cn/display/MS/Get+order+detail
	public static final String GET_SHOPPING_ORDER_DETAIL = "/shopping/order/detail/";

	// http://wiki.lianbi.com.cn/display/MS/Accept+order
	public static final String POST_SHOPPING_ORDER_ACCEPT = "/shopping/order/accept/";

	// http://wiki.lianbi.com.cn/display/MS/Deny+order
	public static final String POST_SHOPPING_ORDER_DENY = "/shopping/order/deny/";

	// http://wiki.lianbi.com.cn/display/MS/get+businessinfo+detail 获取基本信息
	public static final String GET_BUSINESS_INFO_DETAIL = "/business/businessinfo/detail/";

	// http://wiki.lianbi.com.cn/display/MS/update+businessinfo 更改基本信息
	public static final String POT_UPDATE_BUSINESS_INFO = "/business/businessinfo/update/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8683892 提交店铺介绍
	public static final String POST_SHOP_INTRODUCTION = "/business/business/businessintro/change/";

	// http://wiki.lianbi.com.cn/display/MS/change+password 修改密码
	public static final String POST_CHANGE_PWD = "/auth/change_password/";

	// http://wiki.lianbi.com.cn/display/MS/Combo+Add 添加搭配套餐
	public static final String POST_ADD_COMBO = "/product/combo/add/";

	// http://wiki.lianbi.com.cn/display/MS/Combo+Update
	public static final String POST_UPDATE_COMBO = "/product/combo/update/";

	// http://wiki.lianbi.com.cn/display/MS/Combo+Detail 搭配套餐详情
	public static final String GET_COMBO_DETAIL = "/product/combo/detail/";

	// http://wiki.lianbi.com.cn/display/MS/add+vip
	public static final String POST_ADD_VIP = "/customer/vip/add/";

	// http://wiki.lianbi.com.cn/display/MS/edit+vipcard 编辑会员卡
	public static final String POST_EDIT_VIP_CARD = "/customer/vipcard/edit/";

	public static final String GET_VIP_CARD_DETAIL = "/customer/vipcard/detail/";

	// http://wiki.lianbi.com.cn/display/MS/get+product 搭配套餐 产品
	public static final String POST_PRODUCT_LIST = "/product/product/get/";

	// http://wiki.lianbi.com.cn/display/MS/get+withdraw+info 广告申请提现
	// 获取当前总收益数据与银行账户信息接口
	public static final String GET_WITHDRAW_INFO = "/income/withdraw/get/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142473  获取提现状态
	public static final String GET_WITHDRAW_STATUS = "/income/withdrawstate/";

	// http://wiki.lianbi.com.cn/display/MS/withdraw+apply 申请提现
	public static final String POST_WITHDRAW_APPLY = "/income/withdraw/apply/";

	// http://wiki.lianbi.com.cn/display/MS/Application+Add 添加广告投放
	public static final String POST_ADS_ADD = "/advertisement/application/add/";

	// http://wiki.lianbi.com.cn/display/MS/get+routerstatus 获取路由器申请状态
	public static final String GET_ROUTER_STATUS = "/router/router_status/";

	// http://wiki.lianbi.com.cn/display/MS/router+config 获取路由器设置
	public static final String GET_ROUTER_CONFIG = "/router/router_config/";

	// http://wiki.lianbi.com.cn/display/MS/router+config+update 提交路由器设置
	public static final String POST_UPDATE_ROUTER_CFG = "/router/router_config_update/";

	// http://wiki.lianbi.com.cn/display/MS/router+apply 申请路由器
	public static final String POST_APPLY_ROUTER = "/router/router_apply/";

	// http://wiki.lianbi.com.cn/display/MS/get+adsncomeinfo 获取广告总收益
	public static final String GET_AD_INCOME_INFO = "/income/adsincomeinfo/";

	// http://wiki.lianbi.com.cn/display/MS/get+adsincomedetail 广告收益列表
	public static final String GET_AD_INCOME_DETAIL = "/income/adsincomedetail/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142477   取广告投放申请状态
	public static final String GET_AD_APPLY_STATUS = "/advertisement/application/status/";

	// http://wiki.lianbi.com.cn/display/MS/Get+available+businesses+for+alliance
	public static final String GET_AVAILABLE_UNION_SHOP = "/alliance/alliance/valid_businesses/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684308 获取历史订单
	public static final String GET_WHOLE_SALE_HISTORICAL_ORDER = "/wholesale/wholesaleorder/history/";

	// http://wiki.lianbi.com.cn/display/MS/adviser+news+list
	public static final String GET_BUSINESS_CONSULTANT_LIST = "/adviser/news/";

	// http://wiki.lianbi.com.cn/display/MS/adviser+index+advertisement 生意参谋广告
	public static final String GET_BUSINESS_CONSULTANT_ADS = "/adviser/ads/";

	// http://wiki.lianbi.com.cn/display/MS/adviser+news+detail 获取生意参谋详情
	public static final String GET_BUSINESS_CONSULTANT_DETAIL = "/adviser/news/detail/";

	// http://wiki.lianbi.com.cn/display/MS/adviser+activities 获取参与活动列表
	public static final String GET_PARTICIPATE_ACTIVITIES = "/adviser/activities/";

	// http://wiki.lianbi.com.cn/display/MS/adviser+submit+register 报名参加活动
	public static final String POST_ENROLL = "/adviser/register/add/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684400
	public static final String GET_ENROLL_INFO = "/adviser/register/detail/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684426 获取消息列表
	public static final String GET_MSG_BOX_LIST = "/message/messageb/list/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684416 获取消息详情
	public static final String GET_MSG_DETAIL = "/message/messageb/detail/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684418 删除消息
	public static final String POST_DELETE_MSG = "/message/messageb/delete/";

	// http://wiki.lianbi.com.cn/display/MS/Service+List 获取运营服务统计数据
	public static final String GET_YUNYING_STATISTICS = "/statistics/statistics/service/";

	// http://wiki.lianbi.com.cn/display/MS/Marketing+List 获取营销推广统计数据
	public static final String GET_TUIGUANG_STATISTICS = "/statistics/statistics/marketing/";

	// http://wiki.lianbi.com.cn/display/MS/Router+List 获取锦上添花推广统计数据
	public static final String GET_TIANHUA_STATISTICS = "/statistics/statistics/router/";

	// http://wiki.lianbi.com.cn/display/MS/App+Check+Version 检查更新接口
	public static final String GET_CHECK_APP_VERSION = "/app_config/app/version/check/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684516 关于我们
	public static final String GET_ABOUT_US = "/app_config/mezone/about/";

	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684514 功能介绍
	public static final String GET_FUNCTION_INTRODUCTION = "/app_config/mezone/introduction/";

	// http://wiki.lianbi.com.cn/display/MS/Feedback 意见反馈
	public static final String GET_FEEDBACK = "/service/feedback/";

	// http://wiki.lianbi.com.cn/display/MS/Base+Data+Service
	public static final String GET_DATA_SERVICE = "/statistics/statistics/data/base/";

	// 
	public static final String POST_DATA_SERVICE_APPLY = "/statistics/pro/apply/";

	/** 经营项目 */
	public static final String URL_JYXM = "/product/product/get/";
	/**
	 * 消息群发 http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684577
	 */
	public static final String POST_MESSAGE_SEND = "/customer/message/send/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=8684603    数据服务专业版申请，获取用户名和电话
	public static final String GET_APPLY_PRO_INFO = "/statistics/pro/default/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142478   获取商家首页小红点
	public static final String GET_RED_DOT = "/statistics/red/dot/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142480  消除店铺首页小红点
	public static final String GET_CANCEL_RED_DOT = "/statistics/red/dot/cancel/";
	
	/**
	 * 斐讯优惠码列表
	 * http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142537
	 */
	public static final String GET_PHICOMM_COUPONCODE = "/phicomm/couponcode/list/";
	/**
	 * 查询优惠码的详情
	 * http://api.mezone.me/v3/phicomm/couponcode/detail/get/
	 */
	public static final String GET_PHICOMM_QUERYCOUPONCODE = "/phicomm/couponcode/detail/get/";
	/**
	 * 斐讯店铺     使用优惠码
	 * http://api.mezone.me/v3/phicomm/couponcode/use/
	 */
	public static final String POST_PHICOMM_USECOUPONCODE = "/phicomm/couponcode/use/";
	
	// http://wiki.lianbi.com.cn/pages/viewpage.action?pageId=9142549    获取斐讯销售信息列表
	public static final String GET_PHICOMM_SALES_INFO = "/phicomm/salerecord/";
	
}
