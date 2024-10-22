/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 *
 * @author DeanK
 */
public class MyBookingPanelManager {
    //Components
    public JButton leftPanelBOTTOMbtn;
    public JComboBox<String> myBookingsFilter;
    public JList<String> myBookingsList;
    public JLabel bookingTitle, bookingContents;
    private JButton seeMenubtn, seeRoomDetailsbtn;
    String[] myBookingsListstr = new String[]{"No Bookings"};
    
    public void loadMyBookingsPanel(JPanel myBookingsPanel) {
        myBookingsPanel.setLayout(new BorderLayout());
        
        //Panel for Booking selection on the left
        JPanel leftPanel1 = createLeftPanel();
        //Panel for info display on the right
        JPanel rightPanel1 = createRightPanel();        
        JSplitPane tabPanel4Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel1), new JScrollPane(rightPanel1));
        tabPanel4Inner.setResizeWeight(0.3d);
        myBookingsPanel.add(tabPanel4Inner, BorderLayout.CENTER);      
      
    }
    
    public void addActionListener(ActionListener listener) {
        myBookingsFilter.addActionListener(listener);
        leftPanelBOTTOMbtn.addActionListener(listener);
    }
    
    private JPanel createLeftPanel(){
        JPanel leftPanel1 = new JPanel(new BorderLayout()); 
        
        JPanel leftPanelTopList = new JPanel(new BorderLayout());
        leftPanelBOTTOMbtn = new JButton("View Booking");
        
        //Adding the subcontainers into leftPanel
        leftPanel1.add(leftPanelTopList, BorderLayout.CENTER);
        leftPanel1.add(leftPanelBOTTOMbtn, BorderLayout.SOUTH);
        
        
        //Adding components into the subcontainers (JList)
        String[] strMyBookingsFilter = new String[]{"Active Bookings", "All Bookings"};
        myBookingsFilter = new JComboBox<>(strMyBookingsFilter);
        myBookingsFilter.setActionCommand("myBookingsFilterOptions");
        leftPanelTopList.add(myBookingsFilter, BorderLayout.NORTH);    
        
        myBookingsList = new JList<>(myBookingsListstr);
        leftPanelTopList.add(myBookingsList, BorderLayout.CENTER);
        
        return leftPanel1;
    }
    
    //Panel for bookings information on the right
    private JPanel createRightPanel() {
        JPanel rightPanel1 = new JPanel(new BorderLayout());
        
        //Initialize and add subcontainers for the rightpanel
        JPanel rightPanelTitleTOP = new JPanel();
        JPanel rightPanelBtnsBOTTOM = createRightPanelButtons();
        JPanel rightPanelBtnsBOTTOMHELPER = new JPanel();
        rightPanelTitleTOP.setLayout(new BoxLayout(rightPanelTitleTOP, BoxLayout.Y_AXIS));
        rightPanelBtnsBOTTOM.setLayout(new BoxLayout(rightPanelBtnsBOTTOM, BoxLayout.X_AXIS));
        rightPanelBtnsBOTTOMHELPER.setLayout(new FlowLayout());
        
        //Adding the subcontainers into the RIGHT panel
        rightPanel1.add(rightPanelTitleTOP, BorderLayout.NORTH);
        rightPanel1.add(rightPanelBtnsBOTTOMHELPER, BorderLayout.SOUTH);
        rightPanelBtnsBOTTOMHELPER.add(rightPanelBtnsBOTTOM);
        
        bookingTitle = new JLabel();
        bookingTitle.setFont(new Font("Arial", Font.BOLD, 22));
        bookingTitle.setBorder(BorderFactory.createEmptyBorder(20,20,0,0));
        rightPanelTitleTOP.add(bookingTitle);
        
        bookingContents = new JLabel();
        bookingContents.setFont(new Font("Arial", Font.PLAIN, 16));
        bookingContents.setBorder(BorderFactory.createEmptyBorder(20,20,0,0));
        rightPanelTitleTOP.add(bookingContents);
        
        return rightPanel1;
    }
   
    
    
    
    private JPanel createRightPanelButtons() {
        JPanel rightPanelBtnsBOTTOM = new JPanel();
        rightPanelBtnsBOTTOM.setLayout(new BoxLayout(rightPanelBtnsBOTTOM, BoxLayout.X_AXIS));
        
        
        seeMenubtn = new JButton("See Breakfast Menu");
        seeMenubtn.addActionListener(e -> showMenu());
        
        seeRoomDetailsbtn = new JButton("See Room Details");
        seeRoomDetailsbtn.addActionListener(e -> showRoomDetails());
        
        rightPanelBtnsBOTTOM.add(seeMenubtn);
        rightPanelBtnsBOTTOM.add(seeRoomDetailsbtn);
        
        return rightPanelBtnsBOTTOM;
    }   

    
    private void showMenu() {
        bookingTitle.setText("Breakfast Menu");
        bookingContents.setText("<html>(1)Continental Breakfast<br/>(2)Eggs Benedict<br/>(3)Pancake Stack<br/>(4)Fruit Parfait<br/>(5)Omelette Station<br/>(6)Avocado Toast<br/>(7)Breakfast Burrito</html>");
    }
    
    private void showRoomDetails() {
        bookingTitle.setText("Room Details");
        bookingContents.setText("<html>(1)Standard Rooms:<br/>" +
                "A cozy room with essential amenities, perfect for solo travelers or couples.<br/>" +
                "Features a queen bed and modern furnishings.<br/>" +
                "(2)Deluxe Rooms:<br/>" +
                "Spacious and elegant, with added comforts such as a king bed, seating area,<br/>" +
                "and enhanced in-room amenities. Ideal for a relaxing stay.<br/>" +
                "(3)Suite Rooms:<br/>" +
                "Luxurious and expansive, offering a separate living area, premium bedding,<br/>" +
                "and exclusive services. Perfect for guests seeking extra space and comfort.</html>");
    }
    
    public void viewMyBookingDetails(String[] bookingDetails, int bookingId) {
//        guestName, bookingStatus, roomNumber, requestType, description
        bookingTitle.setText("<html>Booking " + bookingId + "<html/>");
        bookingTitle.setFont(new Font("Arial", Font.BOLD, 22));
        bookingContents.setText("<html>Name: " + bookingDetails[0] + " <br/>Booking Status: " + bookingDetails[1] + " <br/>Room Number: " + bookingDetails[2] + " <br/>Request Type: " + bookingDetails[3] + " <br/>Description: " + bookingDetails[4] + "</html>");
        bookingContents.setFont(new Font("Arial", Font.PLAIN, 16));
    }
    
}
