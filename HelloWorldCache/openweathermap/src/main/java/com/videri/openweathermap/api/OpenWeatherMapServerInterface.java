package com.videri.openweathermap.api;

import com.videri.openweathermap.objects.OpenWeatherMapApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yiminglin on 5/31/16.
 */
public interface OpenWeatherMapServerInterface {


    @GET("weather")
    Call<OpenWeatherMapApi> getZipCodeCurrentWeather(@Query("zip") String zip,
                                  @Query("APPID") String appId);
}
