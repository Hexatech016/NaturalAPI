/**
 * @file AddDocument
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.entities.PLA;
import com.hexaTech.portInterface.AddDocumentInputPort;
import com.hexaTech.portInterface.AddDocumentOutputPort;
import com.hexaTech.repo.RepoBAL;
import com.hexaTech.repo.RepoPLA;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocument implements AddDocumentInputPort {
    AddDocumentOutputPort addDocumentOutputPort;
    RepoPLA repoPLA;
    RepoBAL repoBAL;

    /**
     * Add document standard constructor.
     * @param addDocumentOutputPort AddDocumentOutputPort - used to send output notifications.
     * @param repoPLA RepoInterface - used to communicate with Repo.
     * @param repoBAL RepoInterface - used to communicate with Repo.
     */
    public AddDocument(AddDocumentOutputPort addDocumentOutputPort, RepoPLA repoPLA, RepoBAL repoBAL) {
        this.addDocumentOutputPort=addDocumentOutputPort;
        this.repoPLA=repoPLA;
        this.repoBAL=repoBAL;
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addBAL(String directory) throws IOException {
        boolean loaded=repoBAL.importDoc(directory);
        if(loaded) {
            addDocumentOutputPort.showAddedDocument("BAL loaded");
            addDocumentOutputPort.showDone(true);
        }else
            addDocumentOutputPort.showDone(false);
    }//addBAL

    /**
     * Loads a new PLA.
     * @throws IOException if an error occurs during loading process.
     */
    public void addPLA(String directory) throws IOException{
        boolean loaded=repoPLA.importDoc(directory);
        if(loaded) {
            addDocumentOutputPort.showAddedDocument("PLA loaded");
            addDocumentOutputPort.showDone(true);
        }else
            addDocumentOutputPort.showDone(false);
    }//addPLA

    /**
     * Updates PLA's reference.
     * @param path string - new PLA's path.
     * @throws IOException if the specified document doesn't exist.
     */
    public void updatePLA(String path) throws IOException{
        repoPLA.setPLA(new PLA(path,repoPLA.getExtensionFromPLA(path)));
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoBAL.loadBackup(directory);
        addDocumentOutputPort.showRestoredBackUp("Backup restored");
    }

    public void existsDoc(String path){
        if(repoPLA.existsDoc(path))
            addDocumentOutputPort.showDone(true);
        else
            addDocumentOutputPort.showDone(false);
    }

    public void deleteDoc(String path){
        if(repoBAL.deleteDoc(path))
            addDocumentOutputPort.showRemovedDocument("Document deleted.");
        else
            addDocumentOutputPort.showRemovedDocument("Error. Please retry.");
    }

}//AddDocument
