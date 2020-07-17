package com.hexaTech.application.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainGui implements MyObserver {

    private final ViewManualPresenter viewManualPresenter;
    private final JFrame window;
    private JPanel homePanel;
    private final DiscoverNavigation discoverNavigation;
    private final DesignWindow designWindow;
    private final DevelopWindow developWindow;
    private JButton discoverButton;
    private JButton designButton;
    private JButton developButton;
    private JButton guideButton;
    private JLabel image;

    private String stringManual;
    public URL url;

    @Autowired
    public MainGui(DiscoverController discoverController, DesignController designController,
                   DevelopController developController, ViewManualController viewManualController,
                   DiscoverPresenter discoverPresenter, DesignPresenter designPresenter,
                   DevelopPresenter developPresenter, ViewManualPresenter viewManualPresenter) {

        this.viewManualPresenter = viewManualPresenter;

        discoverPresenter.addObserver(this);
        designPresenter.addObserver(this);
        developPresenter.addObserver(this);
        this.viewManualPresenter.addObserver(this);

        //JFrame.setDefaultLookAndFeelDecorated(true);

        this.discoverNavigation = new DiscoverNavigation(this, discoverController, viewManualController, discoverPresenter, viewManualPresenter);
        this.designWindow = new DesignWindow(this, designController,designPresenter, viewManualController, viewManualPresenter);
        this.developWindow = new DevelopWindow(this, developController,developPresenter, viewManualController, viewManualPresenter);

        this.window = new JFrame();

        initComponents();

        discoverButton.addActionListener(e -> {
            window.add(discoverNavigation);
            homePanel.setVisible(false);
            discoverNavigation.setVisible(true);
        });

        designButton.addActionListener(e -> {
            window.add(designWindow);
            homePanel.setVisible(false);
            designWindow.addBOButton.setEnabled(false);
            designWindow.setVisible(true);
            try {
                designWindow.checkForSavedDocs();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        developButton.addActionListener(e -> {
            window.add(developWindow);
            homePanel.setVisible(false);
            developWindow.setVisible(true);
            try {
                developWindow.checkForSavedDocs();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        guideButton.addActionListener(e -> {
            try {
                viewManualController.openManual();
                viewManualController.openManualSection("MAIN:");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(window,
                    stringManual);
        });
    }

    private void initComponents() {

        homePanel = new javax.swing.JPanel();
        discoverButton = new javax.swing.JButton("Discover");
        designButton = new javax.swing.JButton("Design");
        developButton = new javax.swing.JButton("Develop");
        guideButton = new javax.swing.JButton("Guide");
        image = new javax.swing.JLabel();

        url = Thread.currentThread().getContextClassLoader().getResource("style/logo.png");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        window.setFont(new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,22));

        this.homePanel = new JPanel();

        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setBackground(new java.awt.Color(255, 255, 255));

        homePanel.setBackground(new java.awt.Color(255, 255, 255));
        homePanel.setForeground(new java.awt.Color(255, 255, 255));

        discoverButton.setBackground(new java.awt.Color(255, 255, 255));
        discoverButton.setFont(new java.awt.Font("SansSerif", Font.PLAIN, 12)); // NOI18N

        designButton.setBackground(new java.awt.Color(255, 255, 255));
        designButton.setFont(new java.awt.Font("SansSerif", Font.PLAIN, 12)); // NOI18N

        developButton.setBackground(new java.awt.Color(255, 255, 255));
        developButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        guideButton.setBackground(new java.awt.Color(255, 255, 255));
        guideButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(77, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(designButton)
                                        .addComponent(developButton)
                                        .addComponent(guideButton)
                                        .addComponent(discoverButton))
                                .addGap(66, 66, 66))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(image)
                                .addGap(18, 18, 18)
                                .addComponent(discoverButton)
                                .addGap(18, 18, 18)
                                .addComponent(designButton)
                                .addGap(18, 18, 18)
                                .addComponent(developButton)
                                .addGap(18, 18, 18)
                                .addComponent(guideButton)
                                .addContainerGap(21, Short.MAX_VALUE))
        );
        window.add(homePanel, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("NaturalAPI");
        window.pack();
        window.setLocationRelativeTo(null);
        window.setSize(new Dimension(246,440));
        window.setResizable(false);
        window.setVisible(true);
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


    public JFrame getHomeWindow() {
        return window;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }


    //public void showGUI(){
}
