package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ExtractBDL extends JPanel {
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    private DiscoverWindow discoverWindow;
    private JButton backButton;
    private JButton homeButton;

    public ExtractBDL(DiscoverWindow parent, DiscoverController discoverController, DiscoverPresenter discoverPresenter) throws IOException {
        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;
        this.discoverWindow=parent;

        backButton = new JButton("Back");
        homeButton = new JButton("Finish");
        //pulsante di show bdl
        add(backButton);
        add(homeButton);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(true);
                setVisible(false);
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getMainGui().getHomePanel().setVisible(true);
                setVisible(false);
            }
        });


    }
}
