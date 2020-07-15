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
    private final JPanel homePanel;
    private final DiscoverNavigation discoverNavigation;
    private final DesignWindow designWindow;
    private final DevelopWindow developWindow;
    private final JButton discoverButton;
    private final JButton designButton;
    private final JButton developButton;
    private final JButton guideButton;

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

        this.window = new JFrame();
        window.setPreferredSize(new Dimension(800,600));
        url = Thread.currentThread().getContextClassLoader().getResource("style/logo.png");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        window.setFont(new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,22));
        window.pack();
        window.setLocationRelativeTo(null);

        this.homePanel = new JPanel();
        this.discoverNavigation = new DiscoverNavigation(this, discoverController, viewManualController, discoverPresenter, viewManualPresenter);
        this.designWindow = new DesignWindow(this, designController,designPresenter, viewManualController, viewManualPresenter);
        this.developWindow = new DevelopWindow(this, developController,developPresenter, viewManualController, viewManualPresenter);

        this.discoverButton= new JButton("Discover");
        this.designButton= new JButton("Design");
        this.developButton= new JButton("Develop");
        this.guideButton= new JButton("Guide");

        //homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.PAGE_AXIS));
        //buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        //homePanel.setLayout(new BorderLayout());
        //homePanel.setPreferredSize(new Dimension(200, 100));
        //homePanel.setLayout(new GridLayout(4,1, 2, 2));
        //homePanel.setBackground(new Color(0x5e78fa));
        homePanel.setBorder(BorderFactory.createLineBorder(new Color(27, 31, 234)));
        BoxLayout boxlayout = new BoxLayout(homePanel, BoxLayout.Y_AXIS);
        homePanel.setLayout(boxlayout);
        homePanel.setBorder(new EmptyBorder(new Insets(100, 150, 100, 150)));
        discoverButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        discoverButton.setPreferredSize(new Dimension(100,50));
        discoverButton.setBackground(new Color(0x5e78fa));
        designButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        designButton.setPreferredSize(new Dimension(100,50));
        designButton.setBackground(new Color(0x5e78fa));
        developButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        developButton.setPreferredSize(new Dimension(100,50));
        developButton.setBackground(new Color(0x5e78fa));
        guideButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        guideButton.setPreferredSize(new Dimension(100,50));
        guideButton.setBackground(new Color(0x5e78fa));

        //homePanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        //obj.setLayout(new GridLayout());
        homePanel.add(discoverButton);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(designButton);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(developButton);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(guideButton);
        //Obj.add(fc);
        window.add(homePanel, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("NaturalAPI");
        window.pack();
        window.setVisible(true);


        discoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                discoverButton.setBackground(new Color(27, 31, 234));
                //  discoverButton.setBorderPainted(false);
            }
        });
        discoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                discoverButton.setBackground(new Color(0x5e78fa));
                //  discoverButton.setBorderPainted(true);
            }
        });

        discoverButton.addActionListener(e -> {
            window.add(discoverNavigation);
            homePanel.setVisible(false);
            discoverNavigation.setVisible(true);
        });

        designButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                designButton.setBackground(new Color(27, 31, 234));
                // designButton.setBorderPainted(false);
            }
        });
        designButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                designButton.setBackground(new Color(0x5e78fa));
                // designButton.setBorderPainted(true);
            }
        });

        designButton.addActionListener(e -> {
            window.add(designWindow);
            homePanel.setVisible(false);
            designWindow.setVisible(true);
            try {
                designWindow.checkForSavedDocs();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        developButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                developButton.setBackground(new Color(27, 31, 234));
                // developButton.setBorderPainted(false);
            }
        });
        developButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                developButton.setBackground(new Color(0x5e78fa));
                // developButton.setBorderPainted(true);
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

        guideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                guideButton.setBackground(new Color(27, 31, 234));
                // guideButton.setBorderPainted(false);
            }
        });
        guideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                guideButton.setBackground(new Color(0x5e78fa));
                // guideButton.setBorderPainted(true);
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
