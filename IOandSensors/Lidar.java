/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.IOandSensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raz
 */




/*
0x00
W
ACQ_COMMAND
Device command
--
page 8
0x01
R
STATUS
System status
--
page 8
0x02
R/W
SIG_COUNT_VAL
Maximum acquisition count
0x80
page 8
0x04
R/W
ACQ_CONFIG_REG
Acquisition mode control
0x08
page 8
0x09
R
VELOCITY
Velocity measurement output
--
page 8
0x0c
R
PEAK_CORR
Peak value in correlation record
--
page 8
0x0d
R
NOISE_PEAK
Correlation record noise floor
--
page 8
0x0e
R
SIGNAL_STRENGTH
Received signal strength
--
page 9
0x0f
R
FULL_DELAY_HIGH
Distance measurement high byte
--
page 9
0x10
R
FULL_DELAY_LOW
Distance measurement low byte
--
page 9
0x11
R/W
OUTER_LOOP_COUNT
Burst measurement count control
0x01
page 9
0x12
R/W
REF_COUNT_VAL
Reference acquisition count
0x05
page 9
0x14
R
LAST_DELAY_HIGH
Previous distance measurement high byte
--
page 9
0x15
R
LAST_DELAY_LOW
Previous distance measurement low byte
--
page 9
0x16
R
UNIT_ID_HIGH
Serial number high byte
Unique
page 9
0x17
R
UNIT_ID_LOW
Serial number low byte
Unique
page 9
0x18
W
I2C_ID_HIGH
Write serial number high byte for I2C address unlock
--
page 9
0x19
W
I2C_ID_LOW
Write serial number low byte for I2C address unlock
--
page 9
0x1a
R/W
I2C_SEC_ADDR
Write new I2C address after unlock
--
page 9
0x1c
R/W
THRESHOLD_BYPASS
Peak detection threshold bypass
0x00
page 9
0x1e
R/W
I2C_CONFIG
Default address response control
0x00
page 9
0x40
R/W
COMMAND
State command
--
page 10
0x45
R/W
MEASURE_DELAY
Delay between automatic measurements
0x14
page 10
0x4c
R
PEAK_BCK
Second largest peak value in correlation record
--
page 10
0x52
R
CORR_DATA
Correlation record data low byte
--
page 10
0x53
R
CORR_DATA_SIGN
Correlation record data high byte
--
page 10
0x5d
R/W
ACQ_SETTINGS
Correlation record memory bank select
--
page 10
0x65
R/W
POWER_CONTROL
Power state control
0x80
page 10
*/



/*
The simplest method of obtaining measurement results from the I2C interface is as follows:
1 Write 0x04 to register 0x00.
2 Read register 0x01. Repeat until bit 0 (LSB) goes low.
3 Read two bytes from 0x8f (High byte 0x0f then low byte 0x10) to obtain the 16-bit measured distance in centimeters.
*/

/*
The I2C address can be changed from its default value. Available addresses are 7-bit values with a ‘0’ in the least significant bit (even hexadecimal numbers).
To change the I2C address, the unique serial number of the unit must be read then written back to the device before setting the new address. The process is as follows:
1 Read the two byte serial number from 0x96 (High byte 0x16 and low byte 0x17).
2 Write the serial number high byte to 0x18.
3 Write the serial number low byte to 0x19.
4 Write the desired new I2C address to 0x1a.
5 Write 0x08 to 0x1e to disable the default address.
This can be used to run multiple devices on a single bus, by enabling one, changing its address, then enabling the next device and repeating the process.
The I2C address will be restored to default after a power cycle.
Power Control
*/
//works with Lidar Lite v3 https://www.sparkfun.com/products/14032
public class Lidar extends Sensor {
    
    public static final int LIDAR_ADDR = 0x62;
    public static final byte ACQ_COMMAND = (byte)0x00;
    public static final byte STATUS = (byte)0x01;
    public static final byte SIG_COUNT_VAL = (byte)0x02;
    public static final byte ACQ_CONFIG_REG = (byte)0x04;
    public static final byte VELOCITY= (byte)0x09;
    public static final byte PEAK_CORR= (byte)0x0c;
    public static final byte NOISE_PEAK= (byte)0x0d;
    public static final byte SIGNAL_STRENGTH= (byte)0x0e;
    public static final byte FULL_DELAY_HIGH= (byte)0x0f;
    public static final byte FULL_DELAY_LOW= (byte)0x10;
    public static final byte OUTER_LOOP_COUNT= (byte)0x11;
    public static final byte REF_COUNT_VAL= (byte)0x12;
    public static final byte LAST_DELAY_HIGH= (byte)0x14;
    public static final byte LAST_DELAY_LOW= (byte)0x15;
    public static final byte UNIT_ID_HIGH= (byte)0x16;
    public static final byte UNIT_ID_LOW= (byte)0x17;
    public static final byte I2C_ID_HIGH= (byte)0x18;
    public static final byte I2C_ID_LOW= (byte)0x19;
    public static final byte I2C_SEC_ADDR= (byte)0x1a;
    public static final byte THRESHOLD_BYPASS= (byte)0x1c;
    public static final byte I2C_CONFIG= (byte)0x1e;
    public static final byte COMMAND= (byte)0x40;
    public static final byte MEASURE_DELAY= (byte)0x45;
    public static final byte PEAK_BCK= (byte)0x4c;
    public static final byte CORR_DATA= (byte)0x52;
    public static final byte CORR_DATA_SIGN= (byte)0x53;
    public static final byte ACQ_SETTINGS= (byte)0x5d;
    public static final byte POWER_CONTROL= (byte)0x65;
    public static final byte RBC= (byte)0x04;//receiver bias correction
    public static final byte WRBC= (byte)0x03;//without receiver bias correction
    public static final byte COM = (byte)0x0;
    private int status ;
    private final I2CBus i2c;
    private final I2CDevice device;
    private float data;
    private String intData;
    
    public Lidar(String name) throws I2CFactory.UnsupportedBusNumberException, IOException{
        super(name,"Lidar");
      
            // get the I2C bus to communicate on
            i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            // create an I2C device for an individual device on the bus that you want to communicate with
            device = i2c.getDevice(LIDAR_ADDR);
         
    }
        
    @Override
    public float getFloatData(){
        Integer distance; 
        int x =1;
        try {
            device.write(ACQ_COMMAND, RBC);
            status =  device.read(STATUS);
            while(x == 1){
                status =  device.read(STATUS);
                if(status % 2 == 0b0){
                distance = ((device.read( 0x0f) & 0xff) << 8) | (device.read( 0x10) & 0xff);
                data = distance.floatValue() * 10;
                x = 2;
                }
                
            }
            
            
       
            
       
            
            
            return data;
        } catch (IOException ex) {
            Logger.getLogger(Lidar.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    @Override
    public String getStringData(){
      intData = getFloatData() + "cm"; 
      return intData;
    }
}
