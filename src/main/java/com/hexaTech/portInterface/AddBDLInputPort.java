/**
 * @file AddBDLInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

import java.io.IOException;

/**
 * AddBDL input interface.
 */
public interface AddBDLInputPort {

    /**
     * Loads a new BDL.
     * @throws IOException if an error occurs during loading process.
     */
    void addBDL(String directory) throws IOException;

}//AddBDLInputPort
