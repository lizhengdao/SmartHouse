package cn.com.zzwfang.controller;

import android.content.Context;
import android.text.TextUtils;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.controller.DataWorker.Options;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.http.RequestParams;

public class ActionImpl implements Action {

	private Context context;

	private ActionImpl(Context context) {
		this.context = context;
	}

	public static ActionImpl newInstance(Context context) {
		return new ActionImpl(context);
	}

	/**
	 * 获取绝对路径
	 * 
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		if (relativeUrl.startsWith("http://"))
			return relativeUrl;
		return API.host + relativeUrl; // + "?platform=a";
	}

	@Override
	public void fetchVerifyCode(String phoneNum, ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", phoneNum);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_SMS_VERIFYCODE));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void register(String phoneNum, String pwd, String captcha, int type,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", phoneNum);
		requestParams.put("password", pwd);
		requestParams.put("code", captcha);
		requestParams.put("type", type + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_REGISTER));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void checkPhoneNumRegistered(String phoneNum,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("param", phoneNum);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.CHECK_PHONE_HAS_REGISTERED));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void login(String userName, String pwd,
			ResultHandlerCallback callback) {

		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("tel", userName);
		requestParams.put("password", pwd);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.API_POST_LOGIN));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getToken(ResultHandlerCallback callback) {

		RequestParams requestParams = new RequestParams();

		requestParams.put("platform", "a");
		// Log.i("--->", "ActionImpl getToken requestParams.hashcode == "
		// + requestParams.hashCode());
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		// opt.toHttpCacheAble = false;
		opt.toMemCacheAble = false;
		opt.isShowErrorMsg = false;
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.api_get_token_post));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.POST);

		// Log.i("--->", "ActionImpl getToken requestEntity.hashcode == "
		// + requestEntity.hashCode());

		ResultHandler handler = new ResultHandler();
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);
		requestEntity.setOpts(opt);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	/**
	 * 忘记密码
	 */
	@Override
	public void resetPwd(String tel, String password, String captcha, ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();
		
		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("tel", tel);
		requestParams.put("password", password);
		requestParams.put("code", captcha);
		
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_RESET_PWD));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		Options opts = new Options();

		// opts.fromDiskCacheAble = false;
		// opts.fromMemCacheAble = false;
		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		// opts.toHttpCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);

		DataWorker.getWorker(context).load(requestEntity);
	}

	/**
	 * 检查更新
	 */
	@Override
	public void getAppVersionCheck(String version, String client,
			ResultHandlerCallback callback) {
		// RequestParams requestParams = new RequestParams();
		// requestParams.put("version", version);
		// requestParams.put("client", client);
		// requestParams.put("platform", "a");
		// RequestEntity requestEntity = new RequestEntity();
		// requestEntity.setUrl(getAbsoluteUrl(API.GET_APP_VERSIONCHECK));
		// requestEntity.setRequestParams(requestParams);
		// requestEntity.setType(RequestEntity.GET);
		//
		// Options opts = new Options();
		//
		// // opts.fromDiskCacheAble = false;
		// // opts.fromMemCacheAble = false;
		// opts.fromDiskCacheAble = false;
		// opts.fromHttpCacheAble = true;
		// opts.fromMemCacheAble = false;
		// opts.toDiskCacheAble = false;
		// // opts.toHttpCacheAble = false;
		// opts.toMemCacheAble = false;
		// requestEntity.setOpts(opts);
		// ProgressDialogResultHandler handler = new
		// ProgressDialogResultHandler(
		// context, "请稍后...");
		// handler.setResultHandlerCallback(callback);
		// requestEntity.setProcessCallback(handler);
		//
		// DataWorker.getWorker(context).load(requestEntity);

		RequestParams requestParams = new RequestParams();
		requestParams.put("version", version);
		requestParams.put("client", client);
		requestParams.put("platform", "a");
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_APP_VERSIONCHECK));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		Options opts = new Options();

		// opts.fromDiskCacheAble = false;
		// opts.fromMemCacheAble = false;
		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		// opts.toHttpCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);
		requestEntity.setProcessCallback(handler);

		DataWorker.getWorker(context).load(requestEntity);
	}

	@Override
	public void changePwd(String phoneNum, String oldPwd, String newPwd,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();
		
		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("tel", phoneNum);
		requestParams.put("password", oldPwd);
		requestParams.put("nPassword", newPwd);

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_CHANGE_PWD));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		Options opts = new Options();
		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		opts.fromDiskCacheAble = false;
		opts.fromHttpCacheAble = true;
		opts.fromMemCacheAble = false;
		opts.toDiskCacheAble = false;
		opts.toMemCacheAble = false;
		requestEntity.setOpts(opts);
		requestEntity.setProcessCallback(handler);
		DataWorker.getWorker(context).load(requestEntity);
	}

	@Override
	public void getMine(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub

	}

	// ----首页相关接口---------------------------------------------
	@Override
	public void getRecommendHouseSource(String cityId,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("id", cityId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RECOMMEND_HOUSE_SOURCE));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	// ----首页相关接口------完---------------------------------------

	@Override
	public void getCityList(ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_CITY_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSearchHouseArtifactResut(String house, int pageIndex,
			ResultHandlerCallback callback) {
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		requestParams.put("house", house);
		requestParams.put("pageIndex", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SEARCH_HOUSE_ARTIFACT));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getNewHouseList(String cityId, TextValueBean areaCondition, TextValueBean priceCondition,
			TextValueBean roomTypeCondition, TextValueBean usageCondition,
			TextValueBean labelCondition, TextValueBean statusCondition,
			String keyWords, int pageSize, int pageNum, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);
		if (areaCondition != null && !TextUtils.isEmpty(areaCondition.getValue())) {
			requestParams.put("area", areaCondition.getValue());
		}
		if (priceCondition != null && !TextUtils.isEmpty(priceCondition.getValue())) {
			requestParams.put("price", priceCondition.getValue());
		}
		if (roomTypeCondition != null && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
			requestParams.put("type", roomTypeCondition.getValue());
		}
		if (usageCondition != null && !TextUtils.isEmpty(usageCondition.getValue())) {
			requestParams.put("house", usageCondition.getValue());
		}
		if (labelCondition != null && !TextUtils.isEmpty(labelCondition.getValue())) {
			requestParams.put("label", labelCondition.getValue());
		}
		if (statusCondition != null && !TextUtils.isEmpty(statusCondition.getValue())) {
			requestParams.put("status", statusCondition.getValue());
		}
		if (!TextUtils.isEmpty(keyWords)) {
			requestParams.put("key", keyWords);
		}
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageNum + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_NEW_HOUSE_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}
	
	@Override
	public void getNewHouseDetail(String estateId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("estateId", estateId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_NEW_HOUSE_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSecondHandHouseList(String cityId, TextValueBean areaCondition, String direction,
			TextValueBean squareCondition, TextValueBean labelCondition, 
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			String buildYear, String floor, String proNum, String sort,
			int pageSize, int pageIndex, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);
		if (areaCondition != null && !TextUtils.isEmpty(areaCondition.getValue())) {
			requestParams.put("area", areaCondition.getValue());
		}
		requestParams.put("direction", direction);
		if (squareCondition != null && !TextUtils.isEmpty(squareCondition.getValue())) {
			requestParams.put("square", squareCondition.getValue());
		}
		if (labelCondition != null && !TextUtils.isEmpty(labelCondition.getValue())) {
			requestParams.put("label", labelCondition.getValue());
		}
		if (priceCondition != null && !TextUtils.isEmpty(priceCondition.getValue())) {
			requestParams.put("price", priceCondition.getValue());
		}
		if (roomTypeCondition != null && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
			requestParams.put("roomType", roomTypeCondition.getValue());
		}
		if (!TextUtils.isEmpty(buildYear)) {
			requestParams.put("buildYear", buildYear);
		}
		if (!TextUtils.isEmpty(floor)) {
			requestParams.put("floor", floor);
		}
		if (!TextUtils.isEmpty(sort)) {
			requestParams.put("sort", sort);
		}
		
		if (!TextUtils.isEmpty(proNum)) {
			requestParams.put("proNo", proNum);
		}
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SECNOD_HAND_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	/* (总价SalePriceRange、户型HouseType、物业类型PrpUsage、特色标签EstateLabel、售卖状态EstateStatus)、楼层范围（FloorRange）、租价范围（RentPriceRange）
	 * (non-Javadoc)
	 * @see cn.com.zzwfang.controller.Action#getConditionByName(java.lang.String, cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
	 */
	@Override
	public void getConditionByName(String name, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("name", name);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_CONDITION_LIST_BY_NAME));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getAreaList(String cityId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_AREA_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getSecondHandHouseDetail(String proId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("proId", proId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_SECOND_HAND_HOUSE_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getRentHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean squareCondition, String sort,
			String keyWords, String direction, TextValueBean roomTypeCondition,
			int pageSize, int pageIndex, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("siteId", cityId);
		if (areaCondition != null && !TextUtils.isEmpty(areaCondition.getValue())) {
			requestParams.put("area", areaCondition.getValue());
		}
		requestParams.put("direction", direction);
		if (squareCondition != null && !TextUtils.isEmpty(squareCondition.getValue())) {
			requestParams.put("square", squareCondition.getValue());
		}
		if (priceCondition != null && !TextUtils.isEmpty(priceCondition.getValue())) {
			requestParams.put("price", priceCondition.getValue());
		}
		if (roomTypeCondition != null && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
			requestParams.put("roomType", roomTypeCondition.getValue());
		}
		if (!TextUtils.isEmpty(sort)) {
			requestParams.put("sort", sort);
		}
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageNum", pageIndex + "");

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RENT_HOUSE_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getRentHouseDetail(String proId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("proId", proId);

		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_RENT_HOUSE_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getNewsType(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_NEWS_TYPE));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getNewsDetail(String newsTypeId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("id", newsTypeId);
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_NEWS_DETAIL));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void commitFeedback(String userId, String content, String phone,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("userId", userId);
		requestParams.put("content", content);
		if (!TextUtils.isEmpty(phone)) {
			requestParams.put("tel", phone);
		}
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.POST_COMMIT_FEEDBACK));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getAttentionList(String userId, int pageSize, int pageIndex,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("userId", userId);
//		requestParams.put("pageSize", pageSize + "");
//		requestParams.put("pageIndex", pageIndex + "");
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_MY_ATTENTION_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void attentionToHouse(String userId, String propertyId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("userId", userId);
		requestParams.put("propertyId", propertyId);
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_ATTENTION_TO_PROPERTY));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

	@Override
	public void getMyHouseList(String userPhone, int pageSize, int pageIndex,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

		requestParams.put("sign", "1111");
		requestParams.put("timestamp", "2222");
		
		requestParams.put("userId", userPhone);
		requestParams.put("pageSize", pageSize + "");
		requestParams.put("pageIndex", pageIndex + "");
		
		Options opt = new Options();
		opt.fromDiskCacheAble = false;
		opt.fromHttpCacheAble = true;
		opt.fromMemCacheAble = false;
		opt.toDiskCacheAble = false;
		opt.toMemCacheAble = false;

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setUrl(getAbsoluteUrl(API.GET_MY_HOUSE_LIST));
		requestEntity.setRequestParams(requestParams);
		requestEntity.setType(RequestEntity.GET);

		ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
				context, "请稍后...");
		handler.setResultHandlerCallback(callback);

		requestEntity.setOpts(opt);
		requestEntity.setProcessCallback(handler);

		DataWorker worker = DataWorker.getWorker(context);
		worker.load(requestEntity);
	}

    @Override
    public void getCommonProblemList(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_COMMON_PROBLEM));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

	@Override
	public void getWalletInfo(String userId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        requestParams.put("userId", userId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_WALLET_INFO));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getFeeHunterMyCustomerCondition(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_MY_CUSTOMER_CONDITION));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getFeeHunterMyCustomerList(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_MY_CUSTOMER));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	/* (non-Javadoc)
	 * @see cn.com.zzwfang.controller.Action#getSearchHouseList(cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean, java.lang.String, cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
	 */
	@Override
	public void getSearchHouseList(TextValueBean area,
			TextValueBean totalPrice, TextValueBean house, TextValueBean type,
			TextValueBean label, TextValueBean status, String key,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        if (area != null) {
        	requestParams.put("area", area.getValue());
        }
        if (totalPrice != null) {
        	requestParams.put("price", totalPrice.getValue());
        }
        if (house != null) {
        	requestParams.put("house", house.getValue());
        }
        if (type != null) {
        	requestParams.put("type", type.getValue());
        }
        if (label != null) {
        	requestParams.put("label", label.getValue());
        }
        if (status != null) {
        	requestParams.put("status", status.getValue());
        }
        if (!TextUtils.isEmpty(key)) {
        	requestParams.put("key", key);
        }
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_SEARCH_HOUSE_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	/* (non-Javadoc)
	 * @see cn.com.zzwfang.controller.Action#getNewsList(java.lang.String, int, int, cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
	 */
	@Override
	public void getNewsList(String newsId, int pageSize, int pageIndex,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        requestParams.put("type", newsId);
        requestParams.put("pageSize", pageSize + "");
        requestParams.put("pageIndex", pageIndex + "");
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_NEWS_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void createIMAccount(String id, String pwd,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        requestParams.put("uid", id);
        requestParams.put("pwd", pwd);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_CREATE_IM_ACCOUNT));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getResidentialTransactionHistory(String estId, String roomType,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        requestParams.put("estId", estId);
        requestParams.put("roomType", roomType);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_RESIDENTIAL_TRANSACTION_HISTORY));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getMapFindHouseData(int tradeType, String cityId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        requestParams.put("sign", "1111");
        requestParams.put("timestamp", "2222");
        requestParams.put("trade", tradeType + "");
        requestParams.put("siteId", cityId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_MAP_FIND_HOUSE_DATA));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍后...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	
}
