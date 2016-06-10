/**
 * HelloWorldActivity.java
 * Videri Cache Application Sample
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
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.videri.core.utils.FileUtil;
import com.videri.helloworldcache.ui.caching.HelloWorldUICaching;
import com.videri.helloworldcache.ui.caching.UICaching;
import com.videri.helloworldcache.util.Util;

import java.io.File;

/**
 * <p>
 * The <code>HelloWorldActivity</code> displays caching data from
 * the <code>HelloWorldUICaching</code> object
 * </p>
 * <p>
 * The <code>HelloWorldUICaching</code> will store ui status when
 * the app goes onPuase.
 * </p>
 */
public class HelloWorldActivity extends Activity {
    /**
     * Key for log
     */
    public static final String TAG = "HelloWorldActivity";
    /**
     * A file name for HelloWorld ui caching
     */
    private final String CACHE_FILE_NAME = "hello_world_cache.json";
    /**
     * A partial string for <code>helloWorldTextView</code>
     */
    private final String HELLO_WORLD_TEXT = "Hello World ";
    /**
     * The 1000ms delay time for updating <code>helloWorldTextView</code>
     */
    private final int TEXT_UPDATE_DELAY = 1000;

    /**
     * The helloWorldTextView displays "Hello World (n)"
     */
    private TextView helloWorldTextView;
    /**
     * An imageView for displaying background image
     */
    private ImageView backgroundImage;
    /**
     * A handler for calling threads
     */
    private Handler mHandler;
    /**
     * A index for <code>helloWorldTextView</code>'s (n)
     */
    private int currentIndex = 0;
    /**
     * A file director for caching
     */
    private String path = "";
    /**
     * A HelloWorldUICaching object for storing ui status
     */
    private HelloWorldUICaching helloWorldUICaching = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        init();

    }

    /**
     * Initialize references to UI view specified in the layout XML.
     */
    private void initializeUIViews(){
        helloWorldTextView = (TextView)findViewById(R.id.helloword_text);
        backgroundImage = (ImageView)findViewById(R.id.bg_image);
    }
    /**
     * Initialize data.
     */
    private void init(){
        initializeUIViews();
        Util.loadImage(this,backgroundImage,R.drawable.bg_on_smart);
        path = Environment.getExternalStorageDirectory() + Constants.ADAPP
                + getString(R.string.app_name)+"/";
        mHandler = new Handler();
    }

    /**
     * A thread for updating <code>helloWorldTextView</code> and
     * launch <code>VideoPlayerActivity</code> every 20 seconds
     */
    private Runnable TextUpdateTask = new Runnable() {
        @Override
        public void run() {

            helloWorldTextView.setText(HELLO_WORLD_TEXT + currentIndex);
            currentIndex++;
            if(currentIndex == 100){
                currentIndex = 0;
            }
            if(currentIndex%20 == 0){
                startActivity(new Intent(getApplicationContext(),VideoPlayerActivity.class));
            }

            mHandler.postDelayed(this,TEXT_UPDATE_DELAY);
        }
    };

    /**
     * Saving this.<code>HelloWorldActivity</code> status to <code>HelloWorldUICaching</code> object
     */
    private void saveCaching(){
        if(helloWorldUICaching == null)
            helloWorldUICaching = new HelloWorldUICaching();
        helloWorldUICaching.setHelloWorldText(currentIndex+"");

        helloWorldUICaching.saveObject(path, CACHE_FILE_NAME);

        saveUICaching();

    }

    /**
     * Saving activity name to <code>UICaching</code>, so
     * when the app onResume will start this activity
     */
    private void saveUICaching(){
        UICaching caching = new UICaching();
        caching.setActivityName(TAG);
        caching.saveObject(path,Constants.UI_CACHING_FILE);
    }

    /**
     * Load caching data to <code>HelloWorldUICaching</code> and assign to view
     */
    private void loadCacheData(){
        Object object = FileUtil.loadSerializedObject(new File(path+ CACHE_FILE_NAME));
        if(object != null){
            helloWorldUICaching = (HelloWorldUICaching) object;
            if(Constants.DEBUG)
                Log.d(TAG,"Get data from caching: " + helloWorldUICaching);
        }
        else {
            helloWorldUICaching = new HelloWorldUICaching();
            helloWorldUICaching.setHelloWorldText("0");
        }

        currentIndex = Integer.parseInt( helloWorldUICaching.getHelloWorldText());

    }

    /**
     * Saving caching data
     * Removing threads
     */
    @Override
    public void onPause() {
        super.onPause();
        if(Constants.DEBUG)
             Log.v(TAG, "onPause......");

        if(TextUpdateTask != null)
            mHandler.removeCallbacks(TextUpdateTask);
        saveCaching();

    }

    /**
     * Loading caching data
     * Resuming threads
     */
    @Override
    public void onResume() {
        super.onResume();
        if(Constants.DEBUG)
            Log.v(TAG, "onResume......");
        mHandler.post(TextUpdateTask);
        loadCacheData();
    }
}
