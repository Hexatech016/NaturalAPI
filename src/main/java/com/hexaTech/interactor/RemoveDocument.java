/**
 * @file RemoveDocument
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;

import com.hexaTech.portInterface.RemoveDocumentInputPort;
import com.hexaTech.portInterface.RemoveDocumentOutputPort;
import com.hexaTech.repo.RepoBAL;

/**
 * Class used to manage a document removal.
 */
public class RemoveDocument implements RemoveDocumentInputPort {
    RemoveDocumentOutputPort removeDocumentOutputPort;
    RepoBAL repoBAL;

    /**
     * RemoveDocument class standard constructor.
     * @param removeDocumentOutputPort RemoveDocumentOutputPort - used to send output notifications.
     * @param repoBAL RepoInterface - used to communicate with Repo.
     */
    public RemoveDocument(RemoveDocumentOutputPort removeDocumentOutputPort, RepoBAL repoBAL) {
        this.removeDocumentOutputPort=removeDocumentOutputPort;
        this.repoBAL=repoBAL;
    }

    /**
     * Deletes the specified document.
     * @param path string - path to the document to be deleted.
     */
    public void removeDoc(String path){
        if(repoBAL.deleteDoc(path))
            removeDocumentOutputPort.showRemovedDoc("Document deleted.");
        else
            removeDocumentOutputPort.showRemovedDoc("Error. Please retry.");
    }

}//RemoveDocument
