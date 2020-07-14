package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

public class DevelopWindow extends JPanel implements MyObserver{

    private final DevelopController developController;
    private final DevelopPresenter developPresenter;
    private MainGui mainGui;
    private JButton homeButton;
    private JButton javaButton;
    private JButton javascriptButton;
    private JButton addBALButton;
    private JButton addPLAButton;
    private JButton guideButton;
    private JButton extractAPIButton;
    private JButton backButton;
    private JLabel message;
    private JPanel extractBalPanel;

    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    public DevelopWindow(MainGui parent, DevelopController developController,DevelopPresenter developPresenter, ViewManualController viewManualController,
                         ViewManualPresenter viewManualPresenter) throws IOException {
        this.developController=developController;
        this.developPresenter=developPresenter;
        this.mainGui=parent;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;
        javaButton= new JButton("Java");
        javascriptButton = new JButton("Javascript");
        addBALButton= new JButton("Add BAL");
        addPLAButton= new JButton("Add external PLA");
        guideButton= new JButton("Guide");
        extractAPIButton= new JButton("Extract API");
        message = new JLabel("Welcome! Please add a gherkin scenario to proceed");
        homeButton = new JButton("Home");
        backButton = new JButton("Back");
        backupString = "";
        add(homeButton);
        add(message);
        add(addBALButton);
        add(guideButton);
        add(extractAPIButton);
        extractAPIButton.setEnabled(false);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomePanel().setVisible(true);
                setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomePanel().setVisible(false);
                extractBalPanel.setVisible(false);
                setVisible(true);
            }
        });

        javaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        javascriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        extractAPIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractBalPanel=new JPanel();
                extractBalPanel.add(javaButton);
                extractBalPanel.add(javascriptButton);
                extractBalPanel.add(addPLAButton);
                extractBalPanel.add(guideButton);
                //extractBalPanel.add(homeButton);
                extractBalPanel.add(backButton);
                parent.getHomeWindow().add(extractBalPanel);
                extractBalPanel.setVisible(true);
                parent.getHomePanel().setVisible(false);
                setVisible(false);
            }
        });

        addBALButton.addActionListener(new ActionListener() {
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
                        developController.addBAL("Develop",i);
                        notifyMeDevelop();
                        extractAPIButton.setEnabled(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        useCaseDevelop(1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        useCaseDevelop(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }//if_else

            }
        });

        addPLAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    String i=chooser.getSelectedFile().getAbsolutePath();
                    try {
                        developController.addPLA("Develop",i);
                        developController.createAPI();
                        notifyMeDevelop();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        useCaseDevelop(3);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        useCaseDevelop(4);
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
                    viewManualController.openManualSection("DEVELOP:");
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


    public void useCaseDevelop(int i) throws IOException {
        switch (i) {
            case(0):
                developController.existsBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
                if(notifyMeDoneDevelop()){
                    notifyMeDevelop();
                    if(existsBackUpBAL()) {
                        message.setText("Backup restored");
                        extractAPIButton.setEnabled(true);
                    }
                }
                break;
            case(1):
                message.setText("BAL added.");
                JOptionPane.showMessageDialog(this,
                        "BAL added.");
                break;
            case(2):
                message.setText("The file is not a .json or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .json or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case(3):
                message.setText("PLA added.");
                JOptionPane.showMessageDialog(this,
                        "PLA added.");
                break;
            case(4):
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
            return true;
        }else if(choice==1){
            developController.deleteBAL("." + File.separator + "Develop" + File.separator + "BackupBAL.txt");
            return false;
        }
        else{
            homeButton.getAction(); ///sistemare
            return existsBackUpBAL(); //sistemare
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
