package com.hexaTech.interactor;

import com.hexaTech.entities.BAL;
import com.hexaTech.entities.BO;
import com.hexaTech.entities.Document;
import com.hexaTech.portInterface.CreateBALOutputPort;
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
    public void createBO() throws IOException {
        Document doc=repoBOInterface.getBO();
        String path=doc.getPath();
        String document = repoBOInterface.getContentFromPath(path);
        BO bo=repoBOInterface.setBOFromJSON(document);
        repoBOInterface.saveBO(bo);
        createBOOutputPort.showCreatedBO("BO created into folder: Design.");
    }
}
