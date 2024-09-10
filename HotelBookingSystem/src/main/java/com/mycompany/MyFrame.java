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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
     
    //initializes frame
    private void init() {
        setSize(640,400);
        setLocationRelativeTo(null);
        setTitle("Hotel Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //MAIN PANEL
        mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("MAIN PANEL"));
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
//        loginPanel.add(button1, BorderLayout.SOUTH);
        
        
        loginPanel.add(loginComponents, BorderLayout.CENTER);
//        loginPanel.add(loginComponents, BorderLayout.CENTER);
        mainPanel.add(loginPanel, "LOGINPANEL");
    }
    
    private void loadMenuPanel() {
        //MAINMENU PANEL
        menuPanel = new JPanel();
//        menuPanel.setBorder(new TitledBorder("MENU PANEL"));
        menuPanel.setLayout(new BorderLayout());   
        
        
        
        
        
        JTabbedPane menuComponents = new JTabbedPane();
        JPanel tabPanel1 = new JPanel();
        tabPanel1.setLayout(new BorderLayout());
        //tabPanel1.setLayout(new GridLayout(1, 2));
        //Buttons for manage bookings menu
        JPanel leftPanel1 = new JPanel();
        leftPanel1.setLayout(new BorderLayout());
        
        JPanel leftPanel1btnTOP = new JPanel();
        leftPanel1btnTOP.setLayout(new GridLayout(5,1));
        JPanel leftPanel1btnBOTTOM = new JPanel();
        leftPanel1btnBOTTOM.setLayout(new GridLayout(3,1));
        
        //Adding Top and Bottom Panels into left split panel
        leftPanel1.add(leftPanel1btnTOP, BorderLayout.NORTH);

        leftPanel1.add(leftPanel1btnBOTTOM, BorderLayout.SOUTH);
        
        //Adding buttons to left splitpane, North border
        JLabel northBtnTitle = new JLabel("Filter Booking List:");
        JButton viewAllBtn = new JButton("All Bookings");
        JButton viewCurrentBtn = new JButton("Active Bookings");
        JButton viewPendingBtn = new JButton("Pending Bookings"); //Bookings that are to be checked in
        JButton viewOldBtn = new JButton("Historical Bookings");
        leftPanel1btnTOP.add(northBtnTitle);
        leftPanel1btnTOP.add(viewAllBtn);
        leftPanel1btnTOP.add(viewCurrentBtn);
        leftPanel1btnTOP.add(viewPendingBtn);
        leftPanel1btnTOP.add(viewOldBtn);
        
        //Adding buttons to left splitpane, South border
        JButton checkInBtn = new JButton("Check In");
        JButton checkOutBtn = new JButton("Check Out");
        JButton cancelBtn = new JButton("Cancel Booking");
        leftPanel1btnBOTTOM.add(checkInBtn);
        leftPanel1btnBOTTOM.add(checkOutBtn);
        leftPanel1btnBOTTOM.add(cancelBtn);
        
        
        String[][] myStrList= new String[][] { {"Data1a", "Data1b", "Data1c"}, {"Data2a", "Data2b", "Data2c"}, {"Data3a","Data3b","Data3c"}};
        String[] tableHeadings = new String[] {"A", "B", "C"};
        JTable bookingsTable = new JTable(myStrList, tableHeadings){ 
            public boolean editCellAt(int row, int column, java.util.EventObject e) { //Prevents editing of cells in table = https://rb.gy/1qflxh
            return false;
         }
        };
        
        //Adding Table to scrollPane
        JScrollPane sp = new JScrollPane(bookingsTable);
        
        JSplitPane tabPanel1Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel1, sp);
        tabPanel1.add(tabPanel1Inner, BorderLayout.CENTER);
        
        
        
        JPanel tabPanel2 = new JPanel();
        tabPanel2.setLayout(new BorderLayout());
        
        JPanel tabPanel3 = new JPanel();
        tabPanel3.setLayout(new BorderLayout());
                
        menuComponents.add("Manage Bookings", tabPanel1);
        menuComponents.add("Manage Guests", tabPanel2);
        menuComponents.add("Manage Rooms", tabPanel3);
        loadManageBookings(tabPanel1);
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
    
    
    //JSplitPane
    //JList
    private void loadManageBookings(JPanel bookingsPanel) {
        
    }
    
    private void loadManageGuests(JPanel guestsPanel) {
        
    }
    
    private void loadManageRooms(JPanel roomsPanel) {
        
    }
}
