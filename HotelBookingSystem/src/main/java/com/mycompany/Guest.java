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
    
//    public void showMyCurrentBookings()
//    public void showAllMyBookings();
    
//    public boolean equals(Guest i) {
//        return this.getID() == i.getID(); //Based off parent class User (getID)
//    }
    
    public void addNewBooking(Booking newBooking) {
        myBookings.add(newBooking);
    }
    
}
