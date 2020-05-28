/**
 * @file CreateBDLInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.portInterface;

import java.io.IOException;

/**
 * CreateBDL input interface.
 */
public interface CreateBDLInputPort {

    /**
     * Creates a new BDL object.
     * @throws IOException if an error occurs while loading or parsing any file.
     * @param BDLpath
     */
    void createBDL(String BDLpath) throws IOException;

}//CreateBDLInputPort
