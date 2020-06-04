/**
 * @file AddPLA
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.develop;

import com.hexaTech.domain.entity.PLA;
import com.hexaTech.domain.port.in.AddPLAInputPort;
import com.hexaTech.domain.port.out.usecase.AddPLAOutputPort;
import com.hexaTech.domain.port.out.repository.RepoPLAInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class used to manage a document insertion.
 */
@Component
public class AddPLA implements AddPLAInputPort {
    @Autowired
    private final AddPLAOutputPort addPLAOutputPort;
    @Autowired
    private final RepoPLAInterface repoPLAInterface;

    /**
     * Add document standard constructor.
     * @param addPLAOutputPort AddDocumentOutputPort - used to send output notifications.
     * @param repoPLAInterface RepoInterface - used to communicate with Repo.
     */
    public AddPLA(AddPLAOutputPort addPLAOutputPort, RepoPLAInterface repoPLAInterface) {
        this.addPLAOutputPort=addPLAOutputPort;
        this.repoPLAInterface=repoPLAInterface;
    }

    /**
     * Loads a new PLA.
     * @throws IOException if an error occurs during loading process.
     */
    public void addPLA(String directory,String document) throws IOException{
        boolean loaded=repoPLAInterface.importDoc(directory,document);
        if(loaded) {
            addPLAOutputPort.showAddedPLA("PLA loaded");
            addPLAOutputPort.showDone(true);
        }else
            addPLAOutputPort.showDone(false);
    }//addPLA

    /**
     * Updates PLA's reference.
     * @param path string - new PLA's path.
     * @throws IOException if the specified document doesn't exist.
     */
    public void updatePLA(String path) throws IOException{
        repoPLAInterface.setPLA(new PLA(path,repoPLAInterface.getExtensionFromPLA(path)));
    }

    public void existsDoc(String path){
        addPLAOutputPort.showDone(repoPLAInterface.existsDoc(path));
    }

    public void deleteDoc(String path){
        if(repoPLAInterface.deleteDoc(path))
            addPLAOutputPort.showRemovedPLA("Document deleted.");
        else
            addPLAOutputPort.showRemovedPLA("Error. Please retry.");
    }

}//AddPLA
