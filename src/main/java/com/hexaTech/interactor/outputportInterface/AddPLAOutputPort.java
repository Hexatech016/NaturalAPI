/**
 * @file AddPLAOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.outputportInterface;

/**
 * AddPLA output interface.
 */
public interface AddPLAOutputPort {

    /**
     * Notifies the message from adding document.
     * @param result string - message text.
     */
    void showAddedPLA(String result);

    /**
     * Notifies the message from removing document.
     * @param result string - message text.
     */
    void showRemovedPLA(String result);

    /**
     * Notifies the status after the performed action.
     * @param b boolean - status; false if an error occurred, true if not.
     */
    void showDone(boolean b);

}//AddDocumentOutputPort
