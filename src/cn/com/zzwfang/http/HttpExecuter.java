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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;


/**
 *    具体Http 请求方法接口实现  具体get  post 请求实现
 * 
 * @author Soo
 *
 */
public class HttpExecuter implements HttpExecuterInterface{
    
    private HttpClientInterface httpClientInterface;
    
    public HttpExecuter(){
        
    }
    
    public HttpExecuter(HttpClientInterface httpClientInterface){
        this.httpClientInterface = httpClientInterface;
    }
    
    protected HttpResponse execute(HttpUriRequest httpUriRequest){
        if(httpClientInterface == null || httpUriRequest == null){
            return null;
        }
        return httpClientInterface.request(httpUriRequest);
    }
    
    public void setHttpClientInterface(HttpClientInterface httpClientInterface){
        this.httpClientInterface = httpClientInterface;
    }
    
    @Override
    public HttpResponse get(String url) {
        return get(url, null);
    }

    @Override
    public HttpResponse get(String url, RequestParams params) {
        return get(url, params, null);
    }

    @Override
    public HttpResponse get(String url, RequestParams params, Header[] headers) {
        if(url == null){
            return null;
        }
        if(params != null){
            String paramString = params.getParamString();
            if(url.indexOf("?") == -1){
                url += "?" + paramString;
            }else{
                url += "&" + paramString;
            }
        }
        HttpGet httpGet = new HttpGet(url);
        if(headers != null && headers.length > 0){
            httpGet.setHeaders(headers);
        }
        return execute(httpGet);
    }

    @Override
    public HttpResponse post(Context context, String url) {
        return post(context, url, null);
    }

    @Override
    public HttpResponse post(Context context, String url, RequestParams params) {
        return post(context, url, params, null);
    }

    @Override
    public HttpResponse post(Context context, String url, RequestParams params, Header[] headers) {
        if(url == null){
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        
        if(params != null){
            HttpEntity entity = params.getEntity();
            httpPost.setEntity(entity);
        }
        if(headers != null && headers.length > 0){
            httpPost.setHeaders(headers);
        }
        return execute(httpPost);
    }

    @Override
    public HttpResponse put(String url) {
        return put(url, null);
    }

    @Override
    public HttpResponse put(String url, RequestParams params) {
        return put(url, params, null);
    }

    @Override
    public HttpResponse put(String url, RequestParams params, Header[] headers) {
        if(url == null){
            return null;
        }
        HttpPut httpPut = new HttpPut(url);
        if(params != null){
            HttpEntity entity = params.getEntity();
            httpPut.setEntity(entity);
        }
        if(headers != null){
            httpPut.setHeaders(headers);
        }
        return execute(httpPut);
    }

    @Override
    public HttpResponse delete(String url) {
        return delete(url, null);
    }

    @Override
    public HttpResponse delete(String url, RequestParams params) {
        return delete(url, params, null);
    }

    @Override
    public HttpResponse delete(String url, RequestParams params, Header[] headers) {
        if(url == null){
            return null;
        }
        if(params != null){
            String paramString = params.getParamString();
            if(url.indexOf("?") == -1){
                url += "?" + paramString;
            }else{
                url += "&" + paramString;
            }
        }
        HttpDelete httpDelete = new HttpDelete(url);
        if(headers != null && headers.length > 0){
            httpDelete.setHeaders(headers);
        }
        return execute(httpDelete);
    }

    @Override
    public byte[] img(String urlString) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10 * 1000);
            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while((len = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();
                return outputStream.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            try{
                if(inputStream != null){
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    
}
