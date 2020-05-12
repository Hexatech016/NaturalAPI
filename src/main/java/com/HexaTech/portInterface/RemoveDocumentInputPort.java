/**
 * @file RemoveDocumentInputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.portInterface;

/**
 * RemoveDocument input interface.
 */
public interface RemoveDocumentInputPort {

    /**
     * Deletes the specified document.
     * @param path string - path to the document to be deleted.
     */
    void removeDoc(String path);

}//RemoveDocumentInputPort
