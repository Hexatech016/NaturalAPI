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

    private JButton homeButton;
    public JButton extractBDLButton;
    private JButton loadDocButton;
    private JButton deleteDocButton;
    private JButton guideButton;
    private JButton backButton;

    private JLabel image;
    private JLabel message;
    private JLabel fixedString;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane scrollPane;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private final DiscoverNavigation discoverNavigation;

    private String backupString;
    private DefaultListModel listModel;
    private JList list;
    private String stringManual;


    //private JFileChooser insertDocs;

    public DiscoverCreate(DiscoverNavigation parent, DiscoverController discoverController, ViewManualController viewManualController,
                          DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter) {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.discoverNavigation = parent;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;

        backupString = "";



        JScrollPane listScrollPane = new JScrollPane(list);
        //add(listScrollPane, BorderLayout.CENTER);

        initComponents();

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
            parent.getMainGui().getHomeWindow().setSize(new Dimension(246,440));
            discoverNavigation.getMainGui().getHomePanel().setVisible(true);
            setVisible(false);
            listModel.clear();
            backupString = "";
        });

        backButton.addActionListener(e -> {
            message.setText("Welcome! Please add at least one document to proceed");
            parent.getMainGui().getHomeWindow().setSize(new Dimension(246,440));
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
                        parent.getMainGui().getHomeWindow().setSize(new Dimension(246,440));
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

    private void initComponents() {

        image = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        message = new javax.swing.JLabel();
        homeButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        listModel = new DefaultListModel();
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(5);

        scrollPane = new javax.swing.JScrollPane(list);
        jPanel2 = new javax.swing.JPanel();
        loadDocButton = new javax.swing.JButton();
        deleteDocButton = new javax.swing.JButton();
        fixedString = new javax.swing.JLabel();
        guideButton = new javax.swing.JButton();
        extractBDLButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/discover1.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(409, 333));

        message.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        message.setText("Welcome! Please add at least one document to proceed");

        homeButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        homeButton.setText("Home");

        backButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        backButton.setText("Back");

        scrollPane.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 465, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 329, Short.MAX_VALUE)
        );

        //scrollPane.setViewportView(jPanel2);

        loadDocButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        loadDocButton.setText("Load document");

        deleteDocButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        deleteDocButton.setText("Delete selected document");

        fixedString.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        fixedString.setText("Stored documents:");

        guideButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        guideButton.setText("Guide");

        extractBDLButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        extractBDLButton.setText("Extract BDL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(message)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(loadDocButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteDocButton))
                                        .addComponent(fixedString)
                                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(extractBDLButton, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(backButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(guideButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(homeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(message)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loadDocButton)
                                        .addComponent(deleteDocButton))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(fixedString)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(extractBDLButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(40, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(homeButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(guideButton)
                                                .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
        );
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
                message.setText("BDL has been created into folder Discover.");
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
            else
                extractBDLButton.setEnabled(false);
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
