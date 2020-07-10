package com.hexaTech.domain.entity;

import java.util.ArrayList;
import java.util.List;


public class StructureBAL {
    String name;
    List<Parameter> parameters=new ArrayList<Parameter>();

    public StructureBAL(){}

    public StructureBAL(String name, List<Parameter> parameters) {
        this.name=name;
        this.parameters=parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters=parameters;
    }

    public void addParameters(List<Parameter> parameters){
        boolean found=false;
        for(Parameter parameterExt:parameters){
            for(Parameter parameter:this.parameters){
                if(parameter.getName().equalsIgnoreCase(parameterExt.getName())){
                    found=true;
                }//if
            }//for
            if(!found)
                this.parameters.add(parameterExt);
            found=false;
        }//for
    }//addParameters

    public void setParameters(Parameter parameter) {
        this.parameters.add(parameter);
    }

    public String toString(){
        String toReturnString=
                "\""+name+"\": {"+
                 "			\"type\": \"object\","+
                 "				\"properties\": {";
        int last=parameters.size()-1;
        int count=0;
        for(Parameter parameter: parameters){
            toReturnString+="\""+parameter.getName()+"\":{";
            toReturnString+="\"type\":\""+parameter.getType()+"\"}";
            if (count<last){
                toReturnString+=",";
            }//if
            count+=1;
        }//for
        toReturnString+="}}";
        return toReturnString;
    }//toString

}//StructureBAL
