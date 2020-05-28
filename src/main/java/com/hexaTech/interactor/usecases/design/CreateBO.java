package com.hexaTech.interactor.usecases.design;

import com.hexaTech.interactor.entities.BO;
import com.hexaTech.interactor.entities.Document;
import com.hexaTech.interactor.portInterface.CreateBOInputPort;
import com.hexaTech.interactor.portInterface.CreateBOOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBOInterface;

import java.io.IOException;

public class CreateBO implements CreateBOInputPort {
    RepoBOInterface repoBOInterface;
    CreateBOOutputPort createBOOutputPort;

    public CreateBO(CreateBOOutputPort createBOOutputPort, RepoBOInterface repoBOInterface) {
        this.repoBOInterface = repoBOInterface;
        this.createBOOutputPort = createBOOutputPort;
    }

    @Override
    public void createBO(String directory,String document) throws IOException {
        if(!repoBOInterface.importDoc(directory,document))
            createBOOutputPort.showDone(false);
        else {
            createBOOutputPort.showDone(true);
            String path=repoBOInterface.getBO().getPath();
            String content=repoBOInterface.getContentFromPath(path);
            BO bo=repoBOInterface.setBOFromJSON(content);
            repoBOInterface.setBoOpenAPI(bo);
            createBOOutputPort.showCreatedBO("BO created into folder: Design.");
        }//else
    }//createBO

}//CreateBO
