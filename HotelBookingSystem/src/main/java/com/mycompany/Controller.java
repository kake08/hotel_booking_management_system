/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author DeanK
 */
public class Controller implements ActionListener {

    public View view;
    public Model model;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
    }
    
    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action) {
            case "Login":
                //
                break;
            case "Return":
                //
                break;
            default:
                break;
        }
    }
    
}
