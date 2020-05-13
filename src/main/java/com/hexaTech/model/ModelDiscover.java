/**
 * @file Model
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.model;


import com.hexaTech.entities.BDL;
import com.hexaTech.entities.DoubleStruct;
import com.hexaTech.stanford.StanfordInterface;

import java.util.List;

/**
 * Class used to use Stanford's library tools into the project.
 */
public class ModelDiscover implements ModelDiscoverInterface {
    StanfordInterface nlp;

    /**
     * Model class constructor.
     * @param nlp StanfordInterface - used to communicate with Stanford tools class.
     */
    public ModelDiscover(StanfordInterface nlp) {
        this.nlp = nlp;
    }

    /**
     * Fills BDL object with content found into specified documents.
     * @param text string - documents' content.
     * @return BDL - new BDL object.
     */
    @Override
    public BDL extractBDL(String text) {
        BDL bdlToReturn =new BDL();
        List<DoubleStruct> result = nlp.extract(text);
        this.addSost(result, bdlToReturn);
        this.addVerb(result, bdlToReturn);
        this.addPred(result, bdlToReturn);
        return bdlToReturn;
    }

    /**
     * Fills BDL's nouns list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     * @param bdl BDL - element to be filled.
     */
    private void addSost(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("NN"))
                bdl.addNoun(doubleStruct.getLemma(),1);
        }//for
    }//addSost

    /**
     * Fills BDL's verbs list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     * @param bdl BDL - element to be filled.
     */
    private void addVerb(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("VB"))
                bdl.addVerb(doubleStruct.getLemma(),1);
        }//for
    }//addVerb

    /**
     * Fills BDL's predicates list with found elements.
     * @param list List<DoubleStruct> -  extracted elements' list.
     * @param bdl BDL - element to be filled.
     */
    private void addPred(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct doubleStruct : list){
            if(doubleStruct.getToken().contains("obj"))
                bdl.addPredicate(doubleStruct.getLemma(),1);
        }//for
    }//addPred

}//Model
