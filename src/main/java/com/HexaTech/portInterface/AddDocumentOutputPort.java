/**
 * @file AddDocumentOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.HexaTech.portInterface;

/**
 * AddDocument output interface.
 */
public interface AddDocumentOutputPort {

    /**
     * Notifies the message from adding document action.
     * @param result string - message text.
     */
    void showAddedDocument(String result);

    /**
     * Notifies the message from restoring backup action.
     * @param result string - message text.
     */
    void showRestoredBackUp(String result);

    /**
     * Notifies the status after the performed action.
     * @param b boolean - status; false if an error occurred, true if not.
     */
    void showDone(boolean b);

}//AddDocumentOutputPort
