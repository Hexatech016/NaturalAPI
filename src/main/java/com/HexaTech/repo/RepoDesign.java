/**
 * @file Repo
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.repo;


import com.HexaTech.entities.BAL;
import com.HexaTech.entities.BDL;
import com.HexaTech.fileSystem.FileSystemInterface;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class used to submit actions from user to file system.
 */
public class RepoDesign implements RepoDesignInterface {
    List<String> list;
    FileSystemInterface fileSystem;

    /**
     * Repo class constructor.
     * @param file FileSystemInterface - used to communicate with file system.
     */
    public RepoDesign(FileSystemInterface file){
        list=new ArrayList<>();
        fileSystem=file;
    }

    /**
     * Returns documents' list.
     * @return string - documents' list.
     */
    public List<String> getList(){
        return list;
    }

    /**
     * Returns loaded text document's path.
     */
    public void importPath() throws IOException {
        String temp=fileSystem.importPath();
        if(!temp.equalsIgnoreCase("")) {
            list.add(temp);
            saveDocument();
        }//if
    }//importPath

    /**
     * Loads a new BDL from file system.
     * @throws IOException if an error occurs during loading.
     */
    public void setBDLPath() throws IOException {
        String temp=fileSystem.importPathBDL();
        if(!temp.equalsIgnoreCase("")) {
            list.add(temp);
            saveBDL();
        }//if
    }//setBDLPath

    /**
     * Loads a new Gherkin from file system.
     * @throws IOException if an error occurs during loading.
     */
    public void setGherkinPath() throws IOException {
        String temp=fileSystem.importPathGherkin();
        if(!temp.equalsIgnoreCase("")) {
            list.add(temp);
            saveGherkin();
        }//if
    }//setGherkinPath

    /**
     * Saves the Gherkin's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveGherkin() throws IOException {
        StringBuilder temp= new StringBuilder();
        for(String string: list)
            temp.append(string).append("\n");
        this.fileSystem.saveDocDesign(temp.toString(), ".\\tempGherkin.txt");
    }

    /**
     * Saves the BDL's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveBDL() throws IOException {
        StringBuilder temp= new StringBuilder();
        for(String string: list)
            temp.append(string).append("\n");
        this.fileSystem.saveDocDesign(temp.toString(), ".\\tempBDL.txt");
    }

    /**
     * Saves the document's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveDocument() throws IOException {
        StringBuilder temp= new StringBuilder();
        for(String string: list)
            temp.append(string).append("\n");
        this.fileSystem.saveDocDesign(temp.toString(), ".\\temp.txt");
    }

    /**
     * Loads content from a backup file and restore it.
     * @throws IOException if the backup file doesn't exist.
     */
    public void loadBackUp() throws IOException {
        Scanner s = new Scanner(new File(".\\Design\\temp.txt"));
        while (s.hasNextLine()){
            list.add(s.nextLine());
        }//while
        s.close();
    }//loadBackUp

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     */
    public boolean deleteDocument(String doc){
        return fileSystem.deleteDoc(doc);
    }

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    public String returnDocumentContent(String path) throws IOException {
        return fileSystem.getContentFromPath(path);
    }

    /**
     * Saves the BDL object into a new file.
     * @param bdl BDL - BDL object.
     * @throws IOException if an error occurs during saving process.
     */
    public void saveBDL(BDL bdl) throws IOException {
        fileSystem.saveDocDesign(bdl.toString(),".\\BDL.txt");
    }

    /**
     * Saves the BAL object into a new file.
     * @param bal BAL - BAL object.
     * @throws IOException if an error occurs during saving process.
     */
    @Override
    public void saveBAL(BAL bal) throws IOException {
        fileSystem.saveDocDesign(bal.toString(),".\\BAL.json");
    }

}//Repo