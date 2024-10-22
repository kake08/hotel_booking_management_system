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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author DeanK
 */
public class ManageRoomsPanelManager {
    public JPanel leftPanel3btnCENTER;
    public JPanel leftPanel3MessageBOTTOM;
    public CardLayout roomFormCards;
    public JLabel manageRoomMSG;
    public JComboBox<String> roomsFilterOptions;
    public MyTableModel tableModelRooms;
    public JTable roomsTable;

    // Declare form components here
    public JTextField roomNumbertxfRF;
    public JTextField roomNumbertxfRF2;
    public JButton cleanRoomBtn, setOOOBtn, setRoomStatusBtn;
    private JLabel OOOLbl2;
    public JRadioButton availableRB, OOORBtn;
    private Integer roomFormMode; //1.Clean Room 2.Out Of Order
    
    public ManageRoomsPanelManager() {
        roomFormCards = new CardLayout();
        roomFormMode = -1;
    }
    
    public void addActionListener(ActionListener listener) {
        roomsFilterOptions.addActionListener(listener);
        cleanRoomBtn.addActionListener(listener);
        setOOOBtn.addActionListener(listener);
        setRoomStatusBtn.addActionListener(listener);
    }
    
    public void loadManageRooms(JPanel tabPanel3) {
        tabPanel3.setLayout(new BorderLayout());
        
        //Panel for Left side of Manage Rooms for menu
        JPanel leftPanel3 = new JPanel();
        leftPanel3.setLayout(new BorderLayout());
        
        JPanel leftPanel3btnTOP = new JPanel();
        leftPanel3btnTOP.setLayout(new GridLayout(2,2));
        leftPanel3btnCENTER = new JPanel();
        leftPanel3btnCENTER.setLayout(roomFormCards); 
        leftPanel3MessageBOTTOM = new JPanel();
        leftPanel3MessageBOTTOM.setLayout(new FlowLayout());
        
        
        //Adding Top, Center, bottom panels to left split pane
        leftPanel3.add(leftPanel3btnTOP, BorderLayout.NORTH);
        leftPanel3.add(leftPanel3btnCENTER, BorderLayout.CENTER);
        leftPanel3.add(leftPanel3MessageBOTTOM, BorderLayout.SOUTH);
        loadRoomForms();
        
        //Adding JCOMBOBOX to left splitpane, North border
        JLabel northBtnTitle3 = new JLabel("Filter Rooms: ");
        String[] strFilterOptions = new String[]{"All Rooms", "Available Rooms", "N/A Rooms", 
            "N/A Rooms - Occupied", "N/A Rooms - Booked", "N/A Rooms - Need Cleaning"};
        roomsFilterOptions = new JComboBox<String>(strFilterOptions);
        
        leftPanel3btnTOP.add(northBtnTitle3);
        leftPanel3btnTOP.add(roomsFilterOptions);
               
        //Adding buttons to left splitpane, TOP border for options
        JButton cleanRoombtn = new JButton("Clean Room");
        cleanRoombtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roomFormMode == 1) {
                    roomFormCards.show(leftPanel3btnCENTER, "CLEARFORM");
                    manageRoomMSG.setText("");
                    roomFormMode = -1;
                    return;
                }
                roomFormCards.show(leftPanel3btnCENTER, "CLEANROOMFORM");
                manageRoomMSG.setText("");
                roomFormMode = 1;
            }
        });
        
        JButton OOObtn = new JButton("Out of Order");
        OOObtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roomFormMode == 2) {
                    roomFormCards.show(leftPanel3btnCENTER, "CLEARFORM");
                    manageRoomMSG.setText("");
                    roomFormMode = -1;
                    return;
                }
                roomFormCards.show(leftPanel3btnCENTER, "OOOFORM");
                manageRoomMSG.setText("");
                roomFormMode = 2;
            }
        });
        
        leftPanel3btnTOP.add(cleanRoombtn);
        leftPanel3btnTOP.add(OOObtn);
        
        // Adding label for user feedback to left split pane, bottom border
        manageRoomMSG = new JLabel("");
        leftPanel3MessageBOTTOM.add(manageRoomMSG);
        
        //Initializing table model which reflects booking records
            tableModelRooms = new MyTableModel();
            roomsTable = new JTable(tableModelRooms){
            public boolean editCellAt(int row, int column, java.util.EventObject e) { //Prevents editing of cells in table = https://rb.gy/1qflxh
            return false;
         }
        };
        
        
        //Adding Table to scrollPane
        JScrollPane sp1 = new JScrollPane(leftPanel3);
        JScrollPane sp2 = new JScrollPane(roomsTable);        
        JSplitPane tabPanel3Inner = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);
        //Where the splitpane is located along the middle of two panes
        tabPanel3Inner.setResizeWeight(.5d); 
        tabPanel3.add(tabPanel3Inner, BorderLayout.CENTER);
    }
    
    
    private void loadRoomForms() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        
        //1.Empty Panel
        JPanel clearForm = new JPanel();
        leftPanel3btnCENTER.add(clearForm, "CLEARFORM");
        
        //2.Clean Room form
        JPanel cleanRoomForm = new JPanel();
        cleanRoomForm.setLayout(new GridBagLayout());
        JLabel cleanRoomLbl = new JLabel("Set Room as Cleaned: ");
        JLabel roomNumber = new JLabel("Room Number: "); // must be roomstatus 3
        roomNumbertxfRF = new JTextField(10);
        cleanRoomBtn = new JButton("Clean Room");
        
        JButton clearBtnRF1 = new JButton("Clear");
        clearBtnRF1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear the message line and textfields.
                roomNumbertxfRF.setText("");
                manageRoomMSG.setText("");
            }
        });
       
        
        //Adding Form components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        cleanRoomForm.add(cleanRoomLbl, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        cleanRoomForm.add(roomNumber, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        cleanRoomForm.add(roomNumbertxfRF, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        cleanRoomForm.add(cleanRoomBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        cleanRoomForm.add(clearBtnRF1, gbc);
        leftPanel3btnCENTER.add(cleanRoomForm, "CLEANROOMFORM");
        
        //3. Out of Order Form
        JPanel OOOForm = new JPanel();
        OOOForm.setLayout(new GridBagLayout());
        JLabel OOOLbl = new JLabel("Update Room Status: ");
        JLabel roomNumberLbl = new JLabel("Room Number: "); //must be roomstatus 3 or 0;
        roomNumbertxfRF2 = new JTextField(10);
        setOOOBtn = new JButton("Find Room");
        
        JButton clearBtnRF2 = new JButton("Clear");
        clearBtnRF2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear the message line and textfields.
                roomNumbertxfRF2.setText("");
                manageRoomMSG.setText("");
                
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0;
        OOOForm.add(OOOLbl, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        OOOForm.add(roomNumberLbl, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        OOOForm.add(roomNumbertxfRF2, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        OOOForm.add(setOOOBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        OOOForm.add(clearBtnRF2, gbc);
        leftPanel3btnCENTER.add(OOOForm, "OOOFORM");
        
        JPanel OOOForm2 = new JPanel();
        OOOForm2.setLayout(new GridBagLayout());
        OOOLbl2 = new JLabel("Select Status for Room __ : ");
        availableRB = new JRadioButton("Available");
        OOORBtn = new JRadioButton("Out of Order");
            ButtonGroup rbGroup = new ButtonGroup();
            rbGroup.add(availableRB); rbGroup.add(OOORBtn);
        
        setRoomStatusBtn = new JButton("Set Room Status");
        JButton cancelsetRoomStatusBtn = new JButton("Cancel");
        cancelsetRoomStatusBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {               
                roomFormCards.show(leftPanel3btnCENTER, "OOOFORM");
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0;
        OOOForm2.add(OOOLbl2, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        OOOForm2.add(availableRB, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        OOOForm2.add(OOORBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        OOOForm2.add(setRoomStatusBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        OOOForm2.add(cancelsetRoomStatusBtn, gbc);
        leftPanel3btnCENTER.add(OOOForm2, "OOOFORM2");
                
        roomFormCards.show(leftPanel3btnCENTER, "CLEARFORM");
        
    }
    
    public void cleanRoomFeedbackMSG(int output) {
        if (output == -1) {
            manageRoomMSG.setText("Please enter a valid Room Number that needs cleaning");           
        } else if (output == 0) {
            manageRoomMSG.setText("Success! Room has been set as cleaned");
            //success
        } else if (output == 1) {
            manageRoomMSG.setText("Room does not need cleaning/Does Not Exist");
        }
    }
    
        public void OOORoomFeedbackMSG(int output) {
//         System.out.println("OUTPUT: " +  output);
//         -1 is invalid; 1 is updated!! message
//          0 is available, 4 is out of order
//          other outputs --> -1
        if (output == -1){
            manageRoomMSG.setText("Invalid Room Number - Must be an Available Room");
            roomFormCards.show(leftPanel3btnCENTER, "OOOFORM");
            roomNumbertxfRF2.setText("");
            //invalid roomnumber;
        } else if (output == 1) {
            manageRoomMSG.setText("Updated successfully!");
            roomNumbertxfRF2.setText("");
            roomFormCards.show(leftPanel3btnCENTER, "OOOFORM");
            //updated successfully
        } else if (0 == output) { // 0 = available status
            manageRoomMSG.setText("");
            OOOLbl2.setText("Select Status for Room " + roomNumbertxfRF2.getText() + ": ");
            roomFormCards.show(leftPanel3btnCENTER, "OOOFORM2");
            availableRB.setSelected(true);
            //show the OOOROOMFORM2 with radiobutton on available
        } else if (4 == output) { // 4 = Out of order status
            OOOLbl2.setText("Select Status for Room " + roomNumbertxfRF2.getText() + ": ");
            manageRoomMSG.setText("");
            roomFormCards.show(leftPanel3btnCENTER, "OOOFORM2");
            OOORBtn.setSelected(true);
            //show the OOOROOMFORM with radiobutton on Out of Order
        }
    }
}
