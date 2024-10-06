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
            case "Log In":
                String username = this.view.username.getText();
                String password = this.view.password.getText();
                System.out.println(action + " clicked!");
                if(this.model.data.userMode == 1) {
                    this.model.checkStaffLogin(username, password);
                }
                else if (this.model.data.userMode == 0) {
                    this.model.checkGuestLogin(username, password);
                }
                break;
            case "Continue":
                //set the usermode as either staff or guest model.data.usermode
                String userMode = (String) this.view.userComboBox.getSelectedItem();
                if ("Login as Guest".equals(userMode)) {
                    this.model.setUserMode(0);
                    System.out.println("Set Usermode as Guest = 0");
                } else if ("Login as Staff".equals(userMode)) {
                    this.model.setUserMode(1);
                    System.out.println("Set User mode as Staff = 1");
                }
                view.setUserMode(this.model.getUserMode());
                break;
            default:
                break;
        }
    }
    
}

