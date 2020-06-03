/**
 * @file MyObserver
 * @version 1.0.0
 * @type java
 * @data 2020-04-30
 * @author Jacopo Battilana
 * @email hexatech016@gmail.com
 * @license MIT
 */

package com.hexaTech.wiew;

/**
 * Client interface
 */
public interface MyObserver {
    /**
     * Shows presenter message.
     */
    void notifyMe();

    /**
     * Notifies presenter error code.
     * @return integer - error code.
     */
    int notifyMeError();

    /**
     * Notifies presenter status.
     * @return boolean - status.
     */
    boolean notifyMeDone();

}//MyObserver
