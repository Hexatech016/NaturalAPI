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

    private JTextField txtArea;

    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    private String testMethod;
    private JButton typeButton;
    private String typeName;
    private JComboBox typeList;

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

        testMethod="";
        txtArea=new JTextField();
        txtArea.setText(testMethod);

        String[] types = { "Void", "String", "Integer", "Float", "Boolean", "Complex object" };
        typeList = new JComboBox(types);
        typeList.setSelectedIndex(0);
        typeList.setPreferredSize(new Dimension(200, 100));
        txtArea.setPreferredSize(new Dimension(200, 100));
        //typeList.addActionListener(this);
        typeButton=new JButton("ok tipo");


        backupString = "";
        add(message);
        add(homeButton);
        add(addScenarioButton);
        add(addBOButton);
        add(resetButton);
        add(typeList);
        add(txtArea);
        add(typeButton);


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
                        methodsSuggestions(s);
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
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        designController.createBO("Design", path);
                        notifyMeDesign();
                        viewMessage("SuccessBO");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if(returnVal != JFileChooser.CANCEL_OPTION){
                    try {
                        viewMessage("WrongFileBO");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        designController.addGherkin("Design",path);
                        extractBALButton.setEnabled(true);
                        notifyMeDesign();
                        viewMessage("SuccessGherkin");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else if(returnVal != JFileChooser.CANCEL_OPTION){
                    try {
                        viewMessage("WrongFileGherkin");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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
                JOptionPane.showMessageDialog(parent.getHomeWindow(),
                        stringManual);
                }
        });

        typeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeList = (JComboBox)e.getSource();
                typeName = (String)typeList.getSelectedItem();

            }
        });
    }

    public void viewMessage(String type) throws IOException {
        switch (type) {
            case("SuccessGherkin"):
                message.setText("Scenario added.");
                JOptionPane.showMessageDialog(this,
                        "Scenario added.");
                break;
            case("WrongFileGherkin"):
                message.setText("The file is not a .scenario or it doesn't exist. Please retry.");
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
            return false;
        }else{
            return existsBackUpDocument();
        }//if_else
    }//existsBackupDocument

    private void methodsSuggestions(String nameBAL) throws IOException {
        int sentinel=0, identifier=0;
        message.setText("sono entrato");
        designController.checkIfHasMethod(sentinel);
        while(notifyMeDoneDesign()){
            designController.showMethod(sentinel);
            designController.checkIfHasParameter(sentinel,identifier);
            notifyMeDesign();
            JTextArea tmpMethod=new JTextArea();
            tmpMethod.setText(backupString);
            tmpMethod.setPreferredSize(new Dimension(200, 50));
            add(tmpMethod);
            while(notifyMeDoneDesign()){
                designController.showParameter(sentinel,identifier);
                identifier++;
                designController.checkIfHasParameter(sentinel,identifier);
                notifyMeDesign();
                JTextArea tmpParam=new JTextArea();
                tmpParam.setText(backupString);
                tmpParam.setPreferredSize(new Dimension(200, 500));
                add(tmpParam);
            }//external_while
            sentinel++;
            identifier=0;
            designController.checkIfHasMethod(sentinel);
        }//external_while
        designController.updateBAL(nameBAL);
    }//methodsSuggestions

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

    /*private void methodsSuggestions(String nameBAL) throws IOException {
        int sentinel=0, identifier=0;
        designController.checkIfHasMethod(sentinel);
        while(notifyMeDoneDesign()){
            designController.showMethod(sentinel);
            txtArea.setText(backupString);




                        //designController.alterMethodReturn(sentinel,"void",false,false);

                        //designController.alterMethodReturn(sentinel,getType(choice.toUpperCase()),isAnArray(true),false);




            designController.checkIfHasParameter(sentinel,identifier);
            while(notifyMeDoneDesign()){
                designController.showParameter(sentinel,identifier);
                System.out.println("\t\tDo you want to change parameter type? (Y/N)");
                choice=scanner.nextLine();


                        System.out.println("\t\tPlease choose the correct type: \n\t\tS: string\n\t\tI: integer\n\t\tF: float\n\t\tB: boolean\n\t\tC: complex object");
                        choice=scanner.nextLine();
                        while(!checkAnswer(choice)){
                            System.out.println("\tWrong character. Please retry.");
                            choice=scanner.nextLine();
                        }
                        if(getType(choice.toUpperCase()).equalsIgnoreCase(""))
                            chooseObject(sentinel,identifier);
                        else
                            designController.alterParameterType(sentinel,identifier,getType(choice.toUpperCase()),isAnArray(false),false);
                        break;



                identifier++;
                designController.checkIfHasParameter(sentinel,identifier);
            }//external_while
            sentinel++;
            identifier=0;
            designController.checkIfHasMethod(sentinel);
        }//external_while
        designController.updateBAL(nameBAL);
    }//methodsSuggestions*/










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
