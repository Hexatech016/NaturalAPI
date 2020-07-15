package com.hexaTech.application.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionsDesign extends JPanel{

    private JComboBox comboType;
    //private JTextField txtArea;
    private String typeName;
    private JLabel nameMethods;

    public SuggestionsDesign(){

        String[] types = { "Void", "String", "Integer", "Float", "Boolean", "Complex object" };
        comboType = new JComboBox(types);
        //txtArea = new JTextField();
        nameMethods = new JLabel();
        comboType.setSelectedIndex(0);
        comboType.setPreferredSize(new Dimension(100, 20));
        //ameMethods.setPreferredSize(new Dimension(200, 50));

        add(nameMethods);
        add(comboType);
        //add(txtArea);

        comboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox typeList = (JComboBox)e.getSource();
                typeName = (String)typeList.getSelectedItem();
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
