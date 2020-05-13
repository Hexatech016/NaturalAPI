/**
 * @file ModelInterface
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.model;

import com.hexaTech.entities.BDL;

/**
 * Model class interface.
 */
public interface ModelDiscoverInterface {

    /**
     * Fills BDL object with content found into specified documents.
     * @param text string - documents' content.
     * @return BDL - new BDL object.
     */
    BDL extractBDL(String text);

}//ModelInterface
