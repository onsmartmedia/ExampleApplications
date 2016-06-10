package com.videri.helloworldcache.ui.caching;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by yiminglin on 6/1/16.
 */
public class HelloWorldUICaching implements Serializable {

    public String getHelloWorldText() {
        return helloWorldText;
    }

    public void setHelloWorldText(String helloWorldText) {
        this.helloWorldText = helloWorldText;
    }

    @Override
    public String toString() {
        return "HelloWorldUICaching{" +
                "helloWorldText='" + helloWorldText + '\'' +
                '}';
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

    @SerializedName("hello_world_text")
    private String helloWorldText;

}
