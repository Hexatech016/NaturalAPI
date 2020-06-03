/**
 * @file AddPLAInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */
package com.hexaTech.interactor.usecases.develop;

import java.io.IOException;

/**
 * AddPLA input interface.
 */
public interface AddPLAInputPort {

    /**
     * Loads a new PLA.
     * @throws IOException if an error occurs during loading process.
     */
    void addPLA(String directory,String document) throws IOException;

    /**
     * Updates PLA's reference.
     * @param path string - new PLA's path.
     * @throws IOException if the specified document doesn't exist.
     */
    void updatePLA(String path) throws IOException;

    /**
     * Check if a document exists.
     * @param path string - path to the file.
     */
    void existsDoc(String path);

    /**
     * Delete a document.
     * @param path string - path to the file.
     */
    void deleteDoc(String path);

}//AddDocumentInputPort
