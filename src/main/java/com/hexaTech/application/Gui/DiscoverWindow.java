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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DiscoverWindow extends JPanel implements MyObserver{

    private JButton back;
    private JButton next;
    private JButton loadDocButton;
    private JButton homeButton;
    private JButton extractBDLButton;
    private JButton deleteDocButton;
    private JLabel message;
    private JLabel loadedText;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private ExtractBDL extractBDL;
    private MainGui mainGui;

    private String aaaa;
    private JTextArea textArea;
    private DefaultListModel listModel;
    private JList list;


    //private JFileChooser insertDocs;

    public DiscoverWindow(MainGui parent, DiscoverController discoverController,DiscoverPresenter discoverPresenter) throws IOException {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.mainGui=parent;
        extractBDL=new ExtractBDL(this, discoverController, discoverPresenter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        message = new JLabel("Welcome! Please add at least one document to proceed");
        loadedText = new JLabel("Stored documents:");

        loadDocButton = new JButton("Load document");
        homeButton = new JButton("Home");
        extractBDLButton = new JButton("Extract BDL");
        deleteDocButton = new JButton("Delete selected document");
        //loadedText.setText("Stored documents:");
        add(homeButton);
        add(message);
        add(loadDocButton);
        add(deleteDocButton);

        add(loadedText);

        extractBDLButton.setEnabled(false);

        listModel = new DefaultListModel();
        listModel.addElement("Cicciobello pasticcino");
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(200, 50));
        list.setSelectedIndex(0);
        //list.addListSelectionListener(this); sistemare
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(list);
        //add(listScrollPane, BorderLayout.CENTER);

        add(extractBDLButton);


        //getMessage(0); chiamato automaticamente in MainGui, quindi qui non serve

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
                        notifyMeDiscover();
                        listModel.addElement(aaaa);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        useCaseDiscover(1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }else{
                    try {
                        useCaseDiscover(2);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                }//if_else

            }
        });

        deleteDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getHomePanel().setVisible(true);
                setVisible(false);

            }
        });

        homeButton.addActionListener(new ActionListener() {
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

    public void useCaseDiscover(int i) throws IOException {
        switch (i) {
            case(0):
                discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
                if(notifyMeDoneDiscover()){
                    discoverController.showDocumentsList();
                    notifyMeDiscover();
                    listModel.addElement(aaaa); //altrimenti element.getText()
                    if(existsBackUpDocument()) {
                        message.setText("Backup restored");
                        extractBDLButton.setEnabled(true);
                    }
                }else {
                    message.setText("There are no saved documents");
                }
                break;
            case(1):
                message.setText("Document added.");
                JOptionPane.showMessageDialog(this,
                        "Document added.");
                break;
            case(2):
                message.setText("The file is not a .txt or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .txt or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }

    }


    public boolean existsBackUpDocument() throws IOException {
        Object[] choices = {"Yes", "No"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "Some documents are already stored. Do you want to load them?\nIf you type no or exit, you'll clear all previous backup documents",
                "Title message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);
        if(choice==0) {
            discoverController.restoreTextDoc("Discover");
            return true;
        }else if(choice==1){
            discoverController.deleteTextDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
            discoverController.clearRepo();
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
    /*
    dialog.setAlwaysOnTop(true);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
    */

    /*private void deleteDocument(){
        while(true){
            choice=scanner.nextLine();
            if(choice.equalsIgnoreCase("q")){
                return;
            }else if(!StringUtils.isNumeric(choice)){
                System.out.println("\tPlease insert a valid number or 'q' to go back.\n");
            }else{
                discoverController.deleteDoc(Integer.parseInt(choice)-1);
                if(notifyMeDoneDiscover()) {
                    System.out.println("\tDocument deleted.\n");
                    return;
                }else
                    System.out.println("\tPlease insert a valid number or 'q' to go back.\n");
            }//if
        }//while
    }//deleteDocument*/

    @Override
    public void notifyMeDiscover() {
        aaaa=discoverPresenter.getMessage(); //valutare se mettere stringbuilder per l'efficienza

    }

    /**
     * Receives presenterDiscover's boolean status.
     * @return boolean - presenterDevelop status.
     */
    public boolean notifyMeDoneDiscover(){
        return discoverPresenter.isDone();
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
    public void notifyMeManual() {

    }


    public MainGui getMainGui() {
        return mainGui;
    }

}
