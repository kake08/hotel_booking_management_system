/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.util.ArrayList;

/**
 *
 * @author DeanK
 */
public class Guest{
    ArrayList<Booking> myBookings = new ArrayList<>(); //bookingID
    private static int guestCount; //Used for new guests' ID
    
    private int guestID;
    String guestName;
    String guestPhone; 
    
    
    //Not sure if i should have guest be an extension of user still.
    
    
    public Guest(String name, String value) {
        //super(guestCount, name, value); //- used for when Guest extends User 
        guestName = name;
        guestPhone = value;
    }
    
    
    
    public void loadBooking(Booking booking) {
        myBookings.add(booking);
    }
    

    
    public void addNewBooking(Booking newBooking) {
        myBookings.add(newBooking);
    }

    /**
     * @return the guestID
     */
    public int getGuestID() {
        return guestID;
    }

    /**
     * @param guestID the guestID to set
     */
    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }
    
}
