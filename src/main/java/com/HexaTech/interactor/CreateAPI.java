/**
 * @file CreateAPI
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;

import com.HexaTech.entities.API;
import com.HexaTech.model.ModelDevelopInterface;
import com.HexaTech.portInterface.CreateAPIInputPort;
import com.HexaTech.portInterface.CreateAPIOutputPort;
import com.HexaTech.repo.RepoDevelopInterface;

import java.io.IOException;

/**
 * Class used to manage an API's creation.
 */
public class CreateAPI implements CreateAPIInputPort {
    CreateAPIOutputPort createAPIOutputPort;
    RepoDevelopInterface repoInterface;
    ModelDevelopInterface modelDevelopInterface;

    /**
     * CreateAPI class standard constructor.
     * @param createAPIOutputPort CreateAPIOutputPort - used to send output notifications.
     * @param repoInterface RepoInterface - used to communicate with Repo.
     * @param modelDevelopInterface ModelInterface - used to communicate with Model.
     */
    public CreateAPI(CreateAPIOutputPort createAPIOutputPort, RepoDevelopInterface repoInterface, ModelDevelopInterface modelDevelopInterface) {
        this.createAPIOutputPort=createAPIOutputPort;
        this.repoInterface=repoInterface;
        this.modelDevelopInterface=modelDevelopInterface;
    }

    /**
     * Creates a new API.
     * @throws IOException if something went wrong during API necessary documents' loading.
     * @throws IllegalArgumentException if one or more document has invalid syntax.
     */
    @Override
    public void createAPI() throws IOException,IllegalArgumentException{
        if(!repoInterface.existsPLA() && !repoInterface.existsPLAJar()){
            createAPIOutputPort.showErrorTextAPI("PLA file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(1);
        }else if(!repoInterface.existsBAL()){
            createAPIOutputPort.showErrorTextAPI("BAL file doesn't exist.");
            createAPIOutputPort.showErrorCodeAPI(2);
        }else{//if_else_1
            API api;
            String str=repoInterface.getBAL(), pla=repoInterface.getPLA();
            api=modelDevelopInterface.setAPI(str);
            if(api==null){
                repoInterface.deleteDocument(".\\Develop\\temp.txt");
                createAPIOutputPort.showErrorCodeAPI(3);
            }else{//if_else_2
                if(repoInterface.returnDocumentContent(pla).equals("")){
                    createAPIOutputPort.showErrorTextAPI("PLA file is empty.");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else if(repoInterface.returnPLAExtension(pla).equals("")) {
                    createAPIOutputPort.showErrorTextAPI("Input PLA is not valid. Check file syntax or extension (.pla).");
                    createAPIOutputPort.showErrorCodeAPI(4);
                }else{//if_else_3
                    repoInterface.saveAPI(api, api.getAPIName(), repoInterface.returnPLAExtension(pla));
                    repoInterface.deleteDocument(".\\Develop\\temp.txt");
                    createAPIOutputPort.showCreatedAPI("API ."+repoInterface.returnPLAExtension(pla)+" generated into folder: Develop.");
                    createAPIOutputPort.showErrorCodeAPI(0);
                }//if_else_3
            }//if_else_2
        }//if_else_1
    }//createAPI

}//CreateAPI
