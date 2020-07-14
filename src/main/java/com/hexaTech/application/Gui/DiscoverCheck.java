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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DiscoverCheck extends JPanel implements MyObserver {

    private JButton homeButton;
    private JButton backButton;
    private JButton loadBDLButton;
    private JButton loadGherkinButton;
    public JButton checkButton;
    private JButton guideButton;

    private JLabel message;

    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private DiscoverNavigation discoverNavigation;

    boolean boolBdl;
    boolean boolGherkin;
    String rating;
    String stringManual;

    public DiscoverCheck(DiscoverNavigation parent, DiscoverController discoverController, ViewManualController viewManualController,
                         DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter) throws IOException {
        this.discoverController = discoverController;
        this.discoverPresenter = discoverPresenter;
        this.discoverNavigation = parent;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;

        homeButton = new JButton("Home");
        backButton = new JButton("Back");
        checkButton = new JButton("Check between BDL and scenarios");
        loadBDLButton = new JButton("Load BDL");
        loadGherkinButton = new JButton("Load Gherkin scenarios");
        guideButton = new JButton("Guide");
        message = new JLabel();

        message.setText("Welcome! Please add both documents to proceed");

        add(homeButton);
        add(backButton);
        add(message);
        add(loadBDLButton);
        add(loadGherkinButton);
        add(checkButton);
        add(guideButton);

        boolBdl = false;
        boolGherkin = false;

        loadBDLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                discoverController.checkIfRepoBDLIsEmpty();
                if (notifyMeDoneDiscover())
                    loadBDL();
                else{
                    try {
                        if(!existsBDL())
                            loadBDL();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }//else
            }
        });

        loadGherkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGherkin();
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    discoverController.checkBetweenBDLAndGherkin("Discover");
                    notifyMeDiscover();
                    message.setText("Report created.");
                    showRating();
                } catch (IOException | JWNLException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("Welcome! Please add both documents to proceed");
                parent.getMainGui().getHomePanel().setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("Welcome! Please add both documents to proceed");
                parent.setVisible(true);
                setVisible(false);
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
                    if (boolGherkin)
                        checkButton.setEnabled(true);
                }
                else
                    viewMessage("UnexpectedError");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else if(returnVal != JFileChooser.CANCEL_OPTION){
            try {
                viewMessage("WrongFileBDL");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
            try {
                viewMessage("WrongFileGherkin");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }//if_else
    }

    public void viewMessage(String type) throws IOException {
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
                message.setText("The file is not a .BDL or it doesn't exist. Please retry.");
                JOptionPane.showMessageDialog(this,
                        "The file is not a .txt or it doesn't exist. Please retry.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case("WrongFileGherkin"):
                message.setText("The file is not a .scenario or it doesn't exist. Please retry.");
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

    public boolean existsBDL() throws IOException {
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
