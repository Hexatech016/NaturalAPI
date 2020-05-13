package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.entities.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class RepoBAL extends RepoFile{
    Document BAL;

    /**
     * RepoBAL empty constructor.
     */
    public RepoBAL() {
        super();
    }

    /**
     * Returns BAL object.
     * @return BAL - BAL object.
     */
    public Document getBAL() {
        return BAL;
    }

    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @return boolean - false if something goes wrong, true if not.
     * @throws IOException if an error occurs during file loading process.
     */
    @Override
    public boolean importDoc(String directory){
        String temp;
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File JSON", "json");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("json") ){
            temp=chooser.getSelectedFile().getAbsolutePath();
        }else{
            temp="";
        }//if_else
        if(!temp.equalsIgnoreCase("")) {
            BAL=new Document(temp.substring(temp.lastIndexOf("\\")+1),temp);
            saveDoc(".\\BackupBAL.txt", directory);
            return true;
        }else
            return false;
    }//importDoc

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */
    @Override
    public void loadBackup(String directory) throws FileNotFoundException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupBAL.txt"));
        String temp=s.nextLine();
        BAL=new Document((temp.substring(temp.lastIndexOf("\\")+1)), temp);
        s.close();
    }

    /**
     * Saves a backup file.
     * @param title string - document title.
     * @param directory string - document directory.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    @Override
    public void saveDoc(String title, String directory) {
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + title));
            out.write(BAL.getPath());
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc

}//RepoBAL
