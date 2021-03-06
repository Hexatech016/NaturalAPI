package com.hexaTech.domain.usecase.discover;

import com.hexaTech.domain.entity.BDL;
import com.hexaTech.domain.entity.DoubleStruct;
import com.hexaTech.domain.port.in.CheckBetweenBDLAndGherkinInputPort;
import com.hexaTech.domain.port.out.usecase.CheckBetweenBDLAndGherkinOutputPort;
import com.hexaTech.domain.port.out.framework.TextsParsingInterface;
import com.hexaTech.domain.port.out.repository.RepoBDLInterface;
import com.hexaTech.domain.port.out.repository.RepoGherkinInterface;
import net.didion.jwnl.JWNLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CheckBetweenBDLAndGherkin implements CheckBetweenBDLAndGherkinInputPort {
    private final CheckBetweenBDLAndGherkinOutputPort checkBetweenBDLAndGherkinOutputPort;

    private final RepoBDLInterface repoBDLInterface;

    private final RepoGherkinInterface repoGherkinInterface;

    private final TextsParsingInterface textsParsingInterface;

    private Integer matches=0;

    private Integer toCheck=0;

    @Autowired
    public CheckBetweenBDLAndGherkin(CheckBetweenBDLAndGherkinOutputPort checkBetweenBDLAndGherkinOutputPort,
                                     RepoBDLInterface repoBDLInterface,
                                     RepoGherkinInterface repoGherkinInterface,
                                     TextsParsingInterface textsParsingInterface) {
        this.checkBetweenBDLAndGherkinOutputPort = checkBetweenBDLAndGherkinOutputPort;
        this.repoBDLInterface = repoBDLInterface;
        this.repoGherkinInterface = repoGherkinInterface;
        this.textsParsingInterface = textsParsingInterface;
    }

    public void check(String directory) throws IOException {
        BDL bdlOfTexts=repoBDLInterface.getBDL();
        BDL bdlOfGherkin=new BDL();
        String path=repoGherkinInterface.getGherkin().getPath();
        String document = repoGherkinInterface.getContentFromPath(path);
        List<DoubleStruct> usedForBDLConstruction=textsParsingInterface.extractBDLFromGherkin(document);
        BDL bdlToMerge=repoBDLInterface.createBDL(usedForBDLConstruction);
        bdlOfGherkin.mergeBDL(bdlToMerge);

        String log = checkNounsOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkNounsOfGherkin(bdlOfTexts, bdlOfGherkin) +
                checkVerbsOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkVerbsOfGherkin(bdlOfTexts, bdlOfGherkin) +
                checkPredicatesOfBDL(bdlOfTexts, bdlOfGherkin) +
                checkPredicatesOfGherkin(bdlOfTexts, bdlOfGherkin);
        repoBDLInterface.saveDocDiscover(log,"report.txt");
        checkBetweenBDLAndGherkinOutputPort.showCheck(ratingMatch());
        repoBDLInterface.openFile("." + File.separator + "Discover" + File.separator + "report.txt");
        repoGherkinInterface.deleteDoc(("." + File.separator + "Design" + File.separator + "BackupGherkin.txt"));
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
        return "NOUNS: \n\n" + "The following nouns you use in your Gherkin correspond to the Business Domain Language: \n" + usingWell.toString() + "\n\n" +
                "You may need to use the following nouns in your Gherkin: \n" + shouldUse.toString() + "\n\n";
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
                        usingWell.append(verbOfGherkin.getKey()).append("; ");
                    }
                }
                if(!found)
                    shouldUse.append(verbOfTexts.getKey()).append("; ");
            }//if
        }//for
        return "VERBS: \n\n" + "The following verbs you use in your Gherkin correspond to the Business Domain Language: \n" + usingWell.toString() + "\n\n" +
                "You may need to use the following verbs in your Gherkin: \n" + shouldUse.toString() + "\n\n";
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
                        usingWell.append(predsOfGherkin.getKey()).append("; ");
                    }
                }
                if(!found)
                    shouldUse.append(predsOfTexts.getKey()).append("; ");
            }//if
        }//for
        return "PREDICATES: \n\n" +
                "The following predicates you use in your Gherkin correspond to the Business Domain Language: \n" +
                usingWell.toString() + "\n\n" +
                "You may need to use the following predicates in your Gherkin: \n" + shouldUse.toString() + "\n\n";
    }

    private String checkNounsOfGherkin(BDL bdlOfTexts, BDL bdlOfGherkin) {
        StringBuilder notCommon= new StringBuilder();
        StringBuilder alternatives= new StringBuilder();
        for (Map.Entry<String, Integer> nounsOfGherkin : bdlOfGherkin.getNouns().entrySet()) {
            boolean found = false;
            toCheck++;
                for (Map.Entry<String, Integer> nounsOfTexts : bdlOfTexts.getNouns().entrySet()) {
                    if (nounsOfTexts.getKey().equalsIgnoreCase(nounsOfGherkin.getKey())) {
                        found = true;
                        matches++;
                        break;
                    }
                }
                    if (!found && nounsOfGherkin.getValue()>1)
                        notCommon.append(nounsOfGherkin.getKey()).append("; ");
        }//for
        return alternatives.toString() + "\n" + "You are using more than one time the following nouns but they don't correspond to " +
                "the Business Domain Language: \n"
                + notCommon.toString() + "\n\n";
    }

    private String checkVerbsOfGherkin(BDL bdlOfTexts,BDL bdlOfGherkin) {
        StringBuilder notCommon= new StringBuilder();
        StringBuilder alternatives= new StringBuilder();
        for (Map.Entry<String, Integer> verbsOfGherkin : bdlOfGherkin.getVerbs().entrySet()) {
            boolean found = false;
            toCheck++;
            for (Map.Entry<String, Integer> verbsOfTexts : bdlOfTexts.getVerbs().entrySet()) {
                if(verbsOfTexts.getKey().equalsIgnoreCase(verbsOfGherkin.getKey())){
                    found = true;
                    matches++;
                    break;
                }
            }

            if(!found && verbsOfGherkin.getValue()>1)
                notCommon.append(verbsOfGherkin.getKey()).append("; ");
        }//for
        return alternatives.toString() + "\n" +
                "You are using more than one time the following verbs but they don't correspond to " +
                "the Business Domain Language: \n"
                + notCommon.toString() + "\n\n";
    }

    private String checkPredicatesOfGherkin(BDL bdlOfTexts,BDL bdlOfGherkin){
        StringBuilder notCommon= new StringBuilder();
        for (Map.Entry<String, Integer> predsOfGherkin : bdlOfGherkin.getPredicates().entrySet()) {
            boolean found = false;
            toCheck++;
            for (Map.Entry<String, Integer> predsOfTexts : bdlOfTexts.getPredicates().entrySet()) {
                if(predsOfTexts.getKey().equalsIgnoreCase(predsOfGherkin.getKey())){
                    found = true;
                    matches++;
                }
            }
            if(!found && predsOfGherkin.getValue()>1)
                notCommon.append(predsOfGherkin.getKey()).append("; ");
        }//for
        return "You are using more than one time the following predicates but they don't correspond to " +
                "the Business Domain Language: \n" + notCommon.toString() + "\n\n";
    }

    private boolean thisNounIsRelevant(int value, int totalFrequency){
        return value * 1.0 / totalFrequency * 100 > 1;
    }

    private boolean thisVerbIsRelevant(int value, int totalFrequency){
        return value * 1.0 / totalFrequency * 100 > 0.5;
    }

    private String ratingMatch(){
        int result= (matches * 100 /  toCheck) + 20;
        if (result>100)
                result=100;
        String toPrint;
        if(result<25)
            toPrint="\tBe careful, there is no match!\n";
        else if (result<50)
            toPrint="\tYou have to work a little more on your Gherkin!\n";
        else if (result<75)
            toPrint="\tAcceptable match but you could make some adjustments!\n";
        else
            toPrint="\tWell done!\n\n";
        return "\n\tThe match percentage between your Gherkin scenario and Business Domain Language is: " + result + "%\n" +
                toPrint + "\tYou can find the complete analysis in report file inside Discover directory.\n";
    }

}