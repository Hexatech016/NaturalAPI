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

    private final DesignController designController;
    private final DesignPresenter designPresenter;
    private final JButton extractBALButton;
    private final JButton confirmButton;
    private final JLabel message;
    private final JScrollPane scrollPane;
    private final JPanel test;

    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;
    private String name;
    private List<SuggestionsDesign> suggestions;

    public DesignWindow(MainGui parent, DesignController designController,DesignPresenter designPresenter, ViewManualController viewManualController,
                        ViewManualPresenter viewManualPresenter) {
        this.designController=designController;
        this.designPresenter=designPresenter;
        this.viewManualPresenter = viewManualPresenter;
        suggestions = new ArrayList<>();
        extractBALButton= new JButton("Extract BAL");
        JButton addBOButton = new JButton("Add BO");
        JButton guideButton = new JButton("Guide");
        JButton addScenarioButton = new JButton("Add Scenario");
        JButton homeButton = new JButton("Home");
        JButton addComplexType = new JButton("Add a new complex type");
        test = new JPanel();
        test.setLayout(new BoxLayout(test, BoxLayout.Y_AXIS));
        message = new JLabel("Welcome! Please add a gherkin scenario to proceed");
        String testMethod = "";
        JTextField txtArea = new JTextField();
        txtArea.setText(testMethod);
        scrollPane = new JScrollPane(test, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setMaximumSize(new Dimension(500,350));
        confirmButton=new JButton("Generate BAL");
        confirmButton.setEnabled(false);
        confirmButton.setVisible(false);
        extractBALButton.setEnabled(false);

        backupString = "";
        add(message);
        add(homeButton);
        add(addScenarioButton);
        add(addBOButton);
        add(addComplexType);
        add(extractBALButton);
        add(confirmButton);
        add(guideButton);
        add(scrollPane);
        extractBALButton.setEnabled(false);

        homeButton.addActionListener(e -> {
            parent.getHomePanel().setVisible(true);
            setVisible(false);
            test.removeAll();
            confirmButton.setEnabled(false);
            extractBALButton.setEnabled(false);
            confirmButton.setVisible(false);
            extractBALButton.setVisible(true);

        });

        extractBALButton.addActionListener(e -> {
            try {
                name = JOptionPane.showInputDialog("Insert name BAL","Default");
                if(name != null) {
                    if (!name.equals("")) {
                        designController.createBAL(name);
                        methodsSuggestions();
                        confirmButton.setVisible(true);
                        confirmButton.setEnabled(true);
                        extractBALButton.setVisible(false);
                        extractBALButton.setEnabled(false);
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
                    notifyMeDesign();
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
                    notifyMeDesign();
                    viewMessage("SuccessGherkin");
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

        confirmButton.addActionListener(e -> {
            try {
                designController.updateBAL(name);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        addComplexType.addActionListener(e -> {
            ComplexTypeCreator ctc = new ComplexTypeCreator(parent, this, designController);
            ctc.openWindow();

        });
    }

    public void refreshTypes() {
        for(SuggestionsDesign suggestion : suggestions) {

        }
    }

    public void viewMessage(String type) {
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

    private void methodsSuggestions() {
        int sentinel=0, identifier=0;
        message.setText("sono entrato");
        designController.checkIfHasMethod(sentinel);
        while(notifyMeDoneDesign()){
            designController.showMethod(sentinel);
            designController.checkIfHasParameter(sentinel,identifier);
            notifyMeDesign();
            SuggestionsDesign comboMethod = new SuggestionsDesign(sentinel, -1, designController);
            comboMethod.setNameMethods(backupString);
            suggestions.add(comboMethod);

            JPanel methodPanel = new JPanel();
            methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.Y_AXIS));
            methodPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Suggestion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

            methodPanel.add(comboMethod);
            while(notifyMeDoneDesign()){
                designController.showParameter(sentinel,identifier);
                notifyMeDesign();
                SuggestionsDesign comboParam = new SuggestionsDesign(sentinel, identifier, designController);
                comboParam.setNameMethods(backupString);
                suggestions.add(comboParam);
                methodPanel.add(comboParam);
                identifier++;
                designController.checkIfHasParameter(sentinel,identifier);
            }//external_while
            test.add(methodPanel);
            sentinel++;
            identifier=0;
            designController.checkIfHasMethod(sentinel);
        }//external_while
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
