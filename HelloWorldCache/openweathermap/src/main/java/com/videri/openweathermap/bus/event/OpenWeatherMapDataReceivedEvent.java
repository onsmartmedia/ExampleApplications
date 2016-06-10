package com.videri.openweathermap.bus.event;

import com.videri.core.bus.event.DataReceivedEvent;
import com.videri.openweathermap.objects.OpenWeatherMapApi;

/**
 * Created by yiminglin on 5/31/16.
 */
public class OpenWeatherMapDataReceivedEvent extends DataReceivedEvent {

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public OpenWeatherMapApi getOpenWeatherMapApi() {
        return openWeatherMapApi;
    }

    public void setOpenWeatherMapApi(OpenWeatherMapApi openWeatherMapApi) {
        this.openWeatherMapApi = openWeatherMapApi;
    }

    @Override
    public String toString() {
        return "OpenWeatherMapDataReceivedEvent{" +
                "error='" + error + '\'' +
                ", openWeatherMapApi=" + openWeatherMapApi +
                '}';
    }

    private OpenWeatherMapApi openWeatherMapApi;

    private String error;

    public OpenWeatherMapDataReceivedEvent(OpenWeatherMapApi openWeatherMapApi){
        this.openWeatherMapApi = openWeatherMapApi;

    }

    public OpenWeatherMapDataReceivedEvent(String error){
        this.error = error;
    }
}
