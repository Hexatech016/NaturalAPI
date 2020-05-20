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
import com.hexaTech.repointerface.RepoDocumentInterface;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocument implements AddDocumentInputPort {
    AddDocumentOutputPort addDocumentOutputPort;
    RepoDocumentInterface repoDocumentInterface;

    /**
     * AddDocuToParse standard constructor.
     * @param addDocumentOutputPort AddDocToParseOutputPort - used to send output notifications.
     * @param repoDocumentInterface RepoInterface - used to communicate with Repo.
     */
    public AddDocument(AddDocumentOutputPort addDocumentOutputPort, RepoDocumentInterface repoDocumentInterface) {
        this.addDocumentOutputPort = addDocumentOutputPort;
        this.repoDocumentInterface = repoDocumentInterface;
    }

    /**
     * Verifies if there are any loaded documents.
     * @param path
     */
    @Override
    public void checkThereAreDoc(String path) {
        addDocumentOutputPort.thereAreDoc(repoDocumentInterface.existsDoc(path));
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument(String directory) throws IOException {
        addDocumentOutputPort.showAddDocument(repoDocumentInterface.importDoc(directory));
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoDocumentInterface.loadBackup(directory);
        addDocumentOutputPort.showBackUpRestored("Backup loaded");
    }

    /**
     * Delete the specified document.
     * @param path string - document to be deleted.
     */
    public void deleteDocs(String path){
        if(repoDocumentInterface.deleteDoc(path));
            addDocumentOutputPort.showDeletedDoc("Document deleted");
    }

}//AddDocToParse
