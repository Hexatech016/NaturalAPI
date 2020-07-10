/**
 * @file BAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a BAL object.
 */

public class BAL{
    String name;
    List<MethodBAL> methods;
    List<StructureBAL> structures;

    public BAL(){
        name = "BAL";
        methods=new ArrayList<>();
        structures=new ArrayList<>();
    }

    public BAL(String name){
        this.name = name;
        methods=new ArrayList<>();
        structures=new ArrayList<>();
    }

    public void joinBO(List<StructureBAL> BO){
        structures.addAll(BO);
    }

    /**
     * Sets BAL's methods field with the given ones.
     * @param methods List<MethodBAL> - methods to copy into BAL's field.
     */
    public void setMethods(List<MethodBAL> methods) {
        this.methods = methods;
    }

    public void addStructure(StructureBAL structure){
        structures.add(structure);
    }

    public List<MethodBAL> getMethods(){
        return methods;
    }

    public List<StructureBAL> getStructures(){return structures;}

    /**
     * Returns BAL's content into a string.
     * @return string - BAL's content.
     */
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder toRit=
                new StringBuilder("{"+
                        "	\"openapi\": \"3.0.0\","+
                        "	\"info\": {"+
                        "		\"version\": \"1.0.0\","+
                        "		\"title\": \"" + name +"\","+
                        "		\"license\": {"+
                        "			\"name\": \"MIT\"	"+
                        "		}	"+
                        "	},"+
                        "	\"paths\": {");
        //
        int last=methods.size()-1;
        int count=0;
        for(MethodBAL method: this.methods){
            toRit.append(method.toString());
            if (count<last)
                toRit.append(",");

            count++;
        }//for
        toRit.append("},"+"   \"components\": {"+"   \"schemas\": {");
        last=structures.size()-1;
        count=0;
        for(StructureBAL structure: this.structures){
            toRit.append(structure.toString());
            if(count<last)
                toRit.append(",");
            count++;
        }//for
        toRit.append("}}}");
        try {
            Object jsonObject = mapper.readValue(toRit.toString(), Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }//toString

}//BAL
