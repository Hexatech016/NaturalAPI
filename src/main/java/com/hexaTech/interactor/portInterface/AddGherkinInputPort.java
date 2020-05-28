/**
 * @file AddGherkinInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Gerardo Kokoshari
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.portInterface;

import java.io.IOException;

/**
 * AddGherkin input interface.
 */
public interface AddGherkinInputPort {

    /**
     * Loads a new Gherkin scenario.
     * @throws IOException if an error occurs during loading process.
     */
    void addGherkin(String directory,String document) throws IOException;

    void loadBackUp(String directory) throws IOException;

    /**
     * Delete a document.
     * @param path string - path to the file.
     */
    void deleteDoc(String path);

    /**
     * Check if a document exists.
     * @param path string - path to the file.
     */
    void existsDoc(String path);

}//AddGherkinInputInterface
