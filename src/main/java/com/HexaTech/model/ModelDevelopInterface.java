/**
 * @file ModelInterface
 * @version 1.0.0
 * @type java
 * @data 2020-05-01
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.model;

import com.HexaTech.entities.API;

/**
 * Model interface.
 */
public interface ModelDevelopInterface{

    /**
     * Fills API object with Swagger found arguments into the specified PLA.
     * @param path string - PLA's path.
     * @return API - new API object.
     */
    API setAPI(String path);

}//ModelInterface
