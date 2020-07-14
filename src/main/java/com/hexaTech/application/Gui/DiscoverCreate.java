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
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

public class DiscoverCreate extends JPanel implements MyObserver{

    private JButton loadDocButton;
    private JButton homeButton;
    private JButton backButton;
    public JButton extractBDLButton;
    private JButton deleteDocButton;
    private JButton guideButton;


    private JLabel message;
    private JLabel loadedText;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private DiscoverNavigation discoverNavigation;

    private String backupString;
    private DefaultListModel listModel;
    private JList list;
    private String stringManual;


    //private JFileChooser insertDocs;

    public DiscoverCreate(DiscoverNavigation parent, DiscoverController discoverController, ViewManualController viewManualController,
                          DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter) throws IOException {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.discoverNavigation = parent;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        message = new JLabel();
        loadedText = new JLabel("Stored documents:");
        backupString = "";
        message.setText("Welcome! Please add at least one document to proceed");


        loadDocButton = new JButton("Load document");
        homeButton = new JButton("Home");
        extractBDLButton = new JButton("Extract BDL");
        deleteDocButton = new JButton("Delete selected document");
        guideButton= new JButton("Guide");
        backButton = new JButton("Back");
        //loadedText.setText("Stored documents:");
        add(homeButton);
        add(message);
        add(loadDocButton);
        add(deleteDocButton);
        add(loadedText);
        add(backButton);

        //extractBDLButton.setEnabled(false);
        //setLayout(new BorderLayout());
        listModel = new DefaultListModel();
        //Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //list.setPreferredSize(new Dimension(200, 50));
        //list.setSelectedIndex(0);
        //list.addListSelectionListener(this); sistemare
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        //add(list);
        add(listScrollPane, BorderLayout.CENTER);


        add(extractBDLButton);
        add(guideButton);


        //getMessage(0);// chiamato automaticamente in MainGui, quindi qui non serve

        loadDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDocument();
            }
        });

        deleteDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteDocument();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("Welcome! Please add at least one document to proceed");
                parent.getMainGui().getHomePanel().setVisible(true);
                setVisible(false);
                listModel.clear();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("Welcome! Please add at least one document to proceed");
                parent.setVisible(true);
                setVisible(false);
                listModel.clear();
            }
        });

        extractBDLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = insertBDLName();
                try {
                    discoverController.createBDL(name);
                    viewMessage("Created");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewManualController.openManualSection("DISCOVER:");
                    notifyMeManual();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(parent.getMainGui().getHomeWindow(),
                        stringManual);
            }
        });

    }

    public String insertBDLName() {
        return (String)JOptionPane.showInputDialog(
                this,
                "Insert a name for the BDL",
                "Insert BDL name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
    }

    public void checkForSavedDocs() throws IOException {
        discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
        if(notifyMeDoneDiscover()){
            if(existsBackUpDocument()) {
                discoverController.showDocumentsList();
                notifyMeDiscover();
                String[] files = backupString.split("\n");
                for(int j=0; j<files.length; j++)
                {
                    listModel.addElement(files[j]);
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
                try {
                    viewMessage("AlreadyLoaded");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }else if(returnVal != JFileChooser.CANCEL_OPTION){
            try {
                viewMessage("WrongFile");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }//if_else
        return;
    }


    public void viewMessage(String type) throws IOException {
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
                        "BDL has been created successfully");
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
                for (int j = 0; j < files.length; j++) {
                    listModel.addElement(files[j]);
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
