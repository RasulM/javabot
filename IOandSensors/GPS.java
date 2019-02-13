/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.rasulmahones.IOandSensors.javisantananmeapasrer.NMEA;
import com.rasulmahones.IOandSensors.javisantananmeapasrer.NMEA.GPSPosition;

/**
 *
 * @author Raz
 */
public class GPS extends Sensor{
    NMEA nmea;
    GPSPosition s;
    SerialIO ser;
 
    /**
     *
     * @param name
     */
    public GPS(String name){
        super(name,"gps");
        nmea = new NMEA();
        ser = new SerialIO("UART");
    }
    
    
     @Override
     public String getStringData() {
       s = nmea.parse(ser.getLastEvent());
       System.out.println(s.toString());
       return s.toString();  
     }
    
     public float getlat() {
       s = nmea.parse(ser.getLastEvent());
       System.out.println(s.toString());
       return s.lat;  
     }
     public float getlon() {
       s = nmea.parse(ser.getLastEvent());
       System.out.println(s.toString());
       return s.lon;  
     }
     
     
}
