package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.design.DesignController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class SuggestionsDesign extends JPanel{

    private final JComboBox comboType;
    private final JLabel nameMethods;
    private String typeName;
    private boolean array;
    private final int sentinel;
    private final int identifier;
    JCheckBox isArray;
    private Vector<String> types;

    public SuggestionsDesign(int sent, int id, DesignController designController){

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

        add(nameMethods);
        add(comboType);
        add(isArray);

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

    // works only with two strings.
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

    }
}
