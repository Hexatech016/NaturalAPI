/**
 * @file Repo
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.hexaTech.entities.API;
import com.hexaTech.fileSystem.FileSystemInterface;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class used to submit actions from user to file system.
 */
public class RepoDevelop implements RepoDevelopInterface {
    String BAL;
    String PLA;
    FileSystemInterface fileSystemInterface;

    /**
     * Repo class constructor.
     * @param file FileSystemInterface - used to communicate with file system.
     */
    public RepoDevelop(FileSystemInterface file){
        BAL="";
        PLA="";
        fileSystemInterface=file;
    }

    /**
     * Returns BAL's path.
     * @return string - BAL's path.
     */
    public String getBAL(){
        return BAL;
    }

    /**
     * Returns PLA path.
     * @return string - PLA's path.
     */
    public String getPLA(){return PLA;}

    /**
     * Changes PLA's path value.
     * @param PLA string - new PLA's path.
     */
    public void setPLA(String PLA) {
        this.PLA=PLA;
    }

    /**
     * Loads a new BAL from file system.
     * @return bool - false if an error occurs during file loading process, true if not.
     * @throws IOException if an error occurs during backup saving process.
     */
    public boolean setBALPath() throws IOException {
        BAL=fileSystemInterface.importPathBAL();
        if(!BAL.equals("")) {
            saveDocument();
            return true;
        }
        return false;
    }//setBALPath

    /**
     * Loads a new PLA from file system.
     * @return bool - false if an error occurs during file loading process, true if not.
     */
    public boolean setPLAPath(){
        PLA=fileSystemInterface.importPathPLA();
        return !PLA.equals("");
    }//setPLAPath

    /**
     * Extracts output file requested extension from a PLA file.
     * @param path string - PLA's path.
     * @return string - requested output file extension. Empty string if the syntax is invalid.
     * @throws IOException if the specified document doesn't exist.
     */
    public String returnPLAExtension(String path) throws IOException{
        return fileSystemInterface.getExtensionFromPLA(path);
    }

    /**
     * Saves the BAL path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    public void saveDocument() throws IOException {
        this.fileSystemInterface.saveDocDevelop(BAL+"\n", ".\\temp.txt");
    }

    /**
     * Loads content from a backup file and restore it.
     * @throws IOException if the backup file doesn't exist.
     */
    public void loadBackUp() throws IOException {
        Scanner s = new Scanner(new File(".\\Develop\\temp.txt"));
        if(s.hasNextLine())
            BAL=s.nextLine();
        s.close();
    }//loadBackUp

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     */
    public void deleteDocument(String doc){
        fileSystemInterface.deleteDoc(doc);
    }

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    public String returnDocumentContent(String path) throws IOException{
        return fileSystemInterface.getContentFromPath(path);
    }

    /**
     * Saves the API object into a new file.
     * @param api API - API object.
     * @param name string - new file name.
     * @param ext string - new file extension.
     * @throws IOException if an error occurs during saving process.
     */
    public void saveAPI(API api, String name, String ext) throws IOException{
        fileSystemInterface.saveDocDevelop(api.replacePLA(fileSystemInterface.getContentFromPath(PLA)),".\\"+name+"."+ext);
    }

    /**
     * Verifies if the PLA document still exists.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsPLA(){
        return fileSystemInterface.existsDoc(PLA);
    }

    /**
     * Verifies if the PLA document still exists in JAR archive.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsPLAJar(){return fileSystemInterface.existsDocJar(PLA);}

    /**
     * Verifies if the BAL document still exists.
     * @return boolean - true if the document exists, false if not.
     */
    public boolean existsBAL(){
        return fileSystemInterface.existsDoc(BAL);
    }

}//Repo

