package com.hexaTech.application.Gui;

import com.google.common.io.Files;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class ApiCreation extends JPanel{

    private JButton javaButton;
    private JButton javascriptButton;
    private JButton addPLAButton;
    private JButton backButton;
    private JButton guideButton;
    private JLabel image;
    private JLabel message;

    DevelopWindow developWindow;
    DevelopController developController;

    public ApiCreation(DevelopWindow back, DevelopController developController) {
        developWindow = back;
        this.developController = developController;

        initComponents();

        backButton.addActionListener(e -> {
            back.setVisible(true);
            setVisible(false);
        });

        javaButton.addActionListener(e -> {
            try {
                developController.refreshPLA("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "java.pla");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                developController.createAPI();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        javascriptButton.addActionListener(e -> {
            try {
                developController.refreshPLA("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "js.pla");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                developController.createAPI();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        addPLAButton.addActionListener(e -> {
            JFrame dialog = new JFrame();
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File pla", "pla");
            chooser.setFileFilter(filter);
            dialog.getContentPane().add(chooser);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(false);
            dialog.dispose();
            int returnVal = chooser.showOpenDialog(dialog);
            if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("pla") ){
                String path = chooser.getSelectedFile().getAbsolutePath();
                try {
                    developController.addPLA("Develop", path);
                    developController.createAPI();
                    back.viewMessage("SuccessPLA");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if(returnVal != JFileChooser.CANCEL_OPTION){
                back.viewMessage("WrongFilePLA");
            }//if_else
        });
    }

    private void initComponents() {
        backButton = new javax.swing.JButton();
        guideButton = new javax.swing.JButton();
        javaButton = new javax.swing.JButton();
        javascriptButton = new javax.swing.JButton();
        addPLAButton = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        message = new javax.swing.JLabel();

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/develop.png"))); // NOI18N
        message.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        message.setText("Choose a language or load a PLA");

        setBackground(new java.awt.Color(255, 255, 255));

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        backButton.setText("Back");

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        guideButton.setText("Guide");

        javaButton.setBackground(new java.awt.Color(255, 255, 255));
        javaButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        javaButton.setText("Java");

        javascriptButton.setBackground(new java.awt.Color(255, 255, 255));
        javascriptButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        javascriptButton.setText("JavaScript");

        addPLAButton.setBackground(new java.awt.Color(255, 255, 255));
        addPLAButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addPLAButton.setText("Add external PLA");

        javax.swing.GroupLayout layoutBal = new javax.swing.GroupLayout(this);
        this.setLayout(layoutBal);
        layoutBal.setHorizontalGroup(
                layoutBal.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layoutBal.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layoutBal.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layoutBal.createSequentialGroup()
                                                .addGroup(layoutBal.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layoutBal.createSequentialGroup()
                                                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(guideButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(addPLAButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(javascriptButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(javaButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())))
        );
        layoutBal.setVerticalGroup(
                layoutBal.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layoutBal.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addGroup(layoutBal.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(backButton)
                                        .addComponent(guideButton))
                                .addGap(11, 11, 11)
                                .addComponent(message)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(javaButton)
                                .addGap(4, 4, 4)
                                .addComponent(javascriptButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addPLAButton)
                                .addContainerGap())
        );
    }
}


