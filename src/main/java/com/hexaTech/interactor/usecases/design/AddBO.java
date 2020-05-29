package com.hexaTech.interactor.usecases.design;

import com.hexaTech.interactor.entities.BO;
import com.hexaTech.interactor.portInterface.AddBOInputPort;
import com.hexaTech.interactor.portInterface.AddBOOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBOInterface;

import java.io.IOException;

public class AddBO implements AddBOInputPort {
    RepoBOInterface repoBOInterface;
    AddBOOutputPort addBOOutputPort;

    public AddBO(AddBOOutputPort addBOOutputPort, RepoBOInterface repoBOInterface) {
        this.repoBOInterface = repoBOInterface;
        this.addBOOutputPort=addBOOutputPort;
    }

    @Override
    public void addBO(String directory, String document) throws IOException {
        if(!repoBOInterface.importDoc(directory,document))
            addBOOutputPort.showDone(false);
        else {
            addBOOutputPort.showDone(true);
            String path=repoBOInterface.getBO().getPath();
            String content=repoBOInterface.getContentFromPath(path);
            BO bo=repoBOInterface.setBOFromJSON(content);
            repoBOInterface.setBoOpenAPI(bo);
            addBOOutputPort.showCreatedBO("BO added.");
        }//else
    }//addBO

}//AddBO
