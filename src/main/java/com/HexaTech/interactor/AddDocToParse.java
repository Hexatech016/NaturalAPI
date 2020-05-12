/**
 * @file AddDocToParse
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;


import com.HexaTech.portInterface.AddDocToParseInputPort;
import com.HexaTech.portInterface.AddDocToParseOutputPort;
import com.HexaTech.repo.RepoDiscoverInterface;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
public class AddDocToParse implements AddDocToParseInputPort {
    AddDocToParseOutputPort addDocToParseOutputPort;
    RepoDiscoverInterface repoDiscoverInterface;

    /**
     * AddDocuToParse standard constructor.
     * @param addDocToParseOutputPort AddDocToParseOutputPort - used to send output notifications.
     * @param repoDiscoverInterface RepoInterface - used to communicate with Repo.
     */
    public AddDocToParse(AddDocToParseOutputPort addDocToParseOutputPort, RepoDiscoverInterface repoDiscoverInterface) {
        this.addDocToParseOutputPort = addDocToParseOutputPort;
        this.repoDiscoverInterface=repoDiscoverInterface;
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addDocument() throws IOException {
        repoDiscoverInterface.returnPath();
        addDocToParseOutputPort.showAddDocument("Document added");
    }

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp() throws IOException {
        repoDiscoverInterface.loadBackUp();
        addDocToParseOutputPort.showBackUpRestored("Backup loaded");
    }

}//AddDocToParse
