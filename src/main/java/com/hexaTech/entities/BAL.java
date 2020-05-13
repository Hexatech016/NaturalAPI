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

import java.util.List;

/**
 * Class used to represent a BAL object.
 */
public class BAL {
    String intro;
    List<MethodBAL> methods;

    /**
     * Sets BAL's methods field with the given ones.
     * @param methods List<MethodBAL> - methods to copy into BAL's field.
     */
    public void setMethods(List<MethodBAL> methods) {
        this.methods = methods;
    }

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
            if (count<last){
                toRit+=",";
            }//if
            count+=1;
        }//for
        toRit+="}\n}";
        return toRit;
    }//toString

}//BAL
