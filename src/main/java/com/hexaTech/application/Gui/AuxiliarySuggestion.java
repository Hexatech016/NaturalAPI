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
    private final JComboBox comboType;
    private final JLabel nameMethods;
    private String typeName;
    private boolean array;
    JCheckBox isArray;
    private Vector<String> types;

    public AuxiliarySuggestion(int sent, int id, DesignController designController, DesignPresenter designPresenter) {
        this.designPresenter = designPresenter;
        this.designController = designController;
        this.sentinel = sent;
        this.identifier = id;

        array = false;
        typeName = "String";
        types = new Vector<>();
        types.add("String");
        types.add("Void");
        types.add("Integer");
        types.add("Float");
        types.add("Boolean");
        comboType = new JComboBox(types);
        nameMethods = new JLabel();
        comboType.setSelectedIndex(0);
        comboType.setPreferredSize(new Dimension(100, 20));

        isArray = new JCheckBox("Array");
        isArray.setSelected(false);

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

    public String[] getSplitSting(String methodName) {
        String[] contents;
        contents = methodName.split("\n");
        return contents;
    }

    public void setNameMethods(String name) {
        String[] temp = getSplitSting(name);
        this.nameMethods.setText("<html>" + temp[0] + "<br/>" + temp[1] + "</html>");
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

    /*
    private void initComponents() {
        jPanel4 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Suggestions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11))); // NOI18N

        jCheckBox3.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jCheckBox3.setText("Array");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Method name: chooseVideo");

        jComboBox3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Void", "String", "Integer", "Float", "Boolean", "Complex object" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addComponent(jCheckBox3)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jCheckBox3))))
                                .addContainerGap(106, Short.MAX_VALUE))
        );
    }
     */

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