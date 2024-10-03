/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany;

/**
 *
 * @author DeanK
 */
public class HotelBookingSystem {
    
    public static void main(String[] args) {
        View myFrame = new View();
        Model model = new Model();
        Controller controller = new Controller(myFrame, model);
        model.setListener(myFrame);
    }
}
