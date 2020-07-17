package com.hexaTech.application.Gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AuxiliaryComplexType extends JPanel{

    public JComboBox typesComboBox;
    public JCheckBox isArray;
    public JTextField parameterNameTextField;

    private JLabel paramNameLabel;
    private JLabel paramTypeLabel;

    public AuxiliaryComplexType() {
        initComponents();
    }

    private void initComponents() {
        String[] types = { "String", "Integer", "Float", "Boolean" };

        paramNameLabel = new javax.swing.JLabel();
        paramTypeLabel = new javax.swing.JLabel();
        parameterNameTextField = new javax.swing.JTextField();
        typesComboBox = new javax.swing.JComboBox<>(types);
        isArray = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));

        this.setBackground(new java.awt.Color(255, 255, 255));
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        paramNameLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        paramNameLabel.setText("Insert the parameter's name:");

        paramTypeLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        paramTypeLabel.setText("Select the parameter's type:");

        parameterNameTextField.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        parameterNameTextField.setText("default");

        typesComboBox.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        typesComboBox.setSelectedIndex(0);

        isArray.setBackground(new java.awt.Color(255, 255, 255));
        isArray.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        isArray.setText("Array");
        isArray.setSelected(false);

        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(paramTypeLabel)
                                        .addComponent(paramNameLabel))
                                .addGap(18, 18, 18)
                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(typesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(parameterNameTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(isArray)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(paramNameLabel)
                                        .addComponent(parameterNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(paramTypeLabel)
                                        .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(typesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(isArray)))
                                .addContainerGap(15, 15))
        );
    }
}
