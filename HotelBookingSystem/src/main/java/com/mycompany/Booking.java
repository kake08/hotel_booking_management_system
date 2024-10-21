/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author DeanK
 */
public class Booking {
    private Integer bookingID; // unique booking id
    private Guest guest;
    private boolean isCurrent;
    private Room room;
    public Request myRequests;
    
    
    //Constructor for a new Booking
    public Booking(Integer bookingID, String guestName, Guest guest) {
        this.bookingID = bookingID;
        this.guest = guest;
        myRequests = new Request();
    }
    
        /**
     * @param isCurrent the isCurrent to set
     */
    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
    
    //Sets the room object for the booking
    public void setRoom(Room room) {
        this.room = room;
    }

    
    
    /**
     * @return the bookingID
     */
    public Integer getBookingID() {
        return bookingID;
    }

    /**
     * @return the guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * @return the isCurrent
     */
    public boolean isIsCurrent() {
        return isCurrent;
    }

    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }
    
    
    
    
}
