/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.pi4j.wiringpi.*;
/**
 *
 * @author Raz
 */


public class Motor {
    //The motor position
    private int value;
    //The Pin number
    private final int PinNumber;
    
    public Motor( int PinNumber){
      this.PinNumber =  PinNumber;
        
         Gpio.wiringPiSetup();
        
        //Set the PinNumber pin to be a PWM pin, with values changing from 0 to 1000 
        //this will give enough resolution to the servo motor
        SoftPwm.softPwmCreate(PinNumber,0,1000);
        
        //set initial position to the minimal.
        value = 0;
        //tell the servo motor to go to the zero position.
        //this will allow us to keep track of the position of the servo motor.
        //the initial postion of the motor is the value 5. the valid range of positions of the motor is
        //from 5 to 25.
        SoftPwm.softPwmWrite(PinNumber, value + 5);
        //allow sufficient time to the servo mottor to move to the position.
        sleepMillisec(1500);
        //stop sending orders to the motor.
        stop();    
    }
      public void setPosition(int positionNumber)
    {
        if (positionNumber >= 0 && positionNumber <= 20)
        {
            //update the position;
            value = positionNumber;
            //send the value to the motor.
            SoftPwm.softPwmWrite(PinNumber, value + 5);
            //give time to the motor to reach the position
            sleepMillisec(1500);
            //stop sending orders to the motor.
            stop();
        }
        
    }
    
    /**
     * turn servo off
     */
    public void stop(){
        //zero tells the motor to turn itself off and wait for more instructions.
        SoftPwm.softPwmWrite(PinNumber, 0);
    }
    
    /**
     * Get the Servo current position
     * @return the integer with the servo motor's current position.
     */
    public int getPosition(){
        //returns the current position;
        return value;
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
    
    public void forward(int millisec){
      setPosition(20);
      sleepMillisec(millisec);
      stop();
    }
    
    public void backward(int millisec){
      setPosition(0);
      sleepMillisec(millisec);
      stop();
    }
    
    
}
