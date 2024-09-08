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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    CardLayout cards;
    
    
    //Constructor
    public MyFrame () {

        init();
        loadStaffLogin();
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
                cards.show(mainPanel, "LOGINPANEL");
            }            
        });
        userOptionComponents.add(button1, gbc);
        
        startPanel.add(userOptionComponents, BorderLayout.CENTER);
        mainPanel.add(startPanel, "STARTPANEL");
       
    }
    
    private void loadStaffLogin () {
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
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(new TitledBorder("MENU PANEL"));
        JButton button2 = new JButton("Button2");       
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(mainPanel, "LOGINPANEL");
            }
            
        });
        menuPanel.add(button2, "BUTTON2");
        mainPanel.add(menuPanel, "MENUPANEL");
    }
}
