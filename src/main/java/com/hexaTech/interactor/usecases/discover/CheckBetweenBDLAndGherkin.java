package com.hexaTech.interactor.usecases.discover;

import com.hexaTech.interactor.entities.BDL;
import com.hexaTech.interactor.entities.DoubleStruct;
import com.hexaTech.interactor.frameworksInterface.TextsParsingInterface;
import com.hexaTech.interactor.frameworksInterface.WordParsingInterface;
import com.hexaTech.interactor.inputportInterface.CheckBetweenBDLAndGherkinInputPort;
import com.hexaTech.interactor.outputportInterface.CheckBetweenBDLAndGherkinOutputPort;
import com.hexaTech.interactor.repositoriesInterface.RepoBDLInterface;
import com.hexaTech.interactor.repositoriesInterface.RepoGherkinInterface;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CheckBetweenBDLAndGherkin implements CheckBetweenBDLAndGherkinInputPort {
    @Autowired
    CheckBetweenBDLAndGherkinOutputPort checkBetweenBDLAndGherkinOutputPort;
    @Autowired
    RepoBDLInterface repoBDLInterface;
    @Autowired
    RepoGherkinInterface repoGherkinInterface;
    @Autowired
    WordParsingInterface wordParsingInterface;
    @Autowired
    TextsParsingInterface textsParsingInterface;

    public CheckBetweenBDLAndGherkin(CheckBetweenBDLAndGherkinOutputPort checkBetweenBDLAndGherkinOutputPort,
                                     RepoBDLInterface repoBDLInterface,
                                     RepoGherkinInterface repoGherkinInterface,
                                     WordParsingInterface wordParsingInterface,
                                     TextsParsingInterface textsParsingInterface) {
        this.checkBetweenBDLAndGherkinOutputPort = checkBetweenBDLAndGherkinOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoGherkinInterface = repoGherkinInterface;
        this.wordParsingInterface = wordParsingInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    public void check(String directory) throws IOException, JWNLException {
        //carico il BDL della repo
        BDL bdlOfTexts=repoBDLInterface.getBDL();
        //creo un nuovo BDL per il Gherkin
        BDL bdlOfGherkin=new BDL();
        String path=repoGherkinInterface.getGherkin().getPath();
        String document = repoGherkinInterface.getContentFromPath(path);
        List<DoubleStruct> usedForBDLConstruction=textsParsingInterface.extractFromText(document);
        BDL bdlToMerge=repoBDLInterface.createBDL(usedForBDLConstruction);
        bdlOfGherkin.mergeBDL(bdlToMerge);

        String log = checkNounsOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkNounsOfGherkin(bdlOfTexts, bdlOfGherkin) +
                checkVerbsOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkVerbsOfGherkin(bdlOfTexts, bdlOfGherkin) +
                checkPredicatesOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkPredicatesOfGherkin(bdlOfTexts, bdlOfGherkin);
        repoBDLInterface.saveDocDiscover(log,"log.txt");
        checkBetweenBDLAndGherkinOutputPort.showCheck("Check complete, you can find log file in Discover");
    }

    private String checkNounsOfBDL(BDL bdlOfTexts,BDL bdlOfGherkin){
        StringBuilder usingWell= new StringBuilder();
        StringBuilder shouldUse=new StringBuilder();
        int totalFrequency=repoBDLInterface.getTotalFrequency(bdlOfTexts.getNouns());
        for (Map.Entry<String, Integer> nounsOfTexts : bdlOfTexts.getNouns().entrySet()) {
            boolean found = false;
            if (nounsOfTexts.getValue()>2 && thisNounIsRelevant(nounsOfTexts.getValue(),totalFrequency)) {
                for (Map.Entry<String, Integer> nounsOfGherkin : bdlOfGherkin.getNouns().entrySet()) {
                    if (nounsOfTexts.getKey().equalsIgnoreCase(nounsOfGherkin.getKey())) {
                        found = true;
                        usingWell.append(nounsOfGherkin.getKey()).append("; ");
                    }
                }
                if(!found)
                    shouldUse.append(nounsOfTexts.getKey()).append("; ");
            }//if
        }//for
        return "NOUNS: \n\n" + "You are using well: \n" + usingWell.toString() + "\n\n" +
                "You could use: \n" + shouldUse.toString() + "\n\n";
    }

    private String checkVerbsOfBDL(BDL bdlOfTexts,BDL bdlOfGherkin){
        StringBuilder usingWell= new StringBuilder();
        StringBuilder shouldUse=new StringBuilder();
        int totalFrequency=repoBDLInterface.getTotalFrequency(bdlOfTexts.getVerbs());
        for (Map.Entry<String, Integer> verbOfTexts : bdlOfTexts.getVerbs().entrySet()) {
            boolean found = false;
            if (verbOfTexts.getValue()>2 && thisVerbIsRelevant(verbOfTexts.getValue(),totalFrequency)) {
                for (Map.Entry<String, Integer> verbOfGherkin : bdlOfGherkin.getVerbs().entrySet()) {
                    if(verbOfTexts.getKey().equalsIgnoreCase(verbOfGherkin.getKey())){
                        found = true;
                        usingWell.append(verbOfGherkin.getKey() + "; ");
                    }
                }
                if(!found)
                    shouldUse.append(verbOfTexts.getKey() + "; ");
            }//if
        }//for
        return "VERBS: \n\n" + "You are using well: \n" + usingWell.toString() + "\n\n" +
                "You could use: \n" + shouldUse.toString() + "\n\n";
    }
    private String checkPredicatesOfBDL(BDL bdlOfTexts,BDL bdlOfGherkin){
        StringBuilder usingWell= new StringBuilder();
        StringBuilder shouldUse=new StringBuilder();
        for (Map.Entry<String, Integer> predsOfTexts : bdlOfTexts.getPredicates().entrySet()) {
            boolean found = false;
            if (predsOfTexts.getValue()>1) {
                for (Map.Entry<String, Integer> predsOfGherkin : bdlOfGherkin.getPredicates().entrySet()) {
                    if(predsOfTexts.getKey().equalsIgnoreCase(predsOfGherkin.getKey())){
                        found = true;
                        usingWell.append(predsOfGherkin.getKey() + "; ");
                    }
                }
                if(!found)
                    shouldUse.append(predsOfTexts.getKey() + "; ");
            }//if
        }//for
        return "PREDICATES: \n\n" + "You are using well: \n" + usingWell.toString() + "\n\n" +
                "You could use: \n" + shouldUse.toString() + "\n\n";
    }

    private String checkNounsOfGherkin(BDL bdlOfTexts,BDL bdlOfGherkin) throws FileNotFoundException, JWNLException {
        StringBuilder notCommon= new StringBuilder();
        StringBuilder alternatives= new StringBuilder();
        for (Map.Entry<String, Integer> nounsOfGherkin : bdlOfGherkin.getNouns().entrySet()) {
            boolean found = false;
                for (Map.Entry<String, Integer> nounsOfTexts : bdlOfTexts.getNouns().entrySet()) {
                    if (nounsOfTexts.getKey().equalsIgnoreCase(nounsOfGherkin.getKey())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (Map.Entry<String, Integer> nounsOfTexts : bdlOfTexts.getNouns().entrySet()) {
                        if (thisNounIsRelevant(nounsOfTexts.getValue(),
                                repoBDLInterface.getTotalFrequency(bdlOfTexts.getNouns())) && wordParsingInterface.
                                thisNounIsASynonymOf(nounsOfGherkin.getKey(),
                                        nounsOfTexts.getKey())) {
                            alternatives.append("You could use " + "\"")
                                    .append(nounsOfTexts.getKey())
                                    .append("\"")
                                    .append(" instead of ")
                                    .append("\"")
                                    .append(nounsOfGherkin.getKey())
                                    .append("\";\n");
                            found = true;
                        }
                    }
                }
                if(!found)
                    notCommon.append(nounsOfGherkin.getKey()).append("; ");
        }//for
        return alternatives.toString() + "\n" + "You are using the following nouns but they are not common: \n"
                + notCommon.toString() + "\n\n";
    }

    private String checkVerbsOfGherkin(BDL bdlOfTexts,BDL bdlOfGherkin) throws FileNotFoundException, JWNLException {
        StringBuilder notCommon= new StringBuilder();
        StringBuilder alternatives= new StringBuilder();
        for (Map.Entry<String, Integer> verbsOfGherkin : bdlOfGherkin.getVerbs().entrySet()) {
            boolean found = false;
            for (Map.Entry<String, Integer> verbsOfTexts : bdlOfTexts.getVerbs().entrySet()) {
                if(verbsOfTexts.getKey().equalsIgnoreCase(verbsOfGherkin.getKey())){
                    found = true;
                    break;
                }
            }
            if (!found) {
                for (Map.Entry<String, Integer> verbsOfTexts : bdlOfTexts.getVerbs().entrySet()) {
                    if (thisNounIsRelevant(verbsOfTexts.getValue(),
                            repoBDLInterface.getTotalFrequency(bdlOfTexts.getVerbs())) && wordParsingInterface.
                            thisVerbIsASynonymOf(verbsOfGherkin.getKey(),
                                    verbsOfTexts.getKey())) {
                        alternatives.append("You could use " + "\"")
                                .append(verbsOfTexts.getKey())
                                .append("\"")
                                .append(" instead of ")
                                .append("\"")
                                .append(verbsOfGherkin.getKey())
                                .append("\";\n");
                        found = true;
                    }
                }
            }
            if(!found)
                notCommon.append(verbsOfGherkin.getKey()).append("; ");
        }//for
        return alternatives.toString() + "\n"
                + "You are using the following verbs but they are not common: \n"
                + notCommon.toString() + "\n\n";
    }

    private String checkPredicatesOfGherkin(BDL bdlOfTexts,BDL bdlOfGherkin){
        StringBuilder notCommon= new StringBuilder();
        for (Map.Entry<String, Integer> predsOfGherkin : bdlOfGherkin.getPredicates().entrySet()) {
            boolean found = false;
            for (Map.Entry<String, Integer> predsOfTexts : bdlOfTexts.getPredicates().entrySet()) {
                if(predsOfTexts.getKey().equalsIgnoreCase(predsOfGherkin.getKey())){
                    found = true;
                }
            }
            if(!found)
                notCommon.append(predsOfGherkin.getKey()).append("; ");
        }//for
        return "You are using the following predicates but they are not common: \n" + notCommon.toString() + "\n\n";
    }

    private boolean thisNounIsRelevant(int value, int totalFrequency){
        if(value*1.0/totalFrequency*100>1)
            return true;
        return false;
    }
    private boolean thisVerbIsRelevant(int value, int totalFrequency){
        if(value*1.0/totalFrequency*100>0.5)
            return true;
        return false;
    }

}