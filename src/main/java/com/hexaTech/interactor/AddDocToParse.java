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


import com.hexaTech.portInterface.AddDocToParseInputPort;
import com.hexaTech.portInterface.AddDocToParseOutputPort;
import com.hexaTech.repo.RepoInterface;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocToParse implements AddDocToParseInputPort {
    AddDocToParseOutputPort addDocToParseOutputPort;
    RepoInterface repoInterface;

    /**
     * AddDocuToParse standard constructor.
     * @param addDocToParseOutputPort AddDocToParseOutputPort - used to send output notifications.
     * @param repoInterface RepoInterface - used to communicate with Repo.
     */
    public AddDocToParse(AddDocToParseOutputPort addDocToParseOutputPort, RepoInterface repoInterface) {
        this.addDocToParseOutputPort = addDocToParseOutputPort;
        this.repoInterface = repoInterface;
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument(String directory) throws IOException {
        if(repoInterface.importDoc(directory))
            addDocToParseOutputPort.showAddDocument(true);
        else
            addDocToParseOutputPort.showAddDocument(false);
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoInterface.loadBackup(directory);
        addDocToParseOutputPort.showBackUpRestored("Backup loaded");
    }

}//AddDocToParse
