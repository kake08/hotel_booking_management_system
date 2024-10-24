/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author DeanK
 */
public class View extends JFrame implements ModelListener{
     //Panels
    JPanel mainPanel;
    JPanel loginPanel;
    JPanel startPanel;
    JPanel staffMenuPanel;
    JPanel guestMenuPanel;
    CardLayout cards;
    //Buttons
    JButton continueButton;
    JButton loginButton;
    JButton btnLogout1; //guest
    JButton btnLogout2; //staff
    JComboBox<String> userComboBox;
    //Labels
    JLabel loginMessage;
    JLabel labelPW;       
    //Textbox or Typing fields
    JTextField username;
    JPasswordField password ;
    //Data
    Data data = new Data();
    //Panel Manager
    MyBookingPanelManager myBookingPanelMgr;
    ManageBookingsPanelManager manageBookingsPanelMgr;
    ManageGuestsPanelManager manageGuestsPanelMgr;
    ManageRoomsPanelManager manageRoomsPanelMgr;
    
    public View() {
        init();
        loadLogin();       
        manageBookingsPanelMgr = new ManageBookingsPanelManager(data);
        manageGuestsPanelMgr = new ManageGuestsPanelManager();
        manageRoomsPanelMgr = new ManageRoomsPanelManager();        
        loadStaffMenuPanel();    
    
        myBookingPanelMgr = new MyBookingPanelManager();
        loadGuestMenuPanel();        
        
        cards.show(mainPanel, "STARTPANEL");
        this.add(mainPanel);
        setVisible(true);   
    }
    
    //For communication with the Controller.java
    public void addActionListener(ActionListener listener) {       
        this.continueButton.addActionListener(listener);
        this.loginButton.addActionListener(listener);
        this.btnLogout1.addActionListener(listener);
        this.btnLogout2.addActionListener(listener);          
        myBookingPanelMgr.addActionListener(listener);
        manageBookingsPanelMgr.addActionListener(listener);
        manageRoomsPanelMgr.addActionListener(listener);
        manageGuestsPanelMgr.addActionListener(listener);     
    }
     
    //initializes frame 
    private void init() {
        setSize(1120,500);
        setLocationRelativeTo(null);
        setTitle("Hotel Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //MAIN PANEL
        mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("Hotel Booking System - MAIN PANEL"));
        cards = new CardLayout();
        mainPanel.setLayout(cards);
        
        //Start Up Panel
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBorder(new TitledBorder("LOGIN OPTIONS"));
        
        //User-OPTIONS COMPONENTS
        JPanel userOptionComponents = new JPanel();
        userOptionComponents.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.gridheight = 1;
        JLabel labelOptions = new JLabel("Select User: ");
        userOptionComponents.add(labelOptions, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridheight = 2;
        String[] comboOptions = new String[] {"Login as Guest", "Login as Staff"};
        userComboBox = new JComboBox<String>(comboOptions);
        userOptionComponents.add(userComboBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.gridheight = 1;
        continueButton = new JButton("Continue");
        userOptionComponents.add(continueButton, gbc);
        
        startPanel.add(userOptionComponents, BorderLayout.CENTER);
        mainPanel.add(startPanel, "STARTPANEL");      
    }
    
    //Loads starting frame - user Login menu
    private void loadLogin () {
        //LOGIN PANEL
        loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());            
        loginPanel.setBorder(new TitledBorder("LOGIN PANEL"));
        
        //LOGIN COMPONENTS
        JPanel loginComponents = new JPanel();
        loginComponents.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel labelUser = new JLabel("Username:    ");
        loginComponents.add(labelUser, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;

        username = new JTextField(10);
        loginComponents.add(username, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        labelPW = new JLabel("Password:     ");
        loginComponents.add(labelPW, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        password = new JPasswordField(10);
        loginComponents.add(password, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JButton button2 = new JButton("Return");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "STARTPANEL");
            }
        });
        loginComponents.add(button2, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 1; gbc.gridheight = 1;
        loginButton = new JButton("Log In");       
        loginComponents.add(loginButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginMessage = new JLabel("                 ");
        loginComponents.add(loginMessage, gbc);
        
        
        loginPanel.add(loginComponents, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGINPANEL");
    }
    
    //Loads the container for the guest MenuPanel and its main components
    private void loadGuestMenuPanel() {
        guestMenuPanel = new JPanel();
        guestMenuPanel.setLayout(new BorderLayout());
        
        //Single TabbedPane - stylistic and future proof decision
        JTabbedPane menuComponents = new JTabbedPane();
        JPanel myBookingsPanel = new JPanel();
        
        menuComponents.add("My Bookings", myBookingsPanel);
        loadMyBookingsPanel(myBookingsPanel);
        
        //Bottom Panel 
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        btnLogout1 = new JButton("Logout");
        btnLogout1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "LOGINPANEL");
                
            }
        });
        
        bottomPanel.add(btnLogout1);
        guestMenuPanel.add(bottomPanel, BorderLayout.SOUTH);
        guestMenuPanel.add(menuComponents, BorderLayout.CENTER);
        mainPanel.add(guestMenuPanel, "GUESTMENUPANEL");
    }

    //Loads the container for the staff MenuPanel and its main components
    private void loadStaffMenuPanel() {
        staffMenuPanel = new JPanel();
        staffMenuPanel.setLayout(new BorderLayout());   
               
        //TabbedPane has tabPanel1, tabPanel2, tabPanel3
        JTabbedPane menuComponents = new JTabbedPane();
        //MANAGE BOOKINGS
        JPanel tabPanel1 = new JPanel();               
        //MANAGE GUESTS
        JPanel tabPanel2 = new JPanel();
        //MANAGE ROOMS
        JPanel tabPanel3 = new JPanel();
                
        menuComponents.add("Manage Bookings", tabPanel1);
        menuComponents.add("Manage Guests", tabPanel2);
        menuComponents.add("Manage Rooms", tabPanel3);
        loadManageBookings(tabPanel1);
        loadManageGuests(tabPanel2);
        loadManageRooms(tabPanel3);
                
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        btnLogout2 = new JButton("Logout");
        btnLogout2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "LOGINPANEL");
            }
        });
        bottomPanel.add(btnLogout2);
        
        staffMenuPanel.add(bottomPanel, BorderLayout.SOUTH);
        staffMenuPanel.add(menuComponents, BorderLayout.CENTER);
        mainPanel.add(staffMenuPanel, "STAFFMENUPANEL");
        
    }
          
//    -----------------------------LOAD STAFF MANAGEMENT PANELS--------------------------------------------------------------------------------
    //Loads the manage booking tabPanel contents
    private void loadManageBookings(JPanel tabPanel1) {
        manageBookingsPanelMgr.loadManageBookings(tabPanel1);
    }

    //Loads the Manage guests tabPanel contents
    private void loadManageGuests(JPanel tabPanel2) {
        manageGuestsPanelMgr.loadManageGuests(tabPanel2);
    }   
 
    //Loads the Manage Rooms tabPanel contents
    private void loadManageRooms(JPanel tabPanel3) {
        manageRoomsPanelMgr.loadManageRooms(tabPanel3);       
    }    
//    -----------------------------END OF STAFF MANAGEMENT PANELS------------------------------------------------------------------------------
 
    private void loadMyBookingsPanel(JPanel myBookingsPanel) {
        myBookingPanelMgr.loadMyBookingsPanel(myBookingsPanel);
    }
 
    @Override
    public void createBookingFeedbackMSG(int output) {
        manageBookingsPanelMgr.createBookingFeedbackMSG(output);
    }
    
    @Override
    public void cancelBookingFeedbackMSG(int output){
        manageBookingsPanelMgr.cancelBookingFeedbackMSG(output);
    }
    
    @Override
    public void checkOutFeedbackMSG(int output) {
        manageBookingsPanelMgr.checkOutFeedbackMSG(output);
    }
    
    //-1 = error
    @Override
    public void checkInFeedbackMSG(int output) {
        manageBookingsPanelMgr.checkInFeedbackMSG(output);
    }
    
    @Override
    public void cleanRoomFeedbackMSG(int output) {
        manageRoomsPanelMgr.cleanRoomFeedbackMSG(output);
    }
    
    @Override
    public void OOORoomFeedbackMSG(int output) {
        manageRoomsPanelMgr.OOORoomFeedbackMSG(output);
    }
    
    @Override
    public void onModelUpdate(Data data) {
        if(!data.loginFlag) {
            this.username.setText("");
            this.password.setText("");
            this.loginMessage.setText("Invalid username or password. Try Again.");
        } else { 
            this.username.setText("");
            this.password.setText("");
            this.loginMessage.setText("");
            if (data.userMode == 0) //guest
            {
                cards.show(mainPanel, "GUESTMENUPANEL");
                mainPanel.setBorder(new TitledBorder("You are logged in as " + data.currentloggeduser));
                
            }
            else if (data.userMode == 1) { //staff
                cards.show(mainPanel, "STAFFMENUPANEL");
                mainPanel.setBorder(new TitledBorder("You are logged in as " + data.currentloggeduser + " (STAFF)"));
                if (data.isBookingFlag()) {
                    setManageBookingMSG(data);
                }    
                if (data.isCheckInFlag()) {
                    confirmCheckIn(data);
                }
            }
        }      
    }
        
    private void confirmCheckIn(Data data) {
        manageBookingsPanelMgr.confirmCheckIn(data);
    }
    
    private String[] setManageBookingMSG(Data data){      
        return manageBookingsPanelMgr.setManageBookingMSG(data);
    }
    
    @Override
    public void clearGuestBookingContents() {
        myBookingPanelMgr.bookingContents.setText("");
        myBookingPanelMgr.bookingTitle.setText("");
    }
    
    @Override
    public void updateLoggedGuestBookingsList(String[] updatedBookingsList) {
        myBookingPanelMgr.myBookingsList.setListData(updatedBookingsList);
    }
    
    @Override
    public void viewMyBookingDetails(String[] bookingDetails, int bookingId) {
        this.myBookingPanelMgr.viewMyBookingDetails(bookingDetails, bookingId);
    }
    
    
    public void setUserMode(int userMode) {
        if (userMode == 0) { //guest
            guestMenuPanel.setBorder(new TitledBorder("Guest Menu"));
            loginPanel.setBorder(new TitledBorder("Guest Login"));
            mainPanel.setBorder(new TitledBorder("Hotel Booking System - MAIN PANEL"));
            this.loginMessage.setText("");
        }
        else if (userMode == 1) {
             staffMenuPanel.setBorder(new TitledBorder("Staff Menu"));
             loginPanel.setBorder(new TitledBorder("Staff Login"));
             mainPanel.setBorder(new TitledBorder("Hotel Booking System - MAIN PANEL"));
             this.loginMessage.setText("");
        }
        cards.show(mainPanel, "LOGINPANEL");
    }
    
}


