/**
 * @file AddGherkin
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;


import com.HexaTech.model.ModelDesignInterface;
import com.HexaTech.portInterface.AddGherkinInputPort;
import com.HexaTech.portInterface.AddGherkinOutputPort;
import com.HexaTech.repo.RepoDesignInterface;

import java.io.IOException;

/**
 * Class used to manage a Gherkin insertion.
 */
public class AddGherkin implements AddGherkinInputPort {

    AddGherkinOutputPort addGherkinOutputPort;
    RepoDesignInterface repoDesignInterface;
    ModelDesignInterface modelDesignInterface;

    /**
     * AddGherkin class constructor.
     * @param addGherkinOutputPort AddGherkinOutputPort - used to send output notifications.
     * @param repoDesignInterface RepoInterface - used to communicate with repo.
     * @param modelDesignInterface ModelInterface - used to communicate with model.
     */
    public AddGherkin(AddGherkinOutputPort addGherkinOutputPort, RepoDesignInterface repoDesignInterface, ModelDesignInterface modelDesignInterface){
        this.addGherkinOutputPort=addGherkinOutputPort;
        this.repoDesignInterface=repoDesignInterface;
        this.modelDesignInterface=modelDesignInterface;
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
