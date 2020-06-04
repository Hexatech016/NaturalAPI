/**
 * @file Parameter
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.entity;

import org.springframework.stereotype.Component;

/**
 * Class used to represent parameter object.
 */
@Component
public class Parameter {
    String description="";
    String name;
    String type="string";

    public Parameter(){}

    public Parameter(String description, String name, String type) {
        this.description=description;
        this.name=name;
        this.type=type;
    }

    /**
     * Returns parameter's description.
     * @return string - description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets parameter's description to the new value.
     * @param description string - new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns parameter's name.
     * @return string - name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets parameter's name to the new value.
     * @param name string - new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns parameter's type.
     * @return string - type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets parameter's type to the new value.
     * @param type string - new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns parameter's content as a string.
     * @return string - parameter's content.
     */
    public String toString(){
        return "         				{\n"+
                "            				\"in\": \"query\",\n"+
                "            				\"name\": \""+name+"\",\n"+
                "            				\"description\": \""+description+"\",\n"+
                "            				\"required\": true,\n"+
                "                           \"schema\": {\n"+
                "             				    \"type\": \""+type+"\" \n"+
                "                           }\n	"+
                "         				}\n";

    }//toString


}//Parameter
