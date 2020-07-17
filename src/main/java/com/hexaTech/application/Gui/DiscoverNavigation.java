package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import org.mozilla.javascript.tools.debugger.Dim;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DiscoverNavigation extends JPanel implements MyObserver {

    private final DiscoverCreate discoverCreate;
    private final DiscoverCheck discoverCheck;

    private JButton createButton;
    private JButton checkButton;
    private JButton homeButton;
    private JButton guideButton;
    private JLabel image;

    private final ViewManualPresenter viewManualPresenter;
    private final MainGui mainGui;

    String stringManual;

    public DiscoverNavigation(MainGui home, DiscoverController discoverController, ViewManualController viewManualController,
                              DiscoverPresenter discoverPresenter, ViewManualPresenter viewManualPresenter ) {
        this.mainGui = home;
        this.viewManualPresenter = viewManualPresenter;
        this.discoverCreate = new DiscoverCreate(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);
        this.discoverCheck = new DiscoverCheck(this, discoverController,viewManualController,discoverPresenter,viewManualPresenter);

        initComponents();

        homeButton.addActionListener(e -> {
            home.getHomePanel().setVisible(true);
            setVisible(false);
        });

        createButton.addActionListener(e -> {
            home.getHomeWindow().add(discoverCreate);
            home.getHomeWindow().setSize(new Dimension(420,450));
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
            home.getHomeWindow().setSize(new Dimension(260,270));
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

    private void initComponents() {

        homeButton = new javax.swing.JButton("Home");
        guideButton = new javax.swing.JButton("Guide");
        image = new javax.swing.JLabel();
        createButton = new javax.swing.JButton("Process.txt into a BDL");
        checkButton = new javax.swing.JButton("Check compatibility (BDL/scenarios)");

        setBackground(new java.awt.Color(255, 255, 255));

        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/discover.png"))); // NOI18N

        createButton.setBackground(new java.awt.Color(255, 255, 255));
        createButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        checkButton.setBackground(new java.awt.Color(255, 255, 255));
        checkButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        checkButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        checkButton.setMargin(new java.awt.Insets(2, 2, 2, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(checkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(guideButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(homeButton)
                                        .addComponent(guideButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                .addComponent(createButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkButton)
                                .addContainerGap())
        );
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
