package com.videri.openweathermap.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AIDA on 3/9/16.
 */
public class Main implements Serializable {
    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Main{" +
                "tempMin=" + tempMin +
                ", temp=" + temp +
                ", tempMax=" + tempMax +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }

    @SerializedName("temp_min")
    private double tempMin;

    @SerializedName("temp")
    private double temp;

    @SerializedName("temp_max")
    private double tempMax;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("pressure")
    private double pressure;

}
