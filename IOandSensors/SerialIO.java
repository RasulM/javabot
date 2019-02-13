/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Raz
 */
public class SerialIO {
    private final Serial serial;
     private LinkedList<String> EventList;
     private int cap;
     
    public SerialIO(String serialtype){
         EventList = new LinkedList<>(); 
        // create an instance of the serial communications class
         serial = SerialFactory.createInstance();
         if(serialtype.equalsIgnoreCase("UART")){
             serialUart();
             
         }else if(serialtype.equalsIgnoreCase("USB")){
           serialUsb();  
         }
         
             
         
         
      
        
    }
    public SerialIO(String Device,Baud rate,DataBits dataBits,Parity parity,StopBits stopBits,FlowControl flowControl){
      EventList = new LinkedList<>(); 
        // create an instance of the serial communications class
      serial = SerialFactory.createInstance();
      serialyouchoose(Device,rate,dataBits,parity,stopBits,flowControl);
      
     }
    
    
     private void serialUart(){
        

        // create and register the serial data listener
        serial.addListener((SerialDataEventListener) new SerialDataEventListener() {
          @Override
          public void dataReceived(SerialDataEvent event) {
              // NOTE! - It is extremely important to read the data received from the
              // serial port.  If it does not get read from the receive buffer, the
              // buffer will continue to grow and consume memory.
              
              // print out the data received to the console
              try {
                  System.out.println(event.getHexByteString());
                  System.out.println(event.getAsciiString());
                  EventList.add(event.getAsciiString());
                  
              } catch (IOException e) {
                  
                  System.out.println(e.getMessage());
              }
          }
      });
        
           try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            try {
                // set default serial settings (device, baud rate, flow control, etc)
                //
                // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
                // NOTE: this utility method will determine the default serial port for the
                //       detected platform and board/model.  For all Raspberry Pi models
                //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
                //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
                //       environment configuration.
                config.device(SerialPort.getDefaultPort())
                        .baud(Baud._9600)
                        .dataBits(DataBits._8)
                        .parity(Parity.NONE)
                        .stopBits(StopBits._1)
                        .flowControl(FlowControl.NONE);
            } catch (InterruptedException | UnsupportedBoardType ex) {
                Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
            }

            

            // display connection details
            System.out.println(" Connecting to: " + config.toString() +
                    " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);

            


        }
        catch(IOException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            
        }
        
        

    
}
          private void serialUsb(){
        

        // create and register the serial data listener
        serial.addListener((SerialDataEventListener) new SerialDataEventListener() {
          @Override
          public void dataReceived(SerialDataEvent event) {
              // NOTE! - It is extremely important to read the data received from the
              // serial port.  If it does not get read from the receive buffer, the
              // buffer will continue to grow and consume memory.
              
              // print out the data received to the console
              try {
                  System.out.println(event.getHexByteString());
                  System.out.println(event.getAsciiString());
                  EventList.add(event.getAsciiString());
                  
              } catch (IOException e) {
                  
                  System.out.println(e.getMessage());
              }
          }
      });
        
           try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            try {
                // set default serial settings (device, baud rate, flow control, etc)
                //
                // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
                // NOTE: this utility method will determine the default serial port for the
                //       detected platform and board/model.  For all Raspberry Pi models
                //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
                //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
                //       environment configuration.
                config.device("/dev/ttyUSB0")
                        .baud(Baud._57600)
                        .dataBits(DataBits._8)
                        .parity(Parity.NONE)
                        .stopBits(StopBits._1)
                        .flowControl(FlowControl.NONE);
            } catch (UnsupportedBoardType ex) {
                Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
            }

            

            // display connection details
            System.out.println(" Connecting to: " + config.toString() +
                    " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);

            


        }
        catch(IOException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            
        }
          
        
        

    
}
          /*
          .baud(Baud._57600)
                        .dataBits(DataBits._8)
                        .parity(Parity.NONE)
                        .stopBits(StopBits._1)
                        .flowControl(FlowControl.NONE);
          */
          private void serialyouchoose(String Device,Baud rate,DataBits dataBits,Parity parity,StopBits stopBits,FlowControl flowControl){
        

        // create and register the serial data listener
        serial.addListener((SerialDataEventListener) new SerialDataEventListener() {
          @Override
          public void dataReceived(SerialDataEvent event) {
              // NOTE! - It is extremely important to read the data received from the
              // serial port.  If it does not get read from the receive buffer, the
              // buffer will continue to grow and consume memory.
              
              // print out the data received to the console
              try {
                  System.out.println(event.getHexByteString());
                  System.out.println(event.getAsciiString());
                  EventList.add(event.getAsciiString());
                  
              } catch (IOException e) {
                  
                  System.out.println(e.getMessage());
              }
          }
      });
        
           try {
            // create serial config object
            SerialConfig config = new SerialConfig();

            try {
                // set default serial settings (device, baud rate, flow control, etc)
                //
                // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
                // NOTE: this utility method will determine the default serial port for the
                //       detected platform and board/model.  For all Raspberry Pi models
                //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
                //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
                //       environment configuration.
                config.device(Device)
                        .baud(rate)
                        .dataBits(dataBits)
                        .parity(parity)
                        .stopBits(stopBits)
                        .flowControl(flowControl);
            } catch (UnsupportedBoardType ex) {
                Logger.getLogger(SerialIO.class.getName()).log(Level.SEVERE, null, ex);
            }

            

            // display connection details
            System.out.println(" Connecting to: " + config.toString() +
                    " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);

            


        }
        catch(IOException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            
        }
        
        

    
}     
    
          
    public String getLastEvent(){
        return EventList.getLast();
    }
    public LinkedList<String> getLastEvents(int numberofevents){
        int i;
        LinkedList<String>  Events = new LinkedList<>();
        i = numberofevents + 1;
       Events.addAll(EventList.subList(EventList.size()- i, EventList.size()-1));
        
        //last50Events.get(numberofevents)
        return Events;
    }
    
    public int getEventListSize(){
        System.out.println(EventList.size());
        
      return EventList.size();
    }
    
    public void clearEventList(){
        EventList.clear();
    }
    
   
    
        
    }
   
    
   

