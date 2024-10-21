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
        
        //make sure the view table model is assigned TO THE model table model
        model.data = view.data;
        model.data.tableModelBookings = view.tableModelBookings;
        model.data.tableModelGuests = view.tableModelGuests;
        model.data.tableModelRooms = view.tableModelRooms;
        model.data.myBookingsListstr = view.myBookingsListstr;
        
        view.loadBookingMSGCards();
    }
    
    //Listens to user input from GUI and performs an action
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
        switch(action) {
            case "Log In":
                String username = this.view.username.getText();
                String password = this.view.password.getText();
                System.out.println(action + " clicked!");
                if(this.model.data.userMode == 1) {
                    this.model.checkStaffLogin(username, password);
                    model.fetchData();
                }
                else if (this.model.data.userMode == 0) {
                    this.model.checkGuestLogin(username, password);
                    model.fetchGuestUserData();
                }
                break;
            case "Continue":
                //set the usermode as either staff or guest model.data.usermode
                String userMode = (String) this.view.userComboBox.getSelectedItem();
                if ("Login as Guest".equals(userMode)) {
                    this.model.setUserMode(0);
                    view.labelPW.setText("Phone Number: ");
                    System.out.println("Set Usermode as Guest = 0");
                } else if ("Login as Staff".equals(userMode)) {
                    this.model.setUserMode(1);
                    view.labelPW.setText("Password: ");
                    System.out.println("Set User mode as Staff = 1");
                }
                view.setUserMode(this.model.getUserMode());
                break;
            case "Logout":     
                System.out.println("Logged Out...Still userMode" + this.model.getUserMode());
                model.setLoggedOut();
                view.setUserMode(this.model.getUserMode());
                break;
            case "Create Booking":
                System.out.println("Creating new Booking....");
                String[] newBookingStr = new String[] {view.guestNametxf.getText(), view.numTxf.getText(), (String)view.roomOptions.getSelectedItem()};
                model.createBooking(newBookingStr);
                break;
            case "Check In Guest":
                System.out.println("Checking in Guest....");
                String bookingID = view.bookingIDtxf1.getText();
                //Find the booking details for that Booking ID
                model.checkINGuest(bookingID);               
                break;
            case "Confirm Check In":
                System.out.println("Confirming check in...");
                //flag for checkinconfirmed
                model.data.setCheckInConfirmed(true);
                bookingID = view.bookingIDtxf1.getText();
                model.checkINGuest(bookingID);  
                break;
            case "Check Out Guest":
                System.out.println("Checking Out Guest....");
                String bookingID2 = view.bookingIDtxf2.getText();
                model.checkOUTGuest(bookingID2);
                break;
            case "Confirm Check Out":
                System.out.println("Confirming check out...");
                model.data.setCheckOutFlag(true);
                bookingID = view.bookingIDtxf2.getText();
                model.checkOUTGuest(bookingID);
                break;
            case "comboBoxChanged":
                String filter = (String) view.bookingsFilterOptions.getSelectedItem();
                model.data.tableBookingsFilter = filter;
                filter = (String) view.guestsFilterOptions.getSelectedItem();
                model.data.tableGuestsFilter = filter;
                filter = (String) view.roomsFilterOptions.getSelectedItem();
                model.data.tableRoomsFilter = filter;
                model.fetchData();
                break;
            case "Cancel Booking":
                System.out.println("Cancelling booking...");
                bookingID = view.bookingIDtxf3.getText();
                model.cancelBooking(bookingID);
                break;
            case "Confirm Cancellation":
                System.out.println("Confirming cancellation...");
                model.data.setCancelBookingFlag(true);
                bookingID = view.bookingIDtxf3.getText();
                model.cancelBooking(bookingID);
                break;
            case "Clean Room":
                System.out.println("Setting room as cleaned...");
                String roomNumber = view.roomNumbertxfRF.getText();
                model.cleanRoom(roomNumber);
                break;
            case "Find Room":
                roomNumber = view.roomNumbertxfRF2.getText();
                model.fetchRoomStatus(roomNumber);
                break;
            case "Set Room Status":
                if (view.availableRB.isSelected())
                   model.setRoomStatus(view.roomNumbertxfRF2.getText(), 0);                    
                else if (view.OOORBtn.isSelected())
                   model.setRoomStatus(view.roomNumbertxfRF2.getText(), 4);
                break;
            case "Update Phone Number":
                view.ManageGuestMSG.setText("Feature not yet available");
                break;
            case "myBookingsFilterOptions":
                String myBookingsFilter = (String) view.myBookingsFilter.getSelectedItem();
                model.data.listMyBookingsFilter = myBookingsFilter;
                model.fetchGuestUserData();
                if ("Pending Requests".equals(myBookingsFilter)) 
                    view.leftPanelBOTTOMbtn.setText("View Request");
                else 
                    view.leftPanelBOTTOMbtn.setText("View Booking");
                break;
            case "View Booking":                
                model.fetchMyBookingDetails(view.myBookingsList.getSelectedValue());
                break;
            case "View Request":
                model.fetchMyRequestDetails(view.myBookingsList.getSelectedValue());
                break;
            default:
                break;
        }
    }
    
}

