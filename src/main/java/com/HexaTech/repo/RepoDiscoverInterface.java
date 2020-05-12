/**
 * @file RepoInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.repo;

import com.HexaTech.entities.BDL;

import java.io.IOException;
import java.util.List;

/**
 * Repo class interface.
 */
public interface RepoDiscoverInterface {

    /**
     * Loads a new document from file system.
     * @throws IOException if an error accurs during file loading process.
     */
    void returnPath() throws IOException;

    /**
     * Saves the doucment's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveDoc() throws IOException;

    /**
     * Loads content from a backup file and restore it.
     * @throws IOException if the backup file doesn't exist.
     */
    void loadBackUp() throws IOException;

    /**
     * Returns the list of added documents' paths.
     * @return List<String> - added documents.
     */
    List<String> getLista();

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     * @return boolean - false if an error occurs, true if not.
     */
    boolean delete(String doc);

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    String returnContentFromTxt(String path) throws IOException;

    /**
     * Saves the BDL object into different files.
     * @param bdl BDL - object to be saved.
     * @throws IOException
     */
    void saveBDL(BDL bdl) throws IOException;

    /**
     * Loads BDL content from different files and store a new BDL object.
     * @throws IOException if an error occurs while loading one or more file.
     */
    void getBDLFromContentPath() throws IOException;

    /**
     * Verifies is the backup document still exists.
     * @return boolean - true if the document exists, false if not.
     */
    boolean checkThereAreDoc();

}//RepoInterface