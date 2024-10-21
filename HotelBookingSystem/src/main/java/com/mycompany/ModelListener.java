/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author DeanK
 */
public interface ModelListener {
    
    //Manage Bookings
    void onModelUpdate(Data data);
    
    void checkInFeedbackMSG(int output);
    
    void checkOutFeedbackMSG(int output);
    
    void cancelBookingFeedbackMSG(int output);
    
    void createBookingFeedbackMSG(int output);
    
    
    //Manage Rooms
    void cleanRoomFeedbackMSG(int output);
    
    void OOORoomFeedbackMSG(int output);
    
    void updateLoggedGuestBookingsList(String[] updatedBookingsList);
    
    void viewMyBookingDetails(String[] bookingDetails, int bookingID);
}
