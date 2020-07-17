package com.hexaTech.application.Gui;

import com.google.common.io.Files;
import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import net.didion.jwnl.JWNLException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;

public class DiscoverCheck extends JPanel implements MyObserver {

    private JButton homeButton;
    public JButton checkButton;
    private JButton loadBDLButton;
    private JButton loadGherkinButton;
    private JButton guideButton;
    private JButton backButton;

    private JLabel message;
    private JLabel bdlCheck;
    private JLabel gherkinCheck;
    private JLabel image;

    private JPanel panel;

    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualPresenter viewManualPresenter;

    boolean boolBdl;
    boolean boolGherkin;
    String rating;
    String stringManual;

    public DiscoverCheck(DiscoverNavigation parent, DiscoverController discoverController, ViewManualController viewManualController,
                         DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter) {
        this.discoverController = discoverController;
        this.discoverPresenter = discoverPresenter;
        this.viewManualPresenter = viewManualPresenter;

        homeButton = new JButton("Home");


        boolBdl = false;
        boolGherkin = false;

        initComponents();

        loadBDLButton.addActionListener(e -> {
            discoverController.checkIfRepoBDLIsEmpty();
            if (notifyMeDoneDiscover())
                loadBDL();
            else{
                if(!existsBDL())
                    loadBDL();
            }//else
        });

        loadGherkinButton.addActionListener(e -> loadGherkin());

        checkButton.addActionListener(e -> {
            try {
                discoverController.checkBetweenBDLAndGherkin("Discover");
                notifyMeDiscover();
                message.setText("Report created.");
                showRating();
            } catch (IOException | JWNLException ioException) {
                ioException.printStackTrace();
            }
        });

        homeButton.addActionListener(e -> {
            message.setText("Please add both documents to proceed");
            parent.getMainGui().getHomeWindow().setSize(new Dimension(246,440));
            parent.getMainGui().getHomePanel().setVisible(true);
            setVisible(false);
            bdlCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
            gherkinCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
        });

        backButton.addActionListener(e -> {
            message.setText("Please add both documents to proceed");
            parent.getMainGui().getHomeWindow().setSize(new Dimension(246,440));
            parent.setVisible(true);
            setVisible(false);
            bdlCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
            gherkinCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png")));
        });

        guideButton.addActionListener(e -> {
            try {
                viewManualController.openManualSection("DISCOVER:");
                notifyMeManual();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(parent.getMainGui().getHomeWindow(),
                    stringManual);
        });
    }

    private void initComponents() {

        image = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        guideButton = new javax.swing.JButton();
        message = new javax.swing.JLabel();
        gherkinCheck = new javax.swing.JLabel();
        bdlCheck = new javax.swing.JLabel();
        loadBDLButton = new javax.swing.JButton();
        loadGherkinButton = new javax.swing.JButton();
        checkButton = new javax.swing.JButton();

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/discover1.png"))); // NOI18N

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setPreferredSize(new java.awt.Dimension(409, 333));

        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        homeButton.setText("Home");

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        backButton.setText("Back");

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        guideButton.setText("Guide");

        message.setText("Please add both documents to proceed");

        gherkinCheck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gherkinCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png"))); // NOI18N

        bdlCheck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bdlCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/criss-cross.png"))); // NOI18N

        loadBDLButton.setBackground(new java.awt.Color(255, 255, 255));
        loadBDLButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        loadBDLButton.setText("Load BDL");

        loadGherkinButton.setBackground(new java.awt.Color(255, 255, 255));
        loadGherkinButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        loadGherkinButton.setText("Load Gherkin scenarios");

        checkButton.setBackground(new java.awt.Color(255, 255, 255));
        checkButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        checkButton.setText("Check between BDL and Gherkin");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(homeButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(guideButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(checkButton)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(bdlCheck)
                                                        .addComponent(gherkinCheck))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(loadGherkinButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(loadBDLButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(homeButton)
                                        .addComponent(guideButton)
                                        .addComponent(backButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(message)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bdlCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(loadBDLButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(loadGherkinButton)
                                        .addComponent(gherkinCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 270, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
        );
    }

    public void loadBDL() {
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File BDL", "BDL");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("BDL") ){
            String path = chooser.getSelectedFile().getAbsolutePath();
            try {
                discoverController.addBDL(path);
                if(notifyMeDoneDiscover()) {
                    viewMessage("SuccessBDL");
                    boolBdl = true;
                    bdlCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
                    if (boolGherkin)
                        checkButton.setEnabled(true);
                }
                else
                    viewMessage("UnexpectedError");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else if(returnVal != JFileChooser.CANCEL_OPTION){
            viewMessage("WrongFileBDL");
        }//if_else
    }

    public void loadGherkin() {
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
                discoverController.addGherkin("Design", path);
                if(notifyMeDoneDiscover()) {
                    viewMessage("SuccessGherkin");
                    gherkinCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
                    boolGherkin = true;
                    if (boolBdl)
                        checkButton.setEnabled(true);
                }
                else
                    viewMessage("UnexpectedError");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else if(returnVal != JFileChooser.CANCEL_OPTION){
            viewMessage("WrongFileGherkin");
        }//if_else
    }

    public void viewMessage(String type) {
        switch (type) {
            case("SuccessBDL"):
                message.setText("BDL added.");
                JOptionPane.showMessageDialog(this,
                        "BDL added.");
                break;
            case("SuccessGherkin"):
                message.setText("Scenarios added.");
                JOptionPane.showMessageDialog(this,
                        "Scenarios added.");
                break;
            case("WrongFileBDL"):
                message.setText("Not a .BDL or it doesn't exist.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .BDL or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("WrongFileGherkin"):
                message.setText("Not a .scenario or it doesn't exist.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .scenario or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("UnexpectedError"):
                message.setText("Something went wrong.");
                JOptionPane.showMessageDialog(this,
                        "Error while storing the file in memory.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public void showRating() {
        JOptionPane.showMessageDialog( this,
                rating);
    }

    public boolean existsBDL() {
        Object[] choices = {"Yes", "No"};
        Object defaultChoice = choices[0];
        int choice = JOptionPane.showOptionDialog(this,
                "There is already a BDL stored in memory. Do you want to load it?\nIf you click No, you'll be able to import one.",
                "Load BDL?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);
        if(choice==0) {
            viewMessage("SuccessBDL");
            bdlCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tick.png")));
            boolBdl = true;
            if(boolGherkin)
                checkButton.setEnabled(true);
            return true;
        }else if(choice==1){
            return false;
        }
        else{
            homeButton.getAction(); ///sistemare
            return existsBDL(); //sistemare
        }//if_else
    }//existsBackupDocument

    @Override
    public void notifyMeDiscover() {
        rating = discoverPresenter.getMessage();
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
}
