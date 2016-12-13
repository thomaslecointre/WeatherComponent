/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Thomas Lecointre
 */
class WeatherComponent extends Canvas implements Runnable {

    private final long second = 1000;
    private final long refreshDelay = 60 * second; // 1 minute
    private boolean alive = true;
    private String apiKey = "3670017b342a20200b54ab598cc868d0";
    
    private final ArrayList<RefreshListener> listeners = new ArrayList<>();
    
    public String getApiKey() {
        return this.apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
    
    public void paint(Graphics g) {
        g.drawString("Timer", 0, 15);
    }

    private void fireRefreshEvent(RefreshEvent refreshEvent) {
        Iterator i = listeners.iterator();
        while(i.hasNext())
            ((RefreshListener)i.next()).refresh(refreshEvent);
    }
}
