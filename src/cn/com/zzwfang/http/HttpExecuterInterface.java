/*
 * Copyright (c) 2013-2014 Soo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.zzwfang.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.content.Context;

/**
 * 
 *   封装Http 请求方法接口
 * @author Soo
 */
public interface HttpExecuterInterface {
    
    public static final int GET = 0xf01;
    
    public static final int POST = 0xf02;
    
    public static final int DELETE = 0xf03;
    
    public static final int DOWNLOAD = 0xf04;
    
    public static final int IMG = 0xf05;
    
    HttpResponse get(String url);
    
    HttpResponse get(String url, RequestParams params);
    
    HttpResponse get(String url, RequestParams params, Header[] headers);
    
    HttpResponse post(Context context, String url);
    
    HttpResponse post(Context context, String url, RequestParams params);
    
    HttpResponse post(Context context, String url, RequestParams params, Header[] headers);
    
    HttpResponse put(String url);
    
    HttpResponse put(String url, RequestParams params);
    
    HttpResponse put(String url, RequestParams params, Header[] headers);
    
    HttpResponse delete(String url);
    
    HttpResponse delete(String url, RequestParams params);
    
    HttpResponse delete(String url, RequestParams params, Header[] headers);
    
    byte[] img(String url);
    
}