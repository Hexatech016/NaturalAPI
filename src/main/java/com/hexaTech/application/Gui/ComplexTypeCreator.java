package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.design.DesignController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ComplexTypeCreator extends JPanel {
    private MainGui mainGui;
    private JButton backButton;
    private JButton addParameterButton;
    private JButton confirmButton;
    private JTextField objectNameTextField;
    private JLabel objNameLabel;
    private final Box nameBox;
    private DesignController designController;
    private final Box objectsBox;

    private JFrame complexType;

    String name;
    List<String> parameterNames;
    List<String> parameterTypes;

    public ComplexTypeCreator(MainGui parent, DesignController designController) {
        mainGui = parent;
        this.designController = designController;

        objNameLabel = new JLabel("Insert the object's name:    ");
        objectNameTextField = new JTextField("default");
        objectNameTextField.setMaximumSize(new Dimension(200, 25));
        nameBox = Box.createHorizontalBox();
        objectsBox = Box.createVerticalBox();
        backButton = new JButton("Back");
        addParameterButton = new JButton("+");
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
            complexType.validate();
            complexType.repaint();
            confirmButton.setEnabled(true);
        });

        confirmButton.addActionListener(e -> {
            name = objectNameTextField.getText();
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
