package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.design.DesignController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComplexTypeCreator extends JPanel {
    private final MainGui mainGui;
    private final JButton confirmButton;
    private final JTextField objectNameTextField;
    private final Box objectsBox;

    private JFrame complexType;

    String name;
    List<AuxiliaryComplexType> parameters;

    public ComplexTypeCreator(MainGui maingui, DesignController designController) {
        mainGui = maingui;
        this.parameters = new ArrayList<>();

        JLabel objNameLabel = new JLabel("Insert the object's name:    ");
        objectNameTextField = new JTextField("default");
        objectNameTextField.setMaximumSize(new Dimension(200, 25));
        Box nameBox = Box.createHorizontalBox();
        objectsBox = Box.createVerticalBox();
        JButton backButton = new JButton("Back");
        JButton addParameterButton = new JButton("+");
        confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(false);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(backButton);
        nameBox.add(objNameLabel);
        nameBox.add(objectNameTextField);
        add(nameBox);
        add(objectsBox);
        add(addParameterButton);
        add(confirmButton);

        backButton.addActionListener(e -> {
            complexType.dispose();
        });

        addParameterButton.addActionListener(e -> {
            AuxiliaryComplexType aux = new AuxiliaryComplexType();
            objectsBox.add(aux);
            parameters.add(aux);
            complexType.validate();
            complexType.repaint();
            confirmButton.setEnabled(true);
        });

        confirmButton.addActionListener(e -> {
            name = objectNameTextField.getText();
            for(AuxiliaryComplexType parameter : parameters)
            {
                if(parameter.isArray.isSelected())
                    designController.addStructure(name,parameter.parameterNameTextField.getText(),Objects.requireNonNull(parameter.typesComboBox.getSelectedItem()).toString()+"[]");
                else
                    designController.addStructure(name,parameter.parameterNameTextField.getText(),Objects.requireNonNull(parameter.typesComboBox.getSelectedItem()).toString());
            }
            JOptionPane.showMessageDialog(this,
                    "Complex Type added.");
            complexType.dispose();
            //parent.refreshTypes();
        });
    }

    public void openWindow() {
        complexType = new JFrame();
        complexType.setLayout(new GridLayout(0, 1));
        complexType.setPreferredSize(new Dimension(600, 400));
        complexType.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.url));
        complexType.setFont(new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,22));
        complexType.pack();
        complexType.setLocationRelativeTo(null);
        complexType.add(this, BorderLayout.CENTER);
        complexType.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        complexType.setTitle("Create a new Complex Type");
        complexType.setVisible(true);
    }
}
