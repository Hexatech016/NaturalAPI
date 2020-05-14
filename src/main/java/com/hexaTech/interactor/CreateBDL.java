/**
 * @file CreateBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor;


import com.hexaTech.entities.BDL;
import com.hexaTech.entities.Document;
import com.hexaTech.portInterface.CreateBDLInputPort;
import com.hexaTech.portInterface.CreateBDLOutputPort;
import com.hexaTech.repo.RepoBDLInterface;
import com.hexaTech.repo.RepoDocumentInterface;

import java.io.IOException;

/**
 * Class used to manage a BDL object's creation.
 */
public class CreateBDL implements CreateBDLInputPort {
    CreateBDLOutputPort createBDLOutputPort;
    RepoBDLInterface repoBDLInterface;
    RepoDocumentInterface repoDocumentInterface;

    /**
     * CreateBDL standard constructor.
     * @param createBDLOutputPort CreateBDLOutputPort - used to send output notifications.
     * @param repoBDLInterface RepoInterface - used to communicate with Repo.
     * @param repoDocumentInterface ModelInterface - used to communicate with Model.
     *              JAVADOC TO CORRECT
     */


    public CreateBDL(CreateBDLOutputPort createBDLOutputPort, RepoBDLInterface repoBDLInterface, RepoDocumentInterface repoDocumentInterface) {
        this.createBDLOutputPort = createBDLOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoDocumentInterface = repoDocumentInterface;
    }


    /**
     * Creates a new BDL object.
     * @throws IOException if an error occurs while loading or parsing any file.
     */
    /*public void createBDL() throws IOException {
        BDL bdl=new BDL();
        for(String path: repoDiscoverInterface.getLista()) {
            String document = repoDiscoverInterface.returnContentFromTxt(path);
            BDL bdlToMerge=modelDiscoverInterface.extractBDL(document);
            bdl.mergeBDL(bdlToMerge);
        }//for
        repoDiscoverInterface.saveBDL(bdl);
        repoDiscoverInterface.delete(".\\Discover\\temp.txt");
        createBDLOutputPort.showCreateBdl("BDL created into folder: Discover");
    }//createBDL*/

    public void createBDL() throws IOException {
        BDL bdl=new BDL();
        for(Document doc: repoDocumentInterface.getDocuments()) {
            String path=doc.getPath();
            String document = repoDocumentInterface.getContentFromPath(path);
            BDL bdlToMerge=repoBDLInterface.extractBDL(document);
            bdl.mergeBDL(bdlToMerge);
        }//for
        repoBDLInterface.saveBDL(bdl);
        repoDocumentInterface.deleteDoc((".\\Discover\\BackupDocument.txt"));
        createBDLOutputPort.showCreateBdl("BDL created into folder: Discover");
    }//createBDL

}//CreateBDL
