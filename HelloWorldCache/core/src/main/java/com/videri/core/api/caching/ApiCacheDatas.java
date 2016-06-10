package com.videri.core.api.caching;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yiminglin on 5/19/16.
 */
public class ApiCacheDatas implements Serializable {

    @SerializedName("api_datas")
    ArrayList<ApiCacheData> apiCachings = new ArrayList<>();

    public ArrayList<ApiCacheData> getApiCachings() {
        return apiCachings;
    }

    public void setApiCachings(ArrayList<ApiCacheData> apiCachings) {
        this.apiCachings = apiCachings;
    }

    public void addApiCaching(ApiCacheData apiCaching){
        if(!isApiExist(apiCaching.getApiName()))
            apiCachings.add(apiCaching);
    }

    public void saveObject(String path, String fileName){

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path + "/" + fileName)));
            oos.writeObject( this );
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ApiCachings{" +
                "apiCachings=" + apiCachings +
                '}';
    }



    public int getApiCashingIndex (String apiName){
        if(!isEmpty())
            for(int i = 0; i < getCount(); i++) {
                if(apiCachings.get(i).getApiName().equals(apiName))
                    return i;
            }
        return -1;
    }
    public ApiCacheData getApiCaching(String apiName){
        int index = getApiCashingIndex(apiName);
        if(index != - 1)
            return apiCachings.get(index);

        return null;
    }
    public boolean isApiExist(String apiName){
        if(!isEmpty())
            for(int i = 0; i < getCount(); i++) {
                if(apiCachings.get(i).getApiName().equals(apiName))
                    return true;
            }

        return false;
    }

    public boolean isEmpty(){
        if(getCount() == 0)
            return true;
        return false;
    }

    public int getCount(){
        return apiCachings.size();
    }
}
