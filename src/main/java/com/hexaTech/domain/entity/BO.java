/**
 * @file BO
 * @version 1.0.0
 * @type java
 * @data 2020-05-21
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.entity;

import java.util.*;

/**
 * Class used to represent a BO.
 */

public class BO {
    String ontologyName="";
    List<StructureBAL> ontologyObjects= new ArrayList<StructureBAL>();

    /**
     * BO class empty constructor.
     */
    public BO(){}

    /**
     * BO class constructor.
     * @param ontologyName string - name to give at the BO.
     * @param ontologyObjects List<BOObject> - list of objects in the BO.
     */
    public BO(String ontologyName, List<StructureBAL> ontologyObjects){
        this.ontologyObjects=ontologyObjects;
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
    public List<StructureBAL> getOntologyObjects() {
        return ontologyObjects;
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
     * @param ontologyObjects List<BOObject> - list of objects in the BO.
     */
    public void setOntologyObjects(List<StructureBAL> ontologyObjects) {
        this.ontologyObjects=ontologyObjects;
    }

    public void setBOObjects(StructureBAL BOObject) {
        boolean found=false;
        for(StructureBAL structureBAL: this.ontologyObjects) {
            if (structureBAL.getName().equalsIgnoreCase(BOObject.getName())) {
                structureBAL.addParameters(BOObject.getParameters());
                found = true;
                break;
            }//if
        }
        if(!found)
            this.ontologyObjects.add(BOObject);
    }//setBOObjects

    @Override
    public String toString() {
        return "BO{" +
                "ontologyName='" + ontologyName + '\'' +
                ", ontologyObjects=" + ontologyObjects.toString() +
                '}';
    }

    public void mergeBO(BO newBO){
        for(StructureBAL structureBAL: newBO.getOntologyObjects())
            this.setBOObjects(structureBAL);
    }

    public void checkElements(BDL bdl){
        ontologyObjects.removeIf(
                word -> !bdl.getNouns().containsKey(word.getName())
                && !bdl.getNouns().containsKey(word.getName().substring(0,1).toUpperCase() + word.getName().substring(1))
                && !bdl.getNouns().containsKey(word.getName().substring(0,1).toLowerCase() + word.getName().substring(1))
                && !bdl.getNouns().containsKey(word.getName().toLowerCase())
                && !bdl.getNouns().containsKey(word.getName().toUpperCase())
        );
    }//checkElements

}//BO