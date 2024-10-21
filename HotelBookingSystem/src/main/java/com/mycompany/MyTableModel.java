/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DeanK
 */

//Reference: Assistance from ChatGPT
public class MyTableModel extends DefaultTableModel{
    
    public void updateTableModelData(Vector<Vector<Object>> rowData, Vector<String> columnNames){
        setColumnIdentifiers(columnNames);
        setRowCount(0);
        
        for(Vector<Object> row: rowData) {
            addRow(row);
        }      
        fireTableDataChanged();
    }
}
