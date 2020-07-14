package com.hexaTech.application.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
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
@Component
public class MainGui implements MyObserver {

    private final DiscoverController discoverController;
    private final DesignController designController;
    private final DevelopController developController;
    private final DiscoverPresenter discoverPresenter;
    private final DesignPresenter designPresenter;
    private final DevelopPresenter developPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private JFrame window;
    private JPanel homePanel;
    private DiscoverWindow discoverWindow;
    private DesignWindow designWindow;
    private DevelopWindow developWindow;
    private JPanel developPanel;
    private JPanel designPanel;
    private JButton discoverButton;
    private JButton designButton;
    private JButton developButton;
    private JButton guideButton;

    private String stringManual;
    //private final JFileChooser fc;

    @Autowired
    public MainGui(DiscoverController discoverController, DesignController designController,
                   DevelopController developController, ViewManualController viewManualController,
                   DiscoverPresenter discoverPresenter, DesignPresenter designPresenter,
                   DevelopPresenter developPresenter, ViewManualPresenter viewManualPresenter) throws IOException {

        this.discoverController = discoverController;
        this.designController = designController;
        this.developController = developController;
        this.viewManualController = viewManualController;

        this.discoverPresenter = discoverPresenter;
        this.designPresenter = designPresenter;
        this.developPresenter = developPresenter;
        this.viewManualPresenter = viewManualPresenter;

        this.discoverPresenter.addObserver(this);
        this.designPresenter.addObserver(this);
        this.developPresenter.addObserver(this);
        this.viewManualPresenter.addObserver(this);

        this.window = new JFrame();
        window.setPreferredSize(new Dimension(400,600));
        window.pack();
        window.setLocationRelativeTo(null);

        this.homePanel = new JPanel();
        this.discoverWindow = new DiscoverWindow(this, discoverController, viewManualController, discoverPresenter, viewManualPresenter);
        this.designWindow = new DesignWindow(this, designController,designPresenter, viewManualController, viewManualPresenter);
        this.developWindow = new DevelopWindow(this, developController,developPresenter, viewManualController, viewManualPresenter);

        this.discoverButton= new JButton("Discover");
        this.designButton= new JButton("Design");
        this.developButton= new JButton("Develop");
        this.guideButton= new JButton("Guide");

        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.PAGE_AXIS));
        //buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        //this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        discoverButton.setPreferredSize(new Dimension(200,100));
        designButton.setSize(50,80);
        developButton.setSize(50,80);

        //button1.setAlignmentY((float)(homePanel.getSize())/2);
        //homePanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        //obj.setLayout(new GridLayout());
        homePanel.add(discoverButton);
        homePanel.add(designButton);
        homePanel.add(developButton);
        homePanel.add(guideButton);
        //Obj.add(fc);
        window.add(homePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("NaturalAPI");
        window.pack();
        window.setVisible(true);


        discoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(discoverWindow);
                homePanel.setVisible(false);
                discoverWindow.setVisible(true);
                discoverWindow.extractBDLButton.setEnabled(false);
                try {
                    discoverWindow.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        designButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(designWindow);
                homePanel.setVisible(false);
                designWindow.setVisible(true);
                try {
                    designWindow.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        developButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(developWindow);
                homePanel.setVisible(false);
                developWindow.setVisible(true);
                try {
                    developWindow.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewManualController.openManualSection("MAIN:");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //JDialog dialog = new JDialog(window, true); // parent, isModal
                //dialog.setVisible(true); // blocks until dialog is closed
                //JFrame popup=new JFrame();
                //JLabel manual=new JLabel(stringManual);
                //dialog.set;
                //popup.setVisible(true);
                JOptionPane.showMessageDialog(window,
                        stringManual);
            }
        });

    };

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


    public JFrame getHomeWindow() {
        return window;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }


    //public void showGUI(){


};
