/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

/**
 *
 * @author Thomas Lecointre
 */
public class Main {

    public static void main(String[] args) {
       WeatherComponent component = new WeatherComponent();
       component.addRefreshListener(new RefreshListener() {
           @Override
           public void refresh(RefreshEvent e) {
               System.out.println("Getting weather data for Mulhouse...");
           }
           
       });
       component.start();
    }
    
}
