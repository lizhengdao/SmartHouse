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
package cn.com.zzwfang.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

/**
 * @author Soo
 *
 */
public abstract class DiskCache<K, V> implements Cache<K, V>{

    private static final int DEFAULT_PER_CACHE_ENTRY = 1;
    
    private static final int DEFAULT_MAX_CACEH_SIZE = 1024 * 1024 * 20;
    
    private static final long DEFUALT_MAX_CYCLE_LIFE = 3 * 24 * 60 * 60 * 1000;
    
    private String directory;
    
    private long maxLifeCycle;
    
    private int appVersion;
    
    private long maxSize;
    
    private int entry;
    
    private DiskLruCache diskCache;
    
    public DiskCache(String directory) throws IOException{
        this(directory, 1, DEFAULT_PER_CACHE_ENTRY, DEFAULT_MAX_CACEH_SIZE, DEFUALT_MAX_CYCLE_LIFE);
    }
    
    public DiskCache(String directory, int appVersion, int valueCount, long maxSize, long maxCycleLife)throws IOException{
        this.directory = directory;
        this.appVersion = appVersion;
        this.entry = valueCount;
        this.maxSize = maxSize;
        this.maxLifeCycle = maxCycleLife;
        
        File file = getCacheDirectory();
//        File file = AppConfiger.getDiskCacheDir(null, "cachetest");
        this.directory = file.getAbsolutePath();
        diskCache = DiskLruCache.open(file, appVersion, valueCount, maxSize);
        Log.i("--->", "DiskCache 构造方法  file == " + file.getAbsolutePath());
    }
    
    protected abstract String getFileName(K key);
    
    protected abstract V toValue(byte[] data);
    
    protected abstract byte[] toByteArray(V value);
    
    protected byte[] readStream(InputStream is) throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        return outStream.toByteArray();
    }
    
    @Override
    public synchronized V getValue(K key) {
        if(!check()) return null;
        InputStream is = null;
        String fileName = getFileName(key);
        Log.i("--->", "getValue --- key == " + key);
        if(fileName == null) return null;
        Log.i("--->", "getValue --- fileName == " + fileName);
        Log.i("--->", "isOutofDate(fileName) == " + isOutofDate(fileName));
        if(isOutofDate(fileName)){
            remove(key);
            return null;
        }
        
        try {
            DiskLruCache.Snapshot ds = diskCache.get(fileName);
            Log.i("--->", "DiskLruCache.Snapshot ds == " + ds);
            if(ds != null){
                is = ds.getInputStream(0);
                byte[] data = readStream(is);
                return toValue(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public synchronized V putValue(K key, V value) {
        if(!check()) return null;
        Log.i("--->", "putValue --- key == " + key);
        String fileName = getFileName(key);
        Log.i("--->", "putValue --- fileName == " + fileName);
        if(fileName == null || value == null) return null;
        byte[] data = toByteArray(value);
        OutputStream os = null;
        if(data != null){
            try {
                DiskLruCache.Snapshot ds = diskCache.get(fileName);
                if(ds == null){
                    DiskLruCache.Editor de = diskCache.edit(fileName);
                    if(de != null){
                        os = de.newOutputStream(0);
                        os.write(data, 0, data.length);
                        de.commit();
                        os.close();
                        Log.i("--->", key + "  成功写入DiskCache");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public synchronized V remove(K key) {
        if(!check()) return null;
        String fileName = getFileName(key);
        if(fileName == null) return null;
        try {
            diskCache.remove(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void clear() {
        if(!check()){
            try {
                diskCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            diskCache = null;
            File file = getCacheDirectory();
            try {
                diskCache = DiskLruCache.open(file, appVersion, entry, maxSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized long size() {
        return check() ? diskCache.size() : -1;
    }

    @Override
    public int sizeOf(V value) {
        return 0;
    }

    public long getMaxLifeCycle(){
        return maxLifeCycle;
    }
    
    public File getCacheDirectory(){
        if(directory == null){
            return null;
        }
        File file = new File(directory);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
    
    public boolean check() {
    	Log.i("--->", "check()  diskCache == " + diskCache + "  diskCache.isClosed() == " + diskCache.isClosed());
        return diskCache != null && !diskCache.isClosed();
    }
    
    private boolean isOutofDate(String fileName){
        File file = new File(directory, fileName);
        if(file.exists()){
            long lastModify = file.lastModified();
            if(maxLifeCycle < System.currentTimeMillis() - lastModify){
                return true;
            }
        }
        return false;
    }
}
