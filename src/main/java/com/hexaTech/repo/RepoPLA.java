package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.entities.PLA;
import com.hexaTech.fileSystem.FileSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class RepoPLA extends RepoFile{
    PLA PLA;

    /**
     * RepoPLA empty constructor.
     */
    public RepoPLA() {
        PLA=new PLA();
    }

    public PLA getPLA(){
        return PLA;
    }

    /**
     * Changes PLA's path value.
     * @param PLA string - new PLA's path.
     */
    public void setPLA(PLA PLA) {
        this.PLA=PLA;
    }

    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @throws IOException if an error occurs during file loading process.
     * @return boolean - false if something goes wrong, true if not.
     */
    @Override
    public boolean importDoc(String directory) throws IOException {
        String temp;
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File PLA", "pla");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if(returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("pla"))
            temp=chooser.getSelectedFile().getAbsolutePath();
        else
            temp="";
        if(!temp.equalsIgnoreCase("")) {
            PLA=new PLA(chooser.getSelectedFile().getAbsolutePath(),getExtensionFromPLA(chooser.getSelectedFile().getAbsolutePath()));
            //saveDoc(".\\BackupPLA.txt", directory);
            return true;
        }else
            return false;
    }//importDoc

    /**
     * Extracts output file requested extension from a PLA file.
     * @param PLA string - PLA's path.
     * @return string - requested output file extension. Empty string if the syntax is invalid.
     * @throws IOException if the specified document doesn't exist.
     */
    public String getExtensionFromPLA(String PLA) throws IOException{
        String ext="";
        String[] content=getContentFromPath(PLA).split("\n");
        for(String s : content) {
            if(s.contains("<--classExtension:")) {
                ext=s.substring((s.lastIndexOf(":")+1));
                ext=ext.trim();
            }//if
        }//for
        return ext;
    }//getExtensionFromPLA

    /**
     * Verifies if the specified document exists into JAR archive.
     * @param path string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsDocJar(String path){
        String jarName="/"+path.substring(path.lastIndexOf("\\")+1);
        return FileSystem.class.getResourceAsStream(jarName)!=null;
    }

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */
    @Override
    public void loadBackup(String directory) throws IOException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupPLA.txt"));
        String temp=s.nextLine();
        PLA=new PLA(temp,getExtensionFromPLA(temp));
        s.close();
    }//loadBackup

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
            out.write(PLA.getPath());
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc

    /**
     * Saves a new document.
     * @param content string - document content.
     * @param path string - document path.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveOutput(String content,String path){
        try{
            File directory = new File("Develop");
            if (!directory.exists())
                directory.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + path));
            String[] rows=content.split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred" + e);
        }//try_catch
    }//saveOutput

}//RepoPLA
