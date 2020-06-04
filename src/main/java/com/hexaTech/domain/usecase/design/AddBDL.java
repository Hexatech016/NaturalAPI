/**
 * @file AddBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.design;

import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.in.AddBDLInputPort;
import com.hexaTech.domain.port.out.usecase.AddBDLOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage a BDL insertion.
 */
@Component
public class AddBDL implements AddBDLInputPort {
    @Autowired
    private final AddBDLOutputPort addBDLOutputPort;
    @Autowired
    private final RepoBDLInterface repoBDLInterface;
    /**
     * AddBDL class constructor.
     * @param addBDLOutputPort AddBDLOutputPort - used to send output notifications.
     */
    public AddBDL(AddBDLOutputPort addBDLOutputPort, RepoBDLInterface repoBDLInterface) {
        this.addBDLOutputPort = addBDLOutputPort;
        this.repoBDLInterface = repoBDLInterface;
    }

    /**
     * Loads a new BDL.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addBDL(String document) throws IOException {
        String path=repoBDLInterface.importPathOfBDL(document);
        if(path.equals("")){
            addBDLOutputPort.showDone(false);
        }else{
            addBDLOutputPort.showDone(true);
            repoBDLInterface.setBDL(repoBDLInterface.loadBDLFromJsonFile(path));
        }//if_else
    }//addBDL

    public void checkIfRepoBDLIsEmpty(){
        addBDLOutputPort.showDone(repoBDLInterface.isRepoBDLEmpty());
    }

}//AddBDL
