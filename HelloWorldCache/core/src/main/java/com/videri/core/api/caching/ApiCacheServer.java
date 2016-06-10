package com.videri.core.api.caching;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.videri.core.utils.CoreConstants;
import com.videri.core.utils.FileUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yiminglin on 5/19/16.
 */
public class ApiCacheServer implements Serializable {

    private final String TAG = "ApiCacheServer";
    private Gson mGson;
    private String path = "";
    private String cachingFileName = "api_caching.json";



    private ApiCacheDatas apiCachings = new ApiCacheDatas();



    public ApiCacheServer(String DateFormat, String path){
        this.path = path;
        mGson = new GsonBuilder()
                .setDateFormat(DateFormat)
                .create();

        init();
    }

    public void init(){
        try{
            Object object = FileUtil.loadSerializedObject(path, cachingFileName);
            if(object != null)
                apiCachings = (ApiCacheDatas)object;
        }
        catch (Exception e){
            if(CoreConstants.DEBUG)
                Log.e(TAG, "init error: " + e.toString());
        }
    }

    public void save(){
        apiCachings.saveObject(path,cachingFileName);

    }



    public void updateCaching(String apiName, boolean isCallCompleted){
        updateCaching(apiName,-1,null,isCallCompleted);
    }

    public void updateCaching(String apiName, int callInterval, Date lastApiCall, boolean isCallCompleted ){
        ApiCacheData apiCaching = new ApiCacheData();
        apiCaching.setApiName(apiName);
        apiCaching.setCallInterval(callInterval);
        apiCaching.setLastApiCall(lastApiCall);
        apiCaching.setCallCompleted(isCallCompleted);
        updateCaching(apiCaching);
    }

    public void updateCaching(ApiCacheData apiCaching){
        ApiCacheData mApiCaching = apiCachings.getApiCaching(apiCaching.getApiName());
        if(mApiCaching != null){
            if(apiCaching.getApiName() != null)
                mApiCaching.setApiName(apiCaching.getApiName());
            if(apiCaching.getCallInterval() != -1)
                mApiCaching.setCallInterval(apiCaching.getCallInterval());
            if(apiCaching.getLastApiCall() != null)
                mApiCaching.setLastApiCall(apiCaching.getLastApiCall());
            mApiCaching.setCallCompleted(apiCaching.isCallCompleted());
        }
        else
            apiCachings.addApiCaching(apiCaching);
    }

    public ApiCacheDatas getApiCachings() {
        return apiCachings;
    }

    public void setApiCachings(ApiCacheDatas apiCachings) {
        this.apiCachings = apiCachings;
    }

    @Override
    public String toString() {
        return "CachingApi{" +
                "apiCachings=" + apiCachings +
                '}';
    }
}
