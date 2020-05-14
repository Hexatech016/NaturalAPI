/**
 * @file RepoPLAInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.hexaTech.entities.PLA;

import java.io.IOException;

/**
 * RepoPLA class interface.
 */
public interface RepoPLAInterface extends RepoInterface{

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
     * Saves a new document.
     * @param content string - document content.
     * @param path string - document path.
     */
    void saveOutput(String content,String path);

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
