/**
 * @file CreateAPI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.develop;

import com.hexaTech.interactor.entities.API;
import com.hexaTech.interactor.frameworksInterface.JsonParsingInterface;
import com.hexaTech.interactor.portInterface.CreateAPIInputPort;
import com.hexaTech.interactor.portInterface.CreateAPIOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoAPIInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoBALDocumentInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoPLAInterface;

import java.io.IOException;

/**
 * Class used to manage an API's creation.
 */
public class CreateAPI implements CreateAPIInputPort {
    CreateAPIOutputPort createAPIOutputPort;
    RepoPLAInterface repoPLAInterface;
    RepoBALDocumentInterface repoBALDocumentInterface;
    RepoAPIInterface repoAPIInterface;
    JsonParsingInterface jsonParsingInterface;

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
                    repoPLAInterface.saveOutput(api.createAPI(repoPLAInterface.getContentFromPath(pla)),".\\"+api.getAPIName()+"."+repoPLAInterface.getPLA().getExtension());
                    repoPLAInterface.saveOutput(api.createTests(repoPLAInterface.getContentFromPath(pla)),".\\"+api.getAPIName()+"Test."+repoPLAInterface.getPLA().getExtension());
                    repoBALDocumentInterface.deleteDoc(".\\Develop\\BackupBAL.txt");
                    createAPIOutputPort.showCreatedAPI("API ."+repoPLAInterface.getPLA().getExtension()+" generated into folder: Develop.");
                    createAPIOutputPort.showErrorCodeAPI(0);
                }//if_else_3
            }//if_else_2
        }//if_else_1
    }//createAPI

}//CreateAPI
