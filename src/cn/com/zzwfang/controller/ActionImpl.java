package cn.com.zzwfang.controller;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;
import android.text.TextUtils;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.config.API;
import cn.com.zzwfang.controller.DataWorker.Options;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.http.RequestParams;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.RSAUtil;

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

    private void encryptTimeStamp(RequestParams requestParams) {
        long time = System.currentTimeMillis();
        String sign = RSAUtil.encryptByPublic(String.valueOf(time));
        requestParams.put("sign", sign);
        requestParams.put("timestamp", String.valueOf(time));
    }

    @Override
    public void fetchVerifyCode(String phoneNum, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("tel", phoneNum);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void register(String phoneNum, String pwd, String captcha, int type, String reference,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("tel", phoneNum);
        requestParams.put("password", pwd);
        requestParams.put("code", captcha);
        requestParams.put("type", type + "");
        
        if (!TextUtils.isEmpty(reference)) {
            requestParams.put("Reference", reference);
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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

        encryptTimeStamp(requestParams);
        requestParams.put("Tel", phoneNum);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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

        encryptTimeStamp(requestParams);
        requestParams.put("tel", userName);
        requestParams.put("password", pwd);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
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
    public void resetPwd(String tel, String password, String captcha,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("tel", tel);
        requestParams.put("password", password);
        requestParams.put("code", captcha);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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
        // context, "请稍候...");
        // handler.setResultHandlerCallback(callback);
        // requestEntity.setProcessCallback(handler);
        //
        // DataWorker.getWorker(context).load(requestEntity);

        RequestParams requestParams = new RequestParams();
        requestParams.put("version", version);
        requestParams.put("client", client);
        requestParams.put("platform", "a");
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);
        requestEntity.setProcessCallback(handler);

        DataWorker.getWorker(context).load(requestEntity);
    }

    @Override
    public void changePwd(String phoneNum, String oldPwd, String newPwd,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("tel", phoneNum);
        requestParams.put("password", oldPwd);
        requestParams.put("nPassword", newPwd);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.POST_CHANGE_PWD));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        Options opts = new Options();
        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
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

        encryptTimeStamp(requestParams);
        requestParams.put("id", cityId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }
    
    public void getCityByGps(double lat, double lng, ResultHandlerCallback callback) {
        
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("lat", String.valueOf(lat));
        requestParams.put("lng", String.valueOf(lng));
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_CITY_BY_GPS));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getSearchHouseArtifactResut(String allPrice, String partPrice,
            int type, String area, String rooms, String monthlyPay,
            String label, int tradeType, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        // Trade 出售0 出租1
        if (tradeType == 0) { // 出售0
            if (!TextUtils.isEmpty(label)) {
                requestParams.put("label", label);
            }
            if (type == 1) {
                requestParams.put("allPrice", allPrice);
            } else {
                requestParams.put("partPrice", partPrice);
                requestParams.put("priceRange", monthlyPay);
            }
        } else if (tradeType == 1) { // 出租 1
            if (type == 1) {
                requestParams.put("allPrice", allPrice);
            }
        }

        // if (type == 1) { // 一次性付款
        // requestParams.put("allPrice", allPrice);
        // // requestParams.put("area", area);
        // // requestParams.put("house", rooms);
        // // requestParams.put("label", label);
        // } else if (type == 0) { // 按揭
        // requestParams.put("partPrice", partPrice);
        // requestParams.put("priceRange", monthlyPay);
        // // requestParams.put("area", area);
        // // requestParams.put("house", rooms);
        //
        // }
        if (!TextUtils.isEmpty(area)) {
            requestParams.put("area", area);
        }
        if (!TextUtils.isEmpty(rooms)) {
            requestParams.put("house", rooms);
        }

        requestParams.put("type", type + "");
        requestParams.put("Trade", tradeType + "");
        requestParams.put("pageIndex", pageIndex + "");
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getNewHouseList(String cityId, TextValueBean areaCondition,
            TextValueBean priceCondition, TextValueBean roomTypeCondition,
            TextValueBean usageCondition, TextValueBean labelCondition,
            TextValueBean statusCondition, String keyWords, int pageSize,
            int pageNum, ResultHandlerCallback callback) {
        
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("siteId", cityId);
        if (areaCondition != null
                && !TextUtils.isEmpty(areaCondition.getValue())) {
            requestParams.put("area", areaCondition.getValue());
        }
        if (priceCondition != null
                && !TextUtils.isEmpty(priceCondition.getValue())) {
            requestParams.put("price", priceCondition.getValue());
        }
        if (roomTypeCondition != null
                && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
            requestParams.put("type", roomTypeCondition.getValue());
        }
        if (usageCondition != null
                && !TextUtils.isEmpty(usageCondition.getValue())) {
            requestParams.put("house", usageCondition.getValue());
        }
        if (labelCondition != null
                && !TextUtils.isEmpty(labelCondition.getValue())) {
            requestParams.put("label", labelCondition.getValue());
        }
        if (statusCondition != null
                && !TextUtils.isEmpty(statusCondition.getValue())) {
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getNewHouseDetail(String estateId,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("estateId", estateId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getSecondHandHouseList(String cityId,
            TextValueBean areaCondition, String direction,
            TextValueBean squareCondition, TextValueBean labelCondition,
            TextValueBean priceCondition, TextValueBean roomTypeCondition,
            String buildYear, String floor, String proNum, String sort,
            String key, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("siteId", cityId);
        if (areaCondition != null
                && !TextUtils.isEmpty(areaCondition.getValue())) {
            requestParams.put("area", areaCondition.getValue());
        }
        if (!TextUtils.isEmpty(direction)) {
            requestParams.put("direction", direction);
        }
        if (squareCondition != null
                && !TextUtils.isEmpty(squareCondition.getValue())) {
            requestParams.put("square", squareCondition.getValue());
        }
        if (labelCondition != null
                && !TextUtils.isEmpty(labelCondition.getValue())) {
            requestParams.put("label", labelCondition.getValue());
        }
        if (priceCondition != null
                && !TextUtils.isEmpty(priceCondition.getValue())) {
            requestParams.put("price", priceCondition.getValue());
        }
        if (roomTypeCondition != null
                && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
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
        if (!TextUtils.isEmpty(key)) {
            requestParams.put("key", key);
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    /*
     * (总价SalePriceRange、户型HouseType、物业类型PrpUsage、特色标签EstateLabel、售卖状态EstateStatus
     * )、楼层范围（FloorRange）、租价范围（RentPriceRange） (non-Javadoc)
     * 
     * @see
     * cn.com.zzwfang.controller.Action#getConditionByName(java.lang.String,
     * cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
     */
    @Override
    public void getConditionByName(String name, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("name", name);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getAreaList(String cityId, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getSecondHandHouseDetail(String proId,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("proId", proId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getRentHouseList(String cityId, TextValueBean areaCondition,
            TextValueBean priceCondition, TextValueBean squareCondition,
            String sort, String keyWords, String direction,
            TextValueBean roomTypeCondition, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("siteId", cityId);
        if (areaCondition != null
                && !TextUtils.isEmpty(areaCondition.getValue())) {
            requestParams.put("area", areaCondition.getValue());
        }
        requestParams.put("direction", direction);
        if (squareCondition != null
                && !TextUtils.isEmpty(squareCondition.getValue())) {
            requestParams.put("square", squareCondition.getValue());
        }
        if (priceCondition != null
                && !TextUtils.isEmpty(priceCondition.getValue())) {
            requestParams.put("price", priceCondition.getValue());
        }
        if (roomTypeCondition != null
                && !TextUtils.isEmpty(roomTypeCondition.getValue())) {
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getRentHouseDetail(String proId, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("proId", proId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getNewsType(ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getNewsDetail(String newsTypeId, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("id", newsTypeId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void commitFeedback(String userId, String content, String phone,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("content", content);
        if (!TextUtils.isEmpty(phone)) {
            requestParams.put("tel", phone);
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getAttentionList(String userId, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void attentionToHouse(String userId, String propertyId,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("propertyId", propertyId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMySellHouseList(String userPhone, String cityId,
            int pageSize, int pageIndex, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("tel", userPhone);
        requestParams.put("siteId", cityId);
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getCommonProblemList(ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getWalletInfo(String userId, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
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

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_MY_CUSTOMER_CONDITION));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getFeeHunterMyCustomerList(String userId, String cityId,
            String status, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("siteId", cityId);
        requestParams.put("status", status);
        requestParams.put("pageIndex", pageIndex + "");
        requestParams.put("pageSize", pageSize + "");

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.com.zzwfang.controller.Action#getSearchHouseList(cn.com.zzwfang.bean
     * .TextValueBean, cn.com.zzwfang.bean.TextValueBean,
     * cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean,
     * cn.com.zzwfang.bean.TextValueBean, cn.com.zzwfang.bean.TextValueBean,
     * java.lang.String,
     * cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
     */
    @Override
    public void getSearchHouseList(TextValueBean area,
            TextValueBean totalPrice, TextValueBean house, TextValueBean type,
            TextValueBean label, TextValueBean status, String key,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
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
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.zzwfang.controller.Action#getNewsList(java.lang.String, int,
     * int, cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
     */
    @Override
    public void getNewsList(String newsId, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("type", newsId);
        requestParams.put("pageSize", pageSize + "");
        requestParams.put("pageIndex", pageIndex + "");
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void createIMAccount(String id, String pwd,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("uid", id);
        requestParams.put("pwd", pwd);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getResidentialTransactionHistory(String estId, String roomType,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("estId", estId);
        requestParams.put("roomType", roomType);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_RESIDENTIAL_TRANSACTION_HISTORY));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMapFindHouseData(int tradeType, String cityId,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("trade", tradeType + "");
        requestParams.put("siteId", cityId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

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
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void recommendFeeHunterCustomer(int trade, String minPrice,
            String maxPrice, String contactName, String phone, String remark,
            String userId, String citeId, String area,
            ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("Area", area);
        requestParams.put("maxPrice", maxPrice);
        requestParams.put("minPrice", minPrice);
        requestParams.put("name", contactName);
        requestParams.put("telNum", phone);
        if (!TextUtils.isEmpty(remark)) {
            requestParams.put("reMark", remark);
        }
        requestParams.put("userId", userId);
        requestParams.put("siteId", citeId);
        requestParams.put("trade", trade + "");

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        // opt.toHttpCacheAble = false;
        opt.toMemCacheAble = false;
        opt.isShowErrorMsg = false;
        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.POST_FEE_HUNTER_RECOMMEND_CUSTOMER));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ResultHandler handler = new ResultHandler();
        handler.setResultHandlerCallback(callback);
        requestEntity.setProcessCallback(handler);
        requestEntity.setOpts(opt);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getAutoCompleteEstate(String ciytId, String keywords, int top,
            int type, ResultHandlerCallback callback) {
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("siteId", ciytId);
        requestParams.put("value", keywords);
        if (top > 0) {
            requestParams.put("top", top + "");
        }
        if (type > -1) {
            requestParams.put("type", type + "");
        }

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_ESTATE_AUTO_COMPLETE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void recommendFeeHunterHouseSource(String userId, String estateId,
            String rigdepole, String unit, String roomNo, String estateName,
            String cityId, String floor, int trade, String contactName,
            String telNum, String remark, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);

        requestParams.put("userId", userId);
        requestParams.put("estateId", estateId);
        requestParams.put("Ridgepole", rigdepole);
        requestParams.put("unit", unit);
        requestParams.put("roomNo", roomNo);
        requestParams.put("estateName", estateName);
        requestParams.put("siteId", cityId);
        requestParams.put("floor", floor);
        requestParams.put("trade", trade + "");
        requestParams.put("name", contactName);
        requestParams.put("telNum", telNum);

        if (!TextUtils.isEmpty(remark)) {
            requestParams.put("orientation", remark);
        }

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        // opt.toHttpCacheAble = false;
        opt.toMemCacheAble = false;
        opt.isShowErrorMsg = false;
        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.POST_FEE_HUNTER_RECOMMEND_HOUSE_SOURCE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ResultHandler handler = new ResultHandler();
        handler.setResultHandlerCallback(callback);
        requestEntity.setProcessCallback(handler);
        requestEntity.setOpts(opt);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getAgentInfoDetail(String agentId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("employeeId", agentId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_AGENT_INFO_DETAIL));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getFeeHunterRule(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_RULE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getFeeHunterRecommendHouseSourceList(String userId,
            String cityId, int pageSize, int pageIndex,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("siteId", cityId);
        requestParams.put("pageIndex", pageIndex + "");
        requestParams.put("pageSize", pageSize + "");

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_RECOMMEND_HOUSE_SOURCE_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getHouseSourceProgress(String houseSourceId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("id", houseSourceId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_FEE_HUNTER_HOUSE_SOURCE_PROGRESS));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void deleteCollection(String userId, String sourceId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("attId", sourceId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_DELETE_COLLECTION));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMyDemandInfo(String userId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_MY_DEMAND_INFO));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void sendMessage(String from, String to, String message,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("from", from);
        requestParams.put("to", to);
        requestParams.put("message", message);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.SEND_MESSAGE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMonthlyPayRange(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_MONTHLY_PAY_RANGE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void commitFeeHunterBankInfo(String userId, String realName,
            String bankCode, String bankName, String bankCity,
            String bankImage, String openAccountBankName, String cityId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        if (!TextUtils.isEmpty(realName)) {
            requestParams.put("realName", realName);
        }

        if (!TextUtils.isEmpty(bankCode)) {
            requestParams.put("bankCode", bankCode);
        }

        if (!TextUtils.isEmpty(bankName)) {
            requestParams.put("bankName", bankName);
        }

        if (!TextUtils.isEmpty(bankCity)) {
            requestParams.put("bankCity", bankCity);
        }

        if (!TextUtils.isEmpty(bankImage)) {
            requestParams.put("bankImage", bankImage);
        }

        if (!TextUtils.isEmpty(openAccountBankName)) {
            requestParams.put("openAccountName", openAccountBankName);
        }
        
        requestParams.put("SiteId", cityId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.POST_USER_BANK_INFO_TO_BE_FEE_HUNTER));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getHousesRecommendedToMe(String userId, int pageSize,
            int pageIndex, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        requestParams.put("pageIndex", pageIndex + "");
        requestParams.put("pageSize", pageSize + "");
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_HOUSES_RECOMMENDED_TO_ME));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMyBoughtHouses(String userId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_MY_BOUGHT_HOUSES));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getBankProvinceOrCity(String cityCode,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();
        encryptTimeStamp(requestParams);
        if (!TextUtils.isEmpty(cityCode)) {
            requestParams.put("cityCode", cityCode);
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_BANK_PROVINCE_OR_CITY));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getBankNameList(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();
        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_BANK_NAME_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.zzwfang.controller.Action#getContactsList(java.lang.String,
     * cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback)
     */
    @Override
    public void getContactsList(String visitorId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();
        encryptTimeStamp(requestParams);
        requestParams.put("visitorId", visitorId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_CONTACTS_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getMessageRecordsWithSomeBody(String contactId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("key", contactId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_MESSGAE_RECORD_WITH_SOMEBODY));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getContactOnLineStatus(String ids,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("ids", ids);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_MESSGAE_RECORD_WITH_SOMEBODY));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void readMessage(String ids, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("ids", ids);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.READ_MESSAGES));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void entrustSellHouse(String estateId, String estateName,
            String ridgepole, String unit, String roomNo, String type,
            String countFang, String halls, String wc, String square,
            String direction, String totalFloor, String floor,
            String decoration, String title, String desc, String cityId,
            String userId, String Name, boolean sex, String phone,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("EstateId", estateId);
        requestParams.put("EstateName", estateName);
        requestParams.put("Ridgepole", ridgepole);
        requestParams.put("Unit", unit);
        requestParams.put("RoomNo", roomNo);
        requestParams.put("Type", type);
        // requestParams.put("price", "50");
        requestParams.put("CountFang", countFang);
        requestParams.put("Hall", halls);
        requestParams.put("Wc", wc);
        requestParams.put("Acreage", square);
        requestParams.put("PropertyDirection", direction);
        requestParams.put("FloorAll", totalFloor);
        requestParams.put("Floor", floor);
        requestParams.put("Renovated", decoration);
        requestParams.put("Title", title);
        requestParams.put("Description", desc);
        requestParams.put("SiteId", cityId);
        requestParams.put("UserId", userId);
        requestParams.put("Name", Name);
        if (sex) {
            requestParams.put("Sex", "true");
        } else {
            requestParams.put("Sex", "false");
        }
        requestParams.put("TelNum", phone);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.ENTRUST_SELL_HOUSE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void entrustBuyHouse(String cityId, String userId, String estateId,
            double budget, int minSquare, int maxSquare, double minTotalPrice,
            double maxTotalPrice, String monthlyPay, int countFang, int hall,
            String require, String name, boolean sex,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("siteId", cityId);
        requestParams.put("userId", userId);
        requestParams.put("estateId", estateId);
        if (budget >= 0) {
            requestParams.put("budget", String.valueOf(budget));
        }
        requestParams.put("minSquare", minSquare + "");
        requestParams.put("maxSquare", maxSquare + "");
        requestParams.put("minPrice", String.valueOf(minTotalPrice));
        requestParams.put("maxPrice", String.valueOf(maxTotalPrice));
        requestParams.put("monthlyPay", monthlyPay);
        requestParams.put("countFang", countFang + "");
        requestParams.put("hall", hall + "");
        requestParams.put("require", require);
        requestParams.put("name", name);
        if (sex) {
            requestParams.put("sex", "true");
        } else {
            requestParams.put("sex", "false");
        }

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.ENTRUST_BUY_HOUSE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getAboutUsData(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_ABOUT_US));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void otherFileUpload(File file, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        try {
            requestParams.put("requestFile", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.POST_OTHER_UPLOAD));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void updateUserInfo(String userId, String nickName,
            String avatarUrl, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);

        if (!TextUtils.isEmpty(nickName)) {
            requestParams.put("name", nickName);
        }
        if (!TextUtils.isEmpty(avatarUrl)) {
            requestParams.put("photo", avatarUrl);
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_UPDATE_USER_INFO));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getGuiderPageData(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_GUIDER_PAGE_DATA));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ResultHandler handler = new ResultHandler();
        // ProgressDialogResultHandler handler = new
        // ProgressDialogResultHandler(
        // context, "请稍候...");
        // handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getClientProgress(String id, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("id", id);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_CLIENT_PROGRESS));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getBindBankInfo(String userId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userid", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_BIND_BANK_CARD_INFO));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getCourtDetail(String courtId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("estateId", courtId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_COURT_DETAIL));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getIncomeStatement(String prpId, String type, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("PrpId", prpId);
        if (!TextUtils.isEmpty(type)) {
            requestParams.put("type", type);
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_INCOME_STATEMENT));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getSearchHouseArtifactHouseType(ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity
                .setUrl(getAbsoluteUrl(API.GET_SEARCH_HOUSE_ARTIFACT_HOUSE_TYPE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getClientInfoChange(String clientId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("Id", clientId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_CLIENT_INFO_CHANGE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getHouseSourceInfoChange(String houseSourceId, boolean isSale,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("Id", houseSourceId);
        if (isSale) {
            requestParams.put("isSale", "1");
        }
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_HOUSE_SOURCE_INFO_CHANGE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getHouseSourceFollowList(String houseSourceId, int pageIndex,
            int pageSize, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("Id", houseSourceId);
        requestParams.put("PageIndex", pageIndex + "");
        requestParams.put("PageSize", pageSize + "");
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_HOUSE_SOURCE_FOLLOW_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void complain(String id, String type, String userId, String content,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("id", id);
        requestParams.put("type", type);
        requestParams.put("userid", userId);
        requestParams.put("content", content);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.COMPLAIN));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void seeHouseExperience(String userId, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.SEE_HOUSE));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void support(String id, int supportType, String userId,
            String content, ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("id", id);
        requestParams.put("type", supportType + "");
        requestParams.put("userid", userId);
        requestParams.put("content", content);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.SUPPORT));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

	@Override
	public void getEstateBuilding(String estateId,
			ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("estateId", estateId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_ESTATE_BUILDING));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getEstateCell(String buildingId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("buildingId", buildingId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_ESTATE_CELL));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

	@Override
	public void getEstateRoom(String cellId, ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("cellId", cellId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_ESTATE_ROOM));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

    @Override
    public void getBountyHunterInfo(String userId,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("userId", userId);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_Bounty_Hunter_Info));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getCompanyAnnocementList(int pageIndex, int pageSize,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
//        requestParams.put("type", "");
        requestParams.put("pageindex", String.valueOf(pageIndex));
        requestParams.put("pagesize", String.valueOf(pageSize));
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_COMPANY_ANNOCEMENT_LIST));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void getCompanyAnnocementDetail(String id,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
//        requestParams.put("type", "");
        requestParams.put("id", id);
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_COMPANY_ANNOCEMENT_DETAIL));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.GET);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

    @Override
    public void commentCompanyAnnocementDetail(String id, String content,
            String commentId, String commentName,
            ResultHandlerCallback callback) {
        // TODO Auto-generated method stub
        RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        requestParams.put("id", id);
        
        requestParams.put("content", content);
//        requestParams.put("ip", ip);
//        requestParams.put("Address", address);
        requestParams.put("CommonerId", commentId);
        requestParams.put("CommonerName", commentName);
        
        
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);

        Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        // opt.toHttpCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.POST_COMMENT_COMPANY_ANNOCEMENT_DETAIL));
        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
    }

	@Override
	public void getComplainLabels(ResultHandlerCallback callback) {
		// TODO Auto-generated method stub
		RequestParams requestParams = new RequestParams();

        encryptTimeStamp(requestParams);
        CityBean cityBean = ContentUtils.getCityBean(context);
        String SiteId = "";
        if (cityBean != null) {
            SiteId = cityBean.getSiteId();
        }
        requestParams.put("SiteId", SiteId);
        
		Options opt = new Options();
        opt.fromDiskCacheAble = false;
        opt.fromHttpCacheAble = true;
        opt.fromMemCacheAble = false;
        opt.toDiskCacheAble = false;
        // opt.toHttpCacheAble = false;
        opt.toMemCacheAble = false;

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(getAbsoluteUrl(API.GET_COMPLAIN_LABELS));
//        requestEntity.setRequestParams(requestParams);
        requestEntity.setType(RequestEntity.POST);

        ProgressDialogResultHandler handler = new ProgressDialogResultHandler(
                context, "请稍候...");
        handler.setResultHandlerCallback(callback);

        requestEntity.setOpts(opt);
        requestEntity.setProcessCallback(handler);

        DataWorker worker = DataWorker.getWorker(context);
        worker.load(requestEntity);
	}

}
