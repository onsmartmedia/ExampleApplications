package com.videri.openweathermap.utils;

/**
 * Created by yiminglin on 5/31/16.
 */
public class OpenWeatherMapConstants {

    //debug
    public final static boolean DEBUG = true;
    //api name
    public final static String API_OPENWEATHERMAP = "api_openweathermap";
    public final static String CACHING_API_OPENWEATHERMAP = "cache_api_openweathermap.json"; //api call cache

    //endpoints
    public static String OPENWEATHERMAP_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String OPENWEATHERMAP_WEATHER = "/weather?";
    public static String OPENWEATHERMAP_FORECAST = "/forecast";
    public static String OPENWEATHERMAP_DAILY="/daily";
    public static String OPENWEATHERMAP_QUERY = "q";
    public static String OPENWEATHERMAP_ZIP = "zip=";
    public static String OPENWEATHERMAP_APPID = "&APPID=";
    public static String OPENWEATHERMAP_COUNT="cnt";



}
