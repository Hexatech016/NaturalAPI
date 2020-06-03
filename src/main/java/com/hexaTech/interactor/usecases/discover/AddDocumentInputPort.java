/**
 * @file AddDocToParseInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.discover;

import java.io.IOException;

/**
 * AddDocToParse input interface.
 */
public interface AddDocumentInputPort {

    /**
     * Verifies if there are any loaded documents.
     * @param path
     */
    void checkThereAreDoc(String path);

    /**
     * Loads a new document.
     * @throws IOException if an error occurs during loading process.
     */
    void addDocument(String directory,String document) throws IOException;

    /**
     * Load a backup file.
     * @throws IOException if the file doesn't exist.
     */
    void loadBackUp(String directory) throws IOException;

    /**
     * Delete the specified document.
     * @param path string - document to be deleted.
     */
    void deleteDocs(String path);

}//AddDocToParseInputPort
