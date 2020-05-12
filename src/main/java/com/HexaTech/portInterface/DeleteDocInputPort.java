/**
 * @file DeleteDocInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Eduard Serban
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.portInterface;

/**
 * DeleteDoc input interface.
 */
public interface DeleteDocInputPort {

    /**
     * Delete the specified document.
     * @param path string - document to be deleted.
     */
    void deleteDocs(String path);

}//DeleteDocInputPort
