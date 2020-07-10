package com.hexaTech.application.Gui;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;

public class DiscoverWindow extends JPanel {

    private JButton back;
    private JButton next;
    private JButton loadDocButton;
    private JLabel message;
    private final DiscoverController discoverController;
    private final DiscoverPresenter discoverPresenter;
    //private JFileChooser insertDocs;

    public DiscoverWindow(DiscoverController discoverController,DiscoverPresenter discoverPresenter) throws IOException {

        this.discoverController=discoverController;
        this.discoverPresenter=discoverPresenter;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        message = new JLabel();
        loadDocButton = new JButton("Load document");
        add(message);
        add(loadDocButton);
        getMessage();

        loadDocButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dialog = new JFrame();
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("File Text", "txt");
                chooser.setFileFilter(filter);
                dialog.getContentPane().add(chooser);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(false);
                dialog.dispose();
                int returnVal = chooser.showOpenDialog(dialog);
                if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("txt") ){
                    chooser.getSelectedFile().getAbsolutePath();
                    return;
                }else{
                    //return "";
                    return;
                }//if_else

            }
        });
    }

    private void getMessage() throws IOException {

        discoverController.existsDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
        if(discoverPresenter.isDone()){
            if(existsBackUpDocument())
                message.setText("There are saved documents");
        }else {
            message.setText("There are no saved documents");
        }
    }


    public boolean existsBackUpDocument() throws IOException {
        message.setText("Some documents are already stored. Do you want to load them?");
        int choice = JOptionPane.showConfirmDialog(null,"Some documents are already stored. Do you want to load them?");
        if(choice==0) {
            discoverController.restoreTextDoc("Discover");
            return true;
        }else if (choice==1){
            discoverController.deleteTextDoc("." + File.separator + "Discover" + File.separator + "BackupDocument.txt");
            return false;
        }
//        }else if (choice==2){
//            System.out.println("Please insert Y or N.");
//            return existsBackUpDocument();
//        }//if_else
        return false;
    }//existsBackupDocument
    /*
    dialog.setAlwaysOnTop(true);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
    */

}
