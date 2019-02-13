/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rasulmahones.DB;
import com.rasulmahones.IOandSensors.Sensor;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Raz
 */
public class Databasemanger {
    private final String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "test";
    private String userName = "root";
    private String password = "root";
    private Connection con = null;
    
    public Databasemanger(){
     
    }
    public Databasemanger(String Url,String DBName,String UserName,String Password){
      setup(Url,DBName,UserName,Password);  
     
    }
    
    private  void setup(String Url,String DBName,String UserName,String Password){
        setUrl(Url);
        setDbName(DBName);
        setUserName(UserName);
        setPassword(Password);
       
    }
     private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        Connection connection = DriverManager.getConnection(url+dbName,userName,password); 
        return connection;
    }
    public boolean logFloatData(Sensor s){
        
        String sql = "INSERT INTO Sensor (SensorName, SensorType, Data,Time) "
                   + "VALUES (?, ?, ?,?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,s.getSensorName());
            ps.setString(2, s.getSensorType());
            ps.setFloat(3, s.getFloatData());
            ps.setString(4,s.currenttime());
            ps.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Databasemanger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean logStringData(Sensor s){
        
        String sql = "INSERT INTO Sensor (SensorName, SensorType, Data,Time) "
                   + "VALUES (?, ?, ?,?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,s.getSensorName());
            ps.setString(2, s.getSensorType());
            ps.setString(3, s.getStringData());
            ps.setString(4,s.currenttime());
            ps.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Databasemanger.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public  void  InsertImagesMysql(File imgfile){
       
     try{
       Class.forName(driverName);
       con = DriverManager.getConnection(url+dbName,userName,password);
       Statement st = con.createStatement();
       

      FileInputStream fin = new FileInputStream(imgfile);

         try (PreparedStatement pre = con.prepareStatement("insert into Image values(?,?,?)")) {
             pre.setString(1,"test");
             pre.setInt(2,3);
             pre.setBinaryStream(3,(InputStream)fin,(int)imgfile.length());
             pre.executeUpdate();
             System.out.println("Successfully inserted the file into the database!");
         }
       con.close(); 
    }catch (FileNotFoundException | ClassNotFoundException | SQLException e1){
        System.out.println(e1.getMessage());
    }    
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
   
    
}

