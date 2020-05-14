/**
 * @file RepoAPIInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-13
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.repo;

import com.hexaTech.entities.API;

/**
 * RepoAPI class interface.
 */
public interface RepoAPIInterface{

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param path string - PLA's path.
     * @return API - new API object.
     */
    API setAPI(String path) throws IllegalArgumentException;

}//RepoAPIInterface
