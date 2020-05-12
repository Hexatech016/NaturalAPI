/**
 * @file DeleteDoc
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.interactor;


import com.HexaTech.portInterface.DeleteDocInputPort;
import com.HexaTech.portInterface.DeleteDocOutputPort;
import com.HexaTech.repo.RepoDiscoverInterface;

/**
 * Class used to manage a file deletion.
 */
public class DeleteDoc implements DeleteDocInputPort {
    DeleteDocOutputPort deleteDocOutputPort;
    RepoDiscoverInterface repoDiscoverInterface;

    /**
     * DeleteDoc standard constructor.
     * @param deleteDocOutputPort DeleteDocOutputPort - used to send output notifications.
     * @param repoDiscoverInterface RepoInterface - used to communicate with Repo.
     */
    public DeleteDoc(DeleteDocOutputPort deleteDocOutputPort, RepoDiscoverInterface repoDiscoverInterface) {
        this.deleteDocOutputPort = deleteDocOutputPort;
        this.repoDiscoverInterface=repoDiscoverInterface;
    }

    /**
     * Delete the specified document.
     * @param path string - document to be deleted.
     */
    public void deleteDocs(String path){
        if(repoDiscoverInterface.delete(path))
        deleteDocOutputPort.showDeletedDoc("Document deleted");
    }

}//DeleteDoc
