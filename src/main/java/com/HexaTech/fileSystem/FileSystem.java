/**
 * @file FileSystem
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.fileSystem;

import com.HexaTech.Main;
import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Class used to save and load documents.
 */
public class FileSystem implements FileSystemInterface {

    /**
     * Imports a document's path from disk.
     * @return string - document path. Empty string if an error occurs.
     */
    public String importPath(){
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if (returnVal == JFileChooser.APPROVE_OPTION && Files.getFileExtension(chooser.getSelectedFile().getAbsolutePath()).equals("txt")){
            return chooser.getSelectedFile().getAbsolutePath();
        }else{
            return "";
        }//if_else
    }//importPath

    /**
     * Imports a BAL document's path from disk.
     * @return string - document path. Empty string if an error occurs.
     */
    public String importPathBAL() {
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
            return chooser.getSelectedFile().getAbsolutePath();
        }else{
            return "";
        }//if_else
    }//importPathBAL

    /**
     * Imports a BDL's path from disk.
     * @return string - BDL's path. Empty string if an error occurs.
     */
    public String importPathBDL(){
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Json file", "json");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile().getAbsolutePath();
        }else
            return "";
    }//importPathBDL

    /**
     * Imports a Gherkin's path from disk.
     * @return string - Gherkin's path. Empty string if an error occurs.
     */
    @Override
    public String importPathGherkin(){
        JFrame dialog = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Gherkin file", "scenario");
        chooser.setFileFilter(filter);
        dialog.getContentPane().add(chooser);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        dialog.dispose();
        int returnVal = chooser.showOpenDialog(dialog);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile().getAbsolutePath();
        }else
            return "";
    }//importPathGherkin

    /**
     * Imports a PLA document's path from disk.
     * @return string - document path. Empty string if an error occurs.
     */
    public String importPathPLA(){
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
            return chooser.getSelectedFile().getAbsolutePath();
        else
            return "";
    }//importPathPLA

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    public String getContentFromPath(String path) throws IOException{
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
     * Saves the specified content into Develop folder.
     * @param doc string - new document's content.
     * @param path string - new document's path.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveDocDevelop(String doc, String path) throws IOException {
        try {
            File directory = new File("Develop");
            if (!directory.exists())
                directory.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + path));
            String[] rows=doc.split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred" + e);
        }//try_catch
    }//saveDocDevelop

    /**
     * Saves the specified content into Discover folder.
     * @param doc string - new document's content.
     * @param path string - new document's path.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveDocDiscover(String doc, String path) throws IOException {
        try {
            // Open given file in append mode.
            File directory = new File("Discover");
            if (! directory.exists())
                directory.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" +path));
            String[] rows=doc.split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDocDiscover

    /**
     * Saves the specified content into Design folder.
     * @param doc string - new document's content.
     * @param path string - new document's path.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveDocDesign(String doc, String path) throws IOException {
        File directory = new File("Design");
        if (! directory.exists())
            directory.mkdir();
        // Open given file in append mode.
        BufferedWriter out = new BufferedWriter(
                new FileWriter(directory + "/" + path));
        String[] rows=doc.split("\n");
        for(String riga: rows){
            out.write(riga);
            out.newLine();
        }//for
        out.close();
    }//saveDocDesign

    /**
     * Verifies if the specified document exists.
     * @param doc string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsDoc(String doc){
        File temp=new File(doc);
        return temp.exists();
    }//existsDoc

    /**
     * Verifies if the specified document exists into JAR archive.
     * @param doc string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsDocJar(String doc){
        String jarName="/"+doc.substring(doc.lastIndexOf("\\")+1);
        return FileSystem.class.getResourceAsStream(jarName)!=null;
    }

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     */
    public boolean deleteDoc(String doc){
        File temp=new File(doc);
        return temp.delete();
    }

}//FileSystem