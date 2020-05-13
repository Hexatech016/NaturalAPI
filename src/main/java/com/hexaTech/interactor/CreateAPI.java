/**
 * @file CreateAPI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.entities.API;
import com.hexaTech.portInterface.CreateAPIInputPort;
import com.hexaTech.portInterface.CreateAPIOutputPort;
import com.hexaTech.repo.RepoAPI;
import com.hexaTech.repo.RepoBAL;
import com.hexaTech.repo.RepoPLA;

import java.io.IOException;

/**
 * Class used to manage an API's creation.
 */
public class CreateAPI implements CreateAPIInputPort {
    CreateAPIOutputPort createAPIOutputPort;
    RepoPLA repoPLA;
    RepoBAL repoBAL;
    RepoAPI repoAPI;

    /**
     * CreateAPI class standard constructor.
     * @param createAPIOutputPort CreateAPIOutputPort - used to send output notifications.
     * @param repoPLA RepoInterface - used to communicate with Repo.
     * @param repoBAL RepoInterface - used to communicate with Repo.
     * @param repoAPI RepoAPI - used to communicate with Repo.
     */
    public CreateAPI(CreateAPIOutputPort createAPIOutputPort, RepoPLA repoPLA, RepoBAL repoBAL, RepoAPI repoAPI){
        this.createAPIOutputPort=createAPIOutputPort;
        this.repoPLA=repoPLA;
        this.repoBAL=repoBAL;
        this.repoAPI=repoAPI;
    }

    /**
     * Creates a new API.
     * @throws IOException if something went wrong during API necessary documents' loading.
     * @throws IllegalArgumentException if one or more document has invalid syntax.
     */
    @Override
    public void createAPI() throws IOException,IllegalArgumentException{
        if(!repoPLA.existsDoc(repoPLA.getPLA().getPath()) && !repoPLA.existsDocJar(repoPLA.getPLA().getPath())){
            createAPIOutputPort.showErrorTextAPI("PLA file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(1);
        }else if(!repoBAL.existsDoc(repoBAL.getBAL().getPath())){
            createAPIOutputPort.showErrorTextAPI("BAL file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(2);
        }else{//if_else_1
            API api;
            String str=repoBAL.getBAL().getPath(), pla=repoPLA.getPLA().getPath();
            api=repoAPI.setAPI(str);
            if(api==null){
                repoBAL.deleteDoc(".\\Develop\\temp.txt");
                createAPIOutputPort.showErrorCodeAPI(3);
            }else{//if_else_2
                if(repoPLA.getContentFromPath(pla).equals("")){
                    createAPIOutputPort.showErrorTextAPI("PLA file is empty.");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else if(repoPLA.getExtensionFromPLA(pla).equals("")) {
                    createAPIOutputPort.showErrorTextAPI("Input PLA is not valid. Check file syntax or extension (.pla).");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else{//if_else_3
                    repoPLA.saveOutput(api.replacePLA(repoPLA.getContentFromPath(pla)),".\\"+api.getAPIName()+"."+repoPLA.getPLA().getExtension());
                    repoBAL.deleteDoc(".\\Develop\\BackupBAL.txt");
                    createAPIOutputPort.showCreatedAPI("API ."+repoPLA.getPLA().getExtension()+" generated into folder: Develop.");
                    createAPIOutputPort.showErrorCodeAPI(0);
                }//if_else_3
            }//if_else_2
        }//if_else_1
    }//createAPI

}//CreateAPI
