/**
 * @file CreateBDL
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.usecase.discover;


import com.hexaTech.domain.entity.BDL;
import com.hexaTech.domain.entity.BO;
import com.hexaTech.domain.entity.Document;
import com.hexaTech.domain.entity.DoubleStruct;
import com.hexaTech.domain.entity.StructureBAL;
import com.hexaTech.domain.port.in.CreateBDLInputPort;
import com.hexaTech.domain.port.out.usecase.CreateBDLOutputPort;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.repository.RepoBOInterface;
import com.hexaTech.domain.port.out.repository.RepoDocumentInterface;
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
            boToMerge.setOntologyObjects(extractedBO);
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
