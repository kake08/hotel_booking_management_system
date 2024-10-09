/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

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
                    + "guestName varchar(20), guestpw varchar(20), guestPhone varchar(20)");
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
                //if(!checkTableExisting(listName6)) {} //for booking count, guest count, staff count, table for counts(ID)
                        
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
    
    public Data checkStaffLogin(String username, String password, Data data){        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT staffname, staffpw FROM staff"
            + " WHERE staffname = '" + username + "'");
            
            if (rs.next()) { //if result is present for above query
                String pw = rs.getString("staffpw");
                System.out.println("User found\nChecking valid login.....");
                if (password.compareTo(pw) == 0) {
                    System.out.println("Successfully logged In! Loading staff menu....");
                    data.loginFlag = true;
                    data.currentloggeduser = rs.getString("staffname");
                    return data;
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
        data.loginFlag = false;
        return data;
    }
    
    //test guest: qwe, 123
    //Returns to model checkGuestLogin
    public Data checkGuestLogin(String username, String password, Data data) {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT guestName, GUESTPHONE FROM GUESTS"
            + " WHERE guestName = '" + username + "'");
            if (rs.next()) {
                String pw = rs.getString("GUESTPHONE");
                System.out.println("User found\n Checking valid login......");
                if (password.compareTo(pw) == 0) {
                    System.out.println("Successfully logged In! Loading guest Menu.....");
                    data.currentloggeduser = rs.getString("GUESTNAME");
                    data.loginFlag = true;
                    return data;
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
        data.loginFlag = false;
        return data;
    }
    
    private int getRecordCount(String tableName) {
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM " + tableName); //reference: https://stackoverflow.com/questions/7886462/how-to-get-row-count-using-resultset-in-java
            rs.next();
            int rowCount = rs.getInt("recordCount");
            
            statement.close();
            rs.close();
            return rowCount;
        }catch(SQLException e) {
            System.out.println("SQLException in getRecordCount() in Database.java: " + e.getMessage());
        }

        return -1; //error
    }
    
    
    public boolean createBooking(String guestName, String guestPhone, String roomType) {
        try {
            int bookingRowCount = getRecordCount("BOOKINGS");
            int guestRowCount = getRecordCount("GUESTS");

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BOOKINGS (Bookingid, guestname, guestId, iscurrent, roomnumber)"
                    + "VALUES (?,?,?,?,?)");
            pstmt.setInt (1, bookingRowCount); //99 as test bookingid
            pstmt.setString (2, guestName); //guestname
            pstmt.setInt(3, guestRowCount); //test guestid
            pstmt.setBoolean(4, true); //iscurrent
            pstmt.setInt(5, 99); //test roomnumber
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("INSERT INTO GUESTS (GUESTID, GUESTNAME, GUESTPHONE)"
                    + "VALUES (?,?,?)");
            pstmt.setInt(1, guestRowCount);
            pstmt.setString(2, guestName);
            pstmt.setString(3, guestPhone);
            pstmt.executeUpdate();
           
            pstmt.close();
            System.out.println("Added New Booking");
            return true;
            //Statement that checks if guest with matching name and phone number exists - so no duplicates
        } catch(SQLException e) {
            System.out.println("SQLException in Database(createbooking): " +e.getMessage());
        }
        return false;
    }
    
    public void updateGuestsList(Data data) {
            System.out.println("TODO - updateGuestsList - database.java");
//        try {
//            Statement statement = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM guests");
//        }catch (SQLException e) {
//            System.out.println("SQLException in updateBookingsList() Database.java: " + e.getMessage());
//        }
    }
    
    //method for matching guestid to a guest in guestlist
    public Guest matchGuest(int guestID, Data data){
        return null; //no matches
    }
    
    public void fetchBookings(MyTableModel tableModel) {
        Vector<String> columnNames = new Vector<>();        
        Vector<Vector<Object>> rowData = new Vector<>();  
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM bookings");
            
            //Column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
 //CHANGE THIS TO STRING LIST??
            for(int i = 1; i <= numColumns; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
//            tableModel.setColumnIdentifiers(columnNames);
            
            //Row Data
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                for (int i = 1; i <= numColumns; i++) {
                    row.add(rs.getObject(i));
                }
                rowData.add(row);
            }
            rs.close();
            statement.close();
            
        } catch(SQLException e) {
            System.out.println("SQLException in fetchBookings - Database.java: " + e.getMessage());
        }
        
        tableModel.updateTableModelData(rowData, columnNames);
//        return tableModel;
    }

    
    
    //NOT NEEDED???? - fetchBookings() instead?
//    public Data updateBookingsList(Data data) {
////        data.AllBookings
//        try {
//            Statement statement = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM bookings"); //may need to limit this to 100?
//            
//            while(rs.next()) {
//                int bookingID = rs.getInt("bookingid");
//                String guestName = rs.getString("guestname");
//                int guestID = rs.getInt("guestid");
//                boolean isCurrent = rs.getBoolean("iscurrent");
//                int roomNumber = rs.getInt("roomnumber");
//                
//                //Good idea to load guest file first to find the guest with guest id
//                //. - this is temporary Guest
//                Guest currentGuest = new Guest(guestName, "test"); 
//                //Good Idea to load room first aswell to find Room object with this roomNumber - only assign if iscurrent=true booking;
//                //. - this is temporary Room
//                Room tempRoom = new Room(roomNumber, "testRoomType");
//                Booking travBooking = new Booking(bookingID, guestName, currentGuest);
//                travBooking.setIsCurrent(isCurrent);
//                travBooking.setRoom(tempRoom);
//                data.AllBookings.add(travBooking); //add the loaded booking to bookings list
//            }
//            statement.close();
//            rs.close();
//        }catch(SQLException e) {
//            System.out.println("SQLException in Database, updatedBookingsLIst(): " + e.getMessage());
//        }catch(NullPointerException e) {
//            System.out.println("NullPointerException in Database updateBookingsList():" + e.getMessage());
//        }
//        return data;
//    }
    
}
