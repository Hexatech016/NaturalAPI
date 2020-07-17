package com.hexaTech.application.Gui;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionsDesign extends JPanel implements MyObserver {

    private MainGui mainGui;

    DesignController designController;
    DesignPresenter designPresenter;

    private JLabel image;
    private JButton backButton;
    private JButton confirmButton;
    private JButton addComplexTypeButton;

    private JScrollPane scrollArea;

    private final List<AuxiliarySuggestion> suggestions;
    JFrame suggestion;
    private String nameMethod;

    public SuggestionsDesign(MainGui mainGui, DesignController designController, DesignPresenter designPresenter, String nameBal){
        this.designPresenter = designPresenter;
        this.designController = designController;
        this.mainGui = mainGui;

        suggestions = new ArrayList<>();

        initComponents();

        backButton.addActionListener(e-> {
            suggestion.dispose();
        });

        confirmButton.addActionListener(e -> {
            try {
                designController.updateBAL(nameBal);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            suggestion.dispose();
        });

        addComplexTypeButton.addActionListener(e -> {
            ComplexTypeCreator ctc = new ComplexTypeCreator(mainGui, designController);
            ctc.openWindow();
        });
    }

    public void openWindow() {
        suggestion = new JFrame();
        suggestion.setLayout(new GridLayout(0, 1));
        suggestion.setPreferredSize(new Dimension(470,600));
        suggestion.setResizable(false);
        suggestion.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.url));
        suggestion.setFont(new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,22));
        suggestion.pack();
        suggestion.setLocationRelativeTo(null);
        suggestion.add(this, BorderLayout.CENTER);
        suggestion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        suggestion.setTitle("Create a new Complex Type");
        suggestion.setVisible(true);

        methodsSuggestions();
        refreshTypes();
    }

    private void methodsSuggestions() {
        int sentinel=0, identifier=0;
        designController.checkIfHasMethod(sentinel);
        while(notifyMeDoneDesign()){
            designController.showMethod(sentinel);
            designController.checkIfHasParameter(sentinel,identifier);
            notifyMeDesign();
            AuxiliarySuggestion method = new AuxiliarySuggestion(sentinel, -1, designController, designPresenter);
            method.setNameMethods(nameMethod);
            suggestions.add(method);

            JPanel methodPanel = new JPanel();
            methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.Y_AXIS));
            methodPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Suggestion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

            methodPanel.add(method);
            while(notifyMeDoneDesign()){
                designController.showParameter(sentinel,identifier);
                notifyMeDesign();
                AuxiliarySuggestion param = new AuxiliarySuggestion(sentinel, identifier, designController, designPresenter);
                param.setNameMethods(nameMethod);
                suggestions.add(param);
                methodPanel.add(param);
                identifier++;
                designController.checkIfHasParameter(sentinel,identifier);
            }//external_while
            //test.add(methodPanel);
            sentinel++;
            identifier=0;
            designController.checkIfHasMethod(sentinel);
        }//external_while
    }//methodsSuggestions

    public void refreshTypes() {
        for(AuxiliarySuggestion suggestion : suggestions) {
            suggestion.updateTypes();
        }
    }

    private void initComponents() {
        confirmButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        scrollArea = new javax.swing.JScrollPane();
        addComplexTypeButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        confirmButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        confirmButton.setText("Confirm");

        backButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        backButton.setText("Back");

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/designsuggestions.png"))); // NOI18N

        scrollArea.setBackground(new java.awt.Color(255, 255, 255));
        scrollArea.setBorder(null);

        addComplexTypeButton.setBackground(new java.awt.Color(255, 255, 255));
        addComplexTypeButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        addComplexTypeButton.setText("Add a new complex type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 409, Short.MAX_VALUE)
                        .addComponent(scrollArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addComplexTypeButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollArea, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(confirmButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backButton))
                                        .addComponent(addComplexTypeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(13, Short.MAX_VALUE))
        );
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
        nameMethod = designPresenter.getMessage();
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
}
