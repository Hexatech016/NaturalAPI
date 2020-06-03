/**
 * @file RepoAPIInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.repositoriesInterface;

import com.hexaTech.entities.API;

import java.io.IOException;

/**
 * RepoAPI class interface.
 */
public interface RepoAPIInterface{

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param newAPI API - new API object.
     */
    void setAPI(API newAPI);

    API getAPI();

    void saveOutput(String content,String path);

    void openFile(String path) throws IOException;

}//RepoAPIInterface
