/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.AWB;
import com.hopding.jrpicam.enums.DRC;
import com.hopding.jrpicam.enums.Encoding;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import com.rasulmahones.DB.Databasemanger;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Raz
 */
public class Camera extends RPiCamera{
    Process p;
    private boolean logState;
    private final String sensorName;
    private final String camtyp; 
    private Databasemanger dataman;
    
    public Camera(String cameraName, String cameratype,String SaveDir,Databasemanger datamanger) throws FailedToRunRaspistillException{
        super(SaveDir);
        
        dataman = datamanger;
        camtyp = cameratype;
        sensorName = cameraName;
	setAWB(AWB.AUTO);	    // Change Automatic White Balance setting to automatic
	setDRC(DRC.OFF); 			// Turn off Dynamic Range Compression
	setContrast(100); 			// Set maximum contrast
	setSharpness(100);		    // Set maximum sharpness
	setQuality(100); 		    // Set maximum quality
	setTimeout(1000);		    // Wait 1 second to take the image
	turnOnPreview();            // Turn on image preview
	setEncoding(Encoding.PNG);
        setDateTimeOn();
        
		
		
    }
    public Camera(String cameraName, String cameratype,String SaveDir) throws FailedToRunRaspistillException{
        super(SaveDir);
        camtyp = cameratype;
        sensorName = cameraName;	
    }
    
    
    
    public void startpicamWebstearm() throws IOException{
        if(camtyp.equalsIgnoreCase("picam")){
           p = Runtime.getRuntime().exec("python Webstreaming.py"); 
        }
       
        
        
    }
    
    public void logview(){
        try {
             LocalDateTime currentTime = LocalDateTime.now(); // Get the current date and time
             dataman.InsertImagesMysql(takeStill("Log:"+ currentTime));  
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        } 
   
         
     
       
        
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getCamtyp() {
        return camtyp;
    }

    public void setDataman(Databasemanger dataman) {
        this.dataman = dataman;
    }
    
     private void sleepMillisec(int millisec){
        try
        {
            Thread.sleep(millisec);
        }
        catch ( InterruptedException e)
        {
        }
    }
     public void startLogview(int timeinvales){
        logState = true;
        while(logState = true){
            logview();
        sleepMillisec(timeinvales);
            
        }
         
    }

    public boolean isLogState() {
        return logState;
    }
    
    public void stopLogview(){
        logState = false;
    }
    
    
    
    
}
