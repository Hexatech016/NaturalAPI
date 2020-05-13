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
import com.hexaTech.repo.RepoDesignInterface;

import java.io.IOException;

/**
 * Class used to manage a BDL insertion.
 */
public class AddBDL implements AddBDLInputPort {

    AddBDLOutputPort addBDLOutputPort;
    RepoDesignInterface repoDesignInterface;

    /**
     * AddBDL class constructor.
     * @param addBDLOutputPort AddBDLOutputPort - used to send output notifications.
     * @param repoDesignInterface RepoInterface - used to communicate with repo.
     */
    public AddBDL(AddBDLOutputPort addBDLOutputPort, RepoDesignInterface repoDesignInterface){
        this.addBDLOutputPort=addBDLOutputPort;
        this.repoDesignInterface=repoDesignInterface;
    }

    /**
     * Loads a new BDL.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addBDL() throws IOException {
        repoDesignInterface.setBDLPath();
        addBDLOutputPort.showAddedBDL("BDL added.");
    }

}//AddBDL
