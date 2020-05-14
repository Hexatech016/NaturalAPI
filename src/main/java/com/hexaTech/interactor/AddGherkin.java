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
import com.hexaTech.repo.RepoDesignInterface;

import java.io.IOException;

/**
 * Class used to manage a Gherkin insertion.
 */
public class AddGherkin implements AddGherkinInputPort {

    AddGherkinOutputPort addGherkinOutputPort;
    RepoDesignInterface repoDesignInterface;

    /**
     * AddGherkin class constructor.
     * @param addGherkinOutputPort AddGherkinOutputPort - used to send output notifications.
     * @param repoDesignInterface RepoInterface - used to communicate with repo.
     */
    public AddGherkin(AddGherkinOutputPort addGherkinOutputPort, RepoDesignInterface repoDesignInterface) {
        this.addGherkinOutputPort = addGherkinOutputPort;
        this.repoDesignInterface = repoDesignInterface;
    }

    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    @Override
    public void addGherkin() throws IOException {
        repoDesignInterface.setGherkinPath();
        addGherkinOutputPort.showAddedGherkin("Scenario added.");
    }

}//AddGherkin
