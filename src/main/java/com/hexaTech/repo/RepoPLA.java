/**
 * @file RepoPLA
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.PLA;
import com.hexaTech.fileSystem.FileSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class RepoPLA implements RepoPLAInterface{
    PLA PLA;

    /**
     * RepoPLA empty constructor.
     */
    public RepoPLA() {
        PLA=new PLA();
    }

    /**
     * Returns PLA.
     * @return PLA - PLA value.
     */
    public PLA getPLA(){
        return PLA;
    }

    /**
     * Verifies if the specified document exists.
     * @param path string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsDoc(String path){
        File file=new File(path);
        return file.exists();
    }//existsDoc

    /**
     * Deletes the specified document.
     * @param path string - path to the document to be deleted.
     * @return boolean - false if an error occurs, true if not.
     */
    public boolean deleteDoc(String path){
        File temp=new File(path);
        return temp.delete();
    }

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    public String getContentFromPath(String path) throws IOException {
        String jarName="/"+path.substring(path.lastIndexOf("\\")+1);
        InputStream input=null;
        BufferedReader br;
        if(FileSystem.class.getResourceAsStream(jarName)!=null)
            input = Main.class.getResourceAsStream(jarName);
        if(input==null){
            File file=new File(path);
            br=new BufferedReader(new FileReader(file));
        }else{
            br = new BufferedReader(new InputStreamReader(input));
        }//if_else
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }//while
        return sb.toString();
    }//getContentFromPath

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
