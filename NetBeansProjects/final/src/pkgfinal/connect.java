package pkgfinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 * This class used to make the connection between the arduino and java code
 * using the {com.fazecast.jSerialComm} library which is used to implement
 * the serial communication , sending and receving data.
 */
public class connect {
    
  /****************************************************************/
  /******************** Variables Declarations ********************/
  /****************************************************************/  
  
    /***************/
    /* Motor State */
    /***************/
    
    //Motor_On
    private final int MOTOR_ON  = 252;
     
    //Motor Off
    private final int MOTOR_OFF = 253;
   
    //----------------------------------------------------------------
    
    /*******************/
    /* Motor Direction */
    /*******************/
    
    //Motor_Clk_Wise
    private final int MOTOR_CLK_WISE=255;
    
    //Motor_Anti_Clk_Wise
    private final int MOTOR_ANTICLK_WISE=254;
    
    //----------------------------------------------------------------	
    
    /***********************/
    /* Serial Comm Objects */
    /***********************/
	
    //Communication Port
    SerialPort comPort;
    
    //Output Stream
    OutputStream out;
    
    //Input Stream
    InputStream in ;
    SerialPort []comPorts;
    String[]s =new String[10];

    
    //----------------------------------------------------------------
    
    
     /**
     * public constructor used to start the serial communication 
	 *
     */
    public connect(){
         
        
        
        /*comPort = SerialPort.getCommPort("COM3");
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);*/
        
        comPorts= SerialPort.getCommPorts();
        int i =0;
        for(SerialPort port:comPorts){
            s[i]  = comPorts[i].getSystemPortName();
            System.out.println(s[i]);
         
        }
        
      
        

    }
    
    /**
     * Check the Connection 
     * 
     * @return Connection state
     */
  /*  public boolean checkConnection(){
       return comPort.openPort();
    }*/
   
    public void port_connect(String s)
    {
            comPort = SerialPort.getCommPort(s);
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
         
    
    }
    
    /**
	 *	call the {sendData} method to send the state of the motor to the arduino
	 *	
     * @param   state        Motor state
	 *						 (on,off).
     *
     */
    public void motorStateArduino(boolean state){
        if(state == true)
            sendData(MOTOR_ON);
        else
            sendData(MOTOR_OFF);
    }
    
    
    /**
	 *	call the {sendData} method to send the rotation direction of the motor to 
	 *	the arduino
	 *	
     * @param   direction    Motor direction
	 *						 (Clk_Wise,Anti_Clk_Wise).
     *
     */
    public void motorDirectionArduino(boolean direction){
        if(direction == true)
            sendData(MOTOR_CLK_WISE);
        else
            sendData(MOTOR_ANTICLK_WISE);
    }
    
    
    /**
	 *	send the data to the arduino 
	 *	
     * @param   data        data send to the arduino.
     *
     */
    public void sendData(int data){
        out = comPort.getOutputStream();
        try {
            out.write(data);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
           
            
        }
        
    }
    
    
    /**
	 *	read data from the arduino 
	 *	
     * @return	data read from arduino.
     *
     */
    public int readData(){
        int data = 0;
        comPort.getInputStream();
        try {
           data= in.read();
           in.close();
        } catch (IOException ex) {
            Logger.getLogger(connect.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     return data;   
    }
    
}