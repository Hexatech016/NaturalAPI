/**
 * @file AddBDLOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Matteo Brosolo
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.domain.port.out.usecase;

/**
 * AddBDL output interface.
 */
public interface AddBDLOutputPort {

    /**
     * Notifies the message from BDL addiction.
     * @param result string - message text.
     */
    void showAddedBDL(String result);

    void showDone(boolean done);

}//AddBDLOutputPort
