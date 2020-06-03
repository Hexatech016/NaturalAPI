/**
 * @file AddBALOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-14
 * @author Alessio Barbiero
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.usecases.develop;

/**
 * AddBAL output port.
 */
public interface AddBALOutputPort {

    /**
     * Notifies the message from adding document.
     * @param result string - message text.
     */
    void showAddedBAL(String result);

    /**
     * Notifies the message from removing document.
     * @param result string - message text.
     */
    void showRemovedBAL(String result);

    /**
     * Notifies the status after the performed action.
     * @param b boolean - status; false if an error occurred, true if not.
     */
    void showDone(boolean b);

    /**
     * Notifies the message from restoring backup.
     * @param result string - message text.
     */
    void showRestoredBackUp(String result);

}//AddBALOutputPort
