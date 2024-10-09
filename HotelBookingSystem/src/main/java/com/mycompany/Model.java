/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DeanK
 */
public class Model {
    public Database db;
    public Data data;
//    public String currentUser; //should this be User class?
    //public Data array list of data?
    //public String username //current username
    
    private ModelListener listener; //listener to notify when model's data is updated (to update view)
    
    public Model() {
        this.db = new Database();
        this.db.establishConnection(); //why not just call setup when instantiating Database
        data = new Data();
    }
    
    public void setListener(ModelListener listener) {
        this.listener = listener;
    }
    
    public void checkStaffLogin(String username, String password) {
        this.data = this.db.checkStaffLogin(username, password, this.data); 
        refreshLists();
        notifyListener(); //listener is view - update view
    }
    
    public void checkGuestLogin(String username, String password) {
        this.data = this.db.checkGuestLogin(username, password, this.data);
        notifyListener();
    }
    
    public void setUserMode(int usermode) {
        this.data.userMode = usermode;
    }
    
    //Removes user and sets loginFlag to flase
    public void setLoggedOut() { 
        data.loginFlag = false;
        data.currentloggeduser = "";        
    }
    
    public int getUserMode() {
        return this.data.userMode;
    }
    
    
    //Notifying the listener(view) when data is updated
    
    public void fetchBookings() { 
        db.fetchBookings(data.tableModel);
        notifyListener();
        
//        data.tableModel = db.fetchBookings();
//        data.tableModel.fireTableDataChanged();       
    }
    
    public void createBooking(String bookingDetails[]){
        String guestName = bookingDetails[0];
        String guestPhone = bookingDetails[1];
        String selectedRoomType = bookingDetails[2];
        //process of automating room selection.... TODO
        
        //if (no matches) else(matches -> feed the guestID to method)
        //data.setBookingFlag(db.createBooking(guestName, guestPhone, selectedRoomType, guestID = null if no existing guest));
        
        //creates a booking
        data.setBookingFlag(db.createBooking(guestName, guestPhone, selectedRoomType));
        
        fetchBookings(); //refreshes table view
        refreshLists();
    }
    
    //Refresh all lists including: Bookings, Guests and Rooms table -> Only updates database -> Data.
    //Data is always an updated reflection of what's on the database
    public void refreshLists() {

//        fetchBookings();
        notifyListener();
    }
    
    private void notifyListener() {
        if (listener != null) {
            listener.onModelUpdate(this.data);
        }
    }
    
    /* HERE WE ARE SOME METHODS FOR QUERIES TO RETRIEVE DATA FROM DATABASE*/
    //public void retrieveBookings
    //public void retrieveGuestsRequests
    //public void retrieveRoomsList
    
    
    //DOES THIS GO HERE?
    /* HERE ARE SOME METHODS FOR QUERIES THAT UPDATE DATA ON DATABASE*/
    //public void updateBookingsList
    //public void updateGuestsList
    //public void updateRoomsList
}
