package com.hexaTech.application.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionsDesign extends JPanel{

    private JComboBox button;
    private JTextField txtArea;
    private String typeName;

    public SuggestionsDesign(){

        String[] types = { "Void", "String", "Integer", "Float", "Boolean", "Complex object" };
        button = new JComboBox(types);
        txtArea = new JTextField();
        button.setSelectedIndex(0);
        button.setPreferredSize(new Dimension(200, 100));
        txtArea.setPreferredSize(new Dimension(200, 100));

        add(button);
        add(txtArea);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox typeList = (JComboBox)e.getSource();
                typeName = (String)typeList.getSelectedItem();
            }
        });
    }

    public JComboBox getButton() {
        return button;
    }

    public void setButton(JComboBox button) {
        this.button = button;
    }

    public JTextField getTxtArea() {
        return txtArea;
    }

    public void setTxtArea(JTextField txtArea) {
        this.txtArea = txtArea;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
