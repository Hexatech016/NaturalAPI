/**
 * @file CheckThereAreDocOutputPort
 * @version 1.0.0
 * @type java
 * @data 2020-04-25
 * @author Luca Marcon
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.portInterface;

/**
 * CheckThereAreDoc output interface.
 */
public interface CheckThereAreDocOutputPort {

    /**
     * Notifies the flag from document search action.
     * @param flag boolean - true if backup was found, false if not.
     */
    void thereAreDoc(boolean flag);

}//CheckThereAreDocOutputPort
