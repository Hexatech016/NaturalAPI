/**
 * @file RepoInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.hexaTech.entities.BAL;
import com.hexaTech.entities.BDL;

import java.io.IOException;
import java.util.List;

/**
 * Repo class interface.
 */
public interface RepoDesignInterface {

    /**
     * Returns documents' list.
     * @return string - documents' list.
     */
    List<String> getList();

    /**
     * Returns loaded text document's path.
     * @return string - document's path.
     */
    void importPath() throws IOException;

    /**
     * Loads a new BDL from file system.
     * @throws IOException if an error occurs during loading.
     */
    void setBDLPath() throws IOException;

    /**
     * Loads a new Gherkin from file system.
     * @throws IOException if an error occurs during loading.
     */
    void setGherkinPath() throws IOException;

    /**
     * Loads content from a backup file and restore it.
     * @throws IOException if the backup file doesn't exist.
     */
    void loadBackUp() throws IOException;

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     */
    boolean deleteDocument(String doc);

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    String returnDocumentContent(String path) throws IOException;

    /**
     * Saves the BDL object into a new file.
     * @param bdl BDL - BDL object.
     * @throws IOException if an error occurs during saving process.
     */
    void saveBDL(BDL bdl) throws IOException;

    /**
     * Saves the BAL object into a new file.
     * @param bal BAL - BAL object.
     * @throws IOException if an error occurs during saving process.
     */
    void saveBAL(BAL bal) throws IOException;

    /**
     * Saves the Gherkin's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveGherkin() throws IOException;

    /**
     * Saves the document's path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveDocument() throws IOException;

}//RepoInterface