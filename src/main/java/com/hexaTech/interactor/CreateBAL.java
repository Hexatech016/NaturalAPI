/**
 * @file CreateBAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;


import com.hexaTech.entities.BAL;
import com.hexaTech.model.ModelDesignInterface;
import com.hexaTech.portInterface.CreateBALInputPort;
import com.hexaTech.portInterface.CreateBALOutputPort;
import com.hexaTech.repo.RepoDesignInterface;

import java.io.IOException;

/**
 * Class used to manage a BAL creation.
 */
public class CreateBAL implements CreateBALInputPort {
    CreateBALOutputPort createBALOutputPort;
    RepoDesignInterface repoInterface;
    ModelDesignInterface modelInterface;

    /**
     * CreateBAL class constructor.
     * @param createBALOutputPort CreateBALOutputPort - used to send output notifications.
     * @param repoInterface RepoInterface - used to communicate with repo.
     * @param modelInterface ModelInterface - used to communicate with model.
     */
    public CreateBAL(CreateBALOutputPort createBALOutputPort, RepoDesignInterface repoInterface, ModelDesignInterface modelInterface) {
        this.createBALOutputPort = createBALOutputPort;
        this.repoInterface = repoInterface;
        this.modelInterface = modelInterface;
    }

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void createBAL() throws IOException {
        for (String path: repoInterface.getList()) {
            String document = repoInterface.returnDocumentContent(path);
            BAL BAL=modelInterface.setBALFromGherkin(document);
            repoInterface.saveBAL(BAL);
        }//for
        createBALOutputPort.showCreatedBAL("BAL created into folder: Design.");
    }//createBAL

}//CreateBAL
