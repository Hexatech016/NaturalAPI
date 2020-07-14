package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DiscoverNavigation extends JPanel implements MyObserver {

    private JButton createButton;
    private JButton homeButton;
    private JButton checkButton;
    private JButton guideButton;

    private DiscoverCreate discoverCreate;
    private DiscoverCheck discoverCheck;

    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;
    private MainGui mainGui;

    String stringManual;

    public DiscoverNavigation(MainGui home, DiscoverController discoverController, ViewManualController viewManualController,
                              DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter ) throws IOException {
        this.discoverController = discoverController;
        this.discoverPresenter = discoverPresenter;
        this.mainGui = home;
        this.viewManualController = viewManualController;
        this.viewManualPresenter = viewManualPresenter;

        this.discoverCreate = new DiscoverCreate(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);
        this.discoverCheck = new DiscoverCheck(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        createButton = new JButton("Process .txt into a BDL");
        checkButton = new JButton("Check the compatibility of a BDL and your scenarios");
        homeButton = new JButton("Home");
        guideButton = new JButton("Guide");

        add(homeButton);
        add(guideButton);
        add(createButton);
        add(checkButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.getHomePanel().setVisible(true);
                setVisible(false);
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.getHomeWindow().add(discoverCreate);
                setVisible(false);
                discoverCreate.setVisible(true);
                discoverCreate.extractBDLButton.setEnabled(false);
                try {
                    discoverCreate.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.getHomeWindow().add(discoverCheck);
                discoverCheck.checkButton.setEnabled(false);
                discoverCheck.boolGherkin = false;
                discoverCheck.boolBdl = false;
                setVisible(false);
                discoverCheck.setVisible(true);
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
                JOptionPane.showMessageDialog(home.getHomeWindow(),
                        stringManual);
            }
        });
    }

    public MainGui getMainGui() {
        return mainGui;
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
