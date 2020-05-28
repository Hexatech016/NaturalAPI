/**
 * @file RepoBOInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.repositoriesInterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hexaTech.interactor.entities.BO;
import com.hexaTech.interactor.entities.Document;
import com.hexaTech.interactor.entities.StructureBAL;

import java.io.IOException;
import java.util.List;

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
    boolean importDoc(String directory) throws IOException;

    String getContentFromPath(String path) throws IOException;

    BO setBOFromJSON(String document) throws JsonProcessingException;

    void setBO(BO bo);

    void saveBo(BO bo);

    void setBoOpenAPI(BO boOpenAPI);

    List<StructureBAL> getBoOpenAPI();
}
