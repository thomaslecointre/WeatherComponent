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
       WeatherComponent wc = new WeatherComponent();
       wc.addRefreshListener(new RefreshListener() {
           @Override
           public void refresh(RefreshEvent e) {
               wc.doRefresh();
               System.out.println(wc);
           }
           
       });
       wc.start();
    }
    
}
