package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;

public class DevelopWindow extends JPanel implements MyObserver{

    private final DevelopController developController;
    private final DevelopPresenter developPresenter;

    private JButton guideButton;
    private JButton extractAPIButton;

    private JButton homeButton;
    private JButton addBALButton;
    private JLabel image;
    private JLabel checkBal;
    private JLabel message;
    private JPanel extractBalPanel;

    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    public DevelopWindow(MainGui parent, DevelopController developController,DevelopPresenter developPresenter, ViewManualController viewManualController,
                         ViewManualPresenter viewManualPresenter) {
        this.developController=developController;
        this.developPresenter=developPresenter;
        this.viewManualPresenter = viewManualPresenter;
        backupString = "";

        initComponents();

        homeButton.addActionListener(e -> {
            parent.getHomePanel().setVisible(true);
            setVisible(false);
            extractAPIButton.setEnabled(false);
            checkBal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
        });

        extractAPIButton.addActionListener(e -> {
            ApiCreation create = new ApiCreation(this, developController);
            parent.getHomeWindow().add(create);
            create.setVisible(true);
            setVisible(false);
        });

        addBALButton.addActionListener(e -> {
            JFrame dialog = new JFrame();
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File json", "json");
            chooser.setFileFilter(filter);
            dialog.getContentPane().add(chooser);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(false);
            dialog.dispose();
            int returnVal = chooser.showOpenDialog(dialog);
            if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("json") ){
                String path = chooser.getSelectedFile().getAbsolutePath();
                try {
                    developController.addBAL("Develop", path);
                    notifyMeDevelop();
                    extractAPIButton.setEnabled(true);
                    checkBal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
                    viewMessage("SuccessBAL");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if(returnVal != JFileChooser.CANCEL_OPTION){
                viewMessage("WrongFileBAL");
            }//if_else
        });



        guideButton.addActionListener(e -> {
            try {
                viewManualController.openManualSection("DEVELOP:");
                notifyMeManual();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(parent.getHomeWindow(),
                    stringManual);
        });

    }

    private void initComponents() {

        homeButton = new javax.swing.JButton();
        guideButton = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        addBALButton = new javax.swing.JButton();
        checkBal = new javax.swing.JLabel();
        extractAPIButton = new javax.swing.JButton();
        extractBalPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        homeButton.setText("Home");

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        guideButton.setText("Guide");

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/develop.png"))); // NOI18N

        message.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        message.setText("Welcome! Please add a BAL to proceed");

        addBALButton.setBackground(new java.awt.Color(255, 255, 255));
        addBALButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addBALButton.setText("Add BAL");

        checkBal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkBal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png"))); // NOI18N

        extractAPIButton.setBackground(new java.awt.Color(255, 255, 255));
        extractAPIButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        extractAPIButton.setText("Extract API");
        extractAPIButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(message, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(guideButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(extractAPIButton, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(checkBal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addBALButton, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(homeButton)
                                        .addComponent(guideButton))
                                .addGap(16, 16, 16)
                                .addComponent(message)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(checkBal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addBALButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extractAPIButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );



    }

    public void viewMessage(String type) {
        switch (type) {
            case("SuccessBAL"):
                message.setText("BAL added.");
                JOptionPane.showMessageDialog(this,
                        "BAL added.");
                break;
            case("WrongFileBAL"):
                message.setText("The file is not a .json or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .json or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("SuccessPLA"):
                message.setText("PLA added.");
                JOptionPane.showMessageDialog(this,
                        "PLA added.");
                break;
            case("WrongFilePLA"):
                message.setText("The file is not a .PLA or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .PLA or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    public boolean existsBackUpBAL() throws IOException {
        Object[] choices = {"Yes", "No"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "A BAL is already stored. Do you want to load it?",
                "Title message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);
        if(choice==0) {
            developController.restoreBAL("Develop");
            checkBal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
            return true;
        }else if(choice==1){
            developController.deleteBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
            return false;
        }
        else{
            return existsBackUpBAL();
        }//if_else
    }

    public void checkForSavedDocs() throws IOException {
        developController.existsBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
        if(notifyMeDoneDevelop()){
            if(existsBackUpBAL()) {
                notifyMeDevelop();
                message.setText(backupString);
                extractAPIButton.setEnabled(true);
            }
        }
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

    }

    @Override
    public boolean notifyMeDoneDesign() {
        return false;
    }

    @Override
    public void notifyMeDevelop() {
        backupString = developPresenter.getMessage();
    }

    @Override
    public int notifyMeErrorDevelop() {
        return 0;
    }

    @Override
    public boolean notifyMeDoneDevelop() {
        return developPresenter.isDone();
    }

    @Override
    public void notifyMeManual() {
        stringManual=viewManualPresenter.getMessage();
    }
}
