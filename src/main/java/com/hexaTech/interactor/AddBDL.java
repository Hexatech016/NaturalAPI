/**
 * @file AddBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.portInterface.AddBDLInputPort;
import com.hexaTech.portInterface.AddBDLOutputPort;

import java.io.IOException;

/**
 * Class used to manage a BDL insertion.
 */
public class AddBDL implements AddBDLInputPort {

    AddBDLOutputPort addBDLOutputPort;

    /**
     * AddBDL class constructor.
     * @param addBDLOutputPort AddBDLOutputPort - used to send output notifications.
     */
    public AddBDL(AddBDLOutputPort addBDLOutputPort){
        this.addBDLOutputPort=addBDLOutputPort;
    }

    /**
     * Loads a new BDL.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addBDL() throws IOException {
        /*
        DA FARE
        addBDLOutputPort.showAddedBDL("BDL added.");
         */

    }

}//AddBDL
