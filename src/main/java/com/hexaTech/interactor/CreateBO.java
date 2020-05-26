package com.hexaTech.interactor;

import com.hexaTech.entities.BO;
import com.hexaTech.entities.Document;
import com.hexaTech.portInterface.CreateBOInputPort;
import com.hexaTech.portInterface.CreateBOOutputPort;
import com.hexaTech.repointerface.RepoBOInterface;

import java.io.IOException;

public class CreateBO implements CreateBOInputPort {
    RepoBOInterface repoBOInterface;
    CreateBOOutputPort createBOOutputPort;

    public CreateBO(CreateBOOutputPort createBOOutputPort, RepoBOInterface repoBOInterface) {
        this.repoBOInterface = repoBOInterface;
        this.createBOOutputPort = createBOOutputPort;
    }

    @Override
    public void createBO(String directory) throws IOException {
        repoBOInterface.importDoc(directory);
        // createBOOutputPort.showAddedBO("BO added");
        // createBOOutputPort.showAddedBO("BO not added!");
        Document doc=repoBOInterface.getBO();
        String path=doc.getPath();
        String document = repoBOInterface.getContentFromPath(path);
        BO bo=repoBOInterface.setBOFromJSON(document);
        repoBOInterface.setBoOpenAPI(bo);
        //repoBOInterface.saveBO(bo);
        createBOOutputPort.showCreatedBO("BO created into folder: Design.");
    }


}
