package com.videri.core.models;

/**
 * Created by AIDA on 3/9/16.
 */
public class TemperatureConverter {
    public TemperatureConverter()
    {

    }

    // Method to convert from degrees Celcius to degrees Fahrenheit
    public static float celciusToFahrenheit(float degCelcius)
    {
        float degFahrenheit;
        degFahrenheit = degCelcius * 9/5 + 32;
        return degFahrenheit;
    }

    // Method to convert from degrees Fahrenheit to degrees Celcius
    public static float fahrenheitToCelcius(float degFahrenheit)
    {
        float degCelcius;
        degCelcius =  (degFahrenheit - 32) * 5/9;
        return degCelcius;
    }

    // Method to convert from degrees Celcius to degrees Kelvin
    public static float celciusToKelvin(float degCelcius)
    {
        float degKelvin;
        degKelvin = degCelcius + 273.15f;
        return degKelvin;
    }

    // Method to convert from degrees Kelvin to degrees Celcius
    public static float kelvinToCelcius(float degKelvin)
    {
        float degCelcius;
        degCelcius = degKelvin - 273.15f;
        return degCelcius;
    }

    public static double kelvinToFahrenheit(double degKelvin){

        double degFahrenheit;
        degFahrenheit = (degKelvin - 273.15d)*1.800d + 32.00d;

        return degFahrenheit;

    }
}
