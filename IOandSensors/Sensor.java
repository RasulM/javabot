/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.rasulmahones.DB.Databasemanger;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author Raz
 */
public class Sensor {
    final private String sensorName;
    final private String sensorType;
    private final ZoneId id;
    private String time;
    private Databasemanger datamanger;
    private boolean logState;

    
    
    public Sensor( String sensorName,String sensorType){
       this.sensorName = sensorName;
       this.sensorType = sensorType;
       this.id = ZoneId.of("-05:00");
  
        
    }
    
    public String currenttime(){
        time =ZonedDateTime.now(id).toString();
        return time;
    }
     
    
    
    public String getSensorName() {
        return sensorName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public float getFloatData() {
        float data;
        data = 0;
        return data;
    }
    
    public String getStringData() {
        String data;
        data = "";
        return data;
    }

    public void setDatamanger(Databasemanger datamanger) {
        this.datamanger = datamanger;
    }
    
    public void startLogFloatData(int timeinvales){
        logState = true;
        while(logState = true){
            datamanger.logFloatData(this);
        sleepMillisec(timeinvales);
            
        }
         
    }
    
    public void startLogStringData(int timeinvales){
        logState = true;
        while(logState = true){
            datamanger.logStringData(this);
        sleepMillisec(timeinvales);
            
        }
         
    }

    public boolean isLogState() {
        return logState;
    }
    
    public void stopLog(){
        logState = false;
    }
    
     /**
     * Wait for a number of milliseconds
     * @param milisec the number of milliseconds to wait.
     */
    private void sleepMillisec(int millisec){
        try
        {
            Thread.sleep(millisec);
        }
        catch ( InterruptedException e)
        {
        }
    }

   
    
}
