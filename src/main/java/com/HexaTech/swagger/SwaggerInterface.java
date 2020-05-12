/**
 * @file SwaggerInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.swagger;

import com.HexaTech.entities.Method;
import com.HexaTech.entities.Structure;

import java.util.List;

/**
 * Swagger interface.
 */
public interface SwaggerInterface{

    /**
     * Extracts name specified into BAL document.
     * @param path string - BAL path.
     * @return string - name.
     */
    String extractAPIName(String path) throws IllegalArgumentException;

    /**
     * Extracts description specified into BAL document.
     * @param path string - BAL path.
     * @return string - description.
     */
    String extractAPIDescription(String path) throws IllegalArgumentException;

    /**
     * Extracts methods specified into BAL document.
     * @param path string - BAL path.
     * @return List<Method> - a list of methods found. null if an error occurs.
     * @throws IllegalArgumentException if the content of the BAL file isn't valid.
     */
    List<Method> extractAPIMethods(String path) throws IllegalArgumentException;

    /**
     * Extracts structures specified into BAL document.
     * @param path string - BAL path. null if an error occurs.
     * @return List<Structures> - a list of methods found.
     */
    List<Structure> extractAPIStructures(String path) throws IllegalArgumentException;

}//SwaggerInterface
