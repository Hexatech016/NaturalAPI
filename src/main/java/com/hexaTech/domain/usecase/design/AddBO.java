package com.hexaTech.domain.usecase.design;

import com.hexaTech.domain.entity.BO;
import com.hexaTech.domain.port.in.AddBOInputPort;
import com.hexaTech.domain.port.out.usecase.AddBOOutputPort;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddBO implements AddBOInputPort {
    private final RepoBOInterface repoBOInterface;

    private final AddBOOutputPort addBOOutputPort;

    @Autowired
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
            if(bo==null) {
                addBOOutputPort.showCreatedBO("BO format is invalid. Please retry.");
                addBOOutputPort.showDone(false);
            }else {
                repoBOInterface.setBoOpenAPI(bo);
                addBOOutputPort.showCreatedBO("BO added.");
            }
        }//else
    }//addBO

}//AddBO
