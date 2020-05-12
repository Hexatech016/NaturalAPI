/**
 * @file Controller
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.controllerPresenter;


import com.HexaTech.portInterface.AddBDLInputPort;
import com.HexaTech.portInterface.AddGherkinInputPort;
import com.HexaTech.portInterface.CreateBALInputPort;

import java.io.IOException;

/**
 * Class used to invoke methods to perform, based on input actions.
 */
public class ControllerDesign {
    AddBDLInputPort addBDLInputPort;
    AddGherkinInputPort addGherkinInputPort;
    CreateBALInputPort createBALInputPort;

    /**
     * Controller class constructor.
     * @param addBDL AddBDLInputPort - used to communicate with AddDocument interactor.
     * @param addGherkin AddGherkinInputPort - used to communicate with CreateAPI interactor.
     * @param createBAL CreateBALInputPort - used to communicate with RemoveDocument interactor.
     */
    public ControllerDesign(AddBDLInputPort addBDL, AddGherkinInputPort addGherkin, CreateBALInputPort createBAL){
        addBDLInputPort=addBDL;
        addGherkinInputPort=addGherkin;
        createBALInputPort=createBAL;
    }

    /**
     * Invokes AddBDL method to add a BDL.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addBDLController() throws IOException {
        addBDLInputPort.addBDL();
    }

    /**
     * Invokes AddGherkin method to add a new scenario.
     * @throws IOException if the document to add doesn't exist.
     */
    public void addGherkinController() throws IOException {
        addGherkinInputPort.addGherkin();
    }

    /**
     * Invokes CreateBAL method to create a new BAL object.
     * @throws IOException if the document to add doesn't exist.
     */
    public void createBALController() throws IOException {
        createBALInputPort.createBAL();
    }

}//Controller
