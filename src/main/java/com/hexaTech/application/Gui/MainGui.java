package com.hexaTech.application.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import com.hexaTech.adapter.interfaceadapter.MyObserver;
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
    private JFrame window;
    private JPanel homePanel;
    private DiscoverWindow discoverWindow;
    private JPanel developPanel;
    private JPanel designPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    //private final JFileChooser fc;

    @Autowired
    public MainGui(DiscoverController discoverController, DesignController designController,
                   DevelopController developController, DiscoverPresenter discoverPresenter,
                   DesignPresenter designPresenter, DevelopPresenter developPresenter) throws IOException {

        this.discoverController = discoverController;
        this.designController = designController;
        this.developController = developController;

        this.discoverPresenter = discoverPresenter;
        this.designPresenter = designPresenter;
        this.developPresenter = developPresenter;

        this.discoverPresenter.addObserver(this);
        this.designPresenter.addObserver(this);
        this.developPresenter.addObserver(this);
//            this.scanner=new Scanner(System.in);
//            this.choice="";

        this.window = new JFrame();
        window.setPreferredSize(new Dimension(800,500));
        this.homePanel = new JPanel();
        homePanel.setSize(800,500);
        this.discoverWindow = new DiscoverWindow(this, discoverController,discoverPresenter);
        this.homePanel = new JPanel();
        //this.discoverPanel = new JPanel();
        this.button1= new JButton("Discover");
        this.button2= new JButton("Design");
        this.button3= new JButton("Develop");
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.PAGE_AXIS));
        //buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        //this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        button1.setSize(300,80);
        button2.setSize(300,80);
        button3.setSize(300,80);
        //homePanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        //obj.setLayout(new GridLayout());
        homePanel.add(button1);
        homePanel.add(button2);
        homePanel.add(button3);
        //Obj.add(fc);
        window.add(homePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("NaturalAPI");
        window.pack();
        window.setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                window.add(discoverWindow);
                homePanel.setVisible(false);
                discoverWindow.setVisible(true);
                try {
                    discoverWindow.getMessage(0);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //window.add(designWindow);
                homePanel.setVisible(false);
                //designWindow.setVisible(true);
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Test GUI");
                homePanel.setVisible(false);
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

    }

    public JFrame getHomeWindow() {
        return window;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }


    //public void showGUI(){


};
