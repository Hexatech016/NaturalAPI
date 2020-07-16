package com.hexaTech.application.Gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AuxiliaryComplexType extends JPanel{

    public JComboBox typesComboBox;
    public JCheckBox isArray;
    public JTextField parameterNameTextField;
    private Box paramNameBox;
    private Box paramTypeBox;

    private JLabel paramNameLabel;
    private JLabel paramTypeLabel;

    public AuxiliaryComplexType() {
        paramNameLabel = new JLabel("Insert the parameter's name:    ");
        paramTypeLabel = new JLabel("Select the parameter's type:    ");
        parameterNameTextField = new JTextField("default");
        paramNameBox = Box.createHorizontalBox();
        paramTypeBox = Box.createHorizontalBox();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        parameterNameTextField.setMaximumSize(new Dimension(200, 25));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));


        String[] types = { "String", "Integer", "Float", "Boolean" };
        typesComboBox = new JComboBox(types);
        typesComboBox.setSelectedIndex(0);
        isArray = new JCheckBox("Array");
        isArray.setSelected(false);
        typesComboBox.setMaximumSize(new Dimension(200, 25));

        paramNameBox.add(paramNameLabel);
        paramNameBox.add(parameterNameTextField);
        paramTypeBox.add(paramTypeLabel);
        paramTypeBox.add(typesComboBox);
        paramTypeBox.add(isArray);
        add(paramNameBox);
        add(paramTypeBox);
    }
}
