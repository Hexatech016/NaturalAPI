package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;

public class DesignWindow extends JPanel implements MyObserver{

    private MainGui mainGui;
    private DesignController designController;
    private DesignPresenter designPresenter;
    public JButton extractBALButton;
    private JLabel message;
    private JLabel image;
    private JLabel checkScenario;
    private JLabel checkBO;
    public JButton addBOButton;
    private JButton guideButton;
    private JButton addScenarioButton;
    private JButton homeButton;

    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    public DesignWindow(MainGui parent, DesignController designController,DesignPresenter designPresenter, ViewManualController viewManualController,
                        ViewManualPresenter viewManualPresenter) {
        this.mainGui = parent;
        this.designController=designController;
        this.designPresenter=designPresenter;
        this.viewManualPresenter = viewManualPresenter;
        backupString = "";

        initComponents();

        homeButton.addActionListener(e -> {
            parent.getHomePanel().setVisible(true);
            setVisible(false);
            extractBALButton.setEnabled(false);
            checkScenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
            checkBO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
        });

        extractBALButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Insert name BAL","Default");
            try {
                if(name != null) {
                    if (!name.equals("")) {
                        designController.createBAL(name);
                    } else {
                        JOptionPane.showMessageDialog(new JPanel(),
                                "Invalid name",
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            SuggestionsDesign suggestionsDesign = new SuggestionsDesign(mainGui, designController, designPresenter, name);
            suggestionsDesign.openWindow();
        });

        addBOButton.addActionListener(e -> {
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
                    designController.createBO("Design", path);
                    checkBO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
                    viewMessage("SuccessBO");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if(returnVal != JFileChooser.CANCEL_OPTION){
                viewMessage("WrongFileBO");
            }//if_else
        });

        addScenarioButton.addActionListener(e -> {
            JFrame dialog = new JFrame();
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File scenario", "scenario");
            chooser.setFileFilter(filter);
            dialog.getContentPane().add(chooser);
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(false);
            dialog.dispose();
            int returnVal = chooser.showOpenDialog(dialog);
            if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("scenario") ){
                String path = chooser.getSelectedFile().getAbsolutePath();
                try {
                    designController.addGherkin("Design",path);
                    extractBALButton.setEnabled(true);
                    viewMessage("SuccessGherkin");
                    checkScenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
                    extractBALButton.setEnabled(true);
                    addBOButton.setEnabled(true);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if(returnVal != JFileChooser.CANCEL_OPTION){
                viewMessage("WrongFileGherkin");
            }//if_else
        });

        guideButton.addActionListener(e -> {
            try {
                viewManualController.openManualSection("DESIGN:");
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
        addScenarioButton = new javax.swing.JButton();
        addBOButton = new javax.swing.JButton();
        extractBALButton = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        checkScenario = new javax.swing.JLabel();
        checkBO = new javax.swing.JLabel();
        message = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        homeButton.setText("Home");

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        guideButton.setText("Guide");

        addScenarioButton.setBackground(new java.awt.Color(255, 255, 255));
        addScenarioButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addScenarioButton.setText("Add Scenario");

        addBOButton.setBackground(new java.awt.Color(255, 255, 255));
        addBOButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addBOButton.setText("Add BO");

        extractBALButton.setBackground(new java.awt.Color(255, 255, 255));
        extractBALButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        extractBALButton.setText("Generate BAL");

        message.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        message.setText("Please add a gherkin scenario to proceed");

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/design.png"))); // NOI18N

        checkScenario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkScenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png"))); // NOI18N

        checkBO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkBO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png"))); // NOI18N

        extractBALButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(guideButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(extractBALButton, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(checkScenario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(checkBO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(addBOButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(addScenarioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(homeButton)
                                        .addComponent(guideButton))
                                .addGap(18, 18, 18)
                                .addComponent(message)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(checkScenario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addScenarioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(addBOButton)
                                        .addComponent(checkBO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extractBALButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
        );
    }



    public void viewMessage(String type) {
        switch (type) {
            case("SuccessGherkin"):
                message.setText("Scenario added.");
                JOptionPane.showMessageDialog(this,
                        "Scenario added.");
                break;
            case("WrongFileGherkin"):
                message.setText("Not a .scenario or it doesn't exist.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .scenario or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("SuccessBO"):
                message.setText("BO added.");
                JOptionPane.showMessageDialog(this,
                        "BO added.");
                break;
            case("WrongFileBO"):
                message.setText("Not a .json or it doesn't exist.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .json or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public boolean existsBackUpDocument() throws IOException {
        Object[] choices = {"Yes", "No"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "Some documents are already stored. Do you want to load them?\nIf you click no, you'll clear all previous backup documents",
                "Title message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);
        if(choice==0) {
            designController.restoreBackup("Design");
            checkScenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
            return true;
        }else if(choice==1){
          designController.deleteGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
          extractBALButton.setEnabled(false);
            return false;
        }else{
            return existsBackUpDocument();
        }//if_else
    }//existsBackupDocument

    public void checkForSavedDocs() throws IOException {
        designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
        if(notifyMeDoneDesign()){
            if(existsBackUpDocument()) {
                extractBALButton.setEnabled(true);
                addBOButton.setEnabled(true);
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
        return designPresenter.isDone();
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
    public void notifyMeManual() {stringManual=viewManualPresenter.getMessage();
    }
}
