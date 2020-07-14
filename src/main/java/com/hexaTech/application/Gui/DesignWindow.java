package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.hexaTech.Main;
import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DesignWindow extends JPanel implements MyObserver{

    private final DesignController designController;
    private final DesignPresenter designPresenter;
    private MainGui mainGui;
    private JButton homeButton;
    private JButton addScenarioButton;
    private JButton guideButton;
    private JButton extractBALButton;
    private JButton addBOButton;
    private JButton resetButton;
    private JLabel message;

    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    public DesignWindow(MainGui parent, DesignController designController,DesignPresenter designPresenter, ViewManualController viewManualController,
                        ViewManualPresenter viewManualPresenter) throws IOException {
        this.designController=designController;
        this.designPresenter=designPresenter;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;
        this.mainGui=parent;
        resetButton= new JButton("Reset");
        extractBALButton= new JButton("Extract BAL");
        addBOButton= new JButton("Add BO");
        guideButton= new JButton("Guide");
        addScenarioButton= new JButton("Add Scenario");
        homeButton = new JButton("Home");
        message = new JLabel("Welcome! Please add a gherkin scenario to proceed");
        backupString = "";
        add(message);
        add(homeButton);
        add(addScenarioButton);
        add(addBOButton);
        add(resetButton);
        add(extractBALButton);
        add(guideButton);
        extractBALButton.setEnabled(false);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomePanel().setVisible(true);
                setVisible(false);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              designController.deleteGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
              extractBALButton.setEnabled(false);
              }
        });

        extractBALButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {

                    String s = (String)JOptionPane.showInputDialog("Insert name BAL","Default");

                    if ((s != null) && (s.length() > 0)) {
                        designController.createBAL(s);
                    }
                    else{
                        JOptionPane.showMessageDialog(new JPanel(),
                                "Invalid name",
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        });

        addBOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    String i=chooser.getSelectedFile().getAbsolutePath();
                    try {
                        designController.createBO("Design",i);
                        //designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
                        notifyMeDesign();
                        // String lastDocument=split();
                        // listModel.addElement(aaaa);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        useCaseDesign(3);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        useCaseDesign(4);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }//if_else

            }
        });





        addScenarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    String i=chooser.getSelectedFile().getAbsolutePath();
                    try {
                        designController.addGherkin("Design",i);
                        extractBALButton.setEnabled(true);
                        //designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
                        notifyMeDesign();
                       // String lastDocument=split();
                       // listModel.addElement(aaaa);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        useCaseDesign(1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        useCaseDesign(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }//if_else

            }
        });

        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewManualController.openManualSection("DESIGN:");
                    notifyMeManual();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //JDialog dialog = new JDialog(window, true); // parent, isModal
                //dialog.setVisible(true); // blocks until dialog is closed
                //JFrame popup=new JFrame();
                //JLabel manual=new JLabel(stringManual);
                //dialog.set;
                //popup.setVisible(true);
                JOptionPane.showMessageDialog(parent.getHomeWindow(),
                        stringManual);
                }

        });



    }


    public void useCaseDesign(int i) throws IOException {
        switch (i) {
            case(0):
                designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
                if(notifyMeDoneDesign()){
                   // designController.showDocumentsList();
                    notifyMeDesign();
                   // listModel.addElement(aaaa); //altrimenti element.getText()
                    if(existsBackUpDocument()) {
                        message.setText("Backup restored");
                        extractBALButton.setEnabled(true);
                    }
                }
                break;
            case(1):
                message.setText("Scenario added.");
                JOptionPane.showMessageDialog(this,
                        "Scenario added.");
                break;
            case(2):
                message.setText("The file is not a .scenario or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .scenario or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case(3):
                message.setText("BO added.");
                JOptionPane.showMessageDialog(this,
                        "BO added.");
                break;
            case(4):
                message.setText("The file is not a .json or it doesn't exist. Please retry.");
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
            return true;
        }else if(choice==1){
          designController.deleteGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
          extractBALButton.setEnabled(false);
          // discoverController.clearRepo();
            return false;
        }
        else{
            homeButton.getAction(); ///sistemare
            return existsBackUpDocument(); //sistemare
        }//if_else

//        }else if (choice==2){
//            System.out.println("Please insert Y or N.");
//            return existsBackUpDocument();
//        }//if_else
        //return false;
    }//existsBackupDocument


    public void checkForSavedDocs() throws IOException {
        designController.existsGherkin("." + File.separator + "Design" + File.separator + "BackupGherkin.txt");
        if(notifyMeDoneDesign()){
            if(existsBackUpDocument()) {
                notifyMeDesign();
                message.setText(backupString);
                extractBALButton.setEnabled(true);
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
        backupString = designPresenter.getMessage();
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
