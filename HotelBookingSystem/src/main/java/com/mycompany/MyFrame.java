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

/**
 *
 * @author DeanK
 */
public class MyFrame extends JFrame{
    
    JPanel mainPanel;
    JPanel loginPanel;
    JPanel startPanel;
    JPanel menuPanel;
    CardLayout cards;
    Integer bookingMode; //1:MANAGE BOOKING 2:MANAGE GUEST 3:MANAGE ROOMS
    String strLoginMode;
    
    
    //Constructor
    public MyFrame () {

        init();
        loadLogin();
        loadMenuPanel();
        
        cards.show(mainPanel, "STARTPANEL");
        this.add(mainPanel);
        setVisible(true);
        
    }
     
    //initializes frame -- need to encapsulate? init - main, startup  atc.  - different functions
    private void init() {
        setSize(740,500);
        setLocationRelativeTo(null);
        setTitle("Hotel Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //MAIN PANEL
        mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("MAIN PANEL"));
        cards = new CardLayout();
        mainPanel.setLayout(cards);
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
        JComboBox<String> userComboBox = new JComboBox<String>(comboOptions);
        userOptionComponents.add(userComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JButton button1 = new JButton("Continue");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userComboBox.getSelectedItem().equals("Login as Guest"))
                {
                    menuPanel.setBorder(new TitledBorder("Guest Menu"));
                    loginPanel.setBorder(new TitledBorder("Guest Login"));
                }
                    
                else
                {
                    menuPanel.setBorder(new TitledBorder("Staff Menu"));
                    loginPanel.setBorder(new TitledBorder("Staff Login"));
                }
                    
                cards.show(mainPanel, "LOGINPANEL");
            }            
        });
        userOptionComponents.add(button1, gbc);
        
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
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel labelUser = new JLabel("Username: ");
        loginComponents.add(labelUser, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JTextField text1 = new JTextField(10);
        loginComponents.add(text1, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JLabel labelPW = new JLabel("Password: ");
        loginComponents.add(labelPW, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JPasswordField password = new JPasswordField(10);
        loginComponents.add(password, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
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
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        JButton button1 = new JButton("Log In");       
        button1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "MENUPANEL");
            }            
        });
        loginComponents.add(button1, gbc);
        
        
        loginPanel.add(loginComponents, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGINPANEL");
    }
    
    
    
    private void loadMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());   
               
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
        loadManageBookingsPanel(tabPanel1);
        loadManageGuests(tabPanel2);
        loadManageRooms(tabPanel3);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "LOGINPANEL");
            }
        });
        bottomPanel.add(btnLogout);
        
        menuPanel.add(bottomPanel, BorderLayout.SOUTH);
        menuPanel.add(menuComponents, BorderLayout.CENTER);
        mainPanel.add(menuPanel, "MENUPANEL");
        
    }
    
    
    private void loadManageBookingsPanel(JPanel tabPanel1) {
        
        tabPanel1.setLayout(new BorderLayout());

        //Buttons for manage bookings menu on LEFT
        JPanel leftPanel1 = new JPanel();
        leftPanel1.setLayout(new BorderLayout());
        
        JPanel leftPanel1btnTOP = new JPanel();
        leftPanel1btnTOP.setLayout(new GridLayout(3,2));
        JPanel leftPanel1btnCENTER = new JPanel();
        leftPanel1btnCENTER.setLayout(new GridBagLayout());
        
        //Adding Top and Bottom Panels into left split panel
        leftPanel1.add(leftPanel1btnTOP, BorderLayout.NORTH);
        leftPanel1.add(leftPanel1btnCENTER, BorderLayout.CENTER);

        
        //Adding buttons to left splitpane, North border
        JLabel northBtnTitle = new JLabel("Filter Booking List:");
        String[] strFilterOptions = new String[]{ "All Bookings", "Active Bookings", "Pending Bookings", "Historical Bookings"};
        JComboBox<String> filterOptions = new JComboBox<String>(strFilterOptions);
        
        leftPanel1btnTOP.add(northBtnTitle);
        leftPanel1btnTOP.add(filterOptions);
        
        //Adding buttons to left splitpane, South border
        JButton makeBookingbtn = new JButton("Create Booking");
        makeBookingbtn.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {  
                if (bookingMode != 1) {
                    bookingMode = 1;
                    GridBagConstraints gbc = new GridBagConstraints();
                    JLabel label1 = new JLabel("Enter Booking Details");
                    JLabel guestNameLabel = new JLabel("Guest Name: ");
                    JTextField guestNametxf = new JTextField(10);
                    JLabel numLabel = new JLabel("Phone Number: ");
                    JTextField numTxf = new JTextField(10);
                    JButton addBtn = new JButton("Submit");
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1; gbc.gridwidth = 1; 
                    leftPanel1btnCENTER.add(label1, gbc);
                    gbc.gridx = 0; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
                    leftPanel1btnCENTER.add(guestNameLabel, gbc);
                    gbc.gridx = 1; gbc.gridy = 1; gbc.gridheight = 1; gbc.gridwidth = 1;
                    leftPanel1btnCENTER.add(guestNametxf,gbc);
                    gbc.gridx = 0; gbc.gridy = 2; gbc.gridheight = 1; gbc.gridwidth = 1;
                    leftPanel1btnCENTER.add(numLabel, gbc);
                    gbc.gridx = 1; gbc.gridy = 2; gbc.gridheight = 1; gbc.gridwidth = 1;
                    leftPanel1btnCENTER.add(numTxf, gbc);
                    gbc.gridx = 1; gbc.gridy = 3; gbc.gridheight = 1; gbc.gridwidth = 1;
                    leftPanel1btnCENTER.add(addBtn, gbc);
                    loadMenuPanel();
                }
            }
        });
        JButton checkInBtn = new JButton("Check In");       
        JButton checkOutBtn = new JButton("Check Out");
        JButton cancelBtn = new JButton("Cancel Booking");
        leftPanel1btnTOP.add(makeBookingbtn);
        leftPanel1btnTOP.add(checkInBtn);
        leftPanel1btnTOP.add(checkOutBtn);
        leftPanel1btnTOP.add(cancelBtn);
               
        
        String[][] myStrList= new String[][] { {"Data1a", "Data1b", "Data1c"}, {"Data2a", "Data2b", "Data2c"}, {"Data3a","Data3b","Data3c"}};
        String[] tableHeadings = new String[] {"Booking ID", "Guest", "Room Number"};
        JTable bookingsTable = new JTable(myStrList, tableHeadings){ 
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
    
    //JSplitPane
    //JList
    private void loadManageGuests(JPanel tabPanel2) {
        tabPanel2.setLayout(new BorderLayout());
    }
    
    private void loadManageRooms(JPanel tabPanel3) {
        tabPanel3.setLayout(new BorderLayout());
    }
}
