/**
 * @file AddBOInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

import java.io.IOException;

/**
 * AddBO input interface.
 */
public interface AddBOInputPort {

    /**
     * Loads a new BO.
     * @throws IOException if an error occurs during loading process.
     */
    void addBO(String directory) throws IOException;

}//AddBOInputPort
