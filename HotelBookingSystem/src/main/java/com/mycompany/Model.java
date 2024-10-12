/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;


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
    
    public void fetchData() { 
        db.fetchBookings(data.tableModelBookings);
        db.fetchGuests(data.tableModelGuests);
        db.fetchRooms(data.tableModelRooms);
        
        notifyListener();
            
    }
    
    public void createBooking(String bookingDetails[]){
        String guestName = bookingDetails[0];
        String guestPhone = bookingDetails[1];
        String selectedRoomType = bookingDetails[2];
        data.setBookingFlag(true); //true means it will go through booking process
        
        
        
        //if (no matches) else(matches -> feed the guestID to method)
        Guest guest = db.matchingGuestExist(guestName, guestPhone); //-1 is null, other number is the matching guest ID
        if (guest == null) {
            //creates a booking for a new guest
            data.setBookingSuccess(db.createBooking(guestName, guestPhone, selectedRoomType, -1)); //failed the booking process

        } else {
            //creates a booking for a existing guest
            //TODO: confirm staff user before booking
            data.setBookingSuccess(db.createBooking(guestName, guestPhone, selectedRoomType, guest.getGuestID()));
        }
               
        if (!data.isBookingSuccess()) {
            listener.onModelUpdate(this.data);
            return;
        }
        fetchData(); //refreshes table view
        refreshLists();
    }
    
    public void checkINGuest (String bookingID) {
        //occupies the room
    }
    
    //Refresh all lists including: Bookings, Guests and Rooms table -> Only updates database -> Data.
    //Data is always an updated reflection of what's on the database
    public void refreshLists() {
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
