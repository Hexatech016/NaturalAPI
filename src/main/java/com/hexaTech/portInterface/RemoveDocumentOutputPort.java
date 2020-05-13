/**
 * @file RemoveDocumentOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

/**
 * RemoveDocument output interface.
 */
public interface RemoveDocumentOutputPort{

    /**
     * Notifies the message from removing document action.
     * @param result string - message text.
     */
    void showRemovedDoc(String result);

}//RemoveDocumentOutputPort
