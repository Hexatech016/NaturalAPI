/**
 * @file AddGherkin
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.portInterface.AddGherkinInputPort;
import com.hexaTech.portInterface.AddGherkinOutputPort;
import com.hexaTech.repo.RepoGherkinInterface;

import java.io.IOException;

/**
 * Class used to manage a Gherkin insertion.
 */
public class AddGherkin implements AddGherkinInputPort {

    AddGherkinOutputPort addGherkinOutputPort;
    RepoGherkinInterface repoGherkinInterface;

    /**
     * AddGherkin class constructor.
     * @param addGherkinOutputPort AddGherkinOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */
    public AddGherkin(AddGherkinOutputPort addGherkinOutputPort, RepoGherkinInterface repoGherkinInterface) {
        this.addGherkinOutputPort = addGherkinOutputPort;
        this.repoGherkinInterface = repoGherkinInterface;
    }

    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addGherkin(String directory) throws IOException {
        if(repoGherkinInterface.importDoc(directory))
            addGherkinOutputPort.showAddedGherkin("Scenario added");
        else
            addGherkinOutputPort.showAddedGherkin("Scenario not added!");
    }

}//AddGherkin
