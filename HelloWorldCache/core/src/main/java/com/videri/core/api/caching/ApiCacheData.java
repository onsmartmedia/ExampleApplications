package com.videri.core.api.caching;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yiminglin on 5/19/16.
 */
public class ApiCacheData implements Serializable {

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public int getCallInterval() {
        return callInterval;
    }

    public void setCallInterval(int callInterval) {
        this.callInterval = callInterval;
    }

    public boolean isCallCompleted() {
        return isCallCompleted;
    }

    public void setCallCompleted(boolean callCompleted) {
        isCallCompleted = callCompleted;
    }

    public Date getLastApiCall() {
        return lastApiCall;
    }

    public void setLastApiCall(Date lastApiCall) {
        this.lastApiCall = lastApiCall;
    }

    @Override
    public String toString() {
        return "ApiCaching{" +
                "apiName='" + apiName + '\'' +
                ", callInterval=" + callInterval +
                ", lastApiCall=" + lastApiCall +
                ", isCallCompleted=" + isCallCompleted +
                '}';
    }

    @SerializedName("api_name")
    private String apiName;
    @SerializedName("call_interval")
    private int callInterval;
    @SerializedName("last_api_call")
    private Date lastApiCall;
    @SerializedName("is_call_completed")
    private boolean isCallCompleted;

    //TODO: it may some api actions never called. based on the requirement
}
