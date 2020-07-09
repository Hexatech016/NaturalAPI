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
import smile.nlp.collocation.NGram;
import smile.nlp.keyword.CooccurrenceKeywords;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to manage a BDL object's creation.
 */
@Component
public class CreateBDL implements CreateBDLInputPort {
    private final CreateBDLOutputPort createBDLOutputPort;

    private final RepoBDLInterface repoBDLInterface;

    private final RepoBOInterface repoBOInterface;

    private final RepoDocumentInterface repoDocumentInterface;

    private final TextsParsingInterface textsParsingInterface;

    @Autowired
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
            List<DoubleStruct> usedForBDLConstruction=textsParsingInterface.extractBDLFromText(document);
            List<StructureBAL> extractedBO=textsParsingInterface.extractBOFromText(document);
            BO boToMerge=new BO();
            boToMerge.setOntologyObjects(extractedBO);


            NGram[] keyWords = CooccurrenceKeywords.of(document);

            List<String> keyWordList = new ArrayList<>();
            for (NGram keyWord : keyWords) {
                String[] wordArray = keyWord.words;
                String wordString = String.join(" ", wordArray);
                keyWordList.add(wordString);
            }

            BDL bdlToMerge=repoBDLInterface.createBDL(usedForBDLConstruction);
            bdl.mergeBDL(bdlToMerge);
            bdl.removeLowFrequencies();
            bdl.removeIrrelevantPredicates(keyWordList);
            boToMerge.checkElements(bdl);
            bo.mergeBO(boToMerge);



        }//for
        bo.setOntologyName(BDLpath+"BO");
        repoBDLInterface.saveBDL(BDLpath);
        repoBOInterface.saveBO(BDLpath);
        repoBDLInterface.openFile("." + File.separator + "Discover" + File.separator + BDLpath + "BDL_nouns.csv");
        repoBDLInterface.openFile("." + File.separator + "Discover" + File.separator + BDLpath + "BDL_verbs.csv");
        repoBDLInterface.openFile("." + File.separator + "Discover" + File.separator + BDLpath + "BDL_preds.csv");
        repoDocumentInterface.deleteDoc(("." + File.separator + "Discover" + File.separator + "BackupDocument.txt"));
        repoDocumentInterface.makeEmpty();
        createBDLOutputPort.showCreateBdl(BDLpath + " has been created into folder Discover.\nA business ontology has also been created into the same folder.");
    }//createBDL

}//CreateBDL
