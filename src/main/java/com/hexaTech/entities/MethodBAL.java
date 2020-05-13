/**
 * @file MethodBAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import java.util.List;

/**
 * Class used to represent a BAL's method object.
 */
public class MethodBAL {
    String name;
    String description;
    String tag;
    ToReturn toReturn;
    List<Parameter> parameters;

    /**
     * Sets method's name to the new value.
     * @param name string - new name.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets method's description to the new value.
     * @param description string - new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets method's tag to the new value.
     * @param tag string - new tag.
     */
    public void setTags(String tag) {
        this.tag = tag;
    }

    /**
     * Sets method's return type to the new vale.
     * @param toReturn string - return type.
     */
    public void setToRet(ToReturn toReturn) {
        this.toReturn = toReturn;
    }

    /**
     * Returns method parameters' list.
     * @return List<Parameter> - parameters' list.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets method parameters' list to the new vale.
     * @param parameters string - parameters' list.
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters=parameters;
    }

    /**
     * Returns method's content as a string.
     * @return string - method's content.
     */
    public String toString(){
        String toReturnString=
                "		\"/"+name+"\": {\n"+
                        "			\"get\": {\n"+
                        "				\"operationId\": \""+name+"\",\n"+
                        "				\"description\": \""+description+"\",\n"+
                        "      				\"tags\": [\n"+//qua andrebbe una funzione per stampare i tags
                        "       				\""+tag+"\"\n"+
                        "    				 ],\n"+
                        "			        \"parameters\": [\n";
        int last=parameters.size()-1;
        int count=0;
        for(Parameter parametersIterator: parameters){
            toReturnString+=parametersIterator.toString();
            if (count<last){
                toReturnString+=",";
            }//if
            count+=1;
        }//for
        toReturnString+="        			],\n";
        toReturnString+="        			\"responses\":{\n";
        toReturnString+= toReturn.toString();
        toReturnString+="\n}\n}\n}\n";
        return toReturnString;
    }//toString

}//MethodBAL
