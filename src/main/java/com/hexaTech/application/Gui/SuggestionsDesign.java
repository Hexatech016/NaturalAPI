package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.design.DesignController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionsDesign extends JPanel{

    private JComboBox comboType;
    private JLabel nameMethods;
    private String typeName;
    private boolean array;
    private int sentinel;
    private int identifier;
    private DesignController designController;
    JCheckBox isArray;

    public SuggestionsDesign(int sent, int id, DesignController designController){

        this.sentinel = sent;
        this.identifier = id;
        this.designController = designController;
        array = false;
        typeName = "String";

        String[] types = { "String", "Void", "Integer", "Float", "Boolean", "Complex object" };
        comboType = new JComboBox(types);
        nameMethods = new JLabel();
        comboType.setSelectedIndex(0);
        comboType.setPreferredSize(new Dimension(100, 20));
        //ameMethods.setPreferredSize(new Dimension(200, 50));

        isArray = new JCheckBox("Array");
        isArray.setSelected(false);

        add(nameMethods);
        add(comboType);
        add(isArray);

        comboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeName = comboType.getSelectedItem().toString();
                if(identifier == -1)
                    designController.alterMethodReturn(sentinel,typeName,array,false);
                else
                    designController.alterParameterType(sentinel, identifier, typeName, array, false);
            }
        });

        isArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = isArray.isSelected();
                if(identifier == -1)
                     designController.alterMethodReturn(sentinel,typeName, array,false);
                else
                    designController.alterParameterType(sentinel, identifier, typeName, array, false);
            }
        });
    }

    public JComboBox getButton() {
        return comboType;
    }

    public void setButton(JComboBox button) {
        this.comboType = button;
    }

//    public JTextField getTxtArea() {
//        return txtArea;
//    }
//
//    public void setTxtArea(JTextField txtArea) {
//        this.txtArea = txtArea;
//    }

    // works only with two strings.
    public String[] getSplitSting(String methodName) {
        String[] contents;
        contents = methodName.split("\n");
        return contents;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public JLabel getNameMethods() {
        return nameMethods;
    }

    public void setNameMethods(String name) {
        String[] temp = getSplitSting(name);
        this.nameMethods.setText("<html>" + temp[0] + "<br/>" + temp[1] + "</html>");
    }
}
