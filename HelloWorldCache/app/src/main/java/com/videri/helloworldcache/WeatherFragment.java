/**
 * MediaPlayerFragment.java
 * Thread Application Sample
 * 1.0.0
 *
 * Copyright 2016 Videri Software, Inc.
 *
 * Unless required by applicable law or agreed to in writing by both parties,
 * this sample software is distributed on an "AS IS" AND "AS AVAILABLE" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package com.videri.helloworldcache;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.videri.core.models.TemperatureConverter;
import com.videri.core.utils.FileUtil;
import com.videri.helloworldcache.util.Util;
import com.videri.openweathermap.objects.OpenWeatherMapApi;
import com.videri.openweathermap.utils.OpenWeatherMapConstants;

import java.io.File;


/**
 * <p>
 *     The <code>WeatherFragment</code> display weather data
 *     from a caching file
 * </p>
 */
public class WeatherFragment extends Fragment {
    private final String TAG = "WeatherFragment";
    private String path = "";

    private ImageView bgImage;
    private ImageView temperatureImage;
    private TextView temperatureText;
    private TextView tempDescText;
    private OpenWeatherMapApi cachingTemperatureData = null;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_weather, container, false);

        bgImage  = (ImageView)view.findViewById(R.id.bg_image);
        temperatureImage  = (ImageView)view.findViewById(R.id.temperature_image);
        temperatureText  = (TextView)view.findViewById(R.id.temperature_text);
        tempDescText = (TextView)view.findViewById(R.id.temp_desc_text);

        path  = Environment.getExternalStorageDirectory() + Constants.ADAPP
                + getString(R.string.app_name)+"/";
        loadCacheData();
        return view;
    }


    private void loadCacheData(){
        Object object = FileUtil.loadSerializedObject(new File(path+ OpenWeatherMapConstants.CACHING_API_OPENWEATHERMAP));
        if(object != null){
            cachingTemperatureData = (OpenWeatherMapApi) object;
            if(Constants.DEBUG)
                Log.d(TAG,"Get data from caching: " + cachingTemperatureData.toString());
        }
        else {
            cachingTemperatureData = new OpenWeatherMapApi();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause......");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume......");
        updateTemperature(new Gson().toJson(cachingTemperatureData));
    }



    public void updateTemperature(){
        updateTemperature(new Gson().toJson(cachingTemperatureData));
    }

    public void updateTemperature(String tempratureData){
        try {

            Log.v(TAG, "response: " + tempratureData.toString());
            Gson tempGson = new Gson();
            OpenWeatherMapApi tempGsonObj = tempGson.fromJson(tempratureData, OpenWeatherMapApi.class);
            cachingTemperatureData = tempGsonObj;
            Log.v(TAG, tempGsonObj.toString());
            double fahrenheit = TemperatureConverter.kelvinToFahrenheit(tempGsonObj.getMain().getTempMax());
            fahrenheit = fahrenheit + 1.0;
            String city = "";
            String temperat = "";

            city = tempGsonObj.getName();
            temperat = String.format("%d", (long) fahrenheit) + "°F";

            int id = (tempGsonObj.getWeather().get(0).getId());

            Log.v(TAG, "id " + id);
            int imageId = 0;
            switch (id / 100) {
                case 8:
                    if (id == 8) {

                        updateContent(bgImage, R.drawable.sunny_background,
                                temperatureImage, R.drawable.sunny_icon,
                                temperatureText, temperat,
                                tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    } else {
                        updateContent(bgImage, R.drawable.cloudy_background,
                                temperatureImage, R.drawable.cloudy_icon,
                                temperatureText, temperat,
                                tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    }
                    break;
                case 1:
                    updateContent(bgImage, R.drawable.sunny_background,
                            temperatureImage, R.drawable.sunny_icon,
                            temperatureText, temperat,
                            tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    break;
                case 2:
                    updateContent(bgImage, R.drawable.rain_background,
                            temperatureImage, R.drawable.rain_icon,
                            temperatureText, temperat,
                            tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    break;
                case 3:
                    updateContent(bgImage, R.drawable.rain_background,
                            temperatureImage, R.drawable.rain_icon,
                            temperatureText, temperat,
                            tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    break;
                case 5:
                    updateContent(bgImage, R.drawable.rain_background,
                            temperatureImage, R.drawable.rain_icon,
                            temperatureText, temperat,
                            tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    break;
                case 7:
                    updateContent(bgImage, R.drawable.cloudy_background,
                            temperatureImage, R.drawable.cloudy_icon,
                            temperatureText, temperat,
                            tempDescText, tempGsonObj.getWeather().get(0).getDescription());
                    break;
                case 6:

                    break;
            }

        } catch (Exception e) {

            Log.v(TAG, e.toString());

        }
    }

    private void updateContent(ImageView bg, int bgIndex,
                               ImageView ti, int tiIndex,
                               TextView tv, String tempText,
                               TextView tempTv, String tTvText){

        Util.loadImage(getActivity(),bg, bgIndex);
        Util.loadImage(getActivity(),ti,tiIndex);
        tv.setText(tempText);
        tempTv.setText(tTvText.toUpperCase());
        Log.v(TAG, "picture was shown");

    }

}
