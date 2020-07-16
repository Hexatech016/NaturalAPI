package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

import javax.swing.*;
import java.io.IOException;

public class DiscoverNavigation extends JPanel implements MyObserver {

    private final DiscoverCreate discoverCreate;
    private final DiscoverCheck discoverCheck;

    private final ViewManualPresenter viewManualPresenter;
    private final MainGui mainGui;

    String stringManual;

    public DiscoverNavigation(MainGui home, DiscoverController discoverController, ViewManualController viewManualController,
                              DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter ) {
        this.mainGui = home;
        this.viewManualPresenter = viewManualPresenter;

        this.discoverCreate = new DiscoverCreate(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);
        this.discoverCheck = new DiscoverCheck(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JButton createButton = new JButton("Process .txt into a BDL");
        JButton checkButton = new JButton("Check the compatibility of a BDL and your scenarios");
        JButton homeButton = new JButton("Home");
        JButton guideButton = new JButton("Guide");

        add(homeButton);
        add(guideButton);
        add(createButton);
        add(checkButton);

        homeButton.addActionListener(e -> {
            home.getHomePanel().setVisible(true);
            setVisible(false);
        });

        createButton.addActionListener(e -> {
            home.getHomeWindow().add(discoverCreate);
            setVisible(false);
            discoverCreate.setVisible(true);
            discoverCreate.extractBDLButton.setEnabled(false);
            try {
                discoverCreate.checkForSavedDocs();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        checkButton.addActionListener(e -> {
            home.getHomeWindow().add(discoverCheck);
            discoverCheck.checkButton.setEnabled(false);
            discoverCheck.boolGherkin = false;
            discoverCheck.boolBdl = false;
            setVisible(false);
            discoverCheck.setVisible(true);
        });

        guideButton.addActionListener(e -> {
            try {
                viewManualController.openManualSection("DISCOVER:");
                notifyMeManual();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(home.getHomeWindow(),
                    stringManual);
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
