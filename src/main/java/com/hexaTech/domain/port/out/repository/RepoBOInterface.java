/**
 * @file RepoBOInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.port.out.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hexaTech.domain.entity.BO;
import com.hexaTech.domain.entity.Document;

import java.io.IOException;

/**
 * RepoBO class interface.
 */
public interface RepoBOInterface{
    /**
     * Returns BO object.
     * @return BO - BO object.
     */
    Document getBO();

    /**
     * Loads a new document from file system.
     * @param directory string - directory used to save the file.
     * @throws IOException if an error occurs during file loading process.
     * @return boolean - false if something goes wrong, true if not.
     */
    boolean importDoc(String directory,String document) throws IOException;

    String getContentFromPath(String path) throws IOException;

    BO setBOFromJSON(String document) throws JsonProcessingException;

    void setBO(BO bo);

    void saveBO(String BOpath) throws IOException;

    void setBoOpenAPI(BO boOpenAPI);

    BO getBoOpenAPI();
}
