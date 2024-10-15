/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DeanK
 */
public class Data { 

//collective for different data collections?
    //A data structure for bookings e.g. arraylists
    boolean loginFlag = false;
    
    private boolean bookingFlag = false;
    private boolean bookingSuccess = false;
    private boolean checkInFlag = false;
    private boolean checkInConfirmed = false;
    private boolean checkOutFlag = false;
    private boolean cancelBookingFlag = false;
    
    MyTableModel tableModelBookings = new MyTableModel();
    MyTableModel tableModelGuests = new MyTableModel();
    MyTableModel tableModelRooms = new MyTableModel();
    public String tableBookingsFilter = "All Bookings";
    
    int userMode = -1; //-1 neither, 0 for guest, 1 for staff
    String currentloggeduser = null;

    //used for functions
    
    
    private Booking recentBooking;
    private Guest recentGuest = new Guest("null", "null");
    private Room recentRoom = new Room(-1, "NULL");
    
    public Data() {
        this.recentBooking = new Booking(-1, "No Recent Guest", getRecentGuest());
        this.recentBooking.setRoom(recentRoom);
    }
    
    
    
    
    
    
    
    
    
        /**
     * @return the bookingFlag
     */
    public boolean isBookingFlag() {
        return bookingFlag;
    }

    /**
     * @param bookingFlag the bookingFlag to set
     */
    public void setBookingFlag(boolean bookingFlag) {
        this.bookingFlag = bookingFlag;
    }

    /**
     * @return the bookingSuccess
     */
    public boolean isBookingSuccess() {
        return bookingSuccess;
    }

    /**
     * @param bookingSuccess the bookingSuccess to set
     */
    public void setBookingSuccess(boolean bookingSuccess) {
        this.bookingSuccess = bookingSuccess;
    }

    /**
     * @return the recentBooking
     */
    public Booking getRecentBooking() {
        return recentBooking;
    }

    /**
     * @param recentBooking the recentBooking to set
     */
    public void setRecentBooking(Booking recentBooking) {
        this.recentBooking = recentBooking;
    }

    /**
     * @return the recentGuest
     */
    public Guest getRecentGuest() {
        return recentGuest;
    }

    /**
     * @param recentGuest the recentGuest to set
     */
    public void setRecentGuest(Guest recentGuest) {
        this.recentGuest = recentGuest;
    }

    /**
     * @return the recentRoom
     */
    public Room getRecentRoom() {
        return recentRoom;
    }

    /**
     * @param recentRoom the recentRoom to set
     */
    public void setRecentRoom(Room recentRoom) {
        this.recentRoom = recentRoom;
    }

    /**
     * @return the checkInFlag
     */
    public boolean isCheckInFlag() {
        return checkInFlag;
    }

    /**
     * @param checkInFlag the checkInFlag to set
     */
    public void setCheckInFlag(boolean checkInFlag) {
        this.checkInFlag = checkInFlag;
    }


    /**
     * @return the checkInConfirmed
     */
    public boolean isCheckInConfirmed() {
        return checkInConfirmed;
    }

    /**
     * @param checkInConfirmed the checkInConfirmed to set
     */
    public void setCheckInConfirmed(boolean checkInConfirmed) {
        this.checkInConfirmed = checkInConfirmed;
    }

    /**
     * @return the checkOutFlag
     */
    public boolean isCheckOutFlag() {
        return checkOutFlag;
    }

    /**
     * @param checkOutFlag the checkOutFlag to set
     */
    public void setCheckOutFlag(boolean checkOutFlag) {
        this.checkOutFlag = checkOutFlag;
    }

    /**
     * @return the cancelBookingFlag
     */
    public boolean isCancelBookingFlag() {
        return cancelBookingFlag;
    }

    /**
     * @param cancelBookingFlag the cancelBookingFlag to set
     */
    public void setCancelBookingFlag(boolean cancelBookingFlag) {
        this.cancelBookingFlag = cancelBookingFlag;
    }

    
}
