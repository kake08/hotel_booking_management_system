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

/**
 *
 * @author DeanK
 */
public class Database {
    
    private static final String dbusername = "pdc";
    private static final String dbpassword = "pdc";
    private static final String url = "jdbc:derby:HotelDB;create=true";
    Connection conn = null;
    
    public Database() {
        
    }
    
    
    //Bookings table: bookingID; GuestName; guestID; isCurrent; roomNumber
    //Guests table: guestID;guestName
    //Rooms table: roomNumber; roomType; roomStatus (0 vacant, 1 occupied, 2 needcleaning
    //Guest request: bookingID; requestType; description; - single booking ID can have multiple requests
    
    //Create connection with Database. On initial startup, create tables if it doesn't exist.
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
                
                //Check if BOOKINGS table exists yet, if not create it
                if(!checkTableExisting(listName1)) {
                    statement.executeUpdate("CREATE TABLE " + listName1 + " (bookingID int not null, "
                    + "guestName varchar(20), guestID int not null, "
                    + "bookingstatus int not null, roomNumber int, PRIMARY KEY(bookingID))");
                }              
                
                //Check if GUESTS table exists yet, if not create it
                if(!checkTableExisting(listName2)) {
                    statement.executeUpdate("CREATE TABLE " + listName2 + " (guestID int not null, "
                    + "guestName varchar(20), guestPhone varchar(20), guestStatus int)");
                }
                //Check if ROOMS table exists yet
                if(!checkTableExisting(listName3)) {
                    statement.executeUpdate("CREATE TABLE " + listName3 + " (roomNumber int not null, "
                    + "roomType varchar(20), roomStatus int not null)"); 
                    for (int i = 1; i < 11; i++) {
                        statement.executeUpdate("INSERT INTO ROOMS (ROOMNUMBER, ROOMTYPE, ROOMSTATUS) VALUES (" + i + ", 'STANDARD', 0)");
                    }
                    for (int i = 11; i < 21; i++) {
                        statement.executeUpdate("INSERT INTO ROOMS (ROOMNUMBER, ROOMTYPE, ROOMSTATUS) VALUES (" + i + ", 'DELUXE', 0)");
                    }
                    for (int i = 21; i < 31; i++) {
                        statement.executeUpdate("INSERT INTO ROOMS (ROOMNUMBER, ROOMTYPE, ROOMSTATUS) VALUES (" + i + ", 'SUITE', 0)");
                    }
                    
                }              
                //Check if guest request table exists yet
                if(!checkTableExisting(listName4)) { 
                    statement.executeUpdate("CREATE TABLE " + listName4 + " (bookingID int not null, guestID int not null,"
                    + "requestType int, description varchar(100))");
                }               
                if(!checkTableExisting(listName5)) {
                    statement.executeUpdate("CREATE TABLE " + listName5 + " (staffId int not null, "
                    + "staffName varchar(20), staffpw varchar(20))");
                    statement.executeUpdate("INSERT INTO STAFF (STAFFID, STAFFNAME, STAFFPW) VALUES (0, 'kake', 123)");
                }
                                       
                statement.close();
            } catch (SQLException e) {
                System.out.println("SQLException in establishConnection() in database.java: " + e.getMessage());
                closeConnections();  
            }
        }
        
    }
    
    //Checks a table doesn't exist already before creating it to avoid overwritting data
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        
        try {
            System.out.println("Checking for existing tables.... ");
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            
            while(rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + " table exists!");
                    flag = true;
                }
            }

            rsDBMeta.close();
            
        } catch (SQLException ex) {
            System.out.println("Database: checkTableExisting() SQLException: " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println("Database: checkTableExisting() NullPointerException: " + ex.getMessage());
        }
        return flag;
    } 
    
    
    //Close connection
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch(SQLException ex) {
                System.out.println("Database: loseConnections() SQLException: " + ex.getMessage());
            }
        }
    }
    
    //Validate staff login with database records. Returns to model checkStaffLogin
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
    
    //Validates guest login - This checks the user's inputted login details and checks if it's valid against database records. Returns to model checkGuestLogin.
    public Data checkGuestLogin(String username, String password, Data data) {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT guestName, GUESTPHONE, guestID FROM GUESTS"
            + " WHERE guestName = '" + username + "' AND guestphone = '" + password + "'");
            if (rs.next()) { //guest with matching details has been found
                System.out.println("Successfully logged In! Loading guest Menu.....");
                data.currentloggeduser = rs.getString("GUESTNAME");
                data.currentloggedGuestID = rs.getInt("GUESTID"); // Used to find guest's booking data
                data.loginFlag = true;
                return data;         
            } else {
                System.out.println("Invalid Guest Login - Username does not exist/Wrong password");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in Database checkGuestLogin(): " + e.getMessage());
        }
        data.loginFlag = false;
        return data;
    }
    
    //This tells us how many records are in a table, for use with IDs
    private int getRecordCount(String tableName) {
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM " + tableName); //Reference: https://stackoverflow.com/questions/7886462/how-to-get-row-count-using-resultset-in-java
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

    
//  Find a room that matches roomstatus and roomtype, and select one by random. rs.next() returns false when no room matches it and 
//  returns a negative 1 indicating no room found, otherwise the roomNumber is returned which will be assigned to booking
    public int fetchAvailableRoom(String roomType) {
        int roomNumber = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT roomNumber FROM rooms WHERE roomstatus = 0"
                    + " and roomtype = '" + roomType + "' ORDER BY RANDOM() fetch first 1 rows only");
            if (!rs.next()) {
                System.out.println("No rooms available for this Roomtype. Select another roomtype");
            } else {
                roomNumber = rs.getInt("roomNumber");
            }
            
            rs.close();
            statement.close();
        } catch(SQLException e) {
            System.out.println("SQLException in fetchAvailableRooms() in database.java: " + e.getMessage());
        }
        
        return roomNumber; 
    }
    
    //
    public Data createBooking(String guestName, String guestPhone, String roomType, int guestID, Data data) {
        Room newRoom;
        Booking newBooking;
        Guest newGuest;         
        int assignedRoomNumber = fetchAvailableRoom(roomType);
        if (assignedRoomNumber == -1) { //no room available of this room type
            data.setBookingSuccess(false);
            return data; 
        }       
        newRoom = new Room(assignedRoomNumber, roomType);       
        try {
            PreparedStatement pstmt;
            int bookingRowCount = getRecordCount("BOOKINGS"); //this generates the new bookingID based on bookings record COUNT
            int guestRowCount;
            if (guestID == -1) { //No Existing Guest, so generate a new Guest and insert into database records
                guestRowCount = getRecordCount("GUESTS"); //GuestID generated based on the guest record COUNT
                pstmt = conn.prepareStatement("INSERT INTO GUESTS (GUESTID, GUESTNAME, GUESTPHONE, GUESTSTATUS)"
                    + "VALUES (?,?,?,?)");
                pstmt.setInt(1, guestRowCount); //Guest ID
                pstmt.setString(2, guestName);
                pstmt.setString(3, guestPhone);
                pstmt.setInt(4, 20); //gueststatus for new guest w/ new booking is 20 = Active
                pstmt.executeUpdate();
                System.out.println("Added new Guest");
            } else { //Guest exists already - use this GuestID
                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE guests SET gueststatus = 20 WHERE guestID = " + guestID + " AND gueststatus = 1");
                guestRowCount = guestID;
                statement.close();
            }
            
            newGuest = new Guest(guestName, guestPhone);
            newGuest.setGuestID(guestRowCount);
            
            pstmt = conn.prepareStatement("INSERT INTO BOOKINGS (Bookingid, guestname, guestId, bookingstatus, roomnumber)"
                    + "VALUES (?,?,?,?,?)");
            pstmt.setInt (1, bookingRowCount); //bookingid
            pstmt.setString (2, guestName); //guestname
            pstmt.setInt(3, guestRowCount); //guestid
            pstmt.setInt(4, 1); //BookingStatus: set as pending booking
            pstmt.setInt(5, assignedRoomNumber); //test roomnumber
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("UPDATE rooms set roomstatus = 1 where roomnumber = ?");
            pstmt.setInt(1, assignedRoomNumber);               
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Added New Booking");
            
            data.setBookingSuccess(true);
            newBooking = new Booking(bookingRowCount, newGuest.guestName, newGuest);
            newBooking.setRoom(newRoom);
            
            data = setRecentBookingRoomGuest(data, newBooking, newGuest, newRoom);
            return data;
//            return true;
        } catch(SQLException e) {
            System.out.println("SQLException in Database(createbooking): " +e.getMessage());
        }
        data.setBookingSuccess(false);
        return data;
//        return false;
    }
    
    private Data setRecentBookingRoomGuest(Data data, Booking Booking, Guest guest, Room room) {
        data.setRecentBooking(Booking);
        data.setRecentGuest(guest);
        data.setRecentRoom(room);
        
        return data;
    }

    
    //Called in model before making booking for finding an identical guest (same matching details)
    public Guest matchingGuestExist(String guestName, String guestPhone){
        Guest guest = null;
//        SELECT * FROM guests WHERE guestname = 'qwe' AND guestphone = '12345';
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT guestID, guestName, guestPhone from GUESTS "
                    + "WHERE guestName = ? AND guestphone = ?");
            pstmt.setString(1, guestName);
            pstmt.setString(2, guestPhone);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                guest = new Guest(rs.getString("guestname"), rs.getString("guestphone"));
                guest.setGuestID(rs.getInt("guestid"));                     
            }
            
            rs.close();
            pstmt.close();
            return guest;
        } catch(SQLException e){
            System.out.println("SQLException in matchingGuestExist() in database.java: " + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("NullPointerException in matchingGuestExist() in database.java" + e.getMessage());
        }
        return guest; //no matches will return a null
    }
    
    public String[] fetchGuestsUserData (int guestID, String listMyBookingsFilter) {
        String[] myBookingsList = null;
        String myBookingsStr = "";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = null;
            switch(listMyBookingsFilter) {
                case "Pending Requests":
                    rs = statement.executeQuery("SELECT bookingid FROM guestrequest WHERE guestId = " + guestID);
                    break;
                case "Active Bookings":
                    rs = statement.executeQuery("SELECT bookingid FROM bookings WHERE guestId = " + guestID + " AND bookingstatus != -1 AND bookingstatus != 3");
                    break;
                default: 
                    rs = statement.executeQuery("SELECT bookingid FROM bookings WHERE guestId = " + guestID + " AND bookingstatus != -1");
                    break;
            }
            
            if ("Pending Requests".equals(listMyBookingsFilter)) {
                while(rs.next()) {
                    int bookingID = rs.getInt("BOOKINGID"); //position 1
//                    int requestType = rs.getInt("REQUESTTYPE");//position 3
//                    String description = rs.getString("DESCRIPTION"); //position 4       
                    myBookingsStr += "Request for Booking ID " + bookingID + ";";
                }
            } else {
                while(rs.next()) {
                    myBookingsStr += "Booking " + rs.getInt("BOOKINGID") + ";";
                }                
            }

            myBookingsList = myBookingsStr.split(";");
            
            if ("".equals(myBookingsList[0]))
                myBookingsList = null;
            
            statement.close();
            rs.close();
        }catch (SQLException e) {
            System.out.println("SQLException in fetchGuestsUserData in Database.java: " + e.getMessage());
        }catch(NumberFormatException e) {
            System.out.println("");
        }
        
        return myBookingsList;
    }
    
    public String[] fetchMyBookingDetails(Integer bookingID) {
        String[] bookingDetails = null;
        String guestName = null;
        String bookingStatus = null;
        String roomNumber = null;
        String requestType = null;
        String description = null;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingid = " + bookingID);
            if (rs.next()) {
                guestName = rs.getString("GUESTNAME");
                bookingStatus = Integer.toString(rs.getInt("BOOKINGSTATUS"));
                roomNumber = Integer.toString(rs.getInt("ROOMNUMBER"));
                
            }
            rs = statement.executeQuery("SELECT * FROM guestrequest WHERE bookingid = " + bookingID);
            if (rs.next()) {              
                requestType = Integer.toString(rs.getInt("REQUESTTYPE"));
                description = rs.getString("DESCRIPTION");                   
            }
            bookingDetails = new String[]{guestName, bookingStatus, roomNumber, requestType, description};  
            
            rs.close();
            statement.close();
        } catch(SQLException e) {
            System.out.println("SQLException in fetchMyBookingDetails in database.java: " + e.getMessage());
        } catch(NumberFormatException e) {
            System.out.println("NumberFormatException in fetchMyBookingDetails in database.java" + e.getMessage());
        }
        
        return bookingDetails;
    }
    
    public void  fetchRooms(MyTableModel tableModel, String filter) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> rowData = new Vector<>();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs;
            switch(filter) {
                case "Available Rooms":
                    rs = statement.executeQuery("SELECT * FROM rooms WHERE roomstatus = 0");
                    break;
                case "N/A Rooms":
                    rs = statement.executeQuery("SELECT * FROM rooms WHERE roomstatus != 0");
                    break;
                case "N/A Rooms - Occupied":
                    rs = statement.executeQuery("SELECT * FROM rooms WHERE roomstatus = 20 OR roomstatus = 21");
                    break;
                case "N/A Rooms - Booked":
                    rs = statement.executeQuery("SELECT * FROM rooms WHERE roomstatus = 1");
                    break;
                case "N/A Rooms - Need Cleaning":
                    rs = statement.executeQuery("SELECT * FROM rooms WHERE roomstatus = 3 OR roomstatus = 21");
                    break;
                default: //All Rooms
                    rs = statement.executeQuery("SELECT * FROM rooms");
                    break;
            }
            
            //Column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
                       
            //Row Data
            while(rs.next()) {
                Vector<Object> row = new Vector<Object>();
                for (int i = 1; i <= numColumns; i++) {
                    if (i == 3) {
                        int status = rs.getInt("ROOMSTATUS");
                        switch (status) {
                            case 0:
                                row.add("AVAILABLE");
                                break;
                            case 1:
                                row.add("BOOKED BY GUEST: " + findMatchGuestwithRoom(rs.getInt("ROOMNUMBER")));//insert here guest id
                                break;
                            case 20:
                                row.add("OCCUPIED by GUEST: " + findMatchGuestwithRoom(rs.getInt("ROOMNUMBER")));
                                break;
                            case 21:
                                row.add("OCCUPIED by GUEST: (Cleaning requested)" + findMatchGuestwithRoom(rs.getInt("ROOMNUMBER")));
                            case 3:
                                row.add("CHECKED OUT - Need cleaning"); 
                                break;
                            case 4: 
                                row.add("OUT OF ORDER - Maintenance");
                                break;
                            default:
                                row.add("Error: INVALID CODE - fetchRooms() in Database.java");
                                break;
                        }                          
                    } else {
                        row.add(rs.getObject(i));                        
                    }
                }
                rowData.add(row);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQLException in fetchRooms - Database.java: " + e.getMessage());
        }
        
        tableModel.updateTableModelData(rowData, columnNames);
    }
        
    //Find the guest with matching room number
    private String findMatchGuestwithRoom(int roomNumber) {
        String guestDetails = "null";
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT guestname, guestid FROM bookings WHERE roomnumber = " + roomNumber);
            rs.next();
            guestDetails = rs.getInt("GUESTID") + " " + rs.getString("GUESTNAME");
            
            statement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLException in findMatchGuestwithRoom: " + e.getMessage());
        }catch (NullPointerException e) {
            System.out.println("NullPointerException in findMatchGuestwithRoom(no match found)" + e.getMessage());
        }
        
        return guestDetails;
    }
    
    public void fetchGuests(MyTableModel tableModel, String filter) {
        Vector<String> columnNames = new Vector<>();        
        Vector<Vector<Object>> rowData = new Vector<>();  
        
        try{
            Statement statement = conn.createStatement();
            
            ResultSet rs;
            switch(filter) {
                case "Active Guests":
                    rs = statement.executeQuery("SELECT * FROM guests WHERE gueststatus = 20 OR gueststatus = 21");
                    break;
                case "Inactive Guests":
                    rs = statement.executeQuery("SELECT * FROM guests WHERE gueststatus = 1");
                    break;
                case "Guests with Request":
                    rs = statement.executeQuery("SELECT * FROM guestrequest");
                    break;
                default:
                    rs = statement.executeQuery("SELECT * FROM guests"); //ALL GUESTS SHOWN
                    break;
            }
            
            
            //Column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            for(int i = 1; i <= numColumns; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            
            //Row Data
            if ("Guests with Request".equals(filter)) {
                while (rs.next()) {
                   Vector<Object> row = new Vector<Object>();
                    for (int i = 1; i <= numColumns; i++) {
                        row.add(rs.getObject(i));
                    } 
                    rowData.add(row);
                }
            } else {
                while(rs.next()){
                    Vector<Object> row = new Vector<Object>();
                    for (int i = 1; i <= numColumns; i++) {
                        if (i == 4) {
                            int status = rs.getInt("GUESTSTATUS");
                            switch(status) {
                                case 1:
                                    row.add("INACTIVE");
                                    break;
                                case 20: 
                                    row.add("ACTIVE");
                                    break;
                                case 21:
                                    row.add("ACTIVE - pending request");
                                    break;
                                default:
                                    row.add("Status Error");
                                    break;
                            }
                        } else {
                        row.add(rs.getObject(i));
                        }                   
                    }
                rowData.add(row);
                }
            }
            
            rs.close();
            statement.close();
            
        } catch(SQLException e) {
            System.out.println("SQLException in fetchGuests - Database.java: " + e.getMessage());
        }
        
        tableModel.updateTableModelData(rowData, columnNames);   
    }
    
    
    public void fetchBookings(MyTableModel tableModel, String filter) {
//        "All Bookings", "2=Active Bookings", "1=Pending Bookings", "3=Historical Bookings"
        
        
        Vector<String> columnNames = new Vector<>();        
        Vector<Vector<Object>> rowData = new Vector<>();  
        
        try{
            Statement statement = conn.createStatement();
            ResultSet rs;
            switch (filter) {
                case "Active Bookings":
                    rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingstatus = 2");
                    System.out.println("Filter Active Bookings");
                    break;
                case "Pending Bookings":
                    rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingstatus = 1");
                    System.out.println("Filter Pending Bookings");
                    break;
                case "Historical Bookings":
                    rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingstatus = 3");
                    System.out.println("Filter Historical Bookings");
                    break;
                default:
                    rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingstatus != -1"); //ALL BOOKINGS PRINTED except deleted/cancelled bookings
                    System.out.println("Filter All Bookings");
                    break;
            }

            
            //Column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            for(int i = 1; i <= numColumns; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
//            tableModel.setColumnIdentifiers(columnNames);
            
            //Row Data
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                for (int i = 1; i <= numColumns; i++) {
                    if (i == 4) {
                        int status = rs.getInt("BOOKINGSTATUS");
                        switch(status) {
                            case 1: 
                                row.add("Pending");
                                break;
                            case 2:
                                row.add("Active");
                                break;
                            case 3:
                                row.add("Historical");
                                break;
                            default:
                                row.add("Status error");
                                break;
                        }
                    }else {
                        row.add(rs.getObject(i));
                    }                    
                }
                rowData.add(row);
            }
            rs.close();
            statement.close();
            
        } catch(SQLException e) {
            System.out.println("SQLException in fetchBookings - Database.java: " + e.getMessage());
        }
        
        tableModel.updateTableModelData(rowData, columnNames);
    }
    
    public boolean cancelBooking(int roomnumber) {
        System.out.println("Cancelling booking in database...");
        try {
            int guestID = findGuestIDwithRoomNo(roomnumber);
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE bookings SET bookingstatus = -1 WHERE roomnumber = " + roomnumber  + " AND bookingstatus = 1"); //1 = pending booking
            statement.executeUpdate("UPDATE rooms SET roomstatus = 0 WHERE roomnumber = " + roomnumber);
            setGuestIDStatus(guestID);
            
            statement.close();
            System.out.println("Successfully cancelled booking");
            return true;
        } catch(SQLException e) {
            System.out.println("SQLException in cancelBooking Database.java: " + e.getMessage());
        }
        return false;
    }
    
    public boolean checkOUTGuest(int roomnumber) {
        System.out.println("Checking Out Guest in database.....");
        try {
            int guestID = findGuestIDwithRoomNo(roomnumber);
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE bookings SET bookingstatus = 3 WHERE roomnumber = " + roomnumber + " AND bookingstatus = 2");
            statement.executeUpdate("UPDATE rooms SET roomstatus = 3 WHERE roomnumber = " + roomnumber);
            setGuestIDStatus(guestID); 
            
            statement.close();
            System.out.println("Success checking Out Guest");
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException in checkOutGUest Database.java: " + e.getMessage());
        }       
        return false;
    }
    
    private int findGuestIDwithRoomNo(int roomnumber) {
        int guestID = -1;
        try {
            Statement statement = conn.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT guestid FROM bookings WHERE roomnumber = " + roomnumber + " AND bookingstatus = 2");
            rs.next();
            guestID = rs.getInt("GUESTID");
            
            rs.close();
            statement.close();
        }catch (SQLException e) {
            System.out.println("SQLException in findGuestIDwithRoomNo in database.java");
        } catch(NullPointerException e) {
            System.out.println("NullPointerExcecption in setGuestIDStatus after checkout - no match");
        }
        return guestID;
    }
    
    //Checks for active bookings - if all bookings are historical/cancelled, then set Guest status to inactive = 1
    private boolean setGuestIDStatus(int guestID) {
        try {
            Statement statement = conn.createStatement();
            //Check if active bookings exists - not including historical and cancelled bookings 
            ResultSet rs = statement.executeQuery("SELECT bookingID FROM bookings WHERE guestid = " + guestID + " AND bookingstatus != 3 AND bookingstatus !=-1"); 
            if (!rs.next()) {
                System.out.println("No active bookings");
                statement.executeUpdate("UPDATE guests SET gueststatus = 1 WHERE guestid = " + guestID);
                return false;
            };
            rs.close();
        }catch (SQLException e) {
            System.out.println("SQLException in setGuestIDStatus after checkout:" + e.getMessage());
        } catch(NullPointerException e) {
            System.out.println("NullPointerExcecption in setGuestIDStatus after checkout - no match: " + e.getMessage());
        }
        return true;
    }
    
    public boolean checkINGuest(int roomnumber) {
        System.out.println("Checking In Guest....");
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE rooms SET roomstatus = 20 WHERE roomnumber = " + roomnumber);
            statement.executeUpdate("UPDATE bookings SET bookingstatus = 2 WHERE roomnumber = " + roomnumber + " AND bookingstatus = 1");
            statement.close();
            System.out.println("Success checking in Guest");
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException in checkInGUest Database.java: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in checkINGuest in database.java: " + e.getMessage());
        }
        return false;
    }

    
    public String[] findBookingIDMatch(int bookingID, int[] targetRoomStatus) {
        System.out.println("Checking for matching bookingID on the system");
        String[] bookingDetails = null;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM bookings WHERE bookingid = " + bookingID);
            if(!rs.next()) {
                System.out.println("No matching Booking ID on system");
            } else {
                String guestname = rs.getString("GUESTNAME");
                String guestid = Integer.toString(rs.getInt("GUESTID")) ;
                String roomnumber = Integer.toString(rs.getInt("ROOMNUMBER"));                
                bookingDetails = new String[] {guestname, guestid, roomnumber};
                
                rs = statement.executeQuery("SELECT roomstatus FROM rooms WHERE roomnumber = " + roomnumber);
                rs.next();
                int roomstatus = rs.getInt("ROOMSTATUS");
                if (roomstatus != targetRoomStatus[0] && roomstatus != targetRoomStatus[1]){ 
                    System.out.println("Room is already occupied/checked in/checkout/not booked");           
                    return null;
                }
            }
            rs.close();
            statement.close();
        } catch(SQLException e) {
            System.out.println("SQLException in findBookingIDMatch: " + e.getMessage());
        }
        return bookingDetails;
    }
    
    public boolean cleanRoom(int roomnumber) {
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT roomstatus FROM rooms WHERE roomnumber = " + roomnumber);
            rs.next();
            int roomstatus = rs.getInt("ROOMSTATUS");
            if (roomstatus == 3) {
                statement.executeUpdate("UPDATE rooms SET roomstatus = 0 WHERE roomstatus = 3 AND roomnumber = " + roomnumber);            
            } else if (roomstatus == 21) {
                statement.executeUpdate("UPDATE rooms SET roomstatus = 20 WHERE roomstatus = 21 AND roomnumber = " + roomnumber);
            } else {
                rs.close();
                statement.close();
                return false;
            }     
            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("SQLExceptin in cleanRoom database.java: " + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in cleanRoom database.java: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public int findRoomStatus(int roomNumber) {
        int roomstatus = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT roomstatus FROM rooms WHERE roomnumber = " + roomNumber);
            rs.next();
            roomstatus = rs.getInt("ROOMSTATUS");
            rs.close();
            statement.close();
            
            return roomstatus; 
        } catch(SQLException e) {
            System.out.println("SQLException in setRoomStatus in database.java: " + e.getMessage());            
        }
        return roomstatus;
    }
    
    public int setRoomStatus(int roomNumber, int status) {
//        int oldStatus;
        try {
            
            Statement statement = conn.createStatement();
            if (status == 4) //set to Out of Order
                statement.executeUpdate("UPDATE rooms SET roomstatus = 4 WHERE roomstatus = 0 AND roomnumber = " + roomNumber);
            else if (status ==0) //set to available
                statement.executeUpdate("UPDATE rooms SET roomstatus = 0 WHERE roomstatus = 4 AND roomnumber = " + roomNumber);                                
            else {
                //not a correct status 
                statement.close();
                return -1;
            }
            statement.close();
            
        } catch(SQLException e) {
            System.out.println("SQLException in setRoomStatus in database.java: " + e.getMessage());
            return -1;
        }
        return 1;
    }
    
}
