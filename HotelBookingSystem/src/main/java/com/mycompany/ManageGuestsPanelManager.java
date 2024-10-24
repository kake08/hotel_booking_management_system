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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author DeanK
 */
public class ManageGuestsPanelManager {
    
    private JPanel leftPanel2btnCENTER;
    public JComboBox<String> guestsFilterOptions;
    private JLabel ManageGuestMSG;
    public MyTableModel tableModelGuests;
    private JTable guestsTable;
    private CardLayout guestFormCards;
    private int guestFormMode = -1; // Default mode - 1.Complete Request 2.Update Phone 3. Send Note
    private JTextField guestID2txf, oldPhoneNotxf, newPhoneNotxf;
    private JButton updatePhoneBtn;
    public JPanel leftPanel2btnTOP;
    JPanel leftPanel2MessageBOTTOM;
   
    public void addActionListener(ActionListener listener) {
        guestsFilterOptions.addActionListener(listener);
        updatePhoneBtn.addActionListener(listener);
    }
    
    private void init(){
        guestFormCards = new CardLayout();
        guestFormMode = -1;
    }
    
    public void loadManageGuests(JPanel tabPanel2) {
        init();
        tabPanel2.setLayout(new BorderLayout());       
        ////LEFT PANEL
        JPanel leftPanel2 = createLeftPanel();
        loadGuestForms();        
        createLeftPanelButtons();       
        //RIGHT PANEL
        createRightPanelTable();        
        
        //Adding left and right panel to separate scrollPanes, then added to splitpane
        JScrollPane sp1 = new JScrollPane(leftPanel2);
        JScrollPane sp2 = new JScrollPane(guestsTable);        
        JSplitPane tabPanel2Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
        //Where the splitpane is located along the middle of two panes
        tabPanel2Inner.setResizeWeight(.5d); 
        tabPanel2.add(tabPanel2Inner, BorderLayout.CENTER);
    }
    
    private JPanel createLeftPanel() {
        JPanel leftPanel2 = new JPanel();
        leftPanel2.setLayout(new BorderLayout());
        
        leftPanel2btnTOP = new JPanel();
        leftPanel2btnTOP.setLayout(new GridLayout(3,2)); //3 by 2 menu components
        leftPanel2btnCENTER = new JPanel();
        leftPanel2btnCENTER.setLayout(guestFormCards); //Setting as cards layout
        leftPanel2MessageBOTTOM = new JPanel();
        leftPanel2MessageBOTTOM.setLayout(new FlowLayout());
        
        //Adding Top and Center Panels into left split Panel
        leftPanel2.add(leftPanel2btnTOP, BorderLayout.NORTH);
        leftPanel2.add(leftPanel2btnCENTER, BorderLayout.CENTER);
        leftPanel2.add(leftPanel2MessageBOTTOM, BorderLayout.SOUTH);
        
        return leftPanel2;
    }
    
    private void createLeftPanelButtons() {
        //Adding COMBOBOX to left splitpane, North Border
        JLabel northBtnTitle2 = new JLabel("Filter Guest: ");
        String[] strFilterOptions = new String[]{"All Guests", "Active Guests", "Inactive Guests", "Guests with Request"};
        guestsFilterOptions = new JComboBox<String>(strFilterOptions);
        
        leftPanel2btnTOP.add(northBtnTitle2);
        leftPanel2btnTOP.add(guestsFilterOptions);
        
        ManageGuestMSG = new JLabel("");
        leftPanel2MessageBOTTOM.add(ManageGuestMSG);
        
        //Adding buttons to left splitpane, TOP border for options
        JButton completeReq = new JButton("Complete Request");
        addActionListenerCompleteReq(completeReq);
        JButton updatePhone = new JButton("Update Phone");
        addActionListenerUpdatePhone(updatePhone);     
        JButton sendNote = new JButton("Send Note");
        addActionListenerSendNote(sendNote);
        
        leftPanel2btnTOP.add(completeReq);
//        leftPanel2btnTOP.add(updatePhone);
        leftPanel2btnTOP.add(sendNote);
    }
    
    private void addActionListenerCompleteReq(JButton completeReq) {
        completeReq.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guestFormMode == 1) {
                    guestFormCards.show(leftPanel2btnCENTER, "CLEARFORM");
                    guestFormMode = -1;
                    ManageGuestMSG.setText("");
                    return;
                }
                guestFormCards.show(leftPanel2btnCENTER, "COMPLETEREQFORM");
                guestFormMode = 1;
                ManageGuestMSG.setText("");
            }
//            
        });
    }
    
    private void addActionListenerUpdatePhone (JButton updatePhone){
        updatePhone.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guestFormMode == 2){
                    guestFormCards.show(leftPanel2btnCENTER, "CLEARFORM");
                    guestFormMode = -1;
                    ManageGuestMSG.setText("");
                    return;
                }
                guestFormCards.show(leftPanel2btnCENTER, "UPDATEPHONEFORM");
                guestFormMode = 2;
                ManageGuestMSG.setText("");
            }
//            
        });  
    }
    
    private void addActionListenerSendNote(JButton sendNote) {
        sendNote.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guestFormMode == 3) {
                    guestFormCards.show(leftPanel2btnCENTER, "CLEARFORM");
                    guestFormMode = -1;
                    ManageGuestMSG.setText("");
                    return;
                }
                guestFormCards.show(leftPanel2btnCENTER, "SENDNOTEFORM");
                guestFormMode = 3;
                ManageGuestMSG.setText("");
            }
//            
        });
    }
         
    private void createRightPanelTable() {
        //Initializing Table model which reflects Guest records
        tableModelGuests = new MyTableModel();
        guestsTable = new JTable(tableModelGuests){ 
            public boolean editCellAt(int row, int column, java.util.EventObject e) { //Prevents editing of cells in table = https://rb.gy/1qflxh
            return false;
         }
        };
    }
    
    private void loadGuestForms() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;       
        //1.Empty Panel
        createClearForm();       
        //2.Complete Request Panel - initialize components
        createCompleteReqForm(gbc);       
        //3.Update Phone Panel 
        createUpdatePhoneForm(gbc);       
        //4.Send Note Panel
        createSendNodeForm(gbc);   
        guestFormCards.show(leftPanel2btnCENTER, "CLEARFORM");
        
    }
    
    private void createClearForm() {
        JPanel clearForm = new JPanel();
        leftPanel2btnCENTER.add(clearForm, "CLEARFORM");
    }
    
    private void createCompleteReqForm(GridBagConstraints gbc) {
        JPanel completeReqForm = new JPanel();
        completeReqForm.setLayout(new GridBagLayout());
        JLabel label1 = new JLabel("Enter Guest Request Details: ");
        JLabel guestID = new JLabel("Guest ID: ");
        JTextField guestIDtxfGF = new JTextField(10);
        JButton checkRequestsBtn = new JButton("Check Requests"); //Finds requests, creates forms that allows completion of request
        JButton clearBtn1 = new JButton("Clear");
        clearBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestIDtxfGF.setText("");
                ManageGuestMSG.setText("");
            }
        });
        
        //Adding form components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        completeReqForm.add(label1, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        completeReqForm.add(guestID, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        completeReqForm.add(guestIDtxfGF, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        completeReqForm.add(checkRequestsBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        completeReqForm.add(clearBtn1, gbc);
        leftPanel2btnCENTER.add(completeReqForm, "COMPLETEREQFORM");
    }
    
    private void createUpdatePhoneForm(GridBagConstraints gbc) {
        JPanel updatePhoneForm = new JPanel();
        updatePhoneForm.setLayout(new GridBagLayout());
        JLabel label2 = new JLabel("Update Phone Number: ");
        JLabel guestID2 = new JLabel("GuestID: ");
        guestID2txf = new JTextField(10);
        JLabel oldPhoneNo = new JLabel("Old Phone Number: ");
        oldPhoneNotxf = new JTextField(10);
        JLabel newPhoneNo = new JLabel("New Phone Number: ");
        newPhoneNotxf = new JTextField(10);
        updatePhoneBtn = new JButton("Update Phone Number");
        JButton clearBtn2 = new JButton("Clear");
        clearBtn2.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                guestID2txf.setText("");
                oldPhoneNotxf.setText("");
                newPhoneNotxf.setText("");
                ManageGuestMSG.setText("");
            }
        });
        
        //Adding form components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        updatePhoneForm.add(label2, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        updatePhoneForm.add(guestID2, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        updatePhoneForm.add(guestID2txf, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        updatePhoneForm.add(oldPhoneNo, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        updatePhoneForm.add(oldPhoneNotxf, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        updatePhoneForm.add(newPhoneNo, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        updatePhoneForm.add(newPhoneNotxf, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        updatePhoneForm.add(updatePhoneBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        updatePhoneForm.add(clearBtn2, gbc);
        leftPanel2btnCENTER.add(updatePhoneForm, "UPDATEPHONEFORM");
    }
    
    private void createSendNodeForm(GridBagConstraints gbc){
        JPanel sendNoteForm = new JPanel();
        sendNoteForm.setLayout(new GridBagLayout());
        JLabel label3 = new JLabel("Send Note to Guest: ");
        JLabel guestID3 = new JLabel("Guest ID: ");
        JTextField guestID3txf = new JTextField(10);
        JLabel regardBookingID = new JLabel("Related Booking ID: ");
        JTextField regardBookingIDtxf = new JTextField(10);
        JLabel customMessageLbl = new JLabel("Note: ");
        JTextArea customNotetxf = new JTextArea(5,20);
        JScrollPane customNoteSP = new JScrollPane(customNotetxf);
        JButton sendNote = new JButton("Send Note");
        JButton clearBtn3 = new JButton("Clear");
        clearBtn3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                guestID3txf.setText("");
                regardBookingIDtxf.setText("");
                customNotetxf.setText("");      
                ManageGuestMSG.setText("");
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0;
        sendNoteForm.add(label3, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        sendNoteForm.add(guestID3, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        sendNoteForm.add(guestID3txf, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        sendNoteForm.add(regardBookingID, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        sendNoteForm.add(regardBookingIDtxf, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        sendNoteForm.add(customMessageLbl, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridheight = 3; gbc.gridwidth = 2;
        sendNoteForm.add(customNoteSP, gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        sendNoteForm.add(sendNote, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridheight = 1; gbc.gridwidth = 1;
        sendNoteForm.add(clearBtn3, gbc);
        leftPanel2btnCENTER.add(sendNoteForm, "SENDNOTEFORM");
    }
}
