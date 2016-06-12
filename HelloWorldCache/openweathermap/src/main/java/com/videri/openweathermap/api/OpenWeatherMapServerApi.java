package com.videri.openweathermap.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.videri.core.bus.BusProvider;
import com.videri.openweathermap.bus.event.OpenWeatherMapDataReceivedEvent;
import com.videri.openweathermap.objects.OpenWeatherMapApi;
import com.videri.openweathermap.utils.OpenWeatherMapConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yiminglin on 5/31/16.
 */
public class OpenWeatherMapServerApi {

    public static final String TAG = "OpenWeatherMapServerApi";

    private OpenWeatherMapServerInterface serverInterface;

    private String apiKey = "";


    public OpenWeatherMapServerApi(String key){
        init(key,null);
    }


    public OpenWeatherMapServerApi(String key, Converter.Factory converter){
        init(key, converter);
    }

    public void init(String key,  Converter.Factory converter){
        apiKey = key;

        Retrofit retrofit;
        if(converter != null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(OpenWeatherMapConstants.OPENWEATHERMAP_BASE_URL)
                    .addConverterFactory(converter)
                    .build();
        }
        else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(OpenWeatherMapConstants.OPENWEATHERMAP_BASE_URL)
                    .build();
        }
        serverInterface = retrofit.create(OpenWeatherMapServerInterface.class);
    }


    public void getZipCodeCurrentWeather(String zipCode){
        getZipCodeCurrentWeather(zipCode,apiKey);
    }

    public void getZipCodeCurrentWeather(String zipCode,String key){

        Call<OpenWeatherMapApi> call = serverInterface.getZipCodeCurrentWeather(zipCode,key);

        call.enqueue(new Callback<OpenWeatherMapApi>() {
            @Override
            public void onResponse(Call<OpenWeatherMapApi> call, Response<OpenWeatherMapApi> response) {
                if(OpenWeatherMapConstants.DEBUG) {
                    Log.d(TAG, "getData success--: " + response.body());
                }
                BusProvider.getInstance().post(new OpenWeatherMapDataReceivedEvent(response.body()));
            }

            @Override
            public void onFailure(Call<OpenWeatherMapApi> call, Throwable error) {
                if(OpenWeatherMapConstants.DEBUG)
                     Log.v(TAG, "getData failure--: " + error.toString() );
                BusProvider.getInstance().post(new OpenWeatherMapDataReceivedEvent(error.toString()));
            }
        });

    }
}
