/**
 * @file Controller
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.controllerPresenter;


import com.hexaTech.portInterface.AddBDLInputPort;
import com.hexaTech.portInterface.AddBOInputPort;
import com.hexaTech.portInterface.AddGherkinInputPort;
import com.hexaTech.portInterface.CreateBALInputPort;
import com.hexaTech.portInterface.CreateBOInputPort;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDesign {
    AddBDLInputPort addBDLInputPort;
    AddBOInputPort addBOInputPort;
    AddGherkinInputPort addGherkinInputPort;
    CreateBALInputPort createBALInputPort;
    CreateBOInputPort createBOInputPort;

    /**
     * Controller class constructor.
     * @param addBDL AddBDLInputPort - used to communicate with AddBDL interactor.
     * @param addGherkin AddGherkinInputPort - used to communicate with CreateAPI interactor.
     * @param createBAL CreateBALInputPort - used to communicate with CreateBAL interactor.
     * @param addBO AddBOInputPort - used to communicate with AddBO interactor.
     */
    public ControllerDesign(AddBDLInputPort addBDL, AddGherkinInputPort addGherkin, CreateBALInputPort createBAL, AddBOInputPort addBO, CreateBOInputPort createBO){
        addBDLInputPort=addBDL;
        addGherkinInputPort=addGherkin;
        addBOInputPort=addBO;
        createBALInputPort=createBAL;
        createBOInputPort=createBO;
    }

    /**
     * Invokes AddBDL method to add a BDL.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addBDLController(String directory) throws IOException {
        addBDLInputPort.addBDL(directory);
    }

    /**
     * Invokes AddGherkin method to add a new scenario.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addGherkinController(String directory) throws IOException {
        addGherkinInputPort.addGherkin(directory);
    }

    /**
     * Invokes AddPLA method to restore backup.
     * @throws IOException if the document to restore doesn't exist.
     */
    public void restoreBackupController(String directory) throws IOException{
        addGherkinInputPort.loadBackUp(directory);
    }

    /**
     * Invokes DeleteDocument method to delete a document.
     * @param path string - path to the file to be removed.
     */
    public void deleteDocController(String path){
        addGherkinInputPort.deleteDoc(path);
    }

    /**
     * Invokes CreateBAL method to create a new BAL object.
     * @throws IOException if the document to add doesn't exist.
     */
    public void createBALController() throws IOException {
        createBALInputPort.createBAL();
        //createBOInputPort.createBO();
    }

    public void createBOController(String directory) throws IOException {
        createBOInputPort.createBO(directory);
    }

    public void checkSuggestions() throws IOException {
        createBALInputPort.checkTypes();
    }

    public void existsDocController(String path){
        addGherkinInputPort.existsDoc(path);
    }

}//Controller
