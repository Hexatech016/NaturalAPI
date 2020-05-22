/**
 * @file BOOject
 * @version 1.0.0
 * @type java
 * @data 2020-05-21
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.entities;

import java.util.*;

/**
 * Class used to represent a BO object.
 */
public class BOObject {
    String nome;
    List<String> BOParams;
    List<String> BOValueTypes;

    /**
     * BO object class empty constructor.
     */
    public BOObject(){}

    /**
     * BO object class constructor.
     * @param nome string - name to give at the BO object.
     * @param BOParams List<BOParam> - list of parameters in the BO object.
     * @param BOValueTypes List<BOParam> - list of types of the parameters in the BO object.
     */
    public BOObject(String nome, List<String> BOParams, List<String> BOValueTypes){
        this.nome=nome;
        this.BOParams=BOParams;
        this.BOValueTypes=BOValueTypes;
    }//BOObject

    /**
     * Returns BO's name.
     * @return string - BO name.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Changes BO's name.
     * @param nome string - name to give at the BO.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Returns BO's parameters names.
     * @return List<String> - BO parameters names.
     */
    public List<String> getBOParams() {
        return BOParams;
    }

    /**
     * Changes BO's parameters names.
     * @param BOParams List<String> - nams to give at the BO parameters.
     */
    public void setBOParams(List<String> BOParams) {
        this.BOParams = BOParams;
    }

    /**
     * Returns BO's parameters value types.
     * @return List<String> - BO parameters value types.
     */
    public List<String> getBOValueTypes() {
        return BOValueTypes;
    }

    /**
     * Changes BO's parameters value types.
     * @param BOValueTypes List<String> - value types to give to the BO parameters.
     */
    public void setBOValueTypes(List<String> BOValueTypes) {
        this.BOValueTypes = BOValueTypes;
    }

    @Override
    public String toString() {
        return "BOObject{" +
                "nome='" + nome + '\'' +
                ", BOParams=" + BOParams +
                ", BOValueTypes=" + BOValueTypes +
                '}';
    }
}
