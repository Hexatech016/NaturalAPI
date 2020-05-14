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

import com.hexaTech.portInterface.AddBALInputPort;
import com.hexaTech.portInterface.AddPLAInputPort;
import com.hexaTech.portInterface.CreateAPIInputPort;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDevelop {
    private final AddPLAInputPort addPLAInputPort;
    private final AddBALInputPort addBALInputPort;
    private final CreateAPIInputPort createAPIInputPort;

    /**
     * Controller class constructor.
     * @param addPLAInputPort AddDocumentInputPort - used to communicate with AddPLA interactor.
     * @param createAPIInputPort CreateAPIInputPort - used to communicate with CreateAPI interactor.
     */
    public ControllerDevelop(AddPLAInputPort addPLAInputPort, AddBALInputPort addBALInputPort, CreateAPIInputPort createAPIInputPort) {
        this.addPLAInputPort=addPLAInputPort;
        this.addBALInputPort=addBALInputPort;
        this.createAPIInputPort=createAPIInputPort;
    }

    /**
     * Invokes AddPLA method to add a new document.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addBALController(String directory) throws IOException {
        addBALInputPort.addBAL(directory);
    }

    /**
     * Invokes AddPLA method to restore backup.
     * @throws IOException if the document to restore doesn't exist.
     */
    public void restoreBackupController(String directory) throws IOException{
        addBALInputPort.loadBackUp(directory);
    }

    /**
     * Invokes DeleteDocument method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteDocController(String path){
        addBALInputPort.deleteDoc(path);
    }

    /**
     * Invokes CreateAPI method to create a new API object;
     * @throws IOException if API can't be created to repo's stored document.
     */
    public void createAPIController() throws IOException {
        createAPIInputPort.createAPI();
    }

    /**
     * Invokes AddPLA method to add a PLA file.
     * @throws IOException if the specified file doesn't exist.
     */
    public void addPLAController(String directory) throws IOException{
        addPLAInputPort.addPLA(directory);
    }

    /**
     * Invokes AddPLA method to change PLA file source.
     * @param path string - PLA source file path.
     * @throws IOException if the specified path doesn't exist.
     */
    public void refreshPLAController(String path) throws IOException{
        addPLAInputPort.updatePLA(path);
    }

    public void existsDocController(String path){
        addBALInputPort.existsDoc(path);
    }

}//Controller
