/**
 * MainActivity.java
 * HelloWorld Caching Application Sample
 * 1.0.0
 *
 * Copyright 2016 Videri Inc.
 *
 * Unless required by applicable law or agreed to in writing by both parties,
 * this sample software is distributed on an "AS IS" AND "AS AVAILABLE" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.videri.helloworldcache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.videri.core.utils.FileUtil;
import com.videri.helloworldcache.ui.caching.UICaching;

import java.io.File;

/**
 * <p>
 * The <code>MainActivity</code> is the main activity for the application.
 * This is a handler activity.
 * It load a ui caching file and store in <code>UICaching</code> object and
 * load the activity's form it to determine which activity to run.
 * </p>
 */
public class MainActivity extends Activity {
    /**
     * key for log
     */
    private static final String TAG = "MainActivity";
    /**
     * path for caching
     */
    private String path ="";
    /**
     * The <code>UICaching</code> stores ui caching
     */
    private UICaching uiCaching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        init();

    }

    /**
     * Initialize caching data.
     */
    private void init(){
        if(Constants.DEBUG)
            Log.v(TAG,"init......");
        path = Environment.getExternalStorageDirectory() + Constants.ADAPP
                + getString(R.string.app_name)+"/";
        FileUtil.createDirectory(path);

        uiCaching = new UICaching();

    }


    /**
     * Start the Activity by a given activity name
     * @param activityName
     */
    private void startActivity(String activityName){
        Log.v(TAG, "package Name: " + getPackageName());

        Intent intent ;
        if(activityName.equals(HelloWorldActivity.TAG)) {
            Log.v(TAG, "Activity name: " + HelloWorldActivity.TAG );
            intent = new Intent(this, HelloWorldActivity.class);
        }
        else {
            Log.v(TAG, "Activity name: " + VideoPlayerActivity.TAG );
            intent = new Intent(this, VideoPlayerActivity.class);
        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    /**
     * Load ui cache data and store in the <code>UICaching</code> obejct
     */
    private void loadCacheData(){
        Object object = FileUtil.loadSerializedObject(new File(path+Constants.UI_CACHING_FILE));
        if(object != null){
            uiCaching = (UICaching) object;
            if(Constants.DEBUG)
                Log.d(TAG,"Get data from caching: " + uiCaching.toString());
            startActivity(uiCaching.getActivityName());
        }
        else {
            startActivity(HelloWorldActivity.TAG);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(Constants.DEBUG)
            Log.v(TAG, "onPause......");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.DEBUG)
            Log.v(TAG, "onResume.....");

        loadCacheData();
    }


}