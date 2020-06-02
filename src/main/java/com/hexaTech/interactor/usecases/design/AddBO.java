package com.hexaTech.interactor.usecases.design;

import com.hexaTech.interactor.entities.BO;
import com.hexaTech.interactor.inputportInterface.AddBOInputPort;
import com.hexaTech.interactor.outputportInterface.AddBOOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddBO implements AddBOInputPort {
    @Autowired
    RepoBOInterface repoBOInterface;
    @Autowired
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
