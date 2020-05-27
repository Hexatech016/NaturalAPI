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
import com.hexaTech.repointerface.RepoGherkinInterface;

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
    public AddBO(AddBOOutputPort addBOOutputPort,  RepoBOInterface repoBOInterface){
        this.addBOOutputPort=addBOOutputPort;
        this.repoBOInterface=repoBOInterface;
    }

    /**
     * Loads a new BO.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addBO(String directory) throws IOException {
        if(repoBOInterface.importDoc(directory)) {
            addBOOutputPort.showDone(true);
        }else {
            addBOOutputPort.showDone(false);
        }
    }

}//AddBO
