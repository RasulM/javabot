/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

/**
 *
 * @author Raz
 */
public class Ultrasound extends Sensor {
    
   SerialIO ser;
   String data; 
    
    
    
    public Ultrasound(String name){
       super(name,"ultrasonic");
        ser = new SerialIO("USB");
    }
    
    @Override
     public String getStringData() {
         System.out.println(ser.getLastEvent());
         data = ser.getLastEvent().replace("R", "");
         
       
       
       return data;  
     }
    
   
    
    
    
}
