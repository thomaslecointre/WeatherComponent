/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Thomas Lecointre
 */
public class WeatherComponent extends Canvas implements Runnable {

    private final int second = 1000;
    private int refreshDelay = 120 * second; // 2 minutes
    private boolean alive = true;
    private final String apiKey = "3670017b342a20200b54ab598cc868d0";

    private String city = "Mulhouse";
    private String country = "fr";
    private String description;
    private double temperature;
    private double minTemperature;
    private double maxTemperature;
    private double pressure;
    private double humidity;
    private double visibility;
    private double windSpeed;
    private double windDirection;

    private ArrayList<RefreshListener> listeners = new ArrayList<>();
    private boolean hasRefreshed;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRefreshDelay() {
        return refreshDelay;
    }

    public void setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay * this.second;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature - 273.15;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature - 273.15;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature - 273.15;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "WeatherComponent{" + "city=" + city + ", description=" + description + ", temperature=" + temperature + ", minTemperature=" + minTemperature + ", maxTemperature=" + maxTemperature + ", pressure=" + pressure + ", humidity=" + humidity + ", visibility=" + visibility + ", windSpeed=" + windSpeed + ", windDirection=" + windDirection + '}';
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public WeatherComponent() {
        super.setSize(320, 240);
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (alive) {
            try {
                fireRefreshEvent(new RefreshEvent(this));
                Thread.sleep(this.refreshDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRefreshListener(RefreshListener r) {
        listeners.add(r);
    }

    public void removeRefreshListener(RefreshListener r) {
        listeners.remove(r);
    }

    @Override
    public void paint(Graphics g) {

        if (this.hasRefreshed) {
            g.setFont(new Font("Helvetica", Font.PLAIN, g.getFont().getSize() * 3));
            g.drawString(String.valueOf(this.temperature) + " °C", 50, 50);
            g.setFont(new Font("Helvetica", Font.PLAIN, g.getFont().getSize() / 3));
            g.drawString(this.city, 50, 75);
            g.drawString(this.description, 50, 100);

            g.drawString("min temperature : " + this.minTemperature + " °C", 50, 125);
            g.drawString("max temperature : " + this.maxTemperature + " °C", 200, 125);
            g.drawString("humidity : " + this.humidity + "%", 50, 150);
            g.drawString("pressure : " + this.pressure + " hPa", 50, 175);
            g.drawString("wind speed : " + this.windSpeed + " m/s", 50, 200);
            g.drawString("wind direction : " + this.windDirection + "°", 50, 225);
      
        }
    }

    public void fireRefreshEvent(RefreshEvent refreshEvent) {
        Iterator i = listeners.iterator();
        while (i.hasNext()) {
            ((RefreshListener) i.next()).refresh(refreshEvent);
        }
    }

    public void doRefresh() {
        
        System.out.println("Getting weather data for " + this.city + "...");
        
        JSONParser parser = new JSONParser();

        try {

            URL openWeatherMapURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + this.city + "," + this.country + "&appid=" + this.apiKey);
            BufferedReader in = new BufferedReader(new InputStreamReader(openWeatherMapURL.openStream()));

            // Parse input stream containing json data
            JSONObject file = (JSONObject) parser.parse(in);

            JSONArray weather = (JSONArray) file.get("weather");
            JSONObject weatherContents = (JSONObject) weather.get(0);
            this.setDescription((String) weatherContents.get("description"));

            JSONObject main = (JSONObject) file.get("main");
            this.setTemperature(Double.valueOf(String.valueOf(main.get("temp"))));
            this.setPressure(Double.valueOf(String.valueOf(main.get("pressure"))));
            this.setHumidity(Double.valueOf(String.valueOf(main.get("humidity"))));
            this.setMinTemperature(Double.valueOf(String.valueOf(main.get("temp_min"))));
            this.setMaxTemperature(Double.valueOf(String.valueOf(main.get("temp_max"))));

            this.setVisibility((long) file.get("visibility"));

            JSONObject wind = (JSONObject) file.get("wind");
            this.setWindSpeed(Double.valueOf(String.valueOf(wind.get("speed"))));
            this.setWindDirection(Double.valueOf(String.valueOf(wind.get("deg"))));

            // Close inputstream
            in.close();

            // Repaint component
            this.hasRefreshed = true;
            this.repaint();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(WeatherComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
