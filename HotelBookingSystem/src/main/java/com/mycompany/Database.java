/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DeanK
 */
public class Database {
    
    private static final String dbusername = "pdc";
    private static final String dbpassword = "pdc";
//    private static final String url = "jdbc:derby://localhost:1527/HotelDB;create=true";
    private static final String url = "jdbc:derby:HotelDB;create=true";
    Connection conn = null;
    
    public Database() {
        
    }
    
    
    //Bookings table: bookingID; GuestName; guestID; isCurrent; roomNumber
    //Guests table: guestID;guestName
    //Rooms table: roomNumber; roomType; roomStatus (0 vacant, 1 occupied, 2 needcleaning
    //Guest request: bookingID; requestType; description; - single booking ID can have multiple requests
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(url, dbusername, dbpassword);
                Statement statement = conn.createStatement();
                String listName1 = "Bookings";
                String listName2 = "Guests";
                String listName3 = "Rooms";
                String listName4 = "GuestRequest";
                String listName5 = "Staff";
                
                if(!checkTableExisting(listName1)) {
                    statement.executeUpdate("CREATE TABLE " + listName1 + " (bookingID int not null, "
                    + "guestName varchar(20), guestID int not null,"
                    + "isCurrent boolean, roomNumber int, PRIMARY KEY(bookingID))");
                }              
                
                //Check if Guest table exists yet, if not create it
                if(!checkTableExisting(listName2)) {
                    statement.executeUpdate("CREATE TABLE " + listName2 + " (guestID int not null, "
                    + "guestName varchar(20))");
                }
                //Check if Rooms table exists yet
                if(!checkTableExisting(listName3)) {
                    statement.executeUpdate("CREATE TABLE " + listName3 + " (roomNumber int not null, "
                    + "roomType varchar(20), roomStatus int)"); 
                }
                //Check if guest request table exists yet
                if(!checkTableExisting(listName4)) { 
                    statement.executeUpdate("CREATE TABLE " + listName4 + " (bookingID int not null, "
                    + "requestType int, description varchar(100))");
                }
                
                if(!checkTableExisting(listName5)) {
                    statement.executeUpdate("CREATE TABLE " + listName5 + " (staffId int not null, "
                    + "staffName varchar(20), staffpw varchar(20))");
                }
                        
                statement.close();
            } catch (SQLException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
        
    }
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        
        try {
            System.out.println("Checking for existing tables.... ");
//            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            
            while(rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + " table exists!");
                    flag = true;
                }
            }
//            if (rsDBMeta != null) {
                rsDBMeta.close();
//            }
            
        } catch (SQLException ex) {
            System.out.println("Database: checkTableExisting() SQLException: " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println("Database: checkTableExisting() NullPointerException: " + ex.getMessage());
        }
        return flag;
    } 
    
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch(SQLException ex) {
                System.out.println("Database: loseConnections() SQLException: " + ex.getMessage());
            }
        }
    }
    
    public boolean checkStaffLogin(String username, String password){
//        Data data = new Data();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT staffname, staffpw FROM staff"
            + " WHERE staffname = '" + username + "'");
            
            if (rs.next()) { //if result is present for above query
                String pw = rs.getString("staffpw");
                System.out.println("User found\nChecking valid login.....");
                if (password.compareTo(pw) == 0) {
                    System.out.println("Successfully logged In! Loading staff menu....");
                    return true;
                }
                else {
                    System.out.println("Invalid login - check your password");
                }
            }
            else {
                System.out.println("Invalid Staff Login - username does not exist");
            }
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in Database checkLogin(): " + e.getMessage());
        }
        return false;
    }
    
    //test guest: qwe, 123
    public boolean checkGuestLogin(String username, String password) {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT guestID, guestName, guestPW FROM GUESTS"
            + " WHERE guestName = '" + username + "'");
            if (rs.next()) {
                String pw = rs.getString("GUESTPW");
                System.out.println("User found\n Checking valid login......");
                if (password.compareTo(pw) == 0) {
                    System.out.println("Successfully logged In! Loading guest Menu.....");
                    return true;
                }
                else {
                    System.out.println("Invalid login - check your password");
                }
            } else {
                System.out.println("Invalid Guest Login - Username does not exist");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in Database checkGuestLogin(): " + e.getMessage());
        }
        return false;
    }
    
}
