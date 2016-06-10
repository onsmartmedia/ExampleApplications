package com.videri.helloworldcache.ui.caching;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by yiminglin on 5/31/16.
 */
public class UICaching implements Serializable {


    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "UICaching{" +
                "activityName='" + activityName + '\'' +
                '}';
    }

    @SerializedName("activity_name")
    private String activityName;


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
}
