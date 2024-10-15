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
    void onModelUpdate(Data data);
    
    void checkInFeedbackMSG(int output);
    
    void checkOutFeedbackMSG(int output);
    
    void cancelBookingFeedbackMSG(int output);
    
    void createBookingFeedbackMSG(int output);
}
