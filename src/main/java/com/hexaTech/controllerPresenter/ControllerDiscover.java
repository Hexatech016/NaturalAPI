/**
 * @file Controller
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;


import com.hexaTech.portInterface.AddDocToParseInputPort;
import com.hexaTech.portInterface.CheckThereAreDocInputPort;
import com.hexaTech.portInterface.CreateBDLInputPort;
import com.hexaTech.portInterface.DeleteDocInputPort;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDiscover {
    AddDocToParseInputPort addDocToParseInputPort;
    CreateBDLInputPort createBDLInputPort;
    DeleteDocInputPort deleteDocInputPort;
    CheckThereAreDocInputPort checkThereAreDocInputPort;

    /**
     * Controller class constructor.
     * @param addDocToParseInputPort AddDocToParseInputPort - used to communicate with AddDocToParse interactor.
     * @param createBDLInputPort CreateBDLInputPort - used to communicate with CreateBDL interactor.
     * @param deleteDocInputPort DeleteDocInputPort - used to communicate with DeleteDoc interactor.
     * @param checkThereAreDocInputPort CheckThereAreDocInputPort - used to communicate with CheckThereAreDoc interactor.
     */
    public ControllerDiscover(AddDocToParseInputPort addDocToParseInputPort, CreateBDLInputPort createBDLInputPort,
                      DeleteDocInputPort deleteDocInputPort, CheckThereAreDocInputPort checkThereAreDocInputPort) {
        this.addDocToParseInputPort = addDocToParseInputPort;
        this.createBDLInputPort = createBDLInputPort;
        this.deleteDocInputPort = deleteDocInputPort;
        this.checkThereAreDocInputPort = checkThereAreDocInputPort;
    }

    /**
     * Invokes AddDocToParse method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addDocController(String directory) throws IOException {
        addDocToParseInputPort.addDocument(directory);
    }

    /**
     * Invokes AddDocToParse method to restore a backup file.
     * @throws IOException if the backup file doesn't exist.
     */
    public void restoreDocController(String directory) throws IOException {
        addDocToParseInputPort.loadBackUp(directory);
    }

    /**
     * Invokes CreateBDL method to create a new BDL object;
     * @throws IOException if BDL can't be created from repo's stored document.
     */
    public void createBDL() throws IOException {
        createBDLInputPort.createBDL();
    }

    /**
     * Invokes DeleteDoc method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteDocController(String path){
        deleteDocInputPort.deleteDocs(path);
    }

    /**
     * Invokes CheckThereAreDoc method to check the presence of a stored document.
     */
    public void checkThereAreDoc() {
        checkThereAreDocInputPort.checkThereAreDoc();
    }

}//ControllerDiscover
