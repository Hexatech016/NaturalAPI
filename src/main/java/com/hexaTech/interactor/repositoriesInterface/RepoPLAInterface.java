/**
 * @file RepoPLAInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.repositoriesInterface;

import com.hexaTech.entities.PLA;

import java.io.IOException;

/**
 * RepoPLA class interface.
 */
public interface RepoPLAInterface {

    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @throws IOException if an error occurs during file loading process.
     * @return boolean - false if something goes wrong, true if not.
     */
    boolean importDoc(String directory,String document) throws IOException;

    /**
     * Saves the doucment's path into a backup file.
     * @param title string - title of the file to be saved.
     * @param directory string - directory of the file to be saved
     * @throws IOException if occurs an error while creating the file or writing into it.
     */
    void saveDoc(String title, String directory);

    /**
     * Verifies if the documents exists.
     * @param path string - document's path.
     * @return boolean - true if document exists, false if not.
     */
    boolean existsDoc(String path);

    /**
     * Delete the specified document.
     * @param path string - path to the document to be deleted.
     * @return bool - true if the file exists, false if not.
     */
    boolean deleteDoc(String path);

    /**
     * Gets document's content.
     * @param path string - document path.
     * @return string - document content.
     * @throws IOException if the document doesn't exist.
     */
    String getContentFromPath(String path) throws IOException;

    /**
     * Loads content from a backup file and restore it.
     * @param directory string - directory used to search the file in.
     * @throws IOException if the backup file doesn't exist.
     */
    void loadBackup(String directory) throws IOException;

    /**
     * Returns PLA.
     * @return PLA - PLA value.
     */
    PLA getPLA();

    /**
     * Changes PLA's path value.
     * @param PLA string - new PLA's path.
     */
    void setPLA(PLA PLA);

    /**
     * Verifies if the specified document exists into JAR archive.
     * @param path string - path to the document to be searched.
     * @return boolean - true if the document exists, false if not.
     */
    boolean existsDocJar(String path);

    /**
     * Extracts output file requested extension from a PLA file.
     * @param PLA string - PLA's path.
     * @return string - requested output file extension. Empty string if the syntax is invalid.
     * @throws IOException if the specified document doesn't exist.
     */
    String getExtensionFromPLA(String PLA) throws IOException;

}//RepoPLAInterface
