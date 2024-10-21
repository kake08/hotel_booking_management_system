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
    private int guestID;
    String guestName;
    String guestPhone; 
    
    
    
    public Guest(String name, String value) {
        guestName = name;
        guestPhone = value;
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
