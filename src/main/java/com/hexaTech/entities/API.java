/**
 * @file API
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Class used to represent an API object.
 */
public class API{
    String APIName;
    String APIComment;
    List<Method> APIMethods;
    List<Structure> APIStructures;

    /**
     * API class empty constructor.
     */
    public API(){}

    /**
     * API class constructor.
     * @param APIName string - name to give at API.
     * @param APIComment string - comment related to API.
     * @param APIMethods List<Method> - API's methods list.
     * @param APIStructures List<Structures> - API's structures list.
     */
    public API(String APIName, String APIComment, List<Method> APIMethods, List<Structure> APIStructures) {
        this.APIName=APIName.substring(0,1).toUpperCase() + APIName.substring(1);
        this.APIComment=APIComment;
        this.APIMethods=APIMethods;
        this.APIStructures=APIStructures;
    }//API

    /**
     * Returns API's name.
     * @return string - API name.
     */
    public String getAPIName() {
        return APIName;
    }

    /**
     * Changes API's name.
     * @param APIName string new API's name.
     */
    public void setAPIName(String APIName) {
        this.APIName = APIName.substring(0,1).toUpperCase() + APIName.substring(1);
    }

    /**
     * Changes API's comment.
     * @param APIComment string - new API's comment.
     */
    public void setAPIComment(String APIComment) {
        this.APIComment = APIComment;
    }

    /**
     * Changes API's methods list.
     * @param APIMethods List<Method> - new API's methods list.
     */
    public void setAPIMethods(List<Method> APIMethods) {
        this.APIMethods=APIMethods;
    }

    /**
     * Changes API's structures list.
     * @param APIStructures List<Structures> - new API's structures list.
     */
    public void setAPIStructures(List<Structure> APIStructures) {
        this.APIStructures=APIStructures;
    }

    /**
     * Replaces PLA's parameters with API's values into a given string.
     * @param PLA string - PLA's parametrized content.
     * @return string - PLA's developed content.
     */
    public String createAPI(String PLA){
        String[] content=PLA.split("\n");
        int startMethod=0,endMethod=0,startStructure=0,endStructure=0,methodHere=0,structureHere=0;
        boolean typed=true;
        String[] types=searchTypes(content);
        for(int i=0;i<content.length;i++){
            if(content[i].contains("<--method.start-->"))
                startMethod=i;
            if(content[i].contains("<--method.end-->"))
                endMethod=i;
            if(content[i].contains("<--struct.start-->"))
                startStructure=i;
            if(content[i].contains("<--struct.end-->"))
                endStructure=i;
            if(content[i].contains("<--struct.here-->"))
                structureHere=i;
            if(content[i].contains("<--method.here-->"))
                methodHere=i;
            if(content[i].contains("<--className-->"))
                content[i]=content[i].replace("<--className-->",APIName);
            if(content[i].contains("<--classComment-->"))
                content[i]=content[i].replace("<--classComment-->",APIComment);
        }//for
        for(String type : types)
            if(type.contains("/")){
                typed=false;
                break;
            }//if
        StringBuilder methods=new StringBuilder();
        StringBuilder structure=new StringBuilder();
        for(Method method:APIMethods)
            methods.append("\t").append(method.createAPI(content.clone(), startMethod, endMethod, types, typed));
        content[methodHere]=methods.toString();

        for(Structure struct:APIStructures)
            structure.append(struct.createAPI(content.clone(), startStructure, endStructure,types,typed));
        content[structureHere]=structure.toString();

        return String.join("\n", ArrayUtils.addAll(Arrays.copyOfRange(content,0,startStructure-1),Arrays.copyOfRange(content,endMethod+1,content.length)));
    }//createAPI

    public String createTests(String PLA) {
        String[] content=PLA.split("\n");
        int startMethod=0,endMethod=0,methodHere=0;
        boolean typed=true;
        String[] types=searchTypes(content);
        for(int i=0;i<content.length;i++){
            if(content[i].contains("<--method.start-->"))
                startMethod=i;
            if(content[i].contains("<--method.end-->"))
                endMethod=i;
            if(content[i].contains("<--method.here-->"))
                methodHere=i;
            if(content[i].contains("<--struct.here-->"))
                content[i]="";
            if(content[i].contains("<--className-->"))
                content[i]=content[i].replace("<--className-->",APIName+"Test");
            if(content[i].contains("<--classComment-->"))
                content[i]=content[i].replace("<--classComment-->",APIComment);
        }//for
        for(String type : types)
            if(type.contains("/")){
                typed=false;
                break;
            }//if
        StringBuilder methods=new StringBuilder();
        for(Method method:APIMethods)
            methods.append("\t").append(method.createTests(content.clone(), startMethod, endMethod, types, typed, getAPIName()));
        content[methodHere]=methods.toString();

        return String.join("\n", Arrays.copyOfRange(content,endMethod+1,content.length));
    }//createTests

    private String[] searchTypes(String[] content) {
        String[] types=new String[4];
        String intType, stringType, floatType, boolType;
        for(int i=0; i<content.length; i++){
            if(content[i].contains("<--integer:")) {
                intType=content[i].substring((content[i].lastIndexOf(":")+1));
                intType=intType.trim();
                types[0]=intType;
            }//if
            if(content[i].contains("<--float:")) {
                floatType=content[i].substring((content[i].lastIndexOf(":")+1));
                floatType=floatType.trim();
                types[1]=floatType;
            }//if
            if(content[i].contains("<--string:")) {
                stringType=content[i].substring((content[i].lastIndexOf(":")+1));
                stringType=stringType.trim();
                types[2]=stringType;
            }//if
            if(content[i].contains("<--boolean:")) {
                boolType=content[i].substring((content[i].lastIndexOf(":")+1));
                boolType=boolType.trim();
                types[3]=boolType;
            }//if
        }//for
        return types;
    }//searchTypes

}//API
