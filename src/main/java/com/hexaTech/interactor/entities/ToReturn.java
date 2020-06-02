/**
 * @file ToReturn
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Denis Salviato
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.entities;

import org.springframework.stereotype.Component;

/**
 * Class to represent return object.
 */
@Component
public class ToReturn {
    String response="200";
    String description="toRet.des";
    String type="string";;

    public ToReturn(){}

    public ToReturn(String type){
        this.type=type;
    }

    /**
     * Sets return's description to the new value.
     * @param description string - new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns return's type.
     * @return string - type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets return's type to the new value.
     * @param type string - new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns return's content as a string.
     * @return string - return's content.
     */
    public String toString(){
        return  "          				\""+response+"\": {\n"+
                "           					\"description\": \""+description+"\",\n"+
                "                               \"content\": {\n"+
                "                                   \"*/*\":  {\n"+
                "            					         \"schema\": {\n"+
                "              						         \"type\": \""+type+"\"\n"+
                "            					         }\n"+
                "                                   }\n"+
                "                              }\n"+
                "          				}\n";
    }//toString

}//ToReturn
