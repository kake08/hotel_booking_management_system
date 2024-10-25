/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author DeanK
 */
public class ModelTest {

    Database db;
    Data data;
    
    public ModelTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        db = new Database();
        db.establishConnection();
        data = new Data();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createBooking method, of class Model.
     */
    @Test
    public void testCreateBooking() { 
        
        //check if the data is added to database - assert equals
        try {
            int beforeAdding = -1;
            int afterAdding = -1;
            java.sql.Statement statement = db.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS NUMBOOKINGS FROM bookings WHERE guestName = 'TESTINGNEWGUEST'");
            if (rs.next()) {
                beforeAdding = rs.getInt("NUMBOOKINGS"); //SHOULD BE ZERO
            } 
            
            //add a booking
            String[] bookingDetails = new String[]{"TESTINGNEWGUEST", "012345", "STANDARD"};
            data = db.createBooking(bookingDetails[0], bookingDetails[1], bookingDetails[2], -1, data);
            
            rs = statement.executeQuery("SELECT COUNT(*) AS NUMBOOKINGS FROM bookings WHERE guestName = 'TESTINGNEWGUEST'");
            if (rs.next()) {
                afterAdding = rs.getInt("NUMBOOKINGS");
            }
            
            int difference = afterAdding - beforeAdding;
            Assert.assertTrue(difference == 1);
            
            statement.executeUpdate("UPDATE ROOMS SET roomstatus = 0 WHERE roomnumber = (SELECT roomnumber FROM bookings WHERE guestname = 'TESTINGNEWGUEST' AND bookingstatus = 1)");
            statement.executeUpdate("DELETE FROM bookings \n" +
                                    "WHERE guestName = 'TESTINGNEWGUEST' \n" +
                                    "AND bookingID = (SELECT MAX(bookingID) FROM bookings WHERE guestName = 'TESTINGNEWGUEST')");
            statement.executeUpdate("DELETE FROM guests where GUESTNAME = 'TESTINGNEWGUEST' AND guestphone = '012345'");
            
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException" + e.getMessage());
        }
        
    }
    
    
    


    
}
