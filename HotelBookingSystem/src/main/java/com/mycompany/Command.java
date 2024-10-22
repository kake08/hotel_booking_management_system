/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author DeanK
 */
public interface Command {
    void execute();
}

class LogInCommand implements Command {
    private Model model;
    private View view;
    
    public LogInCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        String username = view.username.getText();
        String password = view.password.getText();
        if (model.data.userMode == 1) {
            model.checkStaffLogin(username, password);
            model.fetchData();
        } else if (model.data.userMode == 0) {
            model.checkGuestLogin(username, password);
            model.fetchGuestUserData();
        }
    }
}


class ContinueCommand implements Command {
    private Model model;
    private View view;
    
    public ContinueCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        //set the usermode as either staff or guest model.data.usermode
        String userMode = (String) this.view.userComboBox.getSelectedItem();
        if ("Login as Guest".equals(userMode)) {
            model.setUserMode(0);
            view.labelPW.setText("Phone Number: ");
            System.out.println("Set Usermode as Guest = 0");
        } else if ("Login as Staff".equals(userMode)) {
            model.setUserMode(1);
            view.labelPW.setText("Password: ");
            System.out.println("Set User mode as Staff = 1");
        }
        view.setUserMode(this.model.getUserMode());
    }
}

class LogOutCommand implements Command {
    private Model model;
    private View view;
    
    public LogOutCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Logged Out...Still userMode" + this.model.getUserMode());
        model.setLoggedOut();
        view.setUserMode(this.model.getUserMode());        
    }
}

class CreateBookingCommand implements Command {
    private Model model;
    private View view;
    
    public CreateBookingCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Creating new Booking....");
        String[] newBookingStr = new String[] {view.manageBookingsPanelMgr.guestNametxf.getText(), view.manageBookingsPanelMgr.numTxf.getText(), (String)view.manageBookingsPanelMgr.roomOptions.getSelectedItem()};
        model.createBooking(newBookingStr);        
    }
}

class CheckInGuestCommand implements Command {
    private Model model;
    private View view;
    
    public CheckInGuestCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Checking in Guest....");
        String bookingID = view.manageBookingsPanelMgr.bookingIDtxf1.getText();
        //Find the booking details for that Booking ID
        model.checkINGuest(bookingID);           
    }
}

class ConfirmCheckInCommand implements Command {
    private Model model;
    private View view;
    
    public ConfirmCheckInCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Confirming check in...");
        //flag for checkinconfirmed
        model.data.setCheckInConfirmed(true);
        String bookingID = view.manageBookingsPanelMgr.bookingIDtxf1.getText();
        model.checkINGuest(bookingID);  
    }
}

class CheckOutGuestCommand implements Command {
    private Model model;
    private View view;
    
    public CheckOutGuestCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Checking Out Guest....");
        String bookingID2 = view.manageBookingsPanelMgr.bookingIDtxf2.getText();
        model.checkOUTGuest(bookingID2);        
    }
}

class ConfirmCheckOutCommand implements Command {
    private Model model;
    private View view;
    
    public ConfirmCheckOutCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Confirming check out...");
        model.data.setCheckOutFlag(true);
        String bookingID = view.manageBookingsPanelMgr.bookingIDtxf2.getText();
        model.checkOUTGuest(bookingID);        
    }
}

class ComboBoxChangedCommand implements Command {
    private Model model;
    private View view;
    
    public ComboBoxChangedCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        String filter = (String) view.manageBookingsPanelMgr.bookingsFilterOptions.getSelectedItem();
        model.data.tableBookingsFilter = filter;
        filter = (String) view.manageGuestsPanelMgr.guestsFilterOptions.getSelectedItem();
        model.data.tableGuestsFilter = filter;
        filter = (String) view.manageRoomsPanelMgr.roomsFilterOptions.getSelectedItem();
        model.data.tableRoomsFilter = filter;
        model.fetchData();
    }
}

class CancelBookingCommand implements Command {
    private Model model;
    private View view;
    
    public CancelBookingCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Cancelling booking...");
        String bookingID = view.manageBookingsPanelMgr.bookingIDtxf3.getText();
        model.cancelBooking(bookingID);        
    }
}

class ConfirmCancellationCommand implements Command {
    private Model model;
    private View view;
    
    public ConfirmCancellationCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Confirming cancellation...");
        model.data.setCancelBookingFlag(true);
        String bookingID = view.manageBookingsPanelMgr.bookingIDtxf3.getText();
        model.cancelBooking(bookingID);        
    }
}

class CleanRoomCommand implements Command {
    private Model model;
    private View view;
    
    public CleanRoomCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        System.out.println("Setting room as cleaned...");
        String roomNumber = view.manageRoomsPanelMgr.roomNumbertxfRF.getText();
        model.cleanRoom(roomNumber);        
    }
}

class FindRoomCommand implements Command {
    private Model model;
    private View view;
    
    public FindRoomCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        String roomNumber = view.manageRoomsPanelMgr.roomNumbertxfRF2.getText();
        model.fetchRoomStatus(roomNumber);        
    }
}

class SetRoomStatusCommand implements Command {
    private Model model;
    private View view;
    
    public SetRoomStatusCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        if (view.manageRoomsPanelMgr.availableRB.isSelected())
            model.setRoomStatus(view.manageRoomsPanelMgr.roomNumbertxfRF2.getText(), 0);                    
        else if (view.manageRoomsPanelMgr.OOORBtn.isSelected())
            model.setRoomStatus(view.manageRoomsPanelMgr.roomNumbertxfRF2.getText(), 4);
    }
}

class MyBookingsFilterOptionsCommand implements Command {
    private Model model;
    private View view;
    
    public MyBookingsFilterOptionsCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        String myBookingsFilter = (String) view.myBookingPanelMgr.myBookingsFilter.getSelectedItem();
        model.data.listMyBookingsFilter = myBookingsFilter;
        model.fetchGuestUserData();
        if ("Pending Requests".equals(myBookingsFilter)) 
            view.myBookingPanelMgr.leftPanelBOTTOMbtn.setText("View Request");
        else 
            view.myBookingPanelMgr.leftPanelBOTTOMbtn.setText("View Booking");        
    }
}

class ViewBookingCommand implements Command {
    private Model model;
    private View view;
    
    public ViewBookingCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        model.fetchMyBookingDetails(view.myBookingPanelMgr.myBookingsList.getSelectedValue());
    }
}

class ViewRequestCommand implements Command {
    private Model model;
    private View view;
    
    public ViewRequestCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    @Override
    public void execute() {
        model.fetchMyRequestDetails(view.myBookingPanelMgr.myBookingsList.getSelectedValue());
    }
}





