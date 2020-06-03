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


import com.hexaTech.entities.*;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoBDLInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoBOInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoDocumentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Class used to manage a BDL object's creation.
 */
@Component
public class CreateBDL implements CreateBDLInputPort {
    @Autowired
    private final CreateBDLOutputPort createBDLOutputPort;
    @Autowired
    private final RepoBDLInterface repoBDLInterface;
    @Autowired
    private final RepoBOInterface repoBOInterface;
    @Autowired
    private final RepoDocumentInterface repoDocumentInterface;
    @Autowired
    private final TextsParsingInterface textsParsingInterface;

    public CreateBDL(CreateBDLOutputPort createBDLOutputPort, RepoBDLInterface repoBDLInterface, RepoBOInterface repoBOInterface,
                     RepoDocumentInterface repoDocumentInterface, TextsParsingInterface textsParsingInterface) {
        this.createBDLOutputPort = createBDLOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoBOInterface = repoBOInterface;
        this.repoDocumentInterface = repoDocumentInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    public void createBDL(String BDLpath) throws IOException {
        BDL bdl=repoBDLInterface.getBDL();
        BO bo=repoBOInterface.getBoOpenAPI();
        for(Document doc: repoDocumentInterface.getDocuments()) {
            String path=doc.getPath();
            String document = repoDocumentInterface.getContentFromPath(path);
            HashMap<List<DoubleStruct>,List<StructureBAL>> result=textsParsingInterface.extractFromText(document);
            List<DoubleStruct> usedForBDLConstruction=result.entrySet().iterator().next().getKey();
            List<StructureBAL> extractedBO=result.entrySet().iterator().next().getValue();
            BO boToMerge=new BO();
            boToMerge.setBOObjects(extractedBO);
            BDL bdlToMerge=repoBDLInterface.createBDL(usedForBDLConstruction);
            bdl.mergeBDL(bdlToMerge);
            bo.mergeBO(boToMerge);
        }//for
        bo.setOntologyName(BDLpath+"BO");
        repoBDLInterface.saveBDL(BDLpath);
        repoBOInterface.saveBO(BDLpath);
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + " BDL_nouns.csv");
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + " BDL_verbs.csv");
        repoBDLInterface.openFile(".\\Discover\\" + BDLpath + " BDL_preds.csv");
        repoDocumentInterface.deleteDoc((".\\Discover\\BackupDocument.txt"));
        createBDLOutputPort.showCreateBdl(BDLpath + " has been created into folder Discover.\nA business ontology has also been created into the same folder.");
    }//createBDL

}//CreateBDL
