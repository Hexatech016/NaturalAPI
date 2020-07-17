package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class AuxiliarySuggestion extends JPanel implements MyObserver {

    DesignController designController;
    DesignPresenter designPresenter;
    int sentinel;
    int identifier;
    String extraTypes;
    private JComboBox comboType;
    private JLabel nameMethods;
    private String typeName;
    private boolean array;
    JCheckBox isArray;
    private Vector<String> types;

    public AuxiliarySuggestion(int sent, int id, DesignController designController, DesignPresenter designPresenter) {
        this.designPresenter = designPresenter;
        this.designController = designController;
        this.sentinel = sent;
        this.identifier = id;

        initComponents();

        comboType.addActionListener(e -> {
            typeName = Objects.requireNonNull(comboType.getSelectedItem()).toString();
            if(identifier == -1)
                designController.alterMethodReturn(sentinel,typeName,array,false);
            else
                designController.alterParameterType(sentinel, identifier, typeName, array, false);
        });

        isArray.addActionListener(e -> {
            array = isArray.isSelected();
            if(identifier == -1)
                designController.alterMethodReturn(sentinel,typeName, array,false);
            else
                designController.alterParameterType(sentinel, identifier, typeName, array, false);
        });
    }

    public void setNameMethods(String name) {
        String[] contents;
        contents = name.split("\n");
        this.nameMethods.setText("<html>" + contents[0] + "<br/>" + contents[1].substring(0,contents[1].lastIndexOf(":")+1) + "</html>");
    }

    public void updateTypes() {
        designController.showObjects();
        notifyMeDesign();
        String[] temp = extraTypes.split("\n");
        for(String type : temp) {
            if (type.contains(": ")) {
                String toAdd = type.replaceFirst("^(.*?): ", "");
                if(!types.contains(toAdd))
                    types.add(toAdd);
            }
        }
    }

    private void initComponents() {

        array = false;
        typeName = "String";
        types = new Vector<>();
        types.add("String");
        types.add("Void");
        types.add("Integer");
        types.add("Float");
        types.add("Boolean");

        isArray = new javax.swing.JCheckBox();
        nameMethods = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox<>(types);

        nameMethods = new JLabel();
        comboType.setSelectedIndex(0);
        comboType.setPreferredSize(new Dimension(100, 20));

        this.setBackground(new java.awt.Color(255, 255, 255));

        isArray.setBackground(new java.awt.Color(255, 255, 255));
        isArray.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        isArray.setText("Array");
        isArray.setSelected(false);

        nameMethods.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        comboType.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(nameMethods)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addComponent(isArray)
                                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(thisLayout.createSequentialGroup()
                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nameMethods)
                                        .addGroup(thisLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(isArray))))
                                .addContainerGap(15, 15))
        );
        this.setVisible(true);
    }

    @Override
    public void notifyMeDiscover() {

    }

    @Override
    public boolean notifyMeDoneDiscover() {
        return false;
    }

    @Override
    public void notifyMeDesign() {
        extraTypes = designPresenter.getMessage();
    }

    @Override
    public boolean notifyMeDoneDesign() {
        return false;
    }

    @Override
    public void notifyMeDevelop() {

    }

    @Override
    public int notifyMeErrorDevelop() {
        return 0;
    }

    @Override
    public boolean notifyMeDoneDevelop() {
        return false;
    }

    @Override
    public void notifyMeManual() {

    }
}