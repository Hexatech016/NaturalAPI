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

import com.hexaTech.interactor.CheckBetweenBDLAndGherkinInputPort;
import com.hexaTech.portInterface.AddDocumentInputPort;
import com.hexaTech.portInterface.CreateBDLInputPort;
import net.didion.jwnl.JWNLException;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDiscover {
    AddDocumentInputPort addDocumentInputPort;
    CreateBDLInputPort createBDLInputPort;
    CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort;

    /**
     * Controller class constructor.
     * @param addDocumentInputPort AddDocToParseInputPort - used to communicate with AddDocToParse interactor.
     * @param createBDLInputPort CreateBDLInputPort - used to communicate with CreateBDL interactor.
     */
    public ControllerDiscover(AddDocumentInputPort addDocumentInputPort, CreateBDLInputPort createBDLInputPort, CheckBetweenBDLAndGherkinInputPort checkBetweenBDLAndGherkinInputPort) {
        this.addDocumentInputPort = addDocumentInputPort;
        this.createBDLInputPort = createBDLInputPort;
        this.checkBetweenBDLAndGherkinInputPort = checkBetweenBDLAndGherkinInputPort;
    }

    /**
     * Invokes AddDocToParse method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addDocController(String directory) throws IOException {
        addDocumentInputPort.addDocument(directory);
    }

    /**
     * Invokes AddDocToParse method to restore a backup file.
     * @throws IOException if the backup file doesn't exist.
     */
    public void restoreDocController(String directory) throws IOException {
        addDocumentInputPort.loadBackUp(directory);
    }

    /**
     * Invokes CreateBDL method to create a new BDL object;
     * @throws IOException if BDL can't be created from repo's stored document.
     * @param BDLpath
     */
    public void createBDL(String BDLpath) throws IOException {
        createBDLInputPort.createBDL(BDLpath);
    }

    /**
     * Invokes DeleteDoc method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteDocController(String path){
        addDocumentInputPort.deleteDocs(path);
    }

    /**
     * Invokes CheckThereAreDoc method to check the presence of a stored document.
     */
    public void checkThereAreDoc(String path) {
        addDocumentInputPort.checkThereAreDoc(path);
    }

    public void checkBetweenBDLAndGherkin(String directory) throws IOException, JWNLException {
        checkBetweenBDLAndGherkinInputPort.check(directory);
    }

}//ControllerDiscover
