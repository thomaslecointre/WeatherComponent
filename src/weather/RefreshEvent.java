/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather;

import java.util.EventObject;

/**
 *
 * @author Thomas Lecointre
 */
public class RefreshEvent extends EventObject {

    public RefreshEvent(WeatherComponent component) {
        super(component);
    }
    
}
