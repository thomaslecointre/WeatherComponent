/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import java.awt.Canvas;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Thomas Lecointre
 */
class WeatherComponent extends Canvas implements Runnable, Serializable {

    private final long second = 1000;
    private final long refreshDelay = 60 * second; // 1 minute
    private boolean alive = true;
    private final String apiKey = "3670017b342a20200b54ab598cc868d0";

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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }
    
    private String city;
    private float temperature;
    private float minTemperature;
    private float maxTemperature;
    private int pressure;
    private int humidity;
    private int visibility;
    private float windSpeed;
    private int windDirection;
    
    
    private final ArrayList<RefreshListener> listeners = new ArrayList<>();
    
    public String getApiKey() {
        return this.apiKey;
    }
    
    public WeatherComponent() { 
        super.setSize(640, 480); 
    }
    
    
    public void start() {
        new Thread(this).start(); 
    }
    
    
    public void stop() {
        alive = false;
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
        g.drawString("Test", 0, 15);
    }

    private void fireRefreshEvent(RefreshEvent refreshEvent) {
        Iterator i = listeners.iterator();
        while(i.hasNext())
            ((RefreshListener)i.next()).refresh(refreshEvent);
    }
}
