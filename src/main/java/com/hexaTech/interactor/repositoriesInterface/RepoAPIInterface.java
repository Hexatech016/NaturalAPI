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

import com.hexaTech.interactor.entities.API;

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

}//RepoAPIInterface
