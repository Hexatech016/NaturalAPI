/**
 * @file Controller
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;

import com.hexaTech.portInterface.AddDocumentInputPort;
import com.hexaTech.portInterface.CreateAPIInputPort;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDevelop {
    private final AddDocumentInputPort addDocumentInputPort;
    private final CreateAPIInputPort createAPIInputPort;

    /**
     * Controller class constructor.
     * @param addDocumentInputPort AddDocumentInputPort - used to communicate with AddDocument interactor.
     * @param createAPIInputPort CreateAPIInputPort - used to communicate with CreateAPI interactor.
     */
    public ControllerDevelop(AddDocumentInputPort addDocumentInputPort, CreateAPIInputPort createAPIInputPort) {
        this.addDocumentInputPort=addDocumentInputPort;
        this.createAPIInputPort=createAPIInputPort;
    }//Controller

    /**
     * Invokes AddDocument method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addDocController(String directory) throws IOException {
        addDocumentInputPort.addBAL(directory);
    }

    /**
     * Invokes AddDocument method to restore backup.
     * @throws IOException if the document to restore doesn't exist.
     */
    public void restoreDocController(String directory) throws IOException{
        addDocumentInputPort.loadBackUp(directory);
    }

    /**
     * Invokes DeleteDocument method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteDocController(String path){
        addDocumentInputPort.deleteDoc(path);
    }

    /**
     * Invokes CreateAPI method to create a new API object;
     * @throws IOException if API can't be created to repo's stored document.
     */
    public void createAPIController() throws IOException {
        createAPIInputPort.createAPI();
    }

    /**
     * Invokes AddDocument method to add a PLA file.
     * @throws IOException if the specified file doesn't exist.
     */
    public void addPLAController(String directory) throws IOException{
        addDocumentInputPort.addPLA(directory);
    }

    /**
     * Invokes AddDocument method to change PLA file source.
     * @param path string - PLA source file path.
     * @throws IOException if the specified path doesn't exist.
     */
    public void refreshPLAController(String path) throws IOException{
        addDocumentInputPort.updatePLA(path);
    }

    public void existsDocController(String path){
        addDocumentInputPort.existsDoc(path);
    }

}//Controller
