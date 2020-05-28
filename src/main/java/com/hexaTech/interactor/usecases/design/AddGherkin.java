/**
 * @file AddGherkin
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.design;

import com.hexaTech.interactor.portInterface.AddGherkinInputPort;
import com.hexaTech.interactor.portInterface.AddGherkinOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoGherkinInterface;

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
    public void addGherkin(String directory,String document) throws IOException {
        if(repoGherkinInterface.importDoc(directory,document)) {
            addGherkinOutputPort.showAddedGherkin("Scenario added");
            addGherkinOutputPort.showDone(true);
        }else {
            addGherkinOutputPort.showAddedGherkin("Scenario not added!");
            addGherkinOutputPort.showDone(false);
        }//if_else
    }//addGherkin

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoGherkinInterface.loadBackup(directory);
        addGherkinOutputPort.showAddedGherkin("Backup restored");
        addGherkinOutputPort.showDone(true);
    }

    public void deleteDoc(String path){
        if(repoGherkinInterface.deleteDoc(path))
            addGherkinOutputPort.showAddedGherkin("Document deleted.");
        else
            addGherkinOutputPort.showAddedGherkin("Error. Please retry.");
    }

    public void existsDoc(String path){
        addGherkinOutputPort.showDone(repoGherkinInterface.existsDoc(path));
    }

}//AddGherkin
