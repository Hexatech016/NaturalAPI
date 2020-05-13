/**
 * @file RepoInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.hexaTech.entities.API;

import java.io.IOException;

/**
 * Repo interface.
 */
public interface RepoDevelopInterface {

    /**
     * Loads a new BAL from file system.
     * @return bool - false if an error occurs during file loading process, true if not.
     * @throws IOException if an error occurs during backup saving process.
     */
    boolean setBALPath() throws IOException;

    /**
     * Loads a new PLA from file system.
     * @return bool - false if an error occurs during file loading process, true if not.
     */
    boolean setPLAPath();

    /**
     * Extracts output file requested extension from a PLA file.
     * @param path string - PLA's path.
     * @return string - requested output file extension. Empty string if the syntax is invalid.
     * @throws IOException if the specified document doesn't exist.
     */
    String returnPLAExtension(String path) throws IOException;

    /**
     * Saves the BAL path into a backup file.
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveDocument() throws IOException;

    /**
     * Loads content from a backup file and restore it.
     * @throws IOException if the backup file doesn't exist.
     */
    void loadBackUp() throws IOException;

    /**
     * Changes PLA's path value.
     * @param PLA string - new PLA's path.
     */
    void setPLA(String PLA);

    /**
     * Returns BAL's path.
     * @return string - BAL's path.
     */
    String getBAL();

    /**
     * Returns PLA path.
     * @return string - PLA's path.
     */
    String getPLA();

    /**
     * Deletes the specified document.
     * @param doc string - path to the document to be deleted.
     */
    void deleteDocument(String doc);

    /**
     * Extrapolates content from a document.
     * @param path string - document's path.
     * @return string - document's content.
     * @throws IOException if the specified document doesn't exist.
     */
    String returnDocumentContent(String path) throws IOException;

    /**
     * Saves the API object into a new file.
     * @param api API - API object.
     * @param name string - new file name.
     * @param ext string - new file extension.
     * @throws IOException if an error occurs during saving process.
     */
    void saveAPI(API api, String name, String ext) throws IOException;

    /**
     * Verifies if the PLA document still exists.
     * @return boolean - true if the document exists, false if not.
     */
    boolean existsPLA();

    /**
     * Verifies if the PLA document still exists in JAR archive.
     * @return boolean - true if the document exists, false if not.
     */
    boolean existsPLAJar();

    /**
     * Verifies if the BAL document still exists.
     * @return boolean - true if the document exists, false if not.
     */
    boolean existsBAL();

}//RepoInterface
