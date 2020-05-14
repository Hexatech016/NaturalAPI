/**
 * @file RepoDocument
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.google.common.io.Files;
import com.hexaTech.Main;
import com.hexaTech.entities.Document;
import com.hexaTech.fileSystem.FileSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RepoDocument class.
 */
public class RepoDocument implements RepoDocumentInterface {
    private final List<Document> documents;

    /**
     * RepoDocument empty constructor.
     */
    public RepoDocument() {
        this.documents=new ArrayList<>();
    }

    /**
     * Returns documents' list.
     * @return List<Document> - list of documents stored.
     */
    public List<Document> getDocuments() {
        return documents;
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
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @return boolean - false if something goes wrong, true if not.
     */
    @Override
    public boolean importDoc(String directory){
        String temp;
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
            temp=chooser.getSelectedFile().getAbsolutePath();
        }else{
            temp= "";
        }//if_else
        if(!temp.equalsIgnoreCase("")) {
            documents.add(new Document(temp.substring(temp.lastIndexOf("\\")+1),temp));
            saveDoc(".\\BackupDocument.txt", directory);
            return true;
        }else
            return false;
    }//returnPath

    /**
     * Saves a backup file.
     * @param title string - document title.
     * @param directory string - document directory.
     */
    @Override
    public void saveDoc(String title, String directory) {
        StringBuilder temp= new StringBuilder();
        for(Document document: documents)
            temp.append(document.getPath()).append("\n");
        try {
            // Open given file in append mode.
            File file= new File(directory);
            if (!file.exists())
                file.mkdir();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(directory + "/" + title));
            String[] rows=temp.toString().split("\n");
            for(String row: rows){
                out.write(row);
                out.newLine();
            }//for
            out.close();
        }catch (IOException e) {
            System.out.println("exception occurred " + e);
        }//try_catch
    }//saveDoc

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */

    @Override
    public void loadBackup(String directory) throws IOException {
        Scanner s = new Scanner(new File(".\\" + directory + "\\BackupDocument.txt"));
        String temp=s.nextLine();
        while (temp!=null){
            documents.add(new Document((temp.substring(temp.lastIndexOf("\\")+1)), temp));
            if(!s.hasNextLine())
                temp=null;
            else
                temp=s.nextLine();
        }//while
        s.close();
    }//loadBackUp

}//RepoDocument
