/**
 * @file AddBOOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-05-19
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.interactor.portInterface;

/**
 * AddBO output interface.
 */
public interface AddBOOutputPort {

    /**
     * Notifies the message from BO addiction.
     * @param result string - message text.
     */
    void showAddedBO(String result);

    /**
     * Notifies the status after the performed action.
     * @param b boolean - status; false if an error occurred, true if not.
     */
    void showDone(boolean b);

}//AddBOOutputPort
