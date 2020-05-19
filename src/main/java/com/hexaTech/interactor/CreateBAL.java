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
import com.hexaTech.entities.Document;
import com.hexaTech.portInterface.CreateBALInputPort;
import com.hexaTech.portInterface.CreateBALOutputPort;
import com.hexaTech.repo.RepoBALInterface;
import com.hexaTech.repo.RepoGherkinInterface;

import java.io.IOException;

/**
 * Class used to manage a BAL creation.
 */
public class CreateBAL implements CreateBALInputPort {
    CreateBALOutputPort createBALOutputPort;
    RepoGherkinInterface repoGherkinInterface;
    RepoBALInterface repoBALInterface;

    /**
     * CreateBAL class constructor.
     * @param createBALOutputPort CreateBALOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */

    public CreateBAL(CreateBALOutputPort createBALOutputPort, RepoGherkinInterface repoGherkinInterface, RepoBALInterface repoBALInterface) {
        this.createBALOutputPort = createBALOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
        this.repoBALInterface = repoBALInterface;
    }

    /**
     * Create a new BAL object.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void createBAL() throws IOException {
        for (Document doc: repoGherkinInterface.getGherkin()) {
            String path=doc.getPath();
            String document = repoGherkinInterface.getContentFromPath(path);
            BAL BAL=repoBALInterface.setBALFromGherkin(document);
            repoBALInterface.saveBAL(BAL);
        }//for
        createBALOutputPort.showCreatedBAL("BAL created into folder: Design.");
    }//createBAL

}//CreateBAL
