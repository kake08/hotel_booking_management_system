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
//        this.username = username;
        data.loginFlag = this.db.checkStaffLogin(username, password);
        
        notifyListener(); //listener is view - update view
    }
    
    public void checkGuestLogin(String username, String password) {
        data.loginFlag = this.db.checkGuestLogin(username, password);
        notifyListener();
    }
    
    public void setUserMode(int usermode) {
        this.data.userMode = usermode;
    }
    public int getUserMode() {
        return this.data.userMode;
    }
    

    
    //Notifying the listener(view) when data is updated
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
