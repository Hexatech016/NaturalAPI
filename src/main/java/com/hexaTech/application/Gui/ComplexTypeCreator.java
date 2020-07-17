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
    private JButton confirmButton;
    private JButton addParameterButton;
    private JButton backButton;
    private JLabel objNameLabel;
    private JTextField objectNameTextField;
    private JScrollPane scrollArea;
    private JLabel image;
    private JPanel content;
    private JPanel contentArea;
    SuggestionsDesign parent;
    private JFrame complexType;

    String name;
    List<AuxiliaryComplexType> parameters;

    public ComplexTypeCreator(MainGui maingui, SuggestionsDesign parent, DesignController designController) {
        mainGui = maingui;
        this.parent = parent;
        this.parameters = new ArrayList<>();
        complexType = new JFrame();

        initComponents();
        AuxiliaryComplexType first = new AuxiliaryComplexType();
        contentArea.add(first);
        parameters.add(first);
        complexType.validate();
        complexType.repaint();

        backButton.addActionListener(e -> {
            complexType.dispose();
            parent.suggestion.setEnabled(true);
            parent.suggestion.requestFocus();
        });

        addParameterButton.addActionListener(e -> {
            AuxiliaryComplexType aux = new AuxiliaryComplexType();
            contentArea.add(aux);
            parameters.add(aux);
            complexType.validate();
            complexType.repaint();
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
            this.parent.refreshTypes();
            parent.suggestion.setEnabled(true);
            parent.suggestion.requestFocus();
        });
    }

    public void openWindow() {
        parent.suggestion.setEnabled(false);
        complexType.setLayout(new GridLayout(0, 1));
        complexType.setPreferredSize(new Dimension(470, 430));
        complexType.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.url));
        complexType.setFont(new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,22));
        complexType.pack();
        complexType.setLocationRelativeTo(null);
        complexType.add(this, BorderLayout.CENTER);
        complexType.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        complexType.setTitle("Create a new Complex Type");
        complexType.setVisible(true);

        complexType.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.suggestion.setEnabled(true);
                parent.suggestion.requestFocus();
            }
        });

    }

    private void initComponents() {
        image = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        objNameLabel = new javax.swing.JLabel();
        objectNameTextField = new javax.swing.JTextField();
        addParameterButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        scrollArea = new javax.swing.JScrollPane();
        contentArea = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/designsuggestions.png"))); // NOI18N

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setPreferredSize(new java.awt.Dimension(409, 333));

        contentArea.setBackground(new java.awt.Color(255, 255, 255));
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));

        objNameLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        objNameLabel.setText("Insert the object's name:");

        objectNameTextField.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        objectNameTextField.setText("default");

        addParameterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus.png"))); // NOI18N

        confirmButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        confirmButton.setText("Confirm");

        backButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        backButton.setText("Back");

        scrollArea.setBorder(null);
        scrollArea.getViewport().add(contentArea);

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
                contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollArea, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(objNameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(objectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(addParameterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        contentLayout.setVerticalGroup(
                contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(objNameLabel)
                                        .addComponent(objectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollArea, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(confirmButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backButton))
                                        .addComponent(addParameterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
