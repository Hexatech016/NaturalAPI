/**
 * @file CreateAPI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.develop;

import com.hexaTech.domain.entity.API;
import com.hexaTech.domain.port.in.CreateAPIInputPort;
import com.hexaTech.domain.port.out.usecase.CreateAPIOutputPort;
import com.hexaTech.domain.port.out.framework.JsonParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoAPIInterface;
import com.hexaTech.domain.port.out.repository.RepoBALDocumentInterface;
import com.hexaTech.domain.port.out.repository.RepoPLAInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage an API's creation.
 */
@Component
public class CreateAPI implements CreateAPIInputPort {
    @Autowired
    private final CreateAPIOutputPort createAPIOutputPort;
    @Autowired
    private final RepoPLAInterface repoPLAInterface;
    @Autowired
    private final RepoBALDocumentInterface repoBALDocumentInterface;
    @Autowired
    private final RepoAPIInterface repoAPIInterface;
    @Autowired
    private final JsonParsingInterface jsonParsingInterface;

    /**
     * CreateAPI class standard constructor.
     * @param createAPIOutputPort CreateAPIOutputPort - used to send output notifications.
     * @param repoPLAInterface RepoInterface - used to communicate with Repo.
     * @param repoBALDocumentInterface RepoInterface - used to communicate with Repo.
     * @param repoAPIInterface RepoAPI - used to communicate with Repo.
     * @param jsonParsingInterface JsonParsingInterface - used to extract the API values.
     */
    public CreateAPI(CreateAPIOutputPort createAPIOutputPort, RepoPLAInterface repoPLAInterface,
                     RepoBALDocumentInterface repoBALDocumentInterface, RepoAPIInterface repoAPIInterface,
                     JsonParsingInterface jsonParsingInterface) {
        this.createAPIOutputPort=createAPIOutputPort;
        this.repoPLAInterface=repoPLAInterface;
        this.repoBALDocumentInterface=repoBALDocumentInterface;
        this.repoAPIInterface=repoAPIInterface;
        this.jsonParsingInterface=jsonParsingInterface;
    }

    /**
     * Creates a new API.
     * @throws IOException if something went wrong during API necessary documents' loading.
     * @throws IllegalArgumentException if one or more document has invalid syntax.
     */
    @Override
    public void createAPI() throws IOException,IllegalArgumentException{
        if(!repoPLAInterface.existsDoc(repoPLAInterface.getPLA().getPath()) && !repoPLAInterface.existsDocJar(repoPLAInterface.getPLA().getPath())){
            createAPIOutputPort.showErrorTextAPI("PLA file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(1);
        }else if(!repoBALDocumentInterface.existsDoc(repoBALDocumentInterface.getBAL().getPath())){
            createAPIOutputPort.showErrorTextAPI("BAL file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(2);
        }else{//if_else_1
            String path=repoBALDocumentInterface.getBAL().getPath(), pla=repoPLAInterface.getPLA().getPath();
            repoAPIInterface.setAPI(jsonParsingInterface.extractAPI(path));
            API api=repoAPIInterface.getAPI();
            if(api==null){
                repoBALDocumentInterface.deleteDoc(".\\Develop\\temp.txt");
                createAPIOutputPort.showErrorCodeAPI(3);
            }else{//if_else_2
                if(repoPLAInterface.getContentFromPath(pla).equals("")){
                    createAPIOutputPort.showErrorTextAPI("PLA file is empty.");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else if(repoPLAInterface.getExtensionFromPLA(pla).equals("")) {
                    createAPIOutputPort.showErrorTextAPI("Input PLA is not valid. Check file syntax or extension (.pla).");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else{//if_else_3
                    repoAPIInterface.saveOutput(api.createAPI(repoPLAInterface.getContentFromPath(pla)),".\\"+api.getAPIName()+"."+repoPLAInterface.getPLA().getExtension());
                    repoAPIInterface.saveOutput(api.createTests(repoPLAInterface.getContentFromPath(pla)),".\\"+api.getAPIName()+"Test."+repoPLAInterface.getPLA().getExtension());
                    repoBALDocumentInterface.deleteDoc(".\\Develop\\BackupBAL.txt");
                    createAPIOutputPort.showCreatedAPI("API ."+repoPLAInterface.getPLA().getExtension()+" generated into folder: Develop.");
                    repoAPIInterface.openFile(".\\Develop\\"+api.getAPIName()+"."+repoPLAInterface.getPLA().getExtension());
                    repoAPIInterface.openFile(".\\Develop\\"+api.getAPIName()+"Test."+repoPLAInterface.getPLA().getExtension());
                    createAPIOutputPort.showErrorCodeAPI(0);
                }//if_else_3
            }//if_else_2
        }//if_else_1
    }//createAPI

}//CreateAPI
