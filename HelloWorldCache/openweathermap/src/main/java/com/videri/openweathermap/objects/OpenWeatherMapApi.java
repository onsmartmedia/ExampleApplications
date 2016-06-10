package com.videri.openweathermap.objects;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yiming  on 3/9/16.
 */
public class OpenWeatherMapApi implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDt(){
        return dt;
    }
    public void setDt(long dt){
        this.dt=dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }



    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }


    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
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

    @Override
    public String toString() {
        return "OpenWeatherMapApi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", weather=" + weather +
                ", coord=" + coord +
                ", main=" + main +
                '}';
    }

    @SerializedName("id")
    private int id;


    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    @SerializedName("weather")
    private ArrayList<Weather> weather;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("main")
    private Main main;

    @SerializedName("dt")
    private long dt;


}
