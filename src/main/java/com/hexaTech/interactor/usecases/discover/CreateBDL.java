/**
 * @file CreateBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.discover;


import com.hexaTech.interactor.entities.BDL;
import com.hexaTech.interactor.entities.Document;
import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import com.hexaTech.interactor.portInterface.CreateBDLInputPort;
import com.hexaTech.interactor.portInterface.CreateBDLOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBDLInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoDocumentInterface;

import java.io.IOException;
import java.util.List;

/**
 * Class used to manage a BDL object's creation.
 */
public class CreateBDL implements CreateBDLInputPort {
    CreateBDLOutputPort createBDLOutputPort;
    RepoBDLInterface repoBDLInterface;
    RepoDocumentInterface repoDocumentInterface;
    TextsParsingInterface textsParsingInterface;

    public CreateBDL(CreateBDLOutputPort createBDLOutputPort, RepoBDLInterface repoBDLInterface,
                     RepoDocumentInterface repoDocumentInterface, TextsParsingInterface textsParsingInterface) {
        this.createBDLOutputPort = createBDLOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoDocumentInterface = repoDocumentInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    /*/**
     * Creates a new BDL object.
     * @throws IOException if an error occurs while loading or parsing any file.
     * @param BDLpath

    public void createBDL() throws IOException {
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

    public void createBDL(String BDLpath) throws IOException {
        BDL bdl=repoBDLInterface.getBDL();
        for(Document doc: repoDocumentInterface.getDocuments()) {
            String path=doc.getPath();
            String document = repoDocumentInterface.getContentFromPath(path);
            List<DoubleStruct> usedForBDLConstruction=textsParsingInterface.extractFromText(document);
            BDL bdlToMerge=repoBDLInterface.createBDL(usedForBDLConstruction);
            bdl.mergeBDL(bdlToMerge);
        }//for
        repoBDLInterface.saveBDL(bdl, BDLpath);
        repoDocumentInterface.deleteDoc((".\\Discover\\BackupDocument.txt"));
        createBDLOutputPort.showCreateBdl(BDLpath + " has been created into folder Discover");
    }//createBDL

}//CreateBDL
