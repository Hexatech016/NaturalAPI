package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

public class DiscoverCreate extends JPanel implements MyObserver{

    private final JButton homeButton;
    public JButton extractBDLButton;


    private JLabel message;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private final DiscoverNavigation discoverNavigation;

    private String backupString;
    private final DefaultListModel listModel;
    private final JList list;
    private String stringManual;


    //private JFileChooser insertDocs;

    public DiscoverCreate(DiscoverNavigation parent, DiscoverController discoverController, ViewManualController viewManualController,
                          DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter) {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.discoverNavigation = parent;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        message = new JLabel();
        JLabel loadedText = new JLabel("Stored documents:");
        backupString = "";
        message.setText("Welcome! Please add at least one document to proceed");


        JButton loadDocButton = new JButton("Load document");
        homeButton = new JButton("Home");
        extractBDLButton = new JButton("Extract BDL");
        JButton deleteDocButton = new JButton("Delete selected document");
        JButton guideButton = new JButton("Guide");
        JButton backButton = new JButton("Back");
        add(homeButton);
        add(message);
        add(loadDocButton);
        add(deleteDocButton);
        add(loadedText);
        add(backButton);
        listModel = new DefaultListModel();
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);

        add(extractBDLButton);
        add(guideButton);

        loadDocButton.addActionListener(e -> loadDocument());

        deleteDocButton.addActionListener(e -> {
            try {
                deleteDocument();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        homeButton.addActionListener(e -> {
            message.setText("Welcome! Please add at least one document to proceed");
            discoverNavigation.getMainGui().getHomePanel().setVisible(true);
            setVisible(false);
            listModel.clear();
            backupString = "";
        });

        backButton.addActionListener(e -> {
            message.setText("Welcome! Please add at least one document to proceed");
            discoverNavigation.setVisible(true);
            setVisible(false);
            listModel.clear();
            backupString = "";
        });

        extractBDLButton.addActionListener(e -> {
            String name = (String)JOptionPane.showInputDialog(
                    this,
                    "Insert a name for the BDL",
                    "Insert BDL name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Default");
            if(!(name == null)) {
                if (!name.equals("")) {
                    try {
                        discoverController.createBDL(name);
                        viewMessage("Created");
                        message.setText("Welcome! Please add at least one document to proceed");
                        discoverNavigation.setVisible(true);
                        setVisible(false);
                        listModel.clear();
                        backupString = "";
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    viewMessage("InvalidName");
                }
            }
        });

        guideButton.addActionListener(e -> {
            try {
                this.viewManualController.openManualSection("DISCOVER:");
                notifyMeManual();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(discoverNavigation.getMainGui().getHomeWindow(),
                    stringManual);
        });
    }

    public void checkForSavedDocs() throws IOException {
        discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
        if(notifyMeDoneDiscover()){
            if(existsBackUpDocument()) {
                discoverController.showDocumentsList();
                notifyMeDiscover();
                String[] files = backupString.split("\n");
                for (String file : files) {
                    listModel.addElement(file);
                }
                message.setText("Backup restored");
                extractBDLButton.setEnabled(true);
            }
        }
    }

    public void loadDocument() {
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
            String path = chooser.getSelectedFile().getAbsolutePath();
            if(!backupString.contains(path)) {
                try {
                    discoverController.addTextDoc("Discover", path);
                    if (!extractBDLButton.isEnabled()) extractBDLButton.setEnabled(true);
                    discoverController.showDocumentsList();
                    notifyMeDiscover();
                    String[] temp = backupString.split("\n");
                    listModel.addElement(temp[temp.length-1]);
                    viewMessage("Success");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                viewMessage("AlreadyLoaded");
            }
        }else if(returnVal != JFileChooser.CANCEL_OPTION){
            viewMessage("WrongFile");
        }//if_else
    }


    public void viewMessage(String type) {
        switch (type) {
            case("Success"):
                message.setText("Document added.");
                JOptionPane.showMessageDialog(this,
                        "Document added.");
                break;
            case("WrongFile"):
                message.setText("The file is not a .txt or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .txt or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("AlreadyLoaded"):
                message.setText("You're trying to add a document which is already loaded.");
                JOptionPane.showMessageDialog(this,
                        "You're trying to add a document which is already loaded.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("NoSelection"):
                message.setText("No document selected");
                JOptionPane.showMessageDialog(this,
                        "No document selected",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("Created"):
                message.setText("BDL has been created into folder Discover. A business ontology has also been created into the same folder.");
                JOptionPane.showMessageDialog(this,
                        "BDL has been created successfully. You'll be returned to the Discover Menu");
                break;
            case("InvalidName"):
                message.setText("Invalid Name");
                JOptionPane.showMessageDialog(this,
                        "The name cannot be empty",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    public boolean existsBackUpDocument() throws IOException {
        Object[] choices = {"Yes", "No"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "Some documents are already stored. Do you want to load them?\nIf you click No, you'll clear all previous backup documents",
                "Load Backup?",
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
            backupString="";
            return false;
        }
        else{
            homeButton.getAction(); ///sistemare
            return existsBackUpDocument(); //sistemare
        }//if_else
    }//existsBackupDocument

    private void deleteDocument() throws IOException {
        int index = list.getSelectedIndex();
        if(index<0){
            viewMessage("NoSelection");
        }
        else{
            discoverController.deleteDoc(index);
            message.setText("Document deleted");
            //if(list.getindex<0)extractBDLButton.setEnabled(true); se tolgo unico elemento, non mi fa piu andare su create bdl
            discoverController.showDocumentsList();
            notifyMeDiscover();
            listModel.clear();
            if(!backupString.equals("")) {
                String[] files = backupString.split("\n");
                for (String file : files) {
                    listModel.addElement(file);
                }
            }
            if(notifyMeDoneDiscover()) {
                System.out.println("\tDocument deleted.\n");
            }else
                System.out.println("\tPlease insert a valid number or 'q' to go back.\n");
        }
    }//deleteDocument

    @Override
    public void notifyMeDiscover() {
        backupString = discoverPresenter.getMessage();
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
        stringManual=viewManualPresenter.getMessage();
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }


}
