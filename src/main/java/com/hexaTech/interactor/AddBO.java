/**
 * @file AddBO
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */
package com.hexaTech.interactor;

import com.hexaTech.portInterface.AddBALInputPort;
import com.hexaTech.portInterface.AddBDLOutputPort;
import com.hexaTech.portInterface.AddBOInputPort;
import com.hexaTech.portInterface.AddBOOutputPort;
import com.hexaTech.repointerface.RepoBOInterface;

import java.io.IOException;

/**
 * Class used to manage a BO insertion.
 */
public class AddBO implements AddBOInputPort {
    AddBOOutputPort addBOOutputPort;
    RepoBOInterface repoBOInterface;

    /**
     * AddBO class constructor.
     * @param addBOOutputPort AddBOOutputPort - used to send output notifications.
     */
    public AddBO(AddBOOutputPort addBOOutputPort){
        this.addBOOutputPort=addBOOutputPort;
    }

    /**
     * Loads a new BO.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addBO(String directory) throws IOException {
        if(repoBOInterface.importDoc(directory))
            addBOOutputPort.showAddedBO("Scenario added");
        else
            addBOOutputPort.showAddedBO("Scenario not added!");
    }
}//AddBO
