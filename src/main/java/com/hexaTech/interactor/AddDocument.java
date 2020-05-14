/**
 * @file AddDocToParse
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.portInterface.AddDocumentInputPort;
import com.hexaTech.portInterface.AddDocumentOutputPort;
import com.hexaTech.repo.RepoInterface;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocument implements AddDocumentInputPort {
    AddDocumentOutputPort addDocumentOutputPort;
    RepoInterface repoInterface;

    /**
     * AddDocuToParse standard constructor.
     * @param addDocumentOutputPort AddDocToParseOutputPort - used to send output notifications.
     * @param repoInterface RepoInterface - used to communicate with Repo.
     */
    public AddDocument(AddDocumentOutputPort addDocumentOutputPort, RepoInterface repoInterface) {
        this.addDocumentOutputPort = addDocumentOutputPort;
        this.repoInterface = repoInterface;
    }

    /**
     * Verifies if there are any loaded documents.
     * @param path
     */
    @Override
    public void checkThereAreDoc(String path) {
        addDocumentOutputPort.thereAreDoc(repoInterface.existsDoc(path));
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument(String directory) throws IOException {
        if(repoInterface.importDoc(directory))
            addDocumentOutputPort.showAddDocument(true);
        else
            addDocumentOutputPort.showAddDocument(false);
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoInterface.loadBackup(directory);
        addDocumentOutputPort.showBackUpRestored("Backup loaded");
    }

    /**
     * Delete the specified document.
     * @param path string - document to be deleted.
     */
    public void deleteDocs(String path){
        if(repoInterface.deleteDoc(path));
            addDocumentOutputPort.showDeletedDoc("Document deleted");
    }

}//AddDocToParse
