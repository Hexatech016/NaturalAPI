/**
 * @file AddDocumentInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */
package com.hexaTech.portInterface;

import java.io.IOException;

/**
 * AddDocument input interface.
 */
public interface AddDocumentInputPort {

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     * @param directory string - directory to use.
     */
    void addBAL(String directory) throws IOException;

    /**
     * Loads a new PLA.
     * @throws IOException if an error occurs during loading process.
     */
    void addPLA(String directory) throws IOException;

    /**
     * Updates PLA's reference.
     * @param path string - new PLA's path.
     * @throws IOException if the specified document doesn't exist.
     */
    void updatePLA(String path) throws IOException;

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    void loadBackUp(String directory) throws IOException;

    void existsDoc(String path);

}//AddDocumentInputPort
