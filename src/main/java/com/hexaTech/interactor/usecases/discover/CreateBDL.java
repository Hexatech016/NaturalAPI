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
import com.hexaTech.interactor.inputportInterface.CreateBDLInputPort;
import com.hexaTech.interactor.outputportInterface.CreateBDLOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBDLInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoDocumentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Class used to manage a BDL object's creation.
 */
@Component
public class CreateBDL implements CreateBDLInputPort {
    @Autowired
    CreateBDLOutputPort createBDLOutputPort;
    @Autowired
    RepoBDLInterface repoBDLInterface;
    @Autowired
    RepoDocumentInterface repoDocumentInterface;
    @Autowired
    TextsParsingInterface textsParsingInterface;

    public CreateBDL(CreateBDLOutputPort createBDLOutputPort, RepoBDLInterface repoBDLInterface,
                     RepoDocumentInterface repoDocumentInterface, TextsParsingInterface textsParsingInterface) {
        this.createBDLOutputPort = createBDLOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoDocumentInterface = repoDocumentInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

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
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + "BDLsost.csv");
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + "BDLverbs.csv");
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + "BDLpred.csv");
        repoDocumentInterface.deleteDoc((".\\Discover\\BackupDocument.txt"));
        createBDLOutputPort.showCreateBdl(BDLpath + " has been created into folder Discover");
    }//createBDL

}//CreateBDL
