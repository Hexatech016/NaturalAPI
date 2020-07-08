/**
 * @file AddBALInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.port.in;

import java.io.IOException;

/**
 * AddBAL input interface.
 */
public interface AddBALInputPort {

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     * @param directory string - directory to use.
     */
    void addBAL(String directory,String document) throws IOException;

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    void loadBackUp(String directory) throws IOException;

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

    void checkIfRepoBALIsEmpty();

}//AddBALInputInterface
