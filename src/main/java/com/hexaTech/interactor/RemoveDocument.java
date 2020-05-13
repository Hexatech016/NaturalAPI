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
import com.hexaTech.repo.RepoDevelopInterface;

/**
 * Class used to manage a document removal.
 */
public class RemoveDocument implements RemoveDocumentInputPort {
    RemoveDocumentOutputPort removeDocumentOutputPort;
    RepoDevelopInterface repoInterface;

    /**
     * RemoveDocument class standard constructor.
     * @param removeDocumentOutputPort RemoveDocumentOutputPort - used to send output notifications.
     * @param repoInterface RepoInterface - used to communicate with Repo.
     */
    public RemoveDocument(RemoveDocumentOutputPort removeDocumentOutputPort, RepoDevelopInterface repoInterface) {
        this.removeDocumentOutputPort=removeDocumentOutputPort;
        this.repoInterface=repoInterface;
    }

    /**
     * Deletes the specified document.
     * @param path string - path to the document to be deleted.
     */
    public void removeDoc(String path){
        repoInterface.deleteDocument(path);
        removeDocumentOutputPort.showRemovedDoc("Document deleted.");
    }

}//RemoveDocument
