/**
 * @file AddDocument
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;

import com.HexaTech.portInterface.AddDocumentInputPort;
import com.HexaTech.portInterface.AddDocumentOutputPort;
import com.HexaTech.repo.RepoDevelopInterface;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocument implements AddDocumentInputPort {
    AddDocumentOutputPort addDocumentOutputPort;
    RepoDevelopInterface repoDevelopInterface;

    /**
     * Add document standard constructor.
     * @param addDocumentOutputPort AddDocumentOutputPort - used to send output notifications.
     * @param repoDevelopInterface RepoInterface - used to communicate with Repo.
     */
    public AddDocument(AddDocumentOutputPort addDocumentOutputPort, RepoDevelopInterface repoDevelopInterface) {
        this.addDocumentOutputPort=addDocumentOutputPort;
        this.repoDevelopInterface=repoDevelopInterface;
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument() throws IOException {
        boolean loaded=repoDevelopInterface.setBALPath();
        if(loaded) {
            addDocumentOutputPort.showAddedDocument("BAL loaded");
            addDocumentOutputPort.showDone(true);
        }else
            addDocumentOutputPort.showDone(false);
    }//addDocument

    /**
     * Loads a new PLA.
     * @throws IOException if an error occurs during loading process.
     */
    public void addPLA() throws IOException{
        boolean loaded=repoDevelopInterface.setPLAPath();
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
        repoDevelopInterface.setPLA(path);
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp() throws IOException {
        repoDevelopInterface.loadBackUp();
        addDocumentOutputPort.showRestoredBackUp("Backup restored");
    }

}//AddDocument
