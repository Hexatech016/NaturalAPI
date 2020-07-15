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
    private final JButton javaButton;
    private final JButton javascriptButton;
    private final JButton addPLAButton;
    private final JButton guideButton;
    private final JButton extractAPIButton;
    private final JButton backButton;
    private final JLabel message;
    private JPanel extractBalPanel;

    private final ViewManualPresenter viewManualPresenter;

    private String backupString;
    private String stringManual;

    public DevelopWindow(MainGui parent, DevelopController developController,DevelopPresenter developPresenter, ViewManualController viewManualController,
                         ViewManualPresenter viewManualPresenter) {
        this.developController=developController;
        this.developPresenter=developPresenter;
        this.viewManualPresenter = viewManualPresenter;
        javaButton= new JButton("Java");
        javascriptButton = new JButton("Javascript");
        JButton addBALButton = new JButton("Add BAL");
        addPLAButton= new JButton("Add external PLA");
        guideButton= new JButton("Guide");
        extractAPIButton= new JButton("Extract API");
        message = new JLabel("Welcome! Please add a BAL to proceed");
        JButton homeButton = new JButton("Home");
        backButton = new JButton("Back");
        backupString = "";
        add(homeButton);
        add(message);
        add(addBALButton);
        add(guideButton);
        add(extractAPIButton);
        extractAPIButton.setEnabled(false);

        homeButton.addActionListener(e -> {
            parent.getHomePanel().setVisible(true);
            setVisible(false);
        });
        backButton.addActionListener(e -> {
            parent.getHomePanel().setVisible(false);
            extractBalPanel.setVisible(false);
            setVisible(true);
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

        extractAPIButton.addActionListener(e -> {
            extractBalPanel=new JPanel();
            extractBalPanel.add(javaButton);
            extractBalPanel.add(javascriptButton);
            extractBalPanel.add(addPLAButton);
            extractBalPanel.add(guideButton);
            extractBalPanel.add(backButton);
            parent.getHomeWindow().add(extractBalPanel);
            extractBalPanel.setVisible(true);
            parent.getHomePanel().setVisible(false);
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
                    viewMessage("SuccessBAL");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if(returnVal != JFileChooser.CANCEL_OPTION){
                viewMessage("WrongFileBAL");
            }//if_else
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
                    notifyMeDevelop();
                    viewMessage("SuccessPLA");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if(returnVal != JFileChooser.CANCEL_OPTION){
                viewMessage("WrongFilePLA");
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
