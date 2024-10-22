/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DeanK
 */
public class Controller implements ActionListener {

    public View view;
    public Model model;
    Map<String, Command> commands = new HashMap<>();
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
        
        //make sure the view table model is assigned TO THE model table model
        model.data = view.data;
        model.data.tableModelBookings = view.tableModelBookings;
        model.data.tableModelGuests = view.manageGuestsPanelMgr.tableModelGuests;
        model.data.tableModelRooms = view.manageRoomsPanelMgr.tableModelRooms;
        model.data.myBookingsListstr = view.myBookingPanelMgr.myBookingsListstr;
        
        view.loadBookingMSGCards();
        
        
        putCommands();
    }
    
    private void putCommands() {
        commands.put("Log In", new LogInCommand(model, view));
        commands.put("Continue", new ContinueCommand(model, view));
        commands.put("Logout", new LogOutCommand(model, view));
        commands.put("Create Booking", new CreateBookingCommand(model,view));
        commands.put("Check In Guest", new CheckInGuestCommand(model, view));
        commands.put("Confirm Check In", new ConfirmCheckInCommand(model, view));
        commands.put("Check Out Guest", new CheckOutGuestCommand(model, view));
        commands.put("Confirm Check Out", new ConfirmCheckOutCommand(model, view));
        commands.put("comboBoxChanged", new ComboBoxChangedCommand(model, view));
        commands.put("Cancel Booking", new CancelBookingCommand(model, view));
        commands.put("Confirm Cancellation", new ConfirmCancellationCommand(model, view));
        commands.put("Clean Room", new CleanRoomCommand(model,view));
        commands.put("Find Room", new FindRoomCommand(model, view));
        commands.put("Set Room Status", new SetRoomStatusCommand(model, view));
        commands.put("myBookingsFilterOptions", new MyBookingsFilterOptionsCommand(model, view));
        commands.put("View Booking", new ViewBookingCommand(model, view));
        commands.put("View Request", new ViewRequestCommand(model,view));
 
        //omitted as not implemented yet
//        commands.put("Update Phone Number", new UpdatePhoneNumberCommand(model,view));
    }
    
    //Listens to user input from GUI and performs an action depending on action event
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
        Command command = commands.get(action);
        if (command != null) {
            command.execute();
        }
    }
    
}

