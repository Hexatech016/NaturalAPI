/**
 * @file AddBAL
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.develop;

import com.hexaTech.interactor.repositoriesInterface.RepoBALDocumentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage a BAL insertion.
 */
@Component
public class AddBAL implements AddBALInputPort {
    @Autowired
    private final AddBALOutputPort addBALOutputPort;
    @Autowired
    private final RepoBALDocumentInterface repoBALDocumentInterface;

    /**
     * Add document standard constructor.
     * @param addBALOutputPort AddBALOutputPort - used to send output notifications.
     * @param repoBALDocumentInterface RepoBALDocumentInterface - used to communicate with Repo.
     */
    public AddBAL(AddBALOutputPort addBALOutputPort, RepoBALDocumentInterface repoBALDocumentInterface) {
        this.addBALOutputPort=addBALOutputPort;
        this.repoBALDocumentInterface=repoBALDocumentInterface;
    }

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    public void addBAL(String directory,String document) throws IOException {
        boolean loaded=repoBALDocumentInterface.importDoc(directory,document);
        if(loaded) {
            addBALOutputPort.showAddedBAL("BAL loaded");
            addBALOutputPort.showDone(true);
        }else
            addBALOutputPort.showDone(false);
    }//addBAL

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    public void loadBackUp(String directory) throws IOException {
        repoBALDocumentInterface.loadBackup(directory);
        addBALOutputPort.showRestoredBackUp("Backup restored");
    }

    public void existsDoc(String path){
        addBALOutputPort.showDone(repoBALDocumentInterface.existsDoc(path));
    }

    public void deleteDoc(String path){
        if(repoBALDocumentInterface.deleteDoc(path))
            addBALOutputPort.showRemovedBAL("Document deleted.");
        else
            addBALOutputPort.showRemovedBAL("Error. Please retry.");
    }

}//AddBAL
