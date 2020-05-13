/**
 * @file Model
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.model;

import com.hexaTech.entities.*;
import com.hexaTech.stanford.StanfordInterface;

import java.util.ArrayList;

/**
 * Class used to use Stanford's library tools into the project.
 */
public class ModelDesign implements ModelDesignInterface {
    StanfordInterface nlp;

    /**
     * Model's class constructor.
     * @param nlp StanfordInterface - used to communicate with Stanford class.
     */
    public ModelDesign(StanfordInterface nlp){
        this.nlp=nlp;
    }

    /**
     * Splits the given text into an array of strings.
     * @param text string - text.
     * @return string[] - array of strings.
     */
    private String[] getTextSplit(String text){
        String[] gherkinSplit;
        String delimiter = "[\n]+[\n]";
        gherkinSplit = text.split(delimiter);
        String part ="";
        return gherkinSplit;
    }

    /**
     * Fills a BAL object with the given text parsed elements.
     * @param text string - text to be parsed.
     * @return BAL - filled BAL object.
     */
    public BAL setBALFromGherkin(String text){
        BAL baLjSon = new BAL();
        ArrayList<MethodBAL> methods = new ArrayList<MethodBAL>();
        String[] gherkinSplit = getTextSplit(text);
        for (String temp: gherkinSplit) {
            MethodBAL meth = new MethodBAL();
            Gherkin gherkin = nlp.extractGherkin(temp);
            meth.setName(gherkin.getScenario());
            meth.setDescription(gherkin.getDescription());
            meth.setTags("-");
            ToReturn toRet=new ToReturn();
            toRet.setDescription(gherkin.getThen());
            meth.setToRet(toRet);
            ArrayList<Parameter> params = new ArrayList<Parameter>();
            for(String parameter : gherkin.getWhen()){
                Parameter param = new Parameter();
                param.setDescription("Default");
                param.setName(parameter);
                param.setType("string");
                params.add(param);
            }//for
            meth.setParameters(params);
            methods.add(meth);
        }//for
        baLjSon.setMethods(methods);
        return baLjSon;
    }//setBALFromGherkin


    /*@Override
    public BDL extract(String text) {
        BDL bdl=new BDL();
        //List<DoubleToken> result = nlp.extractGherkin(text);
        //this.addNoun(result,bdl);
        //this.addVerb(result,bdl);
        //this.addPred(result,bdl);
        return bdl;
    }

    public void addNoun(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct d: list){
            if(d.getToken().contains("NN"))
                bdl.addNoun(d.getLemma(),1);
        }
    }
    public void addVerb(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct d: list){
            if(d.getToken().contains("VB"))
                bdl.addVerb(d.getLemma(),1);
        }
    }
    public void addPred(List<DoubleStruct> list, BDL bdl){
        for(DoubleStruct d: list){
            if(d.getToken().contains("obj"))
                bdl.addPredicate(d.getLemma(),1);
        }
    }*/

}//Model
