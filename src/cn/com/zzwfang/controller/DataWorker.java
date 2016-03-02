package cn.com.zzwfang.controller;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import cn.com.zzwfang.cache.DiskCache;
import cn.com.zzwfang.cache.MemoryCache;
import cn.com.zzwfang.config.AppConfiger;
import cn.com.zzwfang.http.HttpClient;
import cn.com.zzwfang.http.HttpExecuter;
import cn.com.zzwfang.http.HttpExecuterInterface;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.StringUtils;

/**
 * 
 * 获取网络数据入口
 * 
 * @author Isnail
 * 
 */
public class DataWorker {

    private MemoryCache<String, byte[]> memCache;

    private DiskCache<String, byte[]> diskCache;

    private static DataWorker instance;

    private Context context;

    private boolean isClose;

    private HashMap<String, InnerTaskEntity> innerTaskEntities = new HashMap<String, InnerTaskEntity>();

    public synchronized static DataWorker getWorker(Context context) {
        if (instance == null) {
            instance = new DataWorker(context);
        }
        return instance;
    }

    private DataWorker(Context context) {
        this.context = context;
        memCache = new MemoryCache<String, byte[]>() {

            @Override
            public int sizeOf(byte[] value) {
                return value.length;
            }

            @Override
            protected Object getCacheKey(String key) {
                return StringUtils.md5(key);
            }
        };

        try {
            diskCache = new DiskCache<String, byte[]>(
                    AppConfiger.getDiskCacheDir("Data")) {

                @Override
                protected String getFileName(String key) {
                    return StringUtils.md5(key);
                }

                @Override
                protected byte[] toValue(byte[] data) {
                    return data;
                }

                @Override
                protected byte[] toByteArray(byte[] value) {
                    return value;
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(RequestEntity requestEntity) {
        load(requestEntity, requestEntity.getOpts(),
                requestEntity.getProcessCallback());
    }

    public void load(RequestEntity requestEntity, Options opts,
            ProcessCallback callback) {

        if (isClose) {
            return;
        }

        if (requestEntity == null) {
            return;
        }
        String requestKey = requestEntity.toString();
        if (requestKey == null) {
            return;
        }
        byte[] data = null;
        if (opts.fromMemCacheAble && memCache != null) {
            data = memCache.getValue(requestKey);
        }
        if (data != null && callback != null) {
            callback.onPostExecute(context, requestEntity, data);
            return;
        }
        if (opts.fromDiskCacheAble || opts.fromHttpCacheAble) {

            if (callback != null) {
                callback.onPreExecute(requestEntity);
            }
            canclePotaintialTask(requestEntity);
            InnerTask task = new InnerTask(opts, callback);
            InnerTaskEntity entity = new InnerTaskEntity(task);
            innerTaskEntities.put(requestKey, entity);
            task.execute(requestEntity);
        }
    }

    public void canclePotaintialTask(RequestEntity requestEntity) {
        if (requestEntity != null) {
            String requestKey = requestEntity.toString();
            if (requestKey != null) {
                InnerTaskEntity entity = innerTaskEntities.remove(requestKey);
                if (entity != null) {
                    InnerTask task = entity.getInnerTask();
                    if (task != null) {
                        task.cancel(true);
                    }
                }
            }
        }
    }

    public void cancleAllTask() {
        Set<String> set = innerTaskEntities.keySet();
        if (set != null) {
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                InnerTaskEntity taskEntity = innerTaskEntities.remove(key);
                InnerTask task = taskEntity.getInnerTask();
                if (task != null) {
                    task.cancel(true);
                }
            }
        }
    }

    public void close() {
        this.isClose = true;
    }

    public void open() {
        this.isClose = false;
    }

    private class InnerTaskEntity {
        private final WeakReference<InnerTask> weakReferenceTask;

        public InnerTaskEntity(InnerTask task) {
            this.weakReferenceTask = new WeakReference<InnerTask>(task);
        }

        public InnerTask getInnerTask() {
            return weakReferenceTask.get();
        }
    }

    private class InnerTask extends AsyncTask<RequestEntity, Integer, byte[]> {

        private Options opts;

        private ProcessCallback callback;

        private RequestEntity requestEntity;

        HttpClient httpClient = new HttpClient(context);

        // private HttpExecuterInterface httpExecuter = new HttpExecuter(new
        // HttpClient());

        private HttpExecuterInterface httpExecuter = new HttpExecuter(
                httpClient);

        public InnerTask(Options opts, ProcessCallback callback) {
            this.opts = opts;
            this.callback = callback;
        }

        @Override
        protected byte[] doInBackground(RequestEntity... params) {
            if (params == null || (requestEntity = params[0]) == null) {
                return null;
            }

            String requestKey = requestEntity.toString();
            if (requestKey == null) {
                return null;
            }
            byte[] data = null;

            // 从Disk取数据
//            if (opts.fromDiskCacheAble && diskCache != null) {
//                data = diskCache.getValue(requestKey);
//
//                File file = diskCache.getCacheDirectory();
//                File files[] = file.listFiles();
//                int length = files.length;
//                for (int i = 0; i < length; i++) {
//                    // Log.i("--->",
//                    // "DataWorker files[" + i + "] == "
//                    // + files[i].getAbsolutePath());
//                }
//            }

            // 从网络取数据
            if (data == null && opts.fromHttpCacheAble && httpExecuter != null) {
                HttpResponse httpResponse = null;
                switch (requestEntity.getType()) {
                case RequestEntity.GET:
                    httpResponse = httpExecuter.get(requestEntity.getUrl(),
                            requestEntity.getRequestParams(),
                            requestEntity.getHeaders());
                    break;
                case RequestEntity.POST:

                    httpResponse = httpExecuter.post(context,
                            requestEntity.getUrl(),
                            requestEntity.getRequestParams(),
                            requestEntity.getHeaders());

                    break;
                case RequestEntity.DELETE:
                    httpResponse = httpExecuter.delete(requestEntity.getUrl(),
                            requestEntity.getRequestParams(),
                            requestEntity.getHeaders());
                    break;
                case RequestEntity.PUT:
                    httpResponse = httpExecuter.put(requestEntity.getUrl(),
                            requestEntity.getRequestParams(),
                            requestEntity.getHeaders());
                    break;
                case RequestEntity.DOWNLOAD:
                    // TODO
                    break;
                }

                if (data == null && httpResponse != null) {
                    StatusLine statusLine = httpResponse.getStatusLine();
                    if (statusLine != null) {
                        // Log.i("--->", "doInBackground  statusLine == "
                        // + statusLine);
                        int statusCode = statusLine.getStatusCode();
                        if (statusCode == HttpStatus.SC_OK) {
                            try {
                                data = EntityUtils.toByteArray(httpResponse
                                        .getEntity());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            if (data != null) {
                if (opts.toMemCacheAble && memCache != null) {
                    memCache.putValue(requestEntity.toString(), data);
                }
                if (opts.toDiskCacheAble && diskCache != null) {
                    diskCache.putValue(requestEntity.toString(), data);
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(byte[] result) {
            super.onPostExecute(result);
            if (callback != null) {
                callback.onPostExecute(context, requestEntity, result);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (callback != null) {
                callback.onProcess(requestEntity, values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (callback != null) {
                callback.onCancelled(requestEntity);
            }
        }
    }

    public interface ProcessCallback {

        void onPreExecute(RequestEntity entity);

        void onProcess(RequestEntity entity, int process);

        void onPostExecute(Context context, RequestEntity entity, byte[] data);

        void onCancelled(RequestEntity entity);
    }

    public static class Options {

        public boolean fromMemCacheAble = true;
        public boolean fromDiskCacheAble = true;
        public boolean fromHttpCacheAble = true;

        public boolean toMemCacheAble = true;
        public boolean toDiskCacheAble = true;
        public boolean isShowErrorMsg = true;
        // public boolean toHttpCacheAble = true;
    }
}
