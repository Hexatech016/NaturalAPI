/**
 * @file AddDocToParse
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.discover;

import com.hexaTech.domain.port.in.AddDocumentInputPort;
import com.hexaTech.domain.port.out.usecase.AddDocumentOutputPort;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
@Component
public class AddDocument implements AddDocumentInputPort {
    private final AddDocumentOutputPort addDocumentOutputPort;

    private final RepoDocumentInterface repoDocumentInterface;

    /**
     * AddDocuToParse standard constructor.
     * @param addDocumentOutputPort AddDocToParseOutputPort - used to send output notifications.
     * @param repoDocumentInterface RepoInterface - used to communicate with Repo.
     */

    @Autowired
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
        addDocumentOutputPort.showDone(repoDocumentInterface.existsDoc(path));
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument(String directory,String document) throws IOException {
        addDocumentOutputPort.showDone(repoDocumentInterface.importDoc(directory,document));
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
