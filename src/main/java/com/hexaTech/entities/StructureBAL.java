package com.hexaTech.entities;

import java.util.List;

public class StructureBAL {
    String name;
    List<Parameter> parameters;

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

    public String toString(){
        String toReturnString=
                "\""+name+"\": {\n"+
                 "			\"type\": \"object\",\n"+
                 "				\"properties\": {\n";
        int last=parameters.size()-1;
        int count=0;
        for(Parameter parameter: parameters){
            toReturnString+="\""+parameter.getName()+"\":{\n";
            toReturnString+="\"type\":\""+parameter.getType()+"\"\n}";
            if (count<last){
                toReturnString+=",\n";
            }//if
            count+=1;
        }//for
        toReturnString+="\n}\n}";
        return toReturnString;
    }//toString

}//StructureBAL
