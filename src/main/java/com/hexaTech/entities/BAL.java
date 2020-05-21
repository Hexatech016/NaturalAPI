/**
 * @file BAL
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a BAL object.
 */
public class BAL{
    List<MethodBAL> methods;
    List<StructureBAL> structures;

    public BAL(){
        methods=new ArrayList<>();
        structures=new ArrayList<>();
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
        String toRit=
                "{\n"+
                        "	\"openapi\": \"3.0.0\",\n"+
                        "	\"info\": {\n"+
                        "		\"version\": \"1.0.0\",\n"+
                        "		\"title\": \"Test\",\n"+
                        "		\"license\": {\n"+
                        "			\"name\": \"MIT\"\n	"+
                        "		}\n	"+
                        "	},\n"+
                        "	\"paths\": {\n";
        //
        int last=methods.size()-1;
        int count=0;
        for(MethodBAL method: this.methods){
            toRit+=method.toString();
            if (count<last)
                toRit+=",";

            count++;
        }//for
        toRit+="\n}," +
                "   \"components\": {\n" +
                "   \"schemas\": {\n";
        last=structures.size()-1;
        count=0;
        for(StructureBAL structure: this.structures){
            toRit+=structure.toString();
            if(count<last)
                toRit+=",";
            count++;
        }//for
        toRit+="\n}\n}\n}";
        return toRit;
    }//toString

}//BAL
