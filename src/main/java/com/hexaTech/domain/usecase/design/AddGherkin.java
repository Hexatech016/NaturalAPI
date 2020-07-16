/**
 * @file AddGherkin
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.design;

import com.hexaTech.domain.port.in.AddGherkinInputPort;
import com.hexaTech.domain.port.out.usecase.AddGherkinOutputPort;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage a Gherkin insertion.
 */
@Component
public class AddGherkin implements AddGherkinInputPort {
    private final AddGherkinOutputPort addGherkinOutputPort;

    private final RepoGherkinInterface repoGherkinInterface;

    /**
     * AddGherkin class constructor.
     * @param addGherkinOutputPort AddGherkinOutputPort - used to send output notifications.
     * @param repoGherkinInterface RepoInterface - used to communicate with repo.
     */
    @Autowired
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
        addGherkinOutputPort.showDone(repoGherkinInterface.importDoc(directory, document));
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
