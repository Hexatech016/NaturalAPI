/**
 * @file BO
 * @version 1.0.0
 * @type java
 * @data 2020-05-21
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class used to represent a BO.
 */
@Component
public class BO {
    String ontologyName;
    List<StructureBAL> BOObjects= new ArrayList<StructureBAL>();

    /**
     * BO class empty constructor.
     */
    public BO(){}

    /**
     * BO class constructor.
     * @param ontologyName string - name to give at the BO.
     * @param BOObjects List<BOObject> - list of objects in the BO.
     */
    public BO(String ontologyName, List<StructureBAL> BOObjects){
        this.BOObjects=BOObjects;
        this.ontologyName=ontologyName;
    }//BO

    /**
     * Returns BO's name.
     * @return string - BO name.
     */
    public String getOntologyName() {
        return ontologyName;
    }

    /**
     * Returns BO's objects.
     * @return string - BO objects.
     */
    public List<StructureBAL> getBOObjects() {
        return BOObjects;
    }

    /**
     * Changes BO's name.
     * @param ontologyName string - name to give at the BO.
     */
    public void setOntologyName(String ontologyName) {
        this.ontologyName = ontologyName;
    }

    /**
     * Changes BO's list of objects.
     * @param BOObjects List<BOObject> - list of objects in the BO.
     */
    public void setBOObjects(List<StructureBAL> BOObjects) {
        this.BOObjects = BOObjects;
    }

    public void setBOObjects(StructureBAL BOObject) {
        boolean found=false;
        for(StructureBAL structureBAL:BOObjects)
            if(structureBAL.getName().equalsIgnoreCase(BOObject.getName())){
                structureBAL.addParameters(BOObject.getParameters());
                found=true;
                break;
            }
        if(!found)
            this.BOObjects.add(BOObject);
    }

    @Override
    public String toString() {
        return "BO{" +
                "ontologyName='" + ontologyName + '\'' +
                ", BOObjects=" + BOObjects.toString() +
                '}';
    }

    public void mergeBO(BO newBO){
        for(StructureBAL structureBAL: newBO.getBOObjects())
            this.setBOObjects(structureBAL);
    }

}//BO