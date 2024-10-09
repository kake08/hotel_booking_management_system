/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DeanK
 */
public class View extends JFrame implements ModelListener{
    
    JPanel mainPanel;
    JPanel loginPanel;
    JPanel startPanel;
    JPanel staffMenuPanel;
    JPanel guestMenuPanel;
    CardLayout cards;
    JPanel leftPanel1btnCENTER; //Manage bookings forms
    JPanel leftPanel2btnCENTER; //Manage guest forms
    CardLayout bookingFormCards;
    Integer bookingMode; //1:MANAGE BOOKING 2:MANAGE GUEST 3:MANAGE ROOMS
    String strLoginMode;
    
    JPanel tabPanel1; //Staff bookings panel
    
    //Buttons
    JButton continueButton;
    JButton loginButton;
    JButton btnLogout1; //guest
    JButton btnLogout2; //staff
    
    JButton createBookingBtn;
    JTextField guestNametxf;
    JTextField numTxf;
    JComboBox<String> roomOptions;
    
    //Labels
    JLabel loginMessage;
    JLabel manageBookingMSG;
    JLabel labelPW;
    
    //Textbox or Typing fields
    JTextField username;
    JPasswordField password ;
    
    //Combobox
    JComboBox<String> userComboBox;
    
    //Tables and strlist
    MyTableModel tableModel;
    JTable bookingsTable;
    String[][] myStrBookingList;
    
    //Data
    Data data;
    
    //Constructor
    public View() {

        init();
        loadLogin();

        //should only load on staff login
        loadStaffMenuPanel();
        
        loadGuestMenuPanel();
        loadBookingForms();
        
        
        cards.show(mainPanel, "STARTPANEL");
        this.add(mainPanel);
        setVisible(true);
        
    }
    
    public void addActionListener(ActionListener listener) {
        this.continueButton.addActionListener(listener);
        this.loginButton.addActionListener(listener);
        this.btnLogout1.addActionListener(listener);
        this.btnLogout2.addActionListener(listener);
        this.createBookingBtn.addActionListener(listener);
    }
   
     
    //initializes frame -- need to encapsulate? init - main, startup  atc.  - different functions
    private void init() {
        setSize(740,500);
        setLocationRelativeTo(null);
        setTitle("Hotel Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //MAIN PANEL
        mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("Hotel Booking System - MAIN PANEL"));
        cards = new CardLayout();
        mainPanel.setLayout(cards);
        bookingFormCards = new CardLayout();
        bookingMode = -1; 
        
        //Start Up Panel
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBorder(new TitledBorder("LOGIN OPTIONS"));
        
        //User-OPTIONS COMPONENTS
        JPanel userOptionComponents = new JPanel();
        userOptionComponents.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel labelOptions = new JLabel("Select User: ");
        userOptionComponents.add(labelOptions, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        String[] comboOptions = new String[] {"Login as Guest", "Login as Staff"};
        userComboBox = new JComboBox<String>(comboOptions);
        userOptionComponents.add(userComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        continueButton = new JButton("Continue");
        userOptionComponents.add(continueButton, gbc);
        
        startPanel.add(userOptionComponents, BorderLayout.CENTER);
        mainPanel.add(startPanel, "STARTPANEL");
       
    }
    
   
    
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
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        username = new JTextField(10);
        loginComponents.add(username, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        labelPW = new JLabel("Password:     ");
        loginComponents.add(labelPW, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        password = new JPasswordField(10);
        loginComponents.add(password, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JButton button2 = new JButton("Return");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "STARTPANEL");
            }
        });
        loginComponents.add(button2, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        loginButton = new JButton("Log In");       
//        loginButton.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cards.show(mainPanel, "MENUPANEL");
//            }            
//        });
        loginComponents.add(loginButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginMessage = new JLabel("                 ");
        loginComponents.add(loginMessage, gbc);
        
        
        loginPanel.add(loginComponents, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGINPANEL");
    }
    
    private void loadGuestMenuPanel() {
        guestMenuPanel = new JPanel();
        guestMenuPanel.setLayout(new BorderLayout());
        
        //TabbedPanes
        JTabbedPane menuComponents = new JTabbedPane();
        //My bookings overview and My requests
        JPanel tabPanel1 = new JPanel();
        JPanel tabPanel2 = new JPanel();
        
        menuComponents.add("My Bookings", tabPanel1);
        menuComponents.add("My Requests", tabPanel2);
        
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
    

    
    private void loadStaffMenuPanel() {
        staffMenuPanel = new JPanel();
        staffMenuPanel.setLayout(new BorderLayout());   
               
        //TabbedPane has tabPanel1, tabPanel2, tabPanel3
        JTabbedPane menuComponents = new JTabbedPane();
        //MANAGE BOOKINGS
        tabPanel1 = new JPanel();               
        //MANAGE GUESTS
        JPanel tabPanel2 = new JPanel();
        //MANAGE ROOMS
        JPanel tabPanel3 = new JPanel();
                
        menuComponents.add("Manage Bookings", tabPanel1);
        menuComponents.add("Manage Guests", tabPanel2);
        menuComponents.add("Manage Rooms", tabPanel3);
        loadManageBookingsPanel(tabPanel1);
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
    
    private void loadBookingForms() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        //1.EMPTY panel
        JPanel clearForm = new JPanel();
        leftPanel1btnCENTER.add(clearForm, "CLEARFORM");
        
        //2.BOOKING panel - initialize components
        JPanel createForm = new JPanel();
        createForm.setLayout(new GridBagLayout());

        JLabel label1 = new JLabel("Enter New Booking Details: ");
        JLabel guestNameLabel = new JLabel("Guest Name: ");
        guestNametxf = new JTextField(10);
        JLabel numLabel = new JLabel("Phone Number: ");
        numTxf = new JTextField(10);
        JLabel roomTypeLabel = new JLabel("Room Type: ");
        String[] strRoomOptions = new String[] {"Standard", "Deluxe", "Suite"};
        roomOptions = new JComboBox<String>(strRoomOptions);                
        createBookingBtn = new JButton("Create Booking");
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                guestNametxf.setText(null);
                numTxf.setText(null);
                manageBookingMSG.setText("");
                
            }
        });
        //Adding form components to panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; gbc.gridwidth = 1; 
        createForm.add(label1, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(guestNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(guestNametxf,gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(numLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(numTxf, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(roomTypeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(roomOptions, gbc);
        //Adding Submit Button at the end
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridheight = 1; gbc.gridwidth = 1;                    
        createForm.add(createBookingBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridheight = 1; gbc.gridwidth = 1;
        createForm.add(clearBtn, gbc);
        //Adding form to the Panel with cards layout
        leftPanel1btnCENTER.add(createForm, "CREATEFORM");
        
        //3.CHECK IN panel - initialize components
        JPanel checkinForm = new JPanel();
        checkinForm.setLayout(new GridBagLayout());
        JLabel label2 = new JLabel("Check In Guest: ");
        JLabel bookingIdLabel1 = new JLabel("Booking ID: ");
        JTextField bookingIDtxf1 = new JTextField(10);
        JButton submitbtn1 = new JButton("Check In Guest");
        JButton clearBtn1 = new JButton("Clear");
        clearBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingIDtxf1.setText(null);
            }
            
        });
        
        //Adding form components to Check In Panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkinForm.add(label2, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkinForm.add(bookingIdLabel1, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkinForm.add(bookingIDtxf1, gbc);
        //Add buttons
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkinForm.add(submitbtn1, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkinForm.add(clearBtn1, gbc);
        //Adding form to the panel with cards layout
        leftPanel1btnCENTER.add(checkinForm, "CHECKINFORM");
        
        
        //4.CHECK OUT panel - initialize components
        JPanel checkoutForm = new JPanel();
        checkoutForm.setLayout(new GridBagLayout());
        
        JLabel label3 = new JLabel("Check Out Guests: ");
        JLabel bookingIDLabel2 = new JLabel("Booking ID: ");
        JTextField bookingIDtxf2 = new JTextField(10);
        JButton submitbtn2 = new JButton("Check Out Guest");
        JButton clearBtn2 = new JButton("Clear");
        clearBtn2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingIDtxf2.setText(null);
            }
        });
        
        //Adding form components to CHECK OUT panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkoutForm.add(label3, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkoutForm.add(bookingIDLabel2, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
        checkoutForm.add(bookingIDtxf2, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        checkoutForm.add(submitbtn2, gbc);
        gbc.gridx = 1; gbc.gridy =3;
        checkoutForm.add(clearBtn2, gbc);
        //Adding Checkout form panel with the Cards Layout
        leftPanel1btnCENTER.add(checkoutForm, "CHECKOUTFORM");
        
        
        //5.CANCEL panel - initialize components
        JPanel cancelForm = new JPanel();
        cancelForm.setLayout(new GridBagLayout());
        
        JLabel label4 = new JLabel("Cancel a Booking: ");
        JLabel bookingIDLabel3 = new JLabel("Booking ID: ");
        JTextField bookingIDtxf3 = new JTextField(10);
        JButton submitbtn3 = new JButton("Cancel Booking");
        JButton clearBtn3 = new JButton("Clear");
        clearBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingIDtxf3.setText(null);
            }
        });
        
        //Adding form components to CANCEL FORM
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; gbc.gridwidth = 1;
        cancelForm.add(label4, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        cancelForm.add(bookingIDLabel3, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        cancelForm.add(bookingIDtxf3, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        cancelForm.add(submitbtn3, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        cancelForm.add(clearBtn3, gbc);
        //Adding Cancel form to panel with the Cards Layout
        leftPanel1btnCENTER.add(cancelForm, "CANCELFORM");
        
        
        //Finally, show the CLEAR card on start up.
        bookingFormCards.show(leftPanel1btnCENTER, "CLEARFORM");
        
    }
    
    private void loadManageBookingsPanel(JPanel tabPanel1) {
        
        tabPanel1.setLayout(new BorderLayout());

        //Manage bookings menu on LEFT
        JPanel leftPanel1 = new JPanel();
        leftPanel1.setLayout(new BorderLayout());
        
        JPanel leftPanel1btnTOP = new JPanel();
        leftPanel1btnTOP.setLayout(new GridLayout(3,2));
        leftPanel1btnCENTER = new JPanel();
        leftPanel1btnCENTER.setLayout(bookingFormCards);
        JPanel leftPanel1MessageBOTTOM = new JPanel();
        leftPanel1MessageBOTTOM.setLayout(new FlowLayout());
        
        
        
        
        //Adding Top, CENTER and Bottom Panels into left split pane
        leftPanel1.add(leftPanel1btnTOP, BorderLayout.NORTH);
        leftPanel1.add(leftPanel1btnCENTER, BorderLayout.CENTER);
        leftPanel1.add(leftPanel1MessageBOTTOM, BorderLayout.SOUTH);
        loadBookingForms();
        
        //Adding Label for user feedback to left splitpane, BOTTOM border
        manageBookingMSG = new JLabel("");
        leftPanel1MessageBOTTOM.add(manageBookingMSG);
        
        //Adding buttons to left splitpane, North border
        JLabel northBtnTitle1 = new JLabel("Filter Booking List:");
        String[] strFilterOptions = new String[]{ "All Bookings", "Active Bookings", "Pending Bookings", "Historical Bookings"};
        JComboBox<String> filterOptions = new JComboBox<String>(strFilterOptions);
        
        leftPanel1btnTOP.add(northBtnTitle1);
        leftPanel1btnTOP.add(filterOptions);
        
        //Adding buttons to left splitpane, CENTRAL border
        JButton makeBookingbtn = new JButton("Create Booking");
        makeBookingbtn.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {  
                if (bookingMode == 1) {
                    bookingFormCards.show(leftPanel1btnCENTER, "CLEARFORM");
                    bookingMode = -1;
                    return;
                }
                bookingFormCards.show(leftPanel1btnCENTER, "CREATEFORM");
                bookingMode = 1;
            }
        });
        JButton checkInBtn = new JButton("Check In");   
        checkInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookingMode == 2) {
                    bookingFormCards.show(leftPanel1btnCENTER, "CLEARFORM");
                    bookingMode = -1;
                    return;
                }
                bookingFormCards.show(leftPanel1btnCENTER, "CHECKINFORM");
                bookingMode = 2;
            }
            
        });
        JButton checkOutBtn = new JButton("Check Out");
        checkOutBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (bookingMode == 3) {
                    bookingFormCards.show(leftPanel1btnCENTER, "CLEARFORM");
                    bookingMode = -1;
                    return;
                }
                bookingFormCards.show(leftPanel1btnCENTER,"CHECKOUTFORM");
                bookingMode = 3;
            }
        });
        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bookingMode == 4) {
                    bookingFormCards.show(leftPanel1btnCENTER, "CLEARFORM");
                    bookingMode = -1;
                    return;
                }
                bookingFormCards.show(leftPanel1btnCENTER, "CANCELFORM");
                bookingMode = 4;
            }
            
        });
        leftPanel1btnTOP.add(makeBookingbtn);
        leftPanel1btnTOP.add(checkInBtn);
        leftPanel1btnTOP.add(checkOutBtn);
        leftPanel1btnTOP.add(cancelBtn);
               
        //Initializing Table model which reflects Booking records
        tableModel = new MyTableModel();
        bookingsTable = new JTable(tableModel){
            public boolean editCellAt(int row, int column, java.util.EventObject e) { //Prevents editing of cells in table = https://rb.gy/1qflxh
            return false;
         }
        };
        
        
        //Adding Table to scrollPane
        JScrollPane sp1 = new JScrollPane(leftPanel1);
        JScrollPane sp2 = new JScrollPane(bookingsTable);        
        JSplitPane tabPanel1Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
        //Where the splitpane is located along the middle of two panes
        tabPanel1Inner.setResizeWeight(.5d); 
        tabPanel1.add(tabPanel1Inner, BorderLayout.CENTER);
    }
     
    

    private void loadManageGuests(JPanel tabPanel2) {
        tabPanel2.setLayout(new BorderLayout());
        
        //Left side of Manage Guests for menu
        JPanel leftPanel2 = new JPanel();
        leftPanel2.setLayout(new BorderLayout());
        
        JPanel leftPanel2btnTOP = new JPanel();
        leftPanel2btnTOP.setLayout(new GridLayout(3,2)); //3 by 2 menu components
        leftPanel2btnCENTER = new JPanel();
        leftPanel2btnCENTER.setLayout(bookingFormCards); //Setting as cards layout
        
        //Adding Top and Bottom Panels into left split Panel
        leftPanel2.add(leftPanel2btnTOP, BorderLayout.NORTH);
        leftPanel2.add(leftPanel2btnCENTER, BorderLayout.CENTER);
        loadBookingForms();
        
        //Adding buttons to left splitpane, North Border
        JLabel northBtnTitle2 = new JLabel("Filter Guest Requests: ");
        String[] strFilterOptions = new String[]{"All Requests", "Housekeeping", "Room Service", "Spa and Massage", "Misc."};
        JComboBox<String> filterOptions = new JComboBox<String>(strFilterOptions);
        
        leftPanel2btnTOP.add(northBtnTitle2);
        leftPanel2btnTOP.add(filterOptions);
        
        //Adding buttons to left splitpane, CENTRAL border for options
        JButton completeBtn = new JButton("Complete Request");
            //Would be ideal to link functionality between left and right split panels.
            
        leftPanel2btnTOP.add(completeBtn);
        
        //Loading the table data
        String[][] myStrList= new String[][] { {"Data1a", "Data1b", "Data1c"}, {"Data2a", "Data2b", "Data2c"}, {"Data3a","Data3b","Data3c"}};
        String[] tableHeadings = new String[] {"Guest", "Room Number", "Request"};
        JTable guestsTable = new JTable(myStrList, tableHeadings){ 
            public boolean editCellAt(int row, int column, java.util.EventObject e) { //Prevents editing of cells in table = https://rb.gy/1qflxh
            return false;
         }
        };
        
        //Adding Table to scrollPane
        JScrollPane sp1 = new JScrollPane(leftPanel2);
        JScrollPane sp2 = new JScrollPane(guestsTable);        
        JSplitPane tabPanel2Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
        //Where the splitpane is located along the middle of two panes
        tabPanel2Inner.setResizeWeight(.5d); 
        tabPanel2.add(tabPanel2Inner, BorderLayout.CENTER);
    }
    
    
    //TODO
    private void loadManageRooms(JPanel tabPanel3) {
        tabPanel3.setLayout(new BorderLayout());
        
        //Panel for Left side of Manage Rooms for menu
        JPanel leftPanel3 = new JPanel();
        leftPanel3.setLayout(new BorderLayout());
        
        JPanel leftPanel3btnTOP = new JPanel();
        leftPanel3btnTOP.setLayout(new GridLayout(3,2));
    }
    
    
    


    @Override
    public void onModelUpdate(Data data) {
        if(!data.loginFlag) {
            this.username.setText("");
            this.password.setText("");
            this.loginMessage.setText("Invalid username or password. Try Again.");
        } else { //SHOW THE MENU PANEL -'reload' all the tables as data has been updated
            this.username.setText("");
            this.password.setText("");
            this.loginMessage.setText("");
            if (data.userMode == 0) //guest
            {
                cards.show(mainPanel, "GUESTMENUPANEL");
//                mainPanel.setBorder(new TitledBorder("Hotel Booking System - MAIN PANEL"));
                mainPanel.setBorder(new TitledBorder("You are logged in as " + data.currentloggeduser));
                
            }
            else if (data.userMode == 1) { //staff
                cards.show(mainPanel, "STAFFMENUPANEL");
                mainPanel.setBorder(new TitledBorder("You are logged in as " + data.currentloggeduser + " (STAFF)"));
                if (data.isBookingFlag()){
                    manageBookingMSG.setText("BOOKING ADDED!\nBooking ID: \nRoom Number: ...., Room Type:.... \nGuest Name: .... Guest ID:.... ");
//                    manageBookingsMSG.setText(data.getmanageBookingMSG = returns a string);
                    data.setBookingFlag(false);
                }

            }
        }
        
        
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


