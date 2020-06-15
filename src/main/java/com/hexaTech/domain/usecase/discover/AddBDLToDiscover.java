package com.hexaTech.domain.usecase.discover;

import com.hexaTech.domain.port.in.AddBDLToDiscoverInputPort;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.usecase.AddBDLToDiscoverOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddBDLToDiscover implements AddBDLToDiscoverInputPort {

    private final AddBDLToDiscoverOutputPort addBDLToDiscoverOutputPort;

    private final RepoBDLInterface repoBDLInterface;

    /**
     * AddBDL class constructor.
     * @param addBDLToDiscoverOutputPort AddBDLOutputPort - used to send output notifications.
     */
    @Autowired
    public AddBDLToDiscover(AddBDLToDiscoverOutputPort addBDLToDiscoverOutputPort, RepoBDLInterface repoBDLInterface) {
        this.addBDLToDiscoverOutputPort = addBDLToDiscoverOutputPort;
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
            addBDLToDiscoverOutputPort.showDone(false);
        }else{
            addBDLToDiscoverOutputPort.showDone(true);
            repoBDLInterface.setBDL(repoBDLInterface.loadBDLFromJsonFile(path));
        }//if_else
    }//addBDL

    public void checkIfRepoBDLIsEmpty(){
        addBDLToDiscoverOutputPort.showDone(repoBDLInterface.isRepoBDLEmpty());
    }

}//AddBDLToDiscover

