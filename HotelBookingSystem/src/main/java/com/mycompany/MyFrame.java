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
    
    //Constructor
    public MyFrame () {
        setSize(640,400);
        setLocationRelativeTo(null);
        setTitle("Hotel Booking System");
        
        //MAIN PANEL
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("MAIN PANEL"));
        CardLayout cards = new CardLayout();
        mainPanel.setLayout(cards);
        
        JLabel label1 = new JLabel("Label1");
        mainPanel.add(label1, "LABEL1");
        
        //LOGIN PANEL
        JPanel loginPanel = new JPanel();
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
        
        
        cards.show(mainPanel, "LOGINPANEL");
        this.add(mainPanel);
        setVisible(true);
        
    }
    
}
