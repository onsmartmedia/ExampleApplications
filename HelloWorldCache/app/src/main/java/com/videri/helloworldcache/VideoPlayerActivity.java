/**
 * VideoPlayerActivity.java
 * Caching Application Sample
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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;
import com.videri.core.api.caching.ApiCacheData;
import com.videri.core.api.caching.ApiCacheServer;
import com.videri.core.bus.BusProvider;
import com.videri.core.bus.event.DataReceivedEvent;
import com.videri.core.utils.TimeUtil;
import com.videri.helloworldcache.ui.caching.UICaching;
import com.videri.helloworldcache.util.Util;
import com.videri.openweathermap.api.OpenWeatherMapServerApi;
import com.videri.openweathermap.bus.event.OpenWeatherMapDataReceivedEvent;
import com.videri.openweathermap.objects.OpenWeatherMapApi;
import com.videri.openweathermap.utils.OpenWeatherMapConstants;

import java.util.Date;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 *     The <code>VideoPlayerActivity</code> contains
 *     <code>VideoPlayerFragment</code> and <code>WeatherFragment</code>.
 * </p>
 * <p>
 *     The <code>VideoPlayerActivity</code> is a api controller.
 *     It deals with api calls in specified interval.
 * </p>
 */
public class VideoPlayerActivity extends Activity {
    /**
     * key for log
     */
    public static final String TAG = "VideoPlayerActivity";
    /**
     * An api call interval
     */
    private static final int OPENWEATHERMAP_API_CALL_INTERVAL = 1200000;
    /**
     * The openweather map api key (if you don't have one, please register one.)
     */
    private static final String FORECAST_API_KEY = "Api_key";
    /**
     * The zip code for where the weather that you want to display
     */
    private static final String ZIP_CODE = "10013";

    /**
     * The imageView for displaying background
     */
    private ImageView backgroundImage;
    /**
     * The <code>OpenWeatherMapApi</code> object for storing weather data
     */
    private OpenWeatherMapApi openWeatherMapApi;
    /**
     * The <code>OpenWeatherMapServerApi</code> object for dealing openweather map api's api call
     */
    private OpenWeatherMapServerApi openWeatherMapServerApi;
    /**
     * The FragmentManager
     */
    private FragmentManager mFragmentManager;
    /**
     * The FragmentTransaction
     */
    private FragmentTransaction mFragmentTransaction;
    /**
     * The VideoPlayerFragment for showing Video contents
     */
    private VideoPlayerFragment videoPlayerFragment;
    /**
     * The WeatherFragment for showing weather data
     */
    private WeatherFragment weatherFragment;
    /**
     * A directory for caching
     */
    private String path = "";
    /**
     * The thread handler
     */
    private Handler mHandler;
    /**
     * The <code>ApiCacheServer</code> is api call handler.
     * It stores api call status to an <code>ApiCacheData</code> object.
     */
    private ApiCacheServer apiCacheServer;
    /**
     * The <code>ApiCacheData</code> stores api call status.
     */
    private ApiCacheData openweatherMapApiCaching;
    /**
     * A Gson object Api data object serialization
     */
    private Gson mGson;

    /**
     * A datetime format
     */
    private static String TIME_FORMAT  = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        path = Environment.getExternalStorageDirectory() + Constants.ADAPP
                + getString(R.string.app_name)+"/";

        backgroundImage = (ImageView) findViewById(R.id.bg_image);
        Util.loadImage(this,backgroundImage,R.drawable.bg);

        videoPlayerFragment = new VideoPlayerFragment();
        weatherFragment = new WeatherFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content_layout, videoPlayerFragment);
        mFragmentTransaction.replace(R.id.main_bottom_layout,weatherFragment);
        mFragmentTransaction.commit();

        mHandler = new Handler();

        mGson = new GsonBuilder()
                .setDateFormat(TIME_FORMAT)
                .create();

        openWeatherMapServerApi = new OpenWeatherMapServerApi(FORECAST_API_KEY, GsonConverterFactory.create(mGson));
    }


    /**
     * The <code>onDataReceived</code> is the api call data receiver.
     * When the api call responded, the data will pass this data receiver.
     * @param event
     *             receiving data by bus
     */
    @Subscribe
    public void onDataReceived(DataReceivedEvent event){

        try{
            OpenWeatherMapDataReceivedEvent eventData = (OpenWeatherMapDataReceivedEvent)event;
            eventData.getOpenWeatherMapApi().saveObject(path,OpenWeatherMapConstants.CACHING_API_OPENWEATHERMAP);
            weatherFragment.updateTemperature();
            if(Constants.DEBUG)
                Log.v(TAG, "Get response: " + eventData.getOpenWeatherMapApi().toString());
            return;
        }
        catch (ClassCastException e){
            Log.e(TAG, "ClassCastException: " + e.toString());
        }
        catch (Exception e){
            Log.e(TAG, "VLE Error2:  "+ e.toString());
        }

    }

    /**
     * Initializing api call data from a caching file to <code>ApiCacheData</code>
     * Distribute api calls to apicall objects
     */
    private void initApiCaching(){
        apiCacheServer= new ApiCacheServer(TIME_FORMAT, path);

        if(apiCacheServer.getApiCachings().isEmpty()){
            apiCacheServer.updateCaching(OpenWeatherMapConstants.API_OPENWEATHERMAP, OPENWEATHERMAP_API_CALL_INTERVAL,null,false );
        }

        for(int i = 0; i < apiCacheServer.getApiCachings().getCount(); i++){
            ApiCacheData capi = apiCacheServer.getApiCachings().getApiCachings().get(i);

            if(capi.getApiName().equals(OpenWeatherMapConstants.API_OPENWEATHERMAP)){
                openweatherMapApiCaching = capi;
                apiInit(openweatherMapApiCaching, OpenWeatherMapApiCallTask);
            }
        }
        Log.v(TAG, "show caching api: " + apiCacheServer.getApiCachings().toString());
    }

    /**
     * Determine next api call
     * @param api
     *           The <code>ApiCacheData</code> tells the last api calls
     * @param runnable
     *           the api call thread
     */
    private void apiInit(ApiCacheData api, Runnable runnable) {
        if (!api.isCallCompleted()) {
            Log.v(TAG, api.getApiName() + " is calling now ");
            mHandler.post(runnable);
        } else {
            long difference = TimeUtil.getTimeDifference(api.getLastApiCall(), TIME_FORMAT);
            Log.v(TAG, api.getApiName() + " is going to call in " + difference + " ms.");
            if (difference > api.getCallInterval())
                mHandler.post(runnable);
            else
                mHandler.postDelayed(runnable, difference);
        }
    }

    /**
     * The OpenWeatherMapApiCallTask calls an api in specified interval.
     */
    private Runnable OpenWeatherMapApiCallTask = new Runnable() {
        @Override
        public void run() {
            Log.v(TAG,"Running WeatherApiTask..." );
            openweatherMapApiCaching.setCallCompleted(false);
            openweatherMapApiCaching.setLastApiCall(new Date());
            openWeatherMapServerApi.getZipCodeCurrentWeather(ZIP_CODE);
            mHandler.postDelayed(this, openweatherMapApiCaching.getCallInterval());
        }
    };

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
     * Saving caching data
     * Removing threads
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(Constants.DEBUG)
            Log.v(TAG, "onPause......");
        BusProvider.getInstance().unregister(this);
        saveUICaching();
    }

    /**
     * Loading caching data
     * Resuming threads
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.DEBUG)
             Log.v(TAG, "onResume.....");
        BusProvider.getInstance().register(this);
        initApiCaching();

    }

}
