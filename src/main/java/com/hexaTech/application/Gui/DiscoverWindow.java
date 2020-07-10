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

public class DiscoverWindow extends JPanel {

    private JButton back;
    private JButton next;
    private JButton loadDocButton;
    private JButton backButton;
    private JButton extractBDLButton;
    private JLabel message;
    private JLabel loadedText;
    private JLabel loadedDocuments;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private ExtractBDL extractBDL;
    private MainGui mainGui;

    //private JFileChooser insertDocs;

    public DiscoverWindow(MainGui parent, DiscoverController discoverController,DiscoverPresenter discoverPresenter) throws IOException {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.mainGui=parent;
        extractBDL=new ExtractBDL(this, discoverController, discoverPresenter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        message = new JLabel();
        loadedText = new JLabel();
        loadedDocuments = new JLabel();
        loadDocButton = new JButton("Load document");
        backButton = new JButton("Back");
        extractBDLButton = new JButton("Extract BDL");
        loadedText.setText("Stored documents:");
        add(backButton);
        add(message);
        add(loadDocButton);
        add(extractBDLButton);
        add(loadedText);
        add(loadedDocuments);
        extractBDLButton.setEnabled(false);

        //getMessage(0);

        loadDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dialog = new JFrame();
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("File Txt", "txt");
                chooser.setFileFilter(filter);
                dialog.getContentPane().add(chooser);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(false);
                dialog.dispose();
                int returnVal = chooser.showOpenDialog(dialog);
                if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("txt") ){
                    String i=chooser.getSelectedFile().getAbsolutePath();
                    try {
                        discoverController.addTextDoc("Discover",i);
                        extractBDLButton.setEnabled(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        getMessage(1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        getMessage(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }//if_else

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomePanel().setVisible(true);
                setVisible(false);

            }
        });

        extractBDLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomeWindow().add(extractBDL);
                extractBDL.setVisible(true);
                setVisible(false);

            }
        });

    }

    public void getMessage(int i) throws IOException {
        switch (i) {
            case(0):
                discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
                if(notifyMeDoneDiscover()){
                    discoverController.showBackup("Discover");
                    loadedDocuments.setText(loadedDocuments.getText() + "\nDio bambino");
                    if(existsBackUpDocument())
                        message.setText("Backup restored");
                        extractBDLButton.setEnabled(true);
                }else {
                    message.setText("There are no saved documents");
                }
                break;
            case(1):
                message.setText("Document added.");
                break;
            case(2):
                message.setText("The file is not a .txt or it doesn't exist. Please retry.");
                break;
        }

    }


    public boolean existsBackUpDocument() throws IOException {
        int choice = JOptionPane.showConfirmDialog(null,
                "Some documents are already stored. Do you want to load them?\nIf you type no or exit, you'll clear all previous backup documents");
        if(choice==0) {
            discoverController.restoreTextDoc("Discover");
            return true;
        }else {
            discoverController.deleteTextDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
            return false;
        }
//        }else if (choice==2){
//            System.out.println("Please insert Y or N.");
//            return existsBackUpDocument();
//        }//if_else
        //return false;
    }//existsBackupDocument
    /*
    dialog.setAlwaysOnTop(true);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
    */
    /**
     * Receives presenterDiscover's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDoneDiscover(){
        return discoverPresenter.isDone();
    }


    public MainGui getMainGui() {
        return mainGui;
    }

}
