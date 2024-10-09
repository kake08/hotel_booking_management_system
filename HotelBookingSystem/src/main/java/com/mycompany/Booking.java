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
    //"Booking ID", "Guest Name", "GuestID", "CURRENT", "RoomNumber"
    private Integer bookingID; // unique booking id
    private Guest guest;
    private boolean isCurrent;
    private static int bookingCount;
    private Room room;
    public Request myRequests;
    
    //Used for making a booking from SCRATCH
    public Booking(Guest guest) {
        this.bookingID = bookingCount;
        this.guest = guest;
        guest.myBookings.add(this);
        bookingCount++;
        room = null;
        myRequests = new Request();
    }
    
    //Loading a Booking from database/FILE
    public Booking(Integer bookingID, String guestName, Guest guest) {
        this.bookingID = bookingID;
        this.guest = guest;
        guest.addNewBooking(this);
        myRequests = new Request();
    }
    
        /**
     * @param isCurrent the isCurrent to set
     */
    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
    
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
